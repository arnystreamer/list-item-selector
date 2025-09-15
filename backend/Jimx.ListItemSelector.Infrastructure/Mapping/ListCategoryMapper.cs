using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Common.Specifications;
using Jimx.ListItemSelector.Domain.Entities;
using Jimx.ListItemSelector.Infrastructure.Persistence.Models;

namespace Jimx.ListItemSelector.Infrastructure.Mapping;

public static class ListCategoryMapper
{
    public static ListCategory ToDomain(this ListCategoryEntity entity) =>
        new ListCategory(entity.Id, entity.Name);
    
    public static ListCategoryEntity ToEntity(this ListCategory domain) =>
        new ListCategoryEntity()
        {
            Id = domain.Id,
            Name = domain.Name
        };

    public static ISpecification<ListCategoryEntity> ToEntitySpecification(this ISpecification<ListCategory> specification)
    {
        return new DomainToEntitySpecification<ListCategory, ListCategoryEntity>(specification);
    }
}