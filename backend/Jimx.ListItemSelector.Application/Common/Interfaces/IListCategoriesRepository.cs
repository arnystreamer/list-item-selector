namespace Jimx.ListItemSelector.Application.Common.Interfaces;

public interface IListCategoriesRepository
{
    Task<int> AddAsync(string name, CancellationToken cancellationToken);
}