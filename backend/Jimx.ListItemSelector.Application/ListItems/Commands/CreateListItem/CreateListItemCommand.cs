using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.CreateListItem;

public record CreateListItemCommand(string Name, string? Description)  : IRequest<int>;