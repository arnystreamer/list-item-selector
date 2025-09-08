using FluentValidation;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.CreateListCategory;

public class CreateListCategoryCommandValidator : AbstractValidator<CreateListCategoryCommand>
{
    public CreateListCategoryCommandValidator()
    {
        RuleFor(x => x.Name)
            .NotEmpty().WithMessage("Name cannot be empty");
            
        RuleFor(x => x.Name)
            .MaximumLength(128).WithMessage("Name cannot be more than 128 characters.");
        
    }
}