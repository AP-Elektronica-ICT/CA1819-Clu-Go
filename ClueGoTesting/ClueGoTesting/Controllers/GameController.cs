using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ClueGoTesting.Models;
using ClueGoTesting.Data;

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
        
        //find Game by ID
        [HttpGet("{GID}")]
        public Game getGame(int GID)
        {
            var defGame = new Game()
            {
                CaseId = 9999,
                GameDataId = 9999
            };

            var gameById = _dbcontext.Games.Find(GID);

            if (gameById != null)
            {
                return gameById;
            }

            else { return defGame; }
        }

        //find Case by Id
        [HttpGet("case/{CID}")]
        public Case GetCase(int CID)
        {
            var defCase = new Case()
            {
                GameInfo ="default,case does not exist",
            };

            var caseById = new Case();
            int id = CID;


            caseById = _dbcontext.Cases.Find(id);
            if (caseById != null)
            {
                return caseById;
            }
            else { return defCase; }
        }


        [HttpGet("gamedata/{GDID}")]

        public GameData getGameData(int GDID)
        {
            var defGameData = new GameData()
            {
                CluesFound = 9999,
                GamesLost = 9999,
                GamesWon = 9999,

            };
            var gameDataById = _dbcontext.GameDatas.Find(GDID);
            if(gameDataById != null)
            {
                return gameDataById;
            }
            return defGameData;
        }

        //Get Data that is affiliated with a certain GameId(GID)

        
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
            
            var suspect = new Suspects()
            {
                //  SuspectId = 1,

                Description = "Case created",
                Name = "Mr CreateCaseTester",
                Weapons = "fork"

            };
            
            newcase.GameInfo = "this is placeholderdata, normaly there would be an introduction to a game in here.";
            newcase.Suspects.Add(suspect);

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
            


            newGame.CaseId = newcase.CaseId;
            newGame.GameDataId = newGameData.GameDataId;



            
            _dbcontext.Games.Add(newGame);
            _dbcontext.SaveChanges();

            
            
            return newGame;

        }
        
        


        //ADD Game data to db
        //[Route("add/")] 
        [HttpPost("add/")]
        public IActionResult AddGameData([FromQuery] GameData gamedata)

        {
            _dbcontext.GameDatas.Add(gamedata);
            _dbcontext.SaveChanges();
            return Created("", gamedata);

        }

        //gets static object, not from databse at


        // Adds a user by posting like this: /api/ClueGoTesting/add/?Name=test&Email=testemail

       // [Route("add")]
        [HttpPost("add")]
        public IActionResult AddUser([FromQuery ] User user )
        {
            var newUser = new User
            {
                Email = user.Email,
                Username = user.Username
            };

            _dbcontext.Users.Add(newUser);
             _dbcontext.SaveChanges();
             return Created("", newUser);
        }


        //Gets all Users In the Users Tabel.

      //  [Route("get")] //api/ClueGoTesting/get
        [HttpGet("get")]
        public List<User> GetUsers()
        {
            var listUsers = new List<User>();
            listUsers = _dbcontext.Users.ToList();
            
            return listUsers;
        }
























    }
}
