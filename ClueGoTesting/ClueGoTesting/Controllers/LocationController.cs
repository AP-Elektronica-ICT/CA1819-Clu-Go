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
        public IActionResult CreateLoc(Location newLoc)
        {
            _context.Locations.Add(newLoc);
            _context.SaveChanges();

            return Ok(newLoc);
        }

        // PUT: api/Location/5
        [HttpPut("{id}")]
        public IActionResult UpdateLocation([FromBody] Location updateLoc)
        {
            var orgLoc = _context.Locations.Find(updateLoc.LocId);

            if (orgLoc == null)
                return NotFound();
            else
            {
                orgLoc.LocName = updateLoc.LocName;
                orgLoc.LocLat = updateLoc.LocLat;
                orgLoc.LocLong = updateLoc.LocLong;
                orgLoc.LocDescription = updateLoc.LocDescription;

                _context.SaveChanges();
                return Ok(orgLoc);
            }
        }

        // DELETE: api/ApiWithActions/5
        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            var location = _context.Locations.Find(id);
            if (location == null)
                return NotFound();
            else
            {
                _context.Locations.Remove(location);
                _context.SaveChanges();

                return NoContent();
            }
        }
    }
}