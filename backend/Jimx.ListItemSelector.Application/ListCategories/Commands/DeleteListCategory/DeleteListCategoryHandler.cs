using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Application.Common.Models;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.DeleteListCategory;

public class DeleteListCategoryHandler : IRequestHandler<DeleteListCategoryCommand, Result>
{
    private readonly IListCategoriesRepository _repository;

    public DeleteListCategoryHandler(IListCategoriesRepository repository)
    {
        _repository = repository;
    }

    public async Task<Result> Handle(DeleteListCategoryCommand request, CancellationToken cancellationToken)
    {
        var entity = await _repository.GetByIdAsync(request.Id, cancellationToken);
        if (entity == null)
        {
            return Result.Failure([$"List category with id {request.Id} not found"]);
        }

        await _repository.DeleteAsync(entity, cancellationToken);
        
        return Result.Success();
    }
}