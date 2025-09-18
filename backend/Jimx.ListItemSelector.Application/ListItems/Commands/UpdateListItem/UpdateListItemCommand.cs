using Jimx.Common.Models;
using Jimx.ListItemSelector.Application.ListItems.Dto;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.UpdateListItem;

public record UpdateListItemCommand(int Id, string Name, string? Description, bool IsExcluded) : IRequest<Result<ListItemDto>>;