using Jimx.ListItemSelector.Application.Common.Interfaces;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.CreateListItem;

public class CreateListItemHandler : IRequestHandler<CreateListItemCommand, int>
{
    private readonly IListItemsRepository _repository;

    public CreateListItemHandler(IListItemsRepository repository)
    {
        _repository = repository;
    }

    public async Task<int> Handle(CreateListItemCommand request, CancellationToken cancellationToken)
    {
        return await _repository.AddAsync(request.Name, request.Description, cancellationToken);
    }
}