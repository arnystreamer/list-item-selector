using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Domain.Entities;

namespace Jimx.ListItemSelector.Infrastructure.Repositories;

public class ListItemsRepository : IListItemsRepository
{
    private readonly AppDbContext _context;

    public ListItemsRepository(AppDbContext context)
    {
        _context = context;
    }

    public async Task<int> AddAsync(string name, string? description, CancellationToken cancellationToken)
    {
        var entry = _context.ListItems.Add(new ListItem() { Name = name, Description = description });
        await _context.SaveChangesAsync(cancellationToken);
        return entry.Entity.Id;
    }
}