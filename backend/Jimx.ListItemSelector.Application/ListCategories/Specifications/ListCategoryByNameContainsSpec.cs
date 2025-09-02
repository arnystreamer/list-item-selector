using System.Linq.Expressions;
using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Entities;

namespace Jimx.ListItemSelector.Application.ListCategories.Specifications;

public class ListCategoryByNameContainsSpec : ISpecification<ListCategory>
{
    private readonly string _name;

    public ListCategoryByNameContainsSpec(string name)
    {
        _name = name;
    }

    public Expression<Func<ListCategory, bool>> Criteria => c => c.Name.Contains(_name);
}