using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ClueGoASP.Models;
using ClueGoASP.Helper;
using ClueGoASP.Services;

namespace ClueGoASP.Data
{
    [Route("api/[controller]")]
    [ApiController]
    public class LocationController : ControllerBase
    {
        private readonly GameContext _dbContext;
        private ILocationService _locationService;

        public LocationController(GameContext context, ILocationService locationService)
        {
            _dbContext = context;
            _locationService = locationService;
        }

        // GET: api/Location
        [HttpGet]
        public ActionResult<List<Location>> GetAll()
        {
            return _locationService.GetLocations();
        }

        // POST: api/Location
        [HttpPost]
        public IActionResult CreateLoc(Location newLoc)
        {
            try
            {
                return Ok(_locationService.CreateLocation(newLoc));
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }            
        }

        // PUT: api/Location/5
        [HttpPut("{id}")]
        public IActionResult UpdateLocation([FromBody] Location updateLoc)
        {
            try
            {
                var location = _locationService.UpdateLoction(updateLoc);
                return Ok(location);
            }
            catch(AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

        // DELETE: api/ApiWithActions/5
        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            try
            {
                var user = _locationService.DeleteLocation(id);
                return Ok(user);
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }
    }
}