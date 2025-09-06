using Jimx.Common.Models;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.DeleteListCategory;

public record DeleteListCategoryCommand(int Id) : IRequest<Result>;