using Jimx.ListItemSelector.Application.Common.Models;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.UpdateListCategory;

public record UpdateListCategoryCommand(int Id, string Name) : IRequest<Result>;