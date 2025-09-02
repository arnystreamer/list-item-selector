using Jimx.ListItemSelector.Domain.Entities;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Queries.GetListCategoryById;

public record GetListCategoryByIdQuery(int Id) : IRequest<ListCategory?>;