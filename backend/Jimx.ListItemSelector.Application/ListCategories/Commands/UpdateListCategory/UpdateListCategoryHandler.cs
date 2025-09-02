using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Application.Common.Models;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.UpdateListCategory;

public class UpdateListCategoryHandler : IRequestHandler<UpdateListCategoryCommand, Result>
{
    private readonly IListCategoriesRepository _repository;

    public UpdateListCategoryHandler(IListCategoriesRepository repository)
    {
        _repository = repository;
    }

    public async Task<Result> Handle(UpdateListCategoryCommand request, CancellationToken cancellationToken)
    {
        var entity = await _repository.GetByIdAsync(request.Id, cancellationToken);
        if (entity == null)
        {
            return Result.Failure([$"List category with id {request.Id} not found"]);
        }
        
        entity.Name = request.Name;

        await _repository.UpdateAsync(entity, cancellationToken);
        
        return Result.Success();
    }
}