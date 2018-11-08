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
    [Route("api/ClueGoTesting")]
    [ApiController]
    public class GameController : ControllerBase
    {
        private GameContext _dbcontext;
        
        public GameController(GameContext dbcontext)
        {
            _dbcontext = dbcontext;
        }
        


        [Route("gamedata/{GID}")]// api/ClueGoTesting/gamedata/"gid"
        [HttpGet]
        public List<GameData> GetGameData(int GID)
        {
            var list = new List<GameData>();

            list.Add(new GameData()
            {
                UserId = 2,
                GamesLost = 0,
                GameDataId =GID,
                GamesWon =1,
                CluesFound =5,
                UserScore =2,
                
            });
            
            return list;
        }
        [Route("user/{UID}")] // api/ClueGoTEsting/user/"uid"
        [HttpGet]

        public List<User> GetUsers(int UID)
        {
            var list = new List<User>();

            list.Add(new User()
            {
                Name = "Jeff",
                UserId =UID,
                Email ="Jeff@Mynameis.com"

            });

            return list;
         }
        [Route("adduser")]
        [HttpPost]
        public IActionResult AddUser([FromBody] User newUser)
        {
            _dbcontext.Users.Add(newUser);
            _dbcontext.SaveChanges();
            return Created("", newUser);
        }

        [Route("get")] //api/ClueGoTesting/get
        [HttpGet]
        public List<User> getUsers()
        {
            var list = new List<User>();
            list = _dbcontext.Users.ToList();

            return list;
        }

    }
}
