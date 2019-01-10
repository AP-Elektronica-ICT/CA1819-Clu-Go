using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ClueGoASP.Services;
using ClueGoASP.Models;
using ClueGoASP.Data;
using ClueGoASP.Helper;

namespace ClueGoASP.Controllers
{
    [Route("api/[controller]")]
    [ApiController]

    public class GameController : ControllerBase
    {
        private readonly GameContext _dbContext;
        private IGameService _gameService;
        private IUserService _userService;
        private IClueService _clueService;


        public GameController(GameContext dbcontext, IGameService gameService, IUserService userService, IClueService clueService)
        {
            _dbContext = dbcontext;
            _gameService = gameService;
            _userService = userService;
            _clueService = clueService;
        }

        [HttpGet]
        public ActionResult<List<Game>> GetAll()
        {
            return Ok(_dbContext.Games
                .Include(x => x.GameLocations)
                    
                .Include(x => x.GameSuspects)
                .Include(x => x.GameClues)
                .ToList());
        }

        [HttpGet("{gameId}")]
        public ActionResult<Game> GetBriefGameByID(int gameId)
        {
            try
            {
                return _gameService.GetBriefGame(gameId);
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

        [HttpGet("full/{gameId}")]
        public ActionResult<List<Game>> GetFullGameById(int gameId)
        {
            var item = _dbContext.Games.Find(gameId);

            return Ok(_gameService.GetGameById(gameId));
        }



        [HttpDelete("{gameId}")]
        public IActionResult Delete(int gameId)
        {
            int amt = _gameService.GetGameInfo(gameId);
            try
            {
                UpdateEndGame(gameId, amt);

                var game = _gameService.DeleteGame(gameId);
                return Ok(game);
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }            
        }

        [HttpGet("create/full/{userId}/{amtSus}")]
        public ActionResult<Game> CreateGameFull(int userId, int amtSus)
        {
            try
            {
                return Ok(_gameService.CreateGameFull(userId, amtSus));
            }
            catch(AppException ex)
            {
                return BadRequest(new { message = ex.Message});
            }            
        }

        [HttpGet("create/{userId}/{amtSus}")]
        public ActionResult CreateGame(int userId, int amtSus)
        {
            try
            {
                return Ok(_gameService.CreateGame(userId, amtSus));
            }
            catch(AppException ex)
            {
                return BadRequest(new { message = ex.Message});
            }            
        }

        [HttpGet("{gameId}/clue")]
        public ActionResult GetPuzzleCluesByGame(int gameId)
        {
            try
            {
                return Ok(_gameService.GetPuzzleCluesByGame(gameId));
            }
            catch(AppException ex)
            {
                return BadRequest(new { message = ex.Message});
            }            
        }



        [HttpPut("updateEndGame/{userId}/{amtSus}")]
        public IActionResult UpdateEndGame(int userId, int amtSus)
        {
            _userService.AddWonGame(userId);
            _userService.AddDistanceWalked(amtSus, userId);
            _userService.AddFoundClues(amtSus, userId);

            return Ok("User stats have been updated.");        
        }

        [HttpGet("{gameId}/{locName}/setFound")]
        public ActionResult SetGameClueFound(int gameId, string locName)
        {
            try
            {
                return Ok(_gameService.SetGameClueFound(gameId, locName));
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

        [HttpGet("{gameId}/found")]
        public ActionResult GetFoundClues(int gameId)
        {
            try
            {
                return Ok(_gameService.GetFoundClues(gameId));
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

        [HttpGet("{gameId}/notfound")]
        public ActionResult GetNotFoundClues(int gameId)
        {
            List<Clue> clues = new List<Clue>();
            try
            {
                return Ok(_gameService.GetNotFoundClues(gameId));
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

        [HttpGet("{gameId}/suspect")]
        public ActionResult GetGameSuspects(int gameId)
        {
            List<Clue> clues = new List<Clue>();
            try
            {
                return Ok(_gameService.GetGameSuspects(gameId));
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

        [HttpGet("{gameId}/setlocation/{locName}")]
        public void SetLocationVisited(int gameId, string locName)
        {
            _gameService.SetLocationVisited(gameId, locName);
        }

        //[HttpGet("{gameId}/location")]
        
    }
}
