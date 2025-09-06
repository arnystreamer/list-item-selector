using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Jimx.ListItemSelector.Infrastructure.Migrations
{
    /// <inheritdoc />
    public partial class CategoryItemDependency : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "CategoryId",
                table: "ListItems",
                type: "integer",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_ListItems_CategoryId",
                table: "ListItems",
                column: "CategoryId");

            migrationBuilder.AddForeignKey(
                name: "FK_ListItems_ListCategories_CategoryId",
                table: "ListItems",
                column: "CategoryId",
                principalTable: "ListCategories",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ListItems_ListCategories_CategoryId",
                table: "ListItems");

            migrationBuilder.DropIndex(
                name: "IX_ListItems_CategoryId",
                table: "ListItems");

            migrationBuilder.DropColumn(
                name: "CategoryId",
                table: "ListItems");
        }
    }
}
