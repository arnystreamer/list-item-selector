namespace Jimx.ListItemSelector.Api.Contracts.ListItems;

public class ListItemUpdateRequest
{
    public required int Id { get; init; }
    public required string Name { get; init; }
    public string? Description { get; init; }
}