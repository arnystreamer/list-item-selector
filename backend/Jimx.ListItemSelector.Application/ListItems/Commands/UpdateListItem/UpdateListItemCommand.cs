using Jimx.Common.Models;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.UpdateListItem;

public record UpdateListItemCommand(int Id, string Name, string? Description) : IRequest<Result>;