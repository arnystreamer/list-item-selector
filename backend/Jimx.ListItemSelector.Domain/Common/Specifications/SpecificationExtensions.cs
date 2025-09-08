namespace Jimx.ListItemSelector.Domain.Common.Specifications;

public static class SpecificationExtensions
{
    public static ISpecification<T> And<T>(this ISpecification<T> spec, ISpecification<T> other) 
        => new AndSpecification<T>(spec, other);
    public static ISpecification<T> Or<T>(this ISpecification<T> spec, ISpecification<T> other) 
        => new OrSpecification<T>(spec, other);
    public static ISpecification<T> Not<T>(this ISpecification<T> spec) 
        => new NotSpecification<T>(spec);
}