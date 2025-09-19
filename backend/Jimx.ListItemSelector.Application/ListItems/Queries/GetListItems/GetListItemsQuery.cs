using Jimx.ListItemSelector.Application.ListItems.Dto;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Queries.GetListItems;

public record GetListItemsQuery(int? CategoryId, string? NameContains, string? DescriptionContains, bool? IsExcluded) : IRequest<IReadOnlyCollection<ListItemDto>>;