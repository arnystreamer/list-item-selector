using FluentValidation;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.CreateListItem;

public class CreateListItemCommandValidator : AbstractValidator<CreateListItemCommand>
{
    public CreateListItemCommandValidator()
    {
        RuleFor(x => x.Name)
            .NotEmpty().WithMessage("Name cannot be empty");
    }
}