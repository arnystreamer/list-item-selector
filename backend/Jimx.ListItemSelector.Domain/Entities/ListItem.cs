namespace Jimx.ListItemSelector.Domain.Entities;

public class ListItem
{
    public int Id { get; set; }
    public required string Name { get; set; }
    public string? Description { get; set; }
}