using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Application.Common.Models;
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
        var entity = await _repository.GetByIdAsync(request.Id, cancellationToken);
        if (entity == null)
        {
            return Result.Failure([$"List item with id {request.Id} not found"]);
        }
        
        entity.Name = request.Name;
        entity.Description = request.Description;
        
        await _repository.UpdateAsync(entity, cancellationToken);
        
        return Result.Success();
    }
}