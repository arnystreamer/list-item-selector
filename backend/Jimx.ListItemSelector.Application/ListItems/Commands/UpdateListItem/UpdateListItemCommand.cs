using FluentValidation;
using Jimx.ListItemSelector.Application.Common.Models;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.UpdateListItem;

public record UpdateListItemCommand(int Id, string Name, string? Description) : IRequest<Result>;

public class UpdateListItemCommandValidator : AbstractValidator<UpdateListItemCommand>
{
    public UpdateListItemCommandValidator()
    {
        RuleFor(x => x.Id)
            .GreaterThan(0).WithMessage("Id must be greater than zero.");
        
        RuleFor(x => x.Name)
            .NotEmpty().WithMessage("Name cannot be empty");
    }
}