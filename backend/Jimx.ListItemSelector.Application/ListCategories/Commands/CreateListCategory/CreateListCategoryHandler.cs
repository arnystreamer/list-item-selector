using Jimx.ListItemSelector.Application.Common.Interfaces;
using MediatR;

namespace Jimx.ListItemSelector.Application.ListCategories.Commands.CreateListCategory;

public class CreateListCategoryHandler : IRequestHandler<CreateListCategoryCommand, int>
{
    private readonly IListCategoriesRepository _repository;
    
    public CreateListCategoryHandler(IListCategoriesRepository repository)
    {
        _repository = repository;
    }

    public async Task<int> Handle(CreateListCategoryCommand request, CancellationToken cancellationToken)
    {
        return await _repository.AddAsync(request.Name, cancellationToken);
    }
}