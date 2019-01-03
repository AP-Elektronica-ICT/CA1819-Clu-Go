using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ClueGoASP.Data;
using ClueGoASP.Helper;
using ClueGoASP.Models;
using ClueGoASP.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace ClueGoASP.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SuspectController : ControllerBase
    {
        // GET: api/Suspect      
        private readonly GameContext _context;
        private ISuspectService _suspectService;

        public SuspectController(GameContext context, ISuspectService suspectService)
        {
            _context = context;
            _suspectService = suspectService;
        }

        [HttpGet]
        public ActionResult<List<Suspect>> GetAll()
        {
            return _suspectService.GetAll() ;
        }

        // POST: api/Suspect
        [HttpPost]
        public IActionResult Post([FromBody] Suspect newSus)
        {
            try
            {
                return Ok(_suspectService.CreateSuspect(newSus));
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }

        }

        // PUT: api/Suspect/5
        [HttpPut("{id}")]
        public IActionResult Put([FromBody] Suspect updateSus)
        {
            try
            {
                var suspect = _suspectService.UpdateSuspect(updateSus);
                return Ok(suspect);
            }
            catch (AppException ex)
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
                var suspect = _suspectService.DeleteSuspect(id);
                return Ok(suspect);
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }
    }
}
