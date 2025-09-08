namespace Jimx.ListItemSelector.Api.Contracts.ListItems;

public record ListItemGetAllResponse(IReadOnlyCollection<ListItemApi> Items);