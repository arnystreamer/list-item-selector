using Jimx.Common.Models;
using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Domain.Entities;
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
        var entity = new ListCategory(request.Id, request.Name);
        var result = await _repository.UpdateAsync(entity, cancellationToken);
        if (!result)
        {
            return Result.Fail($"Updating list category with id {request.Id} was unsuccessful");
        }
        
        return Result.Ok();
    }
}