using Jimx.ListItemSelector.Domain.Entities;
using Microsoft.EntityFrameworkCore;

namespace Jimx.ListItemSelector.Infrastructure;

public class AppDbContext : DbContext
{
    public DbSet<ListCategory> ListCategories => Set<ListCategory>();
    public DbSet<ListItem> ListItems => Set<ListItem>();
    
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) {}
}