using Jimx.Common.Models;
using Jimx.Common.WebApi.Models;
using Jimx.ListItemSelector.Application.Common.Interfaces;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Commands.CreateListItem;

public class CreateListItemHandler : IRequestHandler<CreateListItemCommand, Result<IdObject>>
{
    private readonly IListItemsRepository _repository;

    public CreateListItemHandler(IListItemsRepository repository)
    {
        _repository = repository;
    }

    public async Task<Result<IdObject>> Handle(CreateListItemCommand request, CancellationToken cancellationToken)
    {
        var domainItem = request.ToDomain();
        var id = await _repository.AddAsync(domainItem, cancellationToken);
        return Result<IdObject>.Ok(new IdObject(id));
    }
}