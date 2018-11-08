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
        
       
      
        //get a json with all gameData
        // [Route("{GID}")]// api/ClueGoTesting/gamedata/"gid"
        [HttpGet("gamedata/{GDID}")]
        public List<GameData> GetGameData(int GDID)
        {
            var listGameData = new List<GameData>();
            var gameDataById = new GameData();

            int id = GDID;

            gameDataById = _dbcontext.GameDatas.Find(id);
            listGameData = _dbcontext.GameDatas.ToList();
            return listGameData;
        }



        //find Case by Id
        [HttpGet("case/{CID}")]
        public Case GetCase(int CID)
        {

            var caseById = new Case();
            int id = CID;

            caseById = _dbcontext.Cases.Find(id);

            return caseById;
           
        }

        //find Game by ID
        [HttpGet("{GID}")]
        public Game getGame(int GID)
        {

            var gameById = new Game();
            int id = GID;
            
            gameById = _dbcontext.Games.Find(id);
            
            return gameById;
          
        }

        //get game info from certain game
        //I know the ID of a game and I need the gameinfo from that particular game.
        
        [HttpGet("gameinfo/{GID}")]
        public String getGameInfoFromGame(int GID)
        {
            var gameById = new Game();
            var caseFromGame = new Case();
            string gameInfo;
            gameById = getGame(GID);

            var caseId = gameById.CaseId;
            caseFromGame = GetCase(caseId);
            gameInfo = caseFromGame.GameInfo;

            return gameInfo;
            
        }
        

        [HttpPost("newgame")]

        public Game CreateNewGame()
        {
            var newGame = new Game();
            var newCase = new Case();
            var newGameData = new GameData();
            var listSuspects = new List<Suspects>();

            //Create a suspect,Normaly not necesary, Database will be filled with suspect profiles and random one will be chosen from these.
            var suspect = new Suspects()
            {
                //  SuspectId = 1,

                Description = "description of the  new suspect",
                Name = "suspect1",
                Weapons = "knife"

            };
            //add suspects to the list of suspects within the case data.
            listSuspects.Add(suspect);
            




            newGame.CaseId = newCase.CaseId;
            newGame.GameDataId = newGameData.GameDataId;

            newCase.GameInfo = "game info placeholder data";
            newCase.Suspects = listSuspects;


            _dbcontext.Suspects.Add(suspect);
            _dbcontext.Cases.Add(newCase);
            _dbcontext.Games.Add(newGame);
            _dbcontext.SaveChanges();

            Console.WriteLine(newCase.Suspects);
            
            return newGame;

        }
         
        

        
        


        //get a json with gamedata of a user.
        //[Route("{UID}")]
        [HttpGet("{UID}")]

        public List<GameData> GetUserGameData (int GID)
        {
            //Logic missing
            return null;
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
