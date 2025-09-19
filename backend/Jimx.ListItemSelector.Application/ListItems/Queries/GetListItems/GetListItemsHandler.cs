using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Application.ListItems.Dto;
using Jimx.ListItemSelector.Application.ListItems.Specifications;
using Jimx.ListItemSelector.Application.Mapping;
using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Common.Specifications;
using Jimx.ListItemSelector.Domain.Entities;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Queries.GetListItems;

public class GetListItemsHandler : IRequestHandler<GetListItemsQuery, IReadOnlyCollection<ListItemDto>>
{
    private readonly IListItemsRepository _repository;

    public GetListItemsHandler(IListItemsRepository repository)
    {
        _repository = repository;
    }

    public async Task<IReadOnlyCollection<ListItemDto>> Handle(GetListItemsQuery request, CancellationToken cancellationToken)
    {
        List<ISpecification<ListItem>> specs = [];
        
        if (request.CategoryId.HasValue)
        {
            specs.Add(new ListItemByCategoryIdSpec(request.CategoryId.Value));
        }

        if (!string.IsNullOrEmpty(request.NameContains))
        {
            specs.Add(new ListItemByNameContainsSpec(request.NameContains));
        }

        if (!string.IsNullOrEmpty(request.DescriptionContains))
        {
            specs.Add(new ListItemByDescriptionContainsSpec(request.DescriptionContains));
        }
        
        if (request.IsExcluded.HasValue)
        {
            specs.Add(new ListItemByIsExcludedSpec(request.IsExcluded.Value));
        }

        IReadOnlyCollection<ListItem> domainItems;
        if (specs.Any())
        {   
            domainItems = await _repository.GetAsync(
                specs.Aggregate((a, b) => a.And(b)), 
                cancellationToken);
        }
        else
        {
            domainItems = await _repository.GetAllAsync(cancellationToken);
        }

        return domainItems.Select(i => i.ToDto()).ToList();
    }
}