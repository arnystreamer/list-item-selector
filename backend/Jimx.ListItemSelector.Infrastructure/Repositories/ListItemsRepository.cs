using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Entities;
using Jimx.ListItemSelector.Infrastructure.Mapping;
using Jimx.ListItemSelector.Infrastructure.Persistence;
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

    public async Task<ListItem> AddAsync(ListItem listItem, CancellationToken cancellationToken)
    {
        var entity = listItem.ToEntity();
        entity.UpdatedAt = DateTime.UtcNow;
        
        var entry = _context.ListItems.Add(entity);
        await _context.SaveChangesAsync(cancellationToken);
        return entry.Entity.ToDomain();
    }

    public async Task<ListItem?> GetByIdAsync(int id, CancellationToken cancellationToken)
    {
        var entity = await _context.ListItems.FindAsync([id], cancellationToken);
        return entity?.ToDomain();
    }

    public async Task<IReadOnlyCollection<ListItem>> GetAsync(ISpecification<ListItem> domainSpecification, CancellationToken cancellationToken)
    {
        var entitySpecification = domainSpecification.ToEntitySpecification();
        var query = SpecificationEvaluator.GetQuery(_context.ListItems, entitySpecification);
        return await query.AsNoTracking()
            .OrderBy(c => c.IsExcluded).ThenBy(c => c.Name)
            .Select(i => i.ToDomain())
            .ToListAsync(cancellationToken);
    }

    public async Task<IReadOnlyCollection<ListItem>> GetAllAsync(CancellationToken cancellationToken)
    {
        return await _context.ListItems.AsNoTracking()
            .OrderBy(c => c.IsExcluded).ThenBy(c => c.Name)
            .Select(i => i.ToDomain())
            .ToListAsync(cancellationToken);
    }

    public async Task<ListItem?> UpdateAsync(ListItem listItem, CancellationToken cancellationToken)
    {
        var incomingEntity = listItem.ToEntity();
        var entity = await _context.ListItems.FindAsync([incomingEntity.Id], cancellationToken);
        if (entity == null)
        {
            return null;
        }
        
        entity.Name = incomingEntity.Name;
        entity.Description = incomingEntity.Description;
        entity.IsExcluded = incomingEntity.IsExcluded;
        entity.UpdatedAt = DateTime.UtcNow;
        
        var entry = _context.ListItems.Update(entity);
        await _context.SaveChangesAsync(cancellationToken);

        return entry.Entity.ToDomain();
    }

    public async Task<bool> DeleteAsync(int id, CancellationToken cancellationToken)
    {
        var entity = await _context.ListItems.FindAsync([id], cancellationToken);
        if (entity == null)
        {
            return false;
        }
        
        _context.ListItems.Remove(entity);
        await _context.SaveChangesAsync(cancellationToken);
        return true;
    }
}