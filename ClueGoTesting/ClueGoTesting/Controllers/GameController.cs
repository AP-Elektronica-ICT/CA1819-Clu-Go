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
        private GameContext _dbContext;


        public GameController(GameContext dbcontext)
        {
            _dbContext = dbcontext;

        }
        [HttpGet]
        public ActionResult<List<Game>> GetAll()
        {
            return Ok(_dbContext.Games
                .Include(x => x.GameLocations)
                .ThenInclude(x => x.GameId)
                .ToList());
        }

        [HttpGet("game/{gameId}")]
        public ActionResult<List<Game>> GetGameById(int gameId)
        {
            var item = _dbContext.Games.Find(gameId);

            return Ok(_dbContext.Games
                            .Include(x => x.GameLocations)
                            .ThenInclude(x => x.Location)
                            //.ThenInclude(x => x.LocId)
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

            //Randomize location list
            var locations = _dbContext.Locations.OrderBy(x => Guid.NewGuid()).ToList();
            game.GameLocations = new List<GameLocation>();
            for (int  i = 0;  i < amtGame;  i++)
            {
                game.GameLocations.Add(new GameLocation
                {
                    Location = locations[i]
                });                
            }

            //Randomize Suspect list
            var suspects = _dbContext.Suspects.OrderBy(x => Guid.NewGuid()).ToList();
            game.GameSuspects = new List<GameSuspect>();
            for (int i = 0; i < 3; i++)
            {
                game.GameSuspects.Add(new GameSuspect
                {
                    Suspect = suspects[i]                    
                });
                game.GameSuspects[0].isMurderer = true;            
            }
            
            _dbContext.Games.Add(game);
            _dbContext.SaveChanges();

            return Ok(game);
        }

    }
}
