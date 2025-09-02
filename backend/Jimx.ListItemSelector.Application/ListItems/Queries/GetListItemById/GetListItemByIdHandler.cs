using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Domain.Entities;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Queries.GetListItemById;

public class GetListItemByIdHandler : IRequestHandler<GetListItemByIdQuery, ListItem?>
{
    private readonly IListItemsRepository _repository;

    public GetListItemByIdHandler(IListItemsRepository repository)
    {
        _repository = repository;
    }

    public async Task<ListItem?> Handle(GetListItemByIdQuery request, CancellationToken cancellationToken)
    {
        return await _repository.GetByIdAsync(request.Id, cancellationToken);
    }
}