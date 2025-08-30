namespace Jimx.ListItemSelector.Application.Common.Interfaces;

public interface IListItemsRepository
{
    Task<int> AddAsync(string name, string? description, CancellationToken cancellationToken);
}