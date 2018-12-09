using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ClueGoTesting.Models;
using ClueGoTesting.Data;
using Microsoft.EntityFrameworkCore;


namespace ClueGoTesting.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GameController : ControllerBase
    {
        private GameContext _dbcontext;

        public GameController(GameContext dbcontext)
        {
            _dbcontext = dbcontext;

        }
        [HttpGet]
        public ActionResult<List<Game>> GetAll()
        {
            return Ok(_dbcontext.Games
                .Include(x => x.GameLocations)
                .ThenInclude(x => x.Location)
                //.ThenInclude(x => x.LocId)
                .ToList());
        }

        [HttpGet("game/{gameId}")]
        public ActionResult<List<Game>> GetGameById(int gameId)
        {
            var item = _dbcontext.Games.Find(gameId);

            return Ok(_dbcontext.Games
                            .Include(x => x.GameLocations)
                            .ThenInclude(x => x.Location)
                            .Where(x => x.GameId == gameId)

                            .Include(x => x.GameSuspects)
                            .ThenInclude(x => x.Suspect)
                            .Where(x => x.GameId == gameId)
                            .ToList());
        }

        [HttpGet("create/{amtGame}")]
        public ActionResult<Game> CreateGame(int amtGame)
        {
            var game = new Game();
            var locations = _dbcontext.Locations.ToList();
            game.GameLocations = new List<GameLocation>();
            for (int  i = 0;  i < amtGame;  i++)
            {
                game.GameLocations.Add(new GameLocation
                {
                    Location = locations[i]
                });                
            }
            // Get Random Location
            var r = new Random();
            var index = r.Next(0, locations.Count);

            var suspects = _dbcontext.Suspects.ToList();
            game.GameSuspects = new List<GameSuspect>();
            for (int i = 0; i < 3; i++)
            {
                game.GameSuspects.Add(new GameSuspect
                {
                    Suspect = suspects[i]                    
                });
                game.GameSuspects[0].isMurderer = true;            
            }
            
            _dbcontext.Games.Add(game);
            _dbcontext.SaveChanges();

            return Ok(game);
        }

    }
}
