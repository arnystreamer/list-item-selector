using Jimx.Common.Models;
using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Domain.Entities;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.UpdateListItem;

public class UpdateListItemHandler : IRequestHandler<UpdateListItemCommand, Result>
{
    private readonly IListItemsRepository _repository;

    public UpdateListItemHandler(IListItemsRepository repository)
    {
        _repository = repository;
    }

    public async Task<Result> Handle(UpdateListItemCommand request, CancellationToken cancellationToken)
    {
        var entity = new ListItem(request.Id, 0, request.Name, request.Description);
        var result = await _repository.UpdateAsync(entity, cancellationToken);
        if (!result)
        {
            return Result.Fail($"Updating list item with id {request.Id} was unsuccessful");
        }
        
        return Result.Ok();
    }
}