using System.Linq.Expressions;

namespace Jimx.ListItemSelector.Domain.Common.Specifications;

public class NotSpecification<T> : ISpecification<T>
{
    private readonly ISpecification<T> _inner;

    public NotSpecification(ISpecification<T> inner)
    {
        _inner = inner;
    }

    public Expression<Func<T, bool>> Criteria => Expression.Lambda<Func<T, bool>>(
        Expression.Not(
            Expression.Invoke(_inner.Criteria, Expression.Parameter(typeof(T)))));
}