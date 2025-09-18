using Jimx.Common.Models;
using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Application.ListItems.Dto;
using Jimx.ListItemSelector.Application.Mapping;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.CreateListItem;

public class CreateListItemHandler : IRequestHandler<CreateListItemCommand, Result<ListItemDto>>
{
    private readonly IListItemsRepository _repository;

    public CreateListItemHandler(IListItemsRepository repository)
    {
        _repository = repository;
    }

    public async Task<Result<ListItemDto>> Handle(CreateListItemCommand request, CancellationToken cancellationToken)
    {
        var domainItem = request.ToDomain();
        var listItem = await _repository.AddAsync(domainItem, cancellationToken);
        return Result<ListItemDto>.Ok(listItem.ToDto());
    }
}