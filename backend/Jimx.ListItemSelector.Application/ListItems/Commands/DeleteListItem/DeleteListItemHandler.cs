using Jimx.Common.Models;
using Jimx.ListItemSelector.Application.Common.Interfaces;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.DeleteListItem;

public class DeleteListItemHandler : IRequestHandler<DeleteListItemCommand, Result>
{
    private readonly IListItemsRepository _repository;

    public DeleteListItemHandler(IListItemsRepository repository)
    {
        _repository = repository;
    }

    public async Task<Result> Handle(DeleteListItemCommand request, CancellationToken cancellationToken)
    {
        var result = await _repository.DeleteAsync(request.Id, cancellationToken);
        if (!result)
        {
            return Result.Fail([$"List item with id {request.Id} not found"]);
        }
        
        return Result.Ok();
    }
}