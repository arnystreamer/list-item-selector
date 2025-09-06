using Jimx.Common.Models;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.DeleteListItem;

public record DeleteListItemCommand(int Id) : IRequest<Result>;