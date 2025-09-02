using Jimx.ListItemSelector.Domain.Entities;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Queries.GetListItems;

public record GetListItemsQuery(string? NameContains, string? DescriptionContains) : IRequest<List<ListItem>>;