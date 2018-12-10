using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ClueGoTesting.Data;
using ClueGoTesting.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace ClueGoTesting.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ClueController : ControllerBase
    {
        private readonly GameContext _dbContext;
        public ClueController(GameContext context)
        {
            _dbContext = context;
        }
        // GET: api/Clue
        [HttpGet]
        public ActionResult<List<Clue>> GetAll()
        {
            return _dbContext.Clues.ToList();
        }

      

        // PUT: api/Clue/5
        [HttpGet("{ClueId}")]
        public IActionResult UpdateClue(int ClueId)
        {

            var orgClue = _dbContext.Clues.SingleOrDefault(x => x.ClueId == ClueId);

            if (orgClue == null)
                return NotFound("Clue does not exist");
            else
            {
                orgClue.Found = true;

                _dbContext.SaveChanges();
                return Ok(orgClue);
            }
        }
        [HttpPut("put/{ClueId}")]
        public IActionResult UpdateClue1([FromBody] Clue updateClue, int ClueId)
        {

            var orgClue = _dbContext.Clues.SingleOrDefault(x => x.ClueId == ClueId);

            if (orgClue == null)
                return NotFound("Clue does not exist");
            else
            {
                orgClue.Found = updateClue.Found;

                _dbContext.SaveChanges();
                return Ok(orgClue);
            }
        }


    }
}
