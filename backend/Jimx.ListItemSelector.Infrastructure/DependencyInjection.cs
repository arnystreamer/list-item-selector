using Jimx.ListItemSelector.Application.Common.Interfaces;
using Jimx.ListItemSelector.Infrastructure.Persistence;
using Jimx.ListItemSelector.Infrastructure.Repositories;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;

namespace Jimx.ListItemSelector.Infrastructure;

public static class DependencyInjection
{
    public static IServiceCollection AddInfrastructure(this IServiceCollection services, IConfiguration config)
    {
        var connectionString = config.GetConnectionString("DefaultConnection");

        services.AddDbContext<AppDbContext>(options =>
            options.UseNpgsql(connectionString));

        services.AddScoped<IListCategoriesRepository, ListCategoriesRepository>();
        services.AddScoped<IListItemsRepository, ListItemsRepository>();

        return services;
    }
}