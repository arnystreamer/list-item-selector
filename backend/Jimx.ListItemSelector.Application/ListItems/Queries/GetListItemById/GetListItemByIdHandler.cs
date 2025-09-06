using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Application.ListItems.Dto;
using Jimx.ListItemSelector.Application.Mapping;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListItems.Queries.GetListItemById;

public class GetListItemByIdHandler : IRequestHandler<GetListItemByIdQuery, ListItemDto?>
{
    private readonly IListItemsRepository _repository;

    public GetListItemByIdHandler(IListItemsRepository repository)
    {
        _repository = repository;
    }

    public async Task<ListItemDto?> Handle(GetListItemByIdQuery request, CancellationToken cancellationToken)
    {
        var domainItem = await _repository.GetByIdAsync(request.Id, cancellationToken);
        return domainItem?.ToDto();
    }
}