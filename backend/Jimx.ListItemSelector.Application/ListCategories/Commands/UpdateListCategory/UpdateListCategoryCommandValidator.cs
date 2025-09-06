using FluentValidation;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.UpdateListCategory;

public class UpdateListCategoryCommandValidator : AbstractValidator<UpdateListCategoryCommand>
{
    public UpdateListCategoryCommandValidator()
    {
        RuleFor(x => x.Id)
            .GreaterThan(0).WithMessage("Id must be greater than zero.");
        
        RuleFor(x => x.Name)
            .NotEmpty().WithMessage("Name cannot be empty");
        
        RuleFor(x => x.Name)
            .MaximumLength(128).WithMessage("Name cannot be more than 128 characters.");
    }
}