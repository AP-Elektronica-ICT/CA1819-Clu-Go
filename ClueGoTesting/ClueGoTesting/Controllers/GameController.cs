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
        [HttpGet]
        public ActionResult<List<Game>> GetAll()
        {
            return _dbcontext.Games.ToList();
        }
        [HttpGet("gamelocations")]
        public ActionResult<List<GameLocation>> GetGameLocation()
        {
            return _dbcontext.gameLocations.ToList();
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
                GameInfo = "default,case does not exist",
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
            if (gameDataById != null)
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

        public GameData getGameDataFromGameId(int GID)
        {
            var game = getGame(GID);
            var gameDataId = game.GameDataId;
            var gameData = _dbcontext.GameDatas.First(c => c.GameDataId == gameDataId);

            return gameData;
        }
        [HttpGet("usergamedata/{UID}")]
        public GameData getGameDataFromUser(int UID)
        {
            var gamedata = new GameData();
            var defGameData = new GameData()
            {
                UserId = 9999,
                CluesFound = 0
            };

            gamedata = _dbcontext.GameDatas.First(c => c.UserId == UID);
            return gamedata;

        }

        [HttpGet("gamesofuser/{UID}")]
        public Game getGameOfUser(int UID)
        {
            var gameDataUser = getGameDataFromUser(UID);
            var gameDataID = gameDataUser.GameDataId;
            var game = new Game();
            game = _dbcontext.Games.First(c => c.GameDataId == gameDataID);

            return game;
        }
        [HttpGet("getlocationslist/{UID}")]
        public List<GameLocation> getLocationsOfGame(int UID)
        {
            var game = new Game();
            game = getGameOfUser(UID);
            var gameId = game.GameId;
            var gamelocationList = new List<GameLocation>();

            gamelocationList = _dbcontext.gameLocations.Where(x => x.GameId == gameId).ToList();

            for (int i = 0; i < gamelocationList.Count; i++)
            {

                var item = gamelocationList[i].locId;
            }

            return gamelocationList;

        }
        [HttpGet("getlocation/{UID}")]
        public ActionResult<List<GameLocation>> GetLocation(int UID)
        {
            var listLocation = new List<Location>();
            var listLocId = new List<GameLocation>();
            listLocId = getLocationsOfGame(UID);
            var ids = new List<int>();
            for (int i = 0; i < listLocId.Count; i++)
            {
                ids.Add(listLocId[i].locId);
            }
            for (int i = 0; i< ids.Count; i++)
            {
                var location = new Location();
                location = _dbcontext.Locations.Find(ids[i]);

                listLocation.Add(location);
                
            }
            return listLocId;
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


        //Create a new game if there are none with the same GameDataID, the games cannot have the same gamedataID because a user can only have one active game at a time.
        [HttpPost("newgame/{UID}/{CID}")]

        public IActionResult CreateNewGame(int CID,int UID)
        {
            
            var gamedata = getGameDataFromUser(UID);
            try {
                var games = _dbcontext.Games.First(c => c.GameDataId == gamedata.GameDataId);
                String exists = "there is already a game entry with this gamedataid";

                return Created("",exists);

            }
            catch
            {

                var returnGame = new Game()
                {
                    CaseId = CID,
                    GameDataId = gamedata.GameDataId
                };

                var location = new Location();
                var location2 = new Location();
                location2 = _dbcontext.Locations.Find(2);
                location = _dbcontext.Locations.Find(1);
                returnGame.gameLocations = new List<GameLocation>
            {
                new GameLocation
                {
                    Game = returnGame,
                    Location = location,
                    GameId = returnGame.GameId,
                    locId = location.LocId
                },
                new GameLocation
                {
                    Game = returnGame,
                    Location = location2,
                    GameId = returnGame.GameId,
                    locId = location2.LocId
                }

            };

                _dbcontext.Games.Add(returnGame);
                _dbcontext.SaveChanges();

                return Created("", returnGame);
            }
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
