namespace Jimx.ListItemSelector.Api.Contracts.ListCategories;

public class ListCategoryUpdateRequest
{
    public required int Id { get; init; }
    public required string Name { get; init; }
}