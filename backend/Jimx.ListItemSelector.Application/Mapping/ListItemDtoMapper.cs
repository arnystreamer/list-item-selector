using Jimx.ListItemSelector.Application.ListItems.Dto;
using Jimx.ListItemSelector.Domain.Entities;

namespace Jimx.ListItemSelector.Application.Mapping;

public static class ListItemDtoMapper
{
    public static ListItemDto ToDto(this ListItem domain) => new (domain.Id, domain.CategoryId, domain.Name, domain.Description, domain.IsExcluded);
}