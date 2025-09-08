using Jimx.ListItemSelector.Infrastructure.Persistence.Models;
using Microsoft.EntityFrameworkCore;

namespace Jimx.ListItemSelector.Infrastructure.Persistence;

public class AppDbContext : DbContext
{
    public DbSet<ListCategoryEntity> ListCategories => Set<ListCategoryEntity>();
    public DbSet<ListItemEntity> ListItems => Set<ListItemEntity>();
    
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) {}

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        modelBuilder.Entity<ListCategoryEntity>()
            .HasMany(c => c.ListItems)
            .WithOne(l => l.ListCategory)
            .HasForeignKey(l => l.CategoryId)
            .IsRequired();
        
        modelBuilder.Entity<ListCategoryEntity>()
            .Property(c => c.Name)
            .HasMaxLength(128)
            .IsRequired();
        
        modelBuilder.Entity<ListCategoryEntity>()
            .Property(c => c.CreatedAt)
            .IsRequired()
            .HasDefaultValueSql("CURRENT_TIMESTAMP");
        
        modelBuilder.Entity<ListItemEntity>()
            .Property(c => c.Name)
            .HasMaxLength(128)
            .IsRequired();
        
        modelBuilder.Entity<ListItemEntity>()
            .Property(c => c.Description)
            .HasMaxLength(1024);
        
        modelBuilder.Entity<ListItemEntity>()
            .Property(c => c.CreatedAt)
            .IsRequired()
            .HasDefaultValueSql("CURRENT_TIMESTAMP");
            
    }
}