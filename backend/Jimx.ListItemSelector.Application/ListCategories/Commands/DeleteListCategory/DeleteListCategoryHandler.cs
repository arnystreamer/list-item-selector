using Jimx.Common.Models;
using Jimx.ListItemSelector.Application.Common.Interfaces;
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
        var result = await _repository.DeleteAsync(request.Id, cancellationToken);
        if (!result)
        {
            return Result.Fail($"List category with id {request.Id} not found");
        }
        
        return Result.Ok();
    }
}