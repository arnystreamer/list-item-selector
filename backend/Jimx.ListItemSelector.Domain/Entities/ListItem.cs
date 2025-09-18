namespace Jimx.ListItemSelector.Domain.Entities;

public class ListItem
{
    public int Id { get; set; }
    public int CategoryId { get; set; }
    public string Name { get; set; }
    public string? Description { get; set; }
    public bool IsExcluded { get; set; }

    public ListItem(int id, int categoryId, string name, string? description, bool isExcluded)
    {
        Id = id;
        CategoryId = categoryId;
        Name = name;
        Description = description;
        IsExcluded = isExcluded;
    }
}