using FluentValidation;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.CreateListCategory;

public class CreateListCategoryCommandValidator : AbstractValidator<CreateListCategoryCommand>
{
    public CreateListCategoryCommandValidator()
    {
        RuleFor(x => x.Name)
            .NotEmpty().WithMessage("Name cannot be empty")
            .MaximumLength(127).WithMessage("Name cannot be longer than 127 characters");;
        
    }
}