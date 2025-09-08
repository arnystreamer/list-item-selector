using Jimx.ListItemSelector.Application.ListCategories.Dto;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Queries.GetListCategoryById;

public record GetListCategoryByIdQuery(int Id) : IRequest<ListCategoryDto?>;