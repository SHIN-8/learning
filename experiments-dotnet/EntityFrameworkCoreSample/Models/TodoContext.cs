using Microsoft.EntityFrameworkCore;

namespace EntityFrameworkCoreSample.Models
{   

    /// <summary>
    /// TodoContext
    /// </summary>
    public class TodoContext: DbContext
    {
        /// <summary>
        /// コンストラクタ
        /// </summary>
        /// <param name="options"></param>
        public TodoContext(DbContextOptions<TodoContext> options)
            :   base(options) 
        {

        }

        ///<summary>
        /// モデル生成時に呼ばれるライフサイクルメソッド
        ///</summary>
        ///<param name="modelBuilder"></param>
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            // データのシードを定義
            modelBuilder.Entity<Todo>().HasData(
                new {Id = 1, Description="foobar1", isActive=true},
                new {Id = 2, Description="foobar2", isActive=true},
                new {Id = 3, Description="foobar3", isActive=true}
            );
            
        }

        /// <summary>
        /// DbSet
        /// </summary>
        /// <value></value>
        public DbSet<Todo> Todo {get; set;}
    }   
}