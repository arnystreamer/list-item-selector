namespace Jimx.ListItemSelector.Domain.Entities;

public class ListCategory
{
    public int Id { get; set; }
    public string Name { get; set; }

    public ListCategory(int id, string name)
    {
        Id = id;
        Name = name;
    }
}