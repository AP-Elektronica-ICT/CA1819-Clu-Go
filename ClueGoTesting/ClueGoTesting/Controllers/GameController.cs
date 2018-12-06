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

<<<<<<< HEAD
        
        [HttpGet("case/{GID}")]
        public Case getCaseFromGameId(int GID)
        {
            var game = getGame(GID);
            var caseId = game.CaseId;
            var caseFromGame = _dbcontext.Cases.First(c => c.CaseId == caseId);

            return caseFromGame;
        }
        [HttpGet("gameinfocase/{GID}")]
        public String getGameInfoByCaseId(int GID)
        {
            var caseTemp = GetCase(GID);
            var gameinfo = caseTemp.GameInfo;

            return gameinfo;

        }
        

        [HttpGet("gameinfo/{GID}")]
        public String getGameInfoFromGame(int GID)
        {
            String gameInfo;
            var caseForInfo = getCaseFromGameId(GID);

            gameInfo = caseForInfo.GameInfo;

            return gameInfo;
        }

        [HttpGet("suspects/{GID}")]

        public List<Suspects> getSuspectsFromGame(int GID)
        {
            var listSuspects = new List<Suspects>();

            var caseFromGame = getCaseFromGameId(GID);

            listSuspects = caseFromGame.Suspects.ToList();
              
            return listSuspects;
        }

        [HttpGet("gamedata/{GID}")]

        public GameData getGameDataFromGameId (int GID)
        {
            var game = getGame(GID);
            var gameDataId = game.GameDataId;
            var gameData = _dbcontext.GameDatas.First(c => c.GameDataId == gameDataId);

            return gameData;
        }

        

        [HttpPost("newcase")]

        public Case CreateCase()
        {
            var newcase =  new Case();
            
            
            newcase.GameInfo = "this is placeholderdata, normaly there would be an introduction to a game in here.";

            _dbcontext.Cases.Add(newcase);
            _dbcontext.SaveChanges();

            return newcase;     
        }



        [HttpPost("newgame/{UID}/{CID}")]

        public Game CreateNewGame(int CID,int UID)
        {
            int caseId = CID;
            var newGame = new Game();
            var newGameData = new GameData();
            var newcase = GetCase(caseId);
            
=======
            });
>>>>>>> gamestart_arno

            }


            // Get Random Location
            var r = new Random();
            var index = r.Next(0, locations.Count);


            _dbcontext.Games.Add(game);
            _dbcontext.SaveChanges();

            return Ok(game);
        }

    }
}
