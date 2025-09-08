using FluentValidation;
using Jimx.Common.Models;
using MediatR;

namespace Jimx.ListItemSelector.Application.Common.Behaviors;

public class ValidationBehavior<TRequest, TResponse> : IPipelineBehavior<TRequest, Result<TResponse>>
    where TRequest : IRequest<Result<TResponse>>
    where TResponse : class
{
    private readonly IEnumerable<IValidator<TRequest>> _validators;

    public ValidationBehavior(IEnumerable<IValidator<TRequest>> validators)
    {
        _validators = validators;
    }

    public async Task<Result<TResponse>> Handle(TRequest request, RequestHandlerDelegate<Result<TResponse>> next, CancellationToken cancellationToken)
    {
        if (_validators.Any())
        {
            var context = new ValidationContext<TRequest>(request);
            
            var errorMessages = _validators
                .Select(v => v.Validate(context))
                .SelectMany(v => v.Errors)
                .Where(e => e is not null)
                .Select(e => e.ErrorMessage)
                .ToArray();

            if (errorMessages.Any())
            {
                return Result<TResponse>.Fail(errorMessages);
            }
        }
        
        return await next(cancellationToken);
    }
}