using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Application.ListCategories.Specifications;
using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Entities;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Queries.GetListCategories;

public class GetListCategoriesHandler : IRequestHandler<GetListCategoriesQuery, List<ListCategory>>
{
    private readonly IListCategoriesRepository _repository;

    public GetListCategoriesHandler(IListCategoriesRepository repository)
    {
        _repository = repository;
    }

    public async Task<List<ListCategory>> Handle(GetListCategoriesQuery request, CancellationToken cancellationToken)
    {
        ISpecification<ListCategory>? spec = null;

        if (!string.IsNullOrEmpty(request.NameContains))
        {
            spec = new ListCategoryByNameContainsSpec(request.NameContains);
        }

        if (spec is not null)
        {
            return await _repository.GetAsync(spec, cancellationToken);
        }
        
        return await _repository.GetAllAsync(cancellationToken);
    }
}