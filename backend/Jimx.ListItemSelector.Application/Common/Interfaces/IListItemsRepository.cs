using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Entities;

namespace Jimx.ListItemSelector.Application.Common.Interfaces;

public interface IListItemsRepository
{
    Task<int> AddAsync(string name, string? description, CancellationToken cancellationToken);
    Task<ListItem?> GetByIdAsync(int id, CancellationToken cancellationToken);
    Task<List<ListItem>> GetAsync(ISpecification<ListItem> specification, CancellationToken cancellationToken);
    Task<List<ListItem>> GetAllAsync(CancellationToken cancellationToken);
    Task UpdateAsync(ListItem entity, CancellationToken cancellationToken);
    Task DeleteAsync(ListItem entity, CancellationToken cancellationToken);
}