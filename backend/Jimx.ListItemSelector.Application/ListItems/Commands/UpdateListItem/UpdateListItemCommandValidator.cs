using FluentValidation;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.UpdateListItem;

public class UpdateListItemCommandValidator : AbstractValidator<UpdateListItemCommand>
{
    public UpdateListItemCommandValidator()
    {
        RuleFor(x => x.Id)
            .GreaterThan(0).WithMessage("Id must be greater than zero.");
        
        RuleFor(x => x.Name)
            .NotEmpty().WithMessage("Name cannot be empty");
        
        RuleFor(x => x.Name)
            .MaximumLength(128).WithMessage("Name cannot be more than 128 characters.");
    }
}