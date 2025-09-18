using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Entities;

namespace Jimx.ListItemSelector.Application.Common.Interfaces;

public interface IListItemsRepository
{
    Task<ListItem> AddAsync(ListItem listItem, CancellationToken cancellationToken);
    Task<ListItem?> GetByIdAsync(int id, CancellationToken cancellationToken);
    Task<IReadOnlyCollection<ListItem>> GetAsync(ISpecification<ListItem> specification, CancellationToken cancellationToken);
    Task<IReadOnlyCollection<ListItem>> GetAllAsync(CancellationToken cancellationToken);
    Task<ListItem?> UpdateAsync(ListItem listItem, CancellationToken cancellationToken);
    Task<bool> DeleteAsync(int id, CancellationToken cancellationToken);
}