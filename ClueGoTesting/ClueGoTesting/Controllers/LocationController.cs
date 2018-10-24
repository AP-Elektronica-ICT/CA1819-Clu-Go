using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ClueGoTesting.Models;

namespace ClueGoTesting.Data
{
    [Route("api/[controller]")]
    [ApiController]
    public class LocationController : ControllerBase
    { 
        private readonly GameContext _context;

        public LocationController(GameContext context)
        {
            _context = context;
        }

        // GET: api/Location
        [HttpGet]
        public ActionResult<List<Location>> GetAll()
        {
            return _context.Locations.ToList();
        }

        // POST: api/Location
        [HttpPost]
        public void Post([FromBody] string value)
        {
        }

        // PUT: api/Location/5
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
