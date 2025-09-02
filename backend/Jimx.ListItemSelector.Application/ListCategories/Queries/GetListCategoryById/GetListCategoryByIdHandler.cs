using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Domain.Entities;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Queries.GetListCategoryById;

public class GetListCategoryByIdHandler : IRequestHandler<GetListCategoryByIdQuery, ListCategory?>
{
    private readonly IListCategoriesRepository _repository;

    public GetListCategoryByIdHandler(IListCategoriesRepository repository)
    {
        _repository = repository;
    }

    public async Task<ListCategory?> Handle(GetListCategoryByIdQuery request, CancellationToken cancellationToken)
    {
        return await _repository.GetByIdAsync(request.Id, cancellationToken);
    }
}