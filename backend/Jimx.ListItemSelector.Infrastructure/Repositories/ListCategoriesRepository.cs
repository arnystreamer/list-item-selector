using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Domain.Common;
using Jimx.ListItemSelector.Domain.Entities;
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

    public async Task<int> AddAsync(string name, CancellationToken cancellationToken)
    {
        var entry = _context.ListCategories.Add(new ListCategory() { Name = name });
        await _context.SaveChangesAsync(cancellationToken);
        return entry.Entity.Id;
    }

    public async Task<ListCategory?> GetByIdAsync(int id, CancellationToken cancellationToken)
    {
        return await _context.ListCategories.FirstOrDefaultAsync(c => c.Id == id, cancellationToken);
    }

    public Task<List<ListCategory>> GetAsync(ISpecification<ListCategory> specification, CancellationToken cancellationToken)
    {
        var query = SpecificationEvaluator.GetQuery(_context.ListCategories, specification);
        return query.AsNoTracking().ToListAsync(cancellationToken);
    }

    public async Task<List<ListCategory>> GetAllAsync(CancellationToken cancellationToken)
    {
        return await _context.ListCategories.AsNoTracking().ToListAsync(cancellationToken);
    }

    public async Task UpdateAsync(ListCategory entity, CancellationToken cancellationToken)
    {
        _context.ListCategories.Update(entity);
        await _context.SaveChangesAsync(cancellationToken);
    }

    public async Task DeleteAsync(ListCategory entity, CancellationToken cancellationToken)
    {
        _context.ListCategories.Remove(entity);
        await _context.SaveChangesAsync(cancellationToken);
    }
}