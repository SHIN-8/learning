using Microsoft.EntityFrameworkCore.Migrations;

namespace EntityFrameworkCoreSample.Migrations
{
    public partial class InitialCreate : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Todo",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    Description = table.Column<string>(nullable: true),
                    isActive = table.Column<bool>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Todo", x => x.Id);
                });

            migrationBuilder.InsertData(
                table: "Todo",
                columns: new[] { "Id", "Description", "isActive" },
                values: new object[] { 1, "foobar1", true });

            migrationBuilder.InsertData(
                table: "Todo",
                columns: new[] { "Id", "Description", "isActive" },
                values: new object[] { 2, "foobar2", true });

            migrationBuilder.InsertData(
                table: "Todo",
                columns: new[] { "Id", "Description", "isActive" },
                values: new object[] { 3, "foobar3", true });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Todo");
        }
    }
}
