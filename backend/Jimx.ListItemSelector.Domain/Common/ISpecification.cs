using System.Linq.Expressions;

namespace Jimx.ListItemSelector.Domain.Common;

public interface ISpecification<T>
{
    Expression<Func<T, bool>> Criteria { get; }
}