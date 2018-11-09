using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ClueGoTesting.Models;
using ClueGoTesting.Data;

namespace ClueGoTesting.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SuspectsController : ControllerBase
    {

        private readonly GameContext _context;


        public SuspectsController(GameContext context)
        {
            _context = context;
            context.Database.EnsureCreated();
        }



        [HttpGet]
        public ActionResult<List<Suspects>> GetAll()
        {
            return _context.Suspects.ToList();
        }

        // GET: api/Suspect/5
        [HttpGet("{id}", Name = "Get")]
        public string Get(int id)
        {
            return "value";
        }

        // POST: api/Suspect
        [HttpPost]
        public IActionResult Post(Suspects newSuspect)
        {
            if (ModelState.IsValid)
            {
                _context.Suspects.Add(newSuspect);
                _context.SaveChanges();
               
            }

            return Ok(newSuspect);
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
