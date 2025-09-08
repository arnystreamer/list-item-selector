using Jimx.ListItemSelector.Domain.Entities;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.CreateListCategory;

public static class CreateListCategoryCommandMapper
{
    public static ListCategory ToDomain(this CreateListCategoryCommand command) =>
        new ListCategory(0, command.Name);
}