using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Infrastructure.Persistence;
using Jimx.ListItemSelector.Infrastructure.Repositories;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;

namespace Jimx.ListItemSelector.Infrastructure;

public static class DependencyInjection
{
    public static IServiceCollection AddInfrastructure(this IServiceCollection services, IConfiguration config, 
        Func<DbContextOptionsBuilder, IConfiguration, DbContextOptionsBuilder> dbContextOptionsBuilderFunc)
    {
        var connectionString = config.GetConnectionString("DefaultConnection");

        services.AddDbContext<AppDbContext>(options =>
            dbContextOptionsBuilderFunc(options.UseNpgsql(connectionString), config));

        services.AddScoped<IListCategoriesRepository, ListCategoriesRepository>();
        services.AddScoped<IListItemsRepository, ListItemsRepository>();

        return services;
    }
}