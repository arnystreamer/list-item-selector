using Jimx.Common.Models;
using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Application.ListItems.Dto;
using Jimx.ListItemSelector.Application.Mapping;
using Jimx.ListItemSelector.Domain.Entities;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.UpdateListItem;

public class UpdateListItemHandler : IRequestHandler<UpdateListItemCommand, Result<ListItemDto>>
{
    private readonly IListItemsRepository _repository;

    public UpdateListItemHandler(IListItemsRepository repository)
    {
        _repository = repository;
    }

    public async Task<Result<ListItemDto>> Handle(UpdateListItemCommand request, CancellationToken cancellationToken)
    {
        var entity = request.ToDomain(0);
        var listItem = await _repository.UpdateAsync(entity, cancellationToken);
        if (listItem == null)
        {
            return Result<ListItemDto>.Fail($"Updating list item with id {request.Id} was unsuccessful");
        }
        
        return Result<ListItemDto>.Ok(listItem.ToDto());
    }
}