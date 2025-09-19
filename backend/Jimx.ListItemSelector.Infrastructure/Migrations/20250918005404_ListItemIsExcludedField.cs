using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Jimx.ListItemSelector.Infrastructure.Migrations
{
    /// <inheritdoc />
    public partial class ListItemIsExcludedField : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "IsExcluded",
                table: "ListItems",
                type: "boolean",
                nullable: false,
                defaultValue: false);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "IsExcluded",
                table: "ListItems");
        }
    }
}
