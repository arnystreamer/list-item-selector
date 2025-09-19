namespace Jimx.ListItemSelector.Infrastructure.Persistence.Models;

public class ListItemEntity
{
    public int Id { get; set; }
    public int CategoryId { get; init; }
    public required string Name { get; set; }
    public string? Description { get; set; }
    public bool IsExcluded { get; set; }
    public DateTime CreatedAt { get; set; }
    public DateTime? UpdatedAt { get; set; }

    public ListCategoryEntity ListCategory { get; set; } = null!;
}