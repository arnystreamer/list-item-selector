using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Entities;

namespace Jimx.ListItemSelector.Application.Common.Interfaces;

public interface IListCategoriesRepository
{
    Task<int> AddAsync(string name, CancellationToken cancellationToken);
    Task<ListCategory?> GetByIdAsync(int id, CancellationToken cancellationToken);
    Task<List<ListCategory>> GetAsync(ISpecification<ListCategory> specification, CancellationToken cancellationToken);
    Task<List<ListCategory>> GetAllAsync(CancellationToken cancellationToken);
    Task UpdateAsync(ListCategory entity, CancellationToken cancellationToken);
    Task DeleteAsync(ListCategory entity, CancellationToken cancellationToken);
}