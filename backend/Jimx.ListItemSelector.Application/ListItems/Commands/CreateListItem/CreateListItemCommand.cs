using Jimx.Common.Models;
using Jimx.Common.WebApi.Models;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.CreateListItem;

public record CreateListItemCommand(int CategoryId, string Name, string? Description)  : IRequest<Result<IdObject>>;