using Jimx.ListItemSelector.Api.Contracts.ListItems;
using Jimx.ListItemSelector.Application.ListItems.Dto;

namespace Jimx.ListItemSelector.Api.Mapping;

public static class ListItemApiMapper
{
    public static ListItemApi ToApi(this ListItemDto dto) => new(dto.Id, dto.CategoryId, dto.Name, dto.Description);

}