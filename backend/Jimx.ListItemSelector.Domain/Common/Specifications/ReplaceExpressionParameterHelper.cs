using System.Linq.Expressions;

namespace Jimx.ListItemSelector.Domain.Common.Specifications;

public static class ReplaceExpressionParameterHelper
{
    public static ReplaceParameterResult ReplaceParameter<TOld, TNew>(this Expression<Func<TOld, bool>> expression)
    {
        var oldParameter = expression.Parameters.First();
        var newParameter = Expression.Parameter(typeof(TNew), oldParameter.Name);
        
        var parameterUpdateVisitor = new ParameterUpdateVisitor<TOld, TNew>(oldParameter, newParameter);
        var newExpression = parameterUpdateVisitor.Visit(expression.Body);
        
        return new ReplaceParameterResult(newExpression, newParameter);
    }
    
    public class ReplaceParameterResult
    {
        public Expression Expression { get; }
        public ParameterExpression Parameter { get; }

        public ReplaceParameterResult(Expression expression, ParameterExpression parameter)
        {
            Expression = expression;
            Parameter = parameter;
        }
    }
    
    private class ParameterUpdateVisitor<TOld, TNew> : ExpressionVisitor
    {
        private readonly ParameterExpression _oldParameter;
        private readonly ParameterExpression _newParameter;
        
        public ParameterUpdateVisitor(ParameterExpression oldParameter, ParameterExpression newParameter)
        {
            _oldParameter = oldParameter;
            _newParameter = newParameter;
        }

        protected override Expression VisitMethodCall(MethodCallExpression node)
        {
            var newMemberExpression = CheckIfMemberAndReplace(node.Object);
            if (newMemberExpression is not null)
            {
                return Expression.Call(newMemberExpression, node.Method, node.Arguments);
            }
            
            return base.VisitMethodCall(node);
        }
        
        protected override Expression VisitBinary(BinaryExpression node)
        {
            var newMemberLeftExpression = CheckIfMemberAndReplace(node.Left);
            var newMemberRightExpression = CheckIfMemberAndReplace(node.Right);

            if (newMemberLeftExpression is not null || newMemberRightExpression is not null)
            {
                return Expression.MakeBinary(
                    node.NodeType,
                    newMemberLeftExpression ?? node.Left,
                    newMemberRightExpression ?? node.Right);
            }

            return base.VisitBinary(node);
        }

        protected override Expression VisitParameter(ParameterExpression node)
        {
            if (object.ReferenceEquals(node, _oldParameter))
                return _newParameter;
            
            return base.VisitParameter(node);
        }
        
        private MemberExpression? CheckIfMemberAndReplace(Expression? expression)
        {
            if (expression != null
                && expression is MemberExpression memberExpression
                && memberExpression.Expression is ParameterExpression parameterExpression
                && parameterExpression.Type == typeof(TOld))
            {
                return Expression.PropertyOrField(_newParameter, memberExpression.Member.Name);
            }

            return null;
        }
    }
}