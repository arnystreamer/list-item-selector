using Jimx.Common.Models;
using Jimx.ListItemSelector.Application.ListCategories.Dto;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.UpdateListCategory;

public record UpdateListCategoryCommand(int Id, string Name) : IRequest<Result<ListCategoryDto>>;