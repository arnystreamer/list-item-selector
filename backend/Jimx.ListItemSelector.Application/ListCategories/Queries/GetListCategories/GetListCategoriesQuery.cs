using Jimx.ListItemSelector.Application.ListCategories.Dto;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Queries.GetListCategories;

public record GetListCategoriesQuery(string? NameContains)  : IRequest<IReadOnlyCollection<ListCategoryDto>>;