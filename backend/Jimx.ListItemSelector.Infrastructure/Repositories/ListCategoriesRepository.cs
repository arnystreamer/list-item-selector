using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Entities;
using Jimx.ListItemSelector.Infrastructure.Mapping;
using Jimx.ListItemSelector.Infrastructure.Persistence;
using Jimx.ListItemSelector.Infrastructure.Specifications;
using Microsoft.EntityFrameworkCore;

namespace Jimx.ListItemSelector.Infrastructure.Repositories;

public class ListCategoriesRepository : IListCategoriesRepository
{
    private readonly AppDbContext _context;

    public ListCategoriesRepository(AppDbContext context)
    {
        _context = context;
    }

    public async Task<int> AddAsync(ListCategory listCategory, CancellationToken cancellationToken)
    {
        var entity = listCategory.ToEntity();
        entity.UpdatedAt = DateTime.UtcNow;
        
        var entry = _context.ListCategories.Add(entity);
        await _context.SaveChangesAsync(cancellationToken);
        return entry.Entity.Id;
    }

    public async Task<ListCategory?> GetByIdAsync(int id, CancellationToken cancellationToken)
    {
        var entity = await _context.ListCategories.FindAsync([id], cancellationToken);
        return entity?.ToDomain();
    }

    public async Task<IReadOnlyCollection<ListCategory>> GetAsync(ISpecification<ListCategory> domainSpecification, CancellationToken cancellationToken)
    {
        var entitySpecification = domainSpecification.ToEntitySpecification();
        var query = SpecificationEvaluator.GetQuery(_context.ListCategories, entitySpecification);
        return await query.AsNoTracking()
            .Select(c => c.ToDomain())
            .ToListAsync(cancellationToken);
    }

    public async Task<IReadOnlyCollection<ListCategory>> GetAllAsync(CancellationToken cancellationToken)
    {
        return await _context.ListCategories.AsNoTracking().Select(c => c.ToDomain()).ToListAsync(cancellationToken);
    }

    public async Task<bool> UpdateAsync(ListCategory listCategory, CancellationToken cancellationToken)
    {
        var incomingEntity = listCategory.ToEntity();
        var entity = await _context.ListCategories.FindAsync([incomingEntity.Id], cancellationToken);
        if (entity == null)
        {
            return false;
        }
        
        entity.Name = incomingEntity.Name;
        entity.UpdatedAt = DateTime.UtcNow;
        
        _context.ListCategories.Update(entity);
        await _context.SaveChangesAsync(cancellationToken);

        return true;
    }

    public async Task<bool> DeleteAsync(int id, CancellationToken cancellationToken)
    {
        var entity = await _context.ListCategories.FindAsync([id], cancellationToken);
        if (entity == null)
        {
            return false;
        }
        
        _context.ListCategories.Remove(entity);
        await _context.SaveChangesAsync(cancellationToken);
        return true;
    }
}