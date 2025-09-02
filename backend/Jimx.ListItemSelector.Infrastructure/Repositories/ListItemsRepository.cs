using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Entities;
using Jimx.ListItemSelector.Infrastructure.Specifications;
using Microsoft.EntityFrameworkCore;

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

    public async Task<ListItem?> GetByIdAsync(int id, CancellationToken cancellationToken)
    {
        return await _context.ListItems.FirstOrDefaultAsync(c => c.Id == id, cancellationToken);
    }

    public Task<List<ListItem>> GetAsync(ISpecification<ListItem> specification, CancellationToken cancellationToken)
    {
        var query = SpecificationEvaluator.GetQuery(_context.ListItems, specification);
        return query.AsNoTracking().ToListAsync(cancellationToken);
    }

    public async Task<List<ListItem>> GetAllAsync(CancellationToken cancellationToken)
    {
        return await _context.ListItems.AsNoTracking().ToListAsync(cancellationToken);
    }

    public async Task UpdateAsync(ListItem entity, CancellationToken cancellationToken)
    {
        _context.ListItems.Update(entity);
        await _context.SaveChangesAsync(cancellationToken);
    }

    public async Task DeleteAsync(ListItem entity, CancellationToken cancellationToken)
    {
        _context.ListItems.Remove(entity);
        await _context.SaveChangesAsync(cancellationToken);
    }
}