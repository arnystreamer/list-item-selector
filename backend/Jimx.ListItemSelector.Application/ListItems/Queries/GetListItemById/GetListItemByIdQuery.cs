using Jimx.ListItemSelector.Domain.Entities;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Queries.GetListItemById;

public record GetListItemByIdQuery(int Id) : IRequest<ListItem?>;