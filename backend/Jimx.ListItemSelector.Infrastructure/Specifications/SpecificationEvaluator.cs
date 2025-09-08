using Jimx.ListItemSelector.Domain.Common;

namespace Jimx.ListItemSelector.Infrastructure.Specifications;

public class SpecificationEvaluator
{
    public static IQueryable<T> GetQuery<T>(IQueryable<T> inputQuery, ISpecification<T> spec) where T : class
    {
        return inputQuery.Where(spec.Criteria);
    }
}