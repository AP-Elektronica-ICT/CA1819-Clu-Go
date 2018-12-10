using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
<<<<<<< HEAD
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ClueGoTesting.Data;
using ClueGoTesting.Models;
=======
using ClueGoTesting.Data;
using ClueGoTesting.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
>>>>>>> 8d360d6b9ab2e1386adc15333ec17682719dc198

namespace ClueGoTesting.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SuspectController : ControllerBase
    {
<<<<<<< HEAD
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
=======
        private readonly GameContext _dbContext;
        public SuspectController(GameContext context)
        {
            _dbContext = context;
        }
        // GET: api/Suspect
        [HttpGet]
        public ActionResult<List<Suspect>> GetAll()
        {
            return _dbContext.Suspects.ToList();
>>>>>>> 8d360d6b9ab2e1386adc15333ec17682719dc198
        }
    }
}
