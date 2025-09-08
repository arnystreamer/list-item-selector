using Jimx.Common.Models;
using Jimx.Common.WebApi.Models;
using Jimx.ListItemSelector.Application.Common.Interfaces;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.CreateListCategory;

public class CreateListCategoryHandler : IRequestHandler<CreateListCategoryCommand, Result<IdObject>>
{
    private readonly IListCategoriesRepository _repository;
    
    public CreateListCategoryHandler(IListCategoriesRepository repository)
    {
        _repository = repository;
    }

    public async Task<Result<IdObject>> Handle(CreateListCategoryCommand request, CancellationToken cancellationToken)
    {
        var domainItem = request.ToDomain();
        var id = await _repository.AddAsync(domainItem, cancellationToken);
        return Result<IdObject>.Ok(new IdObject(id));

    }
}