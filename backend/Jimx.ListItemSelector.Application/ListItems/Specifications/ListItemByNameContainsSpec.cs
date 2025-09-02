using System.Linq.Expressions;
using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Entities;

namespace Jimx.ListItemSelector.Application.ListItems.Specifications;

public class ListItemByNameContainsSpec : ISpecification<ListItem>
{
    private readonly string _name;

    public ListItemByNameContainsSpec(string name)
    {
        _name = name;
    }

    public Expression<Func<ListItem, bool>> Criteria => i => i.Name.Contains(_name);
}