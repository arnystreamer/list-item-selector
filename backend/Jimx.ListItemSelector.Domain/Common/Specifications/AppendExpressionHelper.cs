using System.Linq.Expressions;

namespace Jimx.ListItemSelector.Domain.Common.Specifications;

public static class AppendExpressionHelper
{
    public static BinaryAppendResult BinaryAppend<T>(this Expression<Func<T, bool>> expression, Expression<Func<T, bool>> other, ExpressionType expressionType)
    {
        var parameter = expression.Parameters.First();
        var newOther = new ParameterUpdateVisitor(other.Parameters.First(), parameter).Visit(other.Body);

        return new BinaryAppendResult(
            Expression.MakeBinary(expressionType, expression.Body, newOther),
            parameter);
    }

    public class BinaryAppendResult
    {
        public BinaryExpression Expression { get; }
        public ParameterExpression Parameter { get; }

        public BinaryAppendResult(BinaryExpression expression, ParameterExpression parameter)
        {
            Expression = expression;
            Parameter = parameter;
        }
    }

    private class ParameterUpdateVisitor : ExpressionVisitor
    {
        private ParameterExpression _oldParameter;
        private Expression _newParameter;

        public ParameterUpdateVisitor(ParameterExpression oldParameter, Expression newParameter)
        {
            _oldParameter = oldParameter;
            _newParameter = newParameter;
        }

        protected override Expression VisitParameter(ParameterExpression node) => node == _oldParameter ? _newParameter : node;
    }
}