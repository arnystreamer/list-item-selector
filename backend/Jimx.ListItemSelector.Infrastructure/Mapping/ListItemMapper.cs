using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Common.Specifications;
using Jimx.ListItemSelector.Domain.Entities;
using Jimx.ListItemSelector.Infrastructure.Persistence.Models;

namespace Jimx.ListItemSelector.Infrastructure.Mapping;

public static class ListItemMapper
{
    public static ListItem ToDomain(this ListItemEntity entity) => 
        new ListItem(entity.Id, entity.CategoryId, entity.Name, entity.Description);

    public static ListItemEntity ToEntity(this ListItem domain) =>
        new ListItemEntity
        {
            Id = domain.Id,
            CategoryId = domain.CategoryId,
            Name = domain.Name,
            Description = domain.Description
        };

    public static ISpecification<ListItemEntity> ToEntitySpecification(this ISpecification<ListItem> specification)
    {
        return new DomainToEntitySpecification<ListItem, ListItemEntity>(specification);
    }
}