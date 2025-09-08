using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Entities;

namespace Jimx.ListItemSelector.Application.Common.Interfaces;

public interface IListCategoriesRepository
{
    Task<int> AddAsync(ListCategory listCategory, CancellationToken cancellationToken);
    Task<ListCategory?> GetByIdAsync(int id, CancellationToken cancellationToken);
    Task<IReadOnlyCollection<ListCategory>> GetAsync(ISpecification<ListCategory> specification, CancellationToken cancellationToken);
    Task<IReadOnlyCollection<ListCategory>> GetAllAsync(CancellationToken cancellationToken);
    Task<bool> UpdateAsync(ListCategory listCategory, CancellationToken cancellationToken);
    Task<bool> DeleteAsync(int id, CancellationToken cancellationToken);
}