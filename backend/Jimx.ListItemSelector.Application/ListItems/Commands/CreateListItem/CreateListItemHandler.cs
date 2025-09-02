using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Application.Common.Models;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.CreateListItem;

public class CreateListItemHandler : IRequestHandler<CreateListItemCommand, Result<int>>
{
    private readonly IListItemsRepository _repository;

    public CreateListItemHandler(IListItemsRepository repository)
    {
        _repository = repository;
    }

    public async Task<Result<int>> Handle(CreateListItemCommand request, CancellationToken cancellationToken)
    {
        var id = await _repository.AddAsync(request.Name, request.Description, cancellationToken);
        return Result<int>.Success(id);
    }
}