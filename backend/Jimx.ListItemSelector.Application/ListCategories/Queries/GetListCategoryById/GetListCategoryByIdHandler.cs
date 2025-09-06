using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Application.ListCategories.Dto;
using Jimx.ListItemSelector.Application.Mapping;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Queries.GetListCategoryById;

public class GetListCategoryByIdHandler : IRequestHandler<GetListCategoryByIdQuery, ListCategoryDto?>
{
    private readonly IListCategoriesRepository _repository;

    public GetListCategoryByIdHandler(IListCategoriesRepository repository)
    {
        _repository = repository;
    }

    public async Task<ListCategoryDto?> Handle(GetListCategoryByIdQuery request, CancellationToken cancellationToken)
    {
        var domainItem = await _repository.GetByIdAsync(request.Id, cancellationToken);
        return domainItem?.ToDto();
    }
}