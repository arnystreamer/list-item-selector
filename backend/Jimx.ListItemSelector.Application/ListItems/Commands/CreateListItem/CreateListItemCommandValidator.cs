using FluentValidation;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.CreateListItem;

public class CreateListItemCommandValidator : AbstractValidator<CreateListItemCommand>
{
    public CreateListItemCommandValidator()
    {
        RuleFor(x => x.CategoryId)
            .GreaterThan(0).WithMessage("CategoryId must be greater than zero.");
        
        RuleFor(x => x.Name)
            .NotEmpty().WithMessage("Name cannot be empty");
        
        RuleFor(x => x.Name)
            .MaximumLength(128).WithMessage("Name cannot be more than 128 characters.");
    }
}