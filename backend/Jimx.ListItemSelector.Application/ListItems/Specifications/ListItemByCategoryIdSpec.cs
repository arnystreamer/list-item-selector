using System.Linq.Expressions;
using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Entities;

namespace Jimx.ListItemSelector.Application.ListItems.Specifications;

public class ListItemByCategoryIdSpec : ISpecification<ListItem>
{
    private readonly int _categoryId;

    public ListItemByCategoryIdSpec(int categoryId)
    {
        _categoryId = categoryId;
    }

    public Expression<Func<ListItem, bool>> Criteria => i => i.CategoryId == _categoryId;
}