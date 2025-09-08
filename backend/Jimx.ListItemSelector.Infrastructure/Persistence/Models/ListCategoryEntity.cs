namespace Jimx.ListItemSelector.Infrastructure.Persistence.Models;

public class ListCategoryEntity
{
    public int Id { get; set; }
    public required string Name { get; set; }
    public DateTime CreatedAt { get; set; }
    public DateTime? UpdatedAt { get; set; }
    
    public ICollection<ListItemEntity> ListItems { get; } = new List<ListItemEntity>();
}