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

namespace ClueGoASP.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ClueController : ControllerBase
    {
        private readonly GameContext _dbContext;
        private IClueService _clueService;
        public ClueController(GameContext context, IClueService clueService)
        {
            _dbContext = context;
            _clueService = clueService;
        }
        // GET: api/Clue
        [HttpGet]
        public ActionResult<List<Clue>> GetAll()
        {
            return _clueService.GetAll();
        }
        // PUT: api/Clue/5
        [HttpPut("{clueId}")]
        public IActionResult SetFound(int clueId)
        {
            try
            {
                return Ok(_clueService.SetFound(clueId));
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

        [HttpPut("put/{clueId}")]
        public IActionResult UpdateClue([FromBody] Clue updateClue, int clueId)
        {
            try
            {
                return Ok(_clueService.UpdateClue(clueId, updateClue));
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }        
    }
}
