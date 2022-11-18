using System.Collections.Generic;

namespace EntityFrameworkCoreSample.Models
{
    /// <summary>
    /// Todoエンティティ
    /// </summary>
    public class Todo
    {
       /// <summary>
       /// Id
       /// </summary>
       /// <value></value>
       public int Id { get; set; }
       
       /// <summary>
       /// Description
       /// </summary>
       /// <value></value>
       public string Description { get; set; }


       /// <summary>
       /// isActive
       /// </summary>
       /// <value></value>
       public bool isActive { get; set; }

    }
}