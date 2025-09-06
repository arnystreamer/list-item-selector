namespace Jimx.ListItemSelector.Api.Contracts.ListItems;

public class ListItemCreateRequest
{
    public required int CategoryId { get; init; } 
    public required string Name { get; init; } 
    public string? Description { get; init; }
}