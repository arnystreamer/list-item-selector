using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Application.Common.Models;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.CreateListCategory;

public class CreateListCategoryHandler : IRequestHandler<CreateListCategoryCommand, Result<int>>
{
    private readonly IListCategoriesRepository _repository;
    
    public CreateListCategoryHandler(IListCategoriesRepository repository)
    {
        _repository = repository;
    }

    public async Task<Result<int>> Handle(CreateListCategoryCommand request, CancellationToken cancellationToken)
    {
        var id = await _repository.AddAsync(request.Name, cancellationToken);
        return Result<int>.Success(id);

    }
}