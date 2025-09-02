using Jimx.ListItemSelector.Application.Common.Models;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.CreateListCategory;

public record CreateListCategoryCommand(string Name) : IRequest<Result<int>>;