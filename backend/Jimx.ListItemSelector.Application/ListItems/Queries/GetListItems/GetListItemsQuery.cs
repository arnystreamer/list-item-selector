using Jimx.ListItemSelector.Application.ListItems.Dto;
using Jimx.ListItemSelector.Domain.Entities;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Queries.GetListItems;

public record GetListItemsQuery(int? CategoryId, string? NameContains, string? DescriptionContains) : IRequest<IReadOnlyCollection<ListItemDto>>;