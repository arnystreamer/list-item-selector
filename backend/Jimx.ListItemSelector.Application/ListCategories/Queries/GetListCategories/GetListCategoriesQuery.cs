using Jimx.ListItemSelector.Domain.Entities;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Queries.GetListCategories;

public record GetListCategoriesQuery(string? NameContains)  : IRequest<List<ListCategory>>;