using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Application.ListItems.Specifications;
using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Common.Specifications;
using Jimx.ListItemSelector.Domain.Entities;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Queries.GetListItems;

public class GetListItemsHandler : IRequestHandler<GetListItemsQuery, List<ListItem>>
{
    private readonly IListItemsRepository _repository;

    public GetListItemsHandler(IListItemsRepository repository)
    {
        _repository = repository;
    }

    public async Task<List<ListItem>> Handle(GetListItemsQuery request, CancellationToken cancellationToken)
    {
        ISpecification<ListItem>? spec = null;

        if (!string.IsNullOrEmpty(request.NameContains))
        {
            spec = new ListItemByNameContainsSpec(request.NameContains);
        }

        if (!string.IsNullOrEmpty(request.DescriptionContains))
        {
            var newSpec = new ListItemByDescriptionContainsSpec(request.DescriptionContains);

            spec = spec is null ? newSpec : spec.And(newSpec);
        }

        if (spec is not null)
        {   
            return await _repository.GetAsync(spec, cancellationToken);
        }
        
        return await _repository.GetAllAsync(cancellationToken);
    }
}