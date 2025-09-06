using Jimx.ListItemSelector.Application.ListItems.Dto;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Queries.GetListItemById;

public record GetListItemByIdQuery(int Id) : IRequest<ListItemDto?>;