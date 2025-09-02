using System.Linq.Expressions;
using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Entities;

namespace Jimx.ListItemSelector.Application.ListItems.Specifications;

public class ListItemByDescriptionContainsSpec : ISpecification<ListItem>
{
    private readonly string _description;

    public ListItemByDescriptionContainsSpec(string description)
    {
        _description = description;
    }

    public Expression<Func<ListItem, bool>> Criteria => c => c.Description != null && c.Description.Contains(_description);
}