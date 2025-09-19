using Jimx.Common.Models;
using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Application.ListCategories.Dto;
using Jimx.ListItemSelector.Application.Mapping;
using Jimx.ListItemSelector.Domain.Entities;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.UpdateListCategory;

public class UpdateListCategoryHandler : IRequestHandler<UpdateListCategoryCommand, Result<ListCategoryDto>>
{
    private readonly IListCategoriesRepository _repository;

    public UpdateListCategoryHandler(IListCategoriesRepository repository)
    {
        _repository = repository;
    }

    public async Task<Result<ListCategoryDto>> Handle(UpdateListCategoryCommand request, CancellationToken cancellationToken)
    {
        var entity = request.ToDomain();
        var category = await _repository.UpdateAsync(entity, cancellationToken);
        if (category == null)
        {
            return Result<ListCategoryDto>.Fail($"Updating list category with id {request.Id} was unsuccessful");
        }
        
        return Result<ListCategoryDto>.Ok(category.ToDto());
    }
}