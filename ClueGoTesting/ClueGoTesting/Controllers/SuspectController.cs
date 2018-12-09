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
    public class SuspectController : ControllerBase
    {
        private readonly GameContext _context;
        public SuspectController(GameContext context)
        {
            _context = context;
        }
        // GET: api/Suspect
        [HttpGet]
        public ActionResult<List<Suspect>> GetAll()
        {
            return _context.Suspects.ToList();
        }
    }
}
