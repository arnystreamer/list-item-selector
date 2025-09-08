using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Application.ListCategories.Dto;
using Jimx.ListItemSelector.Application.ListCategories.Specifications;
using Jimx.ListItemSelector.Application.Mapping;
using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Entities;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Queries.GetListCategories;

public class GetListCategoriesHandler : IRequestHandler<GetListCategoriesQuery, IReadOnlyCollection<ListCategoryDto>>
{
    private readonly IListCategoriesRepository _repository;

    public GetListCategoriesHandler(IListCategoriesRepository repository)
    {
        _repository = repository;
    }

    public async Task<IReadOnlyCollection<ListCategoryDto>> Handle(GetListCategoriesQuery request, CancellationToken cancellationToken)
    {
        ISpecification<ListCategory>? spec = null;

        if (!string.IsNullOrEmpty(request.NameContains))
        {
            spec = new ListCategoryByNameContainsSpec(request.NameContains);
        }

        IReadOnlyCollection<ListCategory> domainItems;
        if (spec is not null)
        {
            domainItems = await _repository.GetAsync(spec, cancellationToken);
        }
        domainItems = await _repository.GetAllAsync(cancellationToken);

        return domainItems.Select(i => i.ToDto()).ToList();
    }
}