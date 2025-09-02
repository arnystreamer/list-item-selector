using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Application.Common.Models;
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
        var entity = await _repository.GetByIdAsync(request.Id, cancellationToken);
        if (entity == null)
        {
            return Result.Failure([$"List item with id {request.Id} not found"]);
        }
        
        await _repository.DeleteAsync(entity, cancellationToken);
        
        return Result.Success();
    }
}