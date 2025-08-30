using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.CreateListCategory;

public record CreateListCategoryCommand(string Name) : IRequest<int>;