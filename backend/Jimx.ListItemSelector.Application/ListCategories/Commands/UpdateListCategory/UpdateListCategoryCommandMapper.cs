using Jimx.ListItemSelector.Domain.Entities;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.UpdateListCategory;

public static class UpdateListCategoryCommandMapper
{
    public static ListCategory ToDomain(this UpdateListCategoryCommand command) =>
        new ListCategory(command.Id, command.Name);
}