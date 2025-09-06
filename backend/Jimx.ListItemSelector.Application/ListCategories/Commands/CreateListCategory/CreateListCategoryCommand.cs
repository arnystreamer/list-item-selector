using Jimx.Common.Models;
using Jimx.Common.WebApi.Models;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.CreateListCategory;

public record CreateListCategoryCommand(string Name) : IRequest<Result<IdObject>>;