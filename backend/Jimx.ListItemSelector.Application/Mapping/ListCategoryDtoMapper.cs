using Jimx.ListItemSelector.Application.ListCategories.Dto;
using Jimx.ListItemSelector.Domain.Entities;

namespace Jimx.ListItemSelector.Application.Mapping;

public static class ListCategoryDtoMapper
{
    public static ListCategoryDto ToDto(this ListCategory domain) => new(domain.Id, domain.Name);
}