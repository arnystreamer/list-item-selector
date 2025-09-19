using Jimx.Common.Models;
using Jimx.Common.WebApi.Models;
using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Application.ListCategories.Dto;
using Jimx.ListItemSelector.Application.Mapping;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.CreateListCategory;

public class CreateListCategoryHandler : IRequestHandler<CreateListCategoryCommand, Result<ListCategoryDto>>
{
    private readonly IListCategoriesRepository _repository;
    
    public CreateListCategoryHandler(IListCategoriesRepository repository)
    {
        _repository = repository;
    }

    public async Task<Result<ListCategoryDto>> Handle(CreateListCategoryCommand request, CancellationToken cancellationToken)
    {
        var domainItem = request.ToDomain();
        var category = await _repository.AddAsync(domainItem, cancellationToken);
        return Result<ListCategoryDto>.Ok(category.ToDto());

    }
}