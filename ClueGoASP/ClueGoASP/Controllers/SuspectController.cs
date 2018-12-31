using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ClueGoASP.Data;
using ClueGoASP.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;


namespace ClueGoASP.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SuspectController : ControllerBase
    {

        // GET: api/Suspect
      
        private readonly GameContext _context;

        public SuspectController(GameContext context)
        {
            _context = context;
        }

        [HttpGet]
        public ActionResult<List<Suspect>> GetAll()
        {
            return _context.Suspects.ToList();
        }

        // POST: api/Suspect
        [HttpPost]
        public void Post([FromBody] string value)
        {
        }

        // PUT: api/Suspect/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE: api/ApiWithActions/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {

        }
    }
}
