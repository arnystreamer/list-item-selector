using System.Linq.Expressions;

namespace Jimx.ListItemSelector.Domain.Common.Specifications;

public class AndSpecification<T> : ISpecification<T>
{
    private readonly ISpecification<T> _left;
    private readonly ISpecification<T> _right;

    public AndSpecification(ISpecification<T> left, ISpecification<T> right)
    {
        _left = left;
        _right = right;
    }

    public Expression<Func<T, bool>> Criteria {
        get
        {
            var appendResult = _left.Criteria.BinaryAppend(_right.Criteria, ExpressionType.AndAlso);
            return Expression.Lambda<Func<T, bool>>(appendResult.Expression, appendResult.Parameter);
        }
    }

}