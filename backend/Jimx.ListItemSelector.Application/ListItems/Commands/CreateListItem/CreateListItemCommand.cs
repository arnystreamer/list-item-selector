using Jimx.Common.Models;
using Jimx.Common.WebApi.Models;
using Jimx.ListItemSelector.Application.ListItems.Dto;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.CreateListItem;

public record CreateListItemCommand(int CategoryId, string Name, string? Description, bool IsExcluded)  : IRequest<Result<ListItemDto>>;