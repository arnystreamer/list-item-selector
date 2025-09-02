using FluentValidation;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.DeleteListItem;

public class DeleteListItemCommandValidator : AbstractValidator<DeleteListItemCommand>
{
    public DeleteListItemCommandValidator()
    {
        RuleFor(x => x.Id)
            .GreaterThan(0).WithMessage("Id must be greater than zero.");
    }
}