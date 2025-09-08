using System.Linq.Expressions;

namespace Jimx.ListItemSelector.Domain.Common.Specifications;

public class OrSpecification<T> : ISpecification<T>
{
    private readonly ISpecification<T> _left;
    private readonly ISpecification<T> _right;

    public OrSpecification(ISpecification<T> left, ISpecification<T> right)
    {
        _left = left;
        _right = right;
    }

    public Expression<Func<T, bool>> Criteria => Expression.Lambda<Func<T, bool>>(
        Expression.OrElse(
            Expression.Invoke(_left.Criteria, Expression.Parameter(typeof(T))),
            Expression.Invoke(_right.Criteria, Expression.Parameter(typeof(T)))));
}