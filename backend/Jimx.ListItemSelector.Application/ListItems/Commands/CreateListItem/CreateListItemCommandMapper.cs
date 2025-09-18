using Jimx.ListItemSelector.Domain.Entities;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.CreateListItem;

public static class CreateListItemCommandMapper
{
    public static ListItem ToDomain(this CreateListItemCommand request) => 
        new(0, request.CategoryId, request.Name, request.Description, request.IsExcluded); 
    
}