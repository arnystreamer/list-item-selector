using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Domain.Entities;

namespace Jimx.ListItemSelector.Infrastructure.Repositories;

public class ListCategoriesRepository : IListCategoriesRepository
{
    private readonly AppDbContext _context;

    public ListCategoriesRepository(AppDbContext context)
    {
        _context = context;
    }

    public async Task<int> AddAsync(string name, CancellationToken cancellationToken)
    {
        var entry = _context.ListCategories.Add(new ListCategory() { Name = name });
        await _context.SaveChangesAsync(cancellationToken);
        return entry.Entity.Id;
    }
}