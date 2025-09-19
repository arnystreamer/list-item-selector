using Jimx.ListItemSelector.Domain.Entities;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.UpdateListItem;

public static class UpdateListItemCommandMapper
{
    public static ListItem ToDomain(this UpdateListItemCommand request, int categoryId) => 
        new(request.Id, categoryId, request.Name, request.Description, request.IsExcluded); 
}