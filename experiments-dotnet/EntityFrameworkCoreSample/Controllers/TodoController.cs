using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace EntityFrameworkCoreSample.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class TodoController : ControllerBase
    {
        /// <summary>
        /// TodoContext
        /// </summary>
        private readonly EntityFrameworkCoreSample.Models.TodoContext _context;
        
        /// <summary>
        /// ロガー
        /// </summary>
        private readonly ILogger<TodoController> _logger;

        /// <summary>
        /// コンストラクタ
        /// </summary>
        /// <param name="context"></param>
        /// <param name="logger"></param>
        public TodoController(EntityFrameworkCoreSample.Models.TodoContext context, ILogger<TodoController> logger)
        {
            _context = context;
            _logger = logger;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        [HttpGet]
        public IEnumerable<Models.Todo> Get()
        {
            List<Models.Todo> lists = _context.Todo
                    .Where(e=> e.isActive == true)
                    .OrderBy(e=>e.Id)
                    .ToList();

            return lists;
        }
    }
}
