using FluentValidation;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.DeleteListCategory;

public class DeleteListCategoryCommandValidator : AbstractValidator<DeleteListCategoryCommand>
{
    public DeleteListCategoryCommandValidator()
    {
        RuleFor(x => x.Id)
            .GreaterThan(0).WithMessage("Id must be greater than zero.");
    }
}