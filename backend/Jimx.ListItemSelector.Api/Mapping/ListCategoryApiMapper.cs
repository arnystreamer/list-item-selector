using Jimx.ListItemSelector.Api.Contracts.ListCategories;
using Jimx.ListItemSelector.Application.ListCategories.Dto;

namespace Jimx.ListItemSelector.Api.Mapping;

public static class ListCategoryApiMapper
{
    public static ListCategoryApi ToApi(this ListCategoryDto dto) => new(dto.Id, dto.Name);
}