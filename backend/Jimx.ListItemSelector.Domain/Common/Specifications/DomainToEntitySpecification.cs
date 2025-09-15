using System.Linq.Expressions;

namespace Jimx.ListItemSelector.Domain.Common.Specifications;

public class DomainToEntitySpecification<TDomain, TEntity> : ISpecification<TEntity> 
{
public DomainToEntitySpecification(ISpecification<TDomain> domainSpecification)
    {
        var domainCriteria = domainSpecification.Criteria;

        var replaceResult = domainCriteria.ReplaceParameter<TDomain, TEntity>();
        var newLambda = Expression.Lambda<Func<TEntity, bool>>(replaceResult.Expression, replaceResult.Parameter);
        Criteria = newLambda;
    }
    
    public Expression<Func<TEntity, bool>> Criteria { get; }
    
    
}