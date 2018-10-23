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
        [HttpGet("{GID}")]
        public List<GameData> GetGameData()
        {
            var listGameData = new List<GameData>();
            listGameData = _dbcontext.GameDatas.ToList();
            return listGameData;
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
