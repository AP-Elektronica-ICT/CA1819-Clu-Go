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


        //gets static object, not from databse atm
        
        
        [Route("user/{UID}")] // api/ClueGoTEsting/user/"uid"
        [HttpGet]
       
        public List<User> GetUsers(int UID)
        {
            var list = new List<User>();

            //TestDATA will delete later.
            /*
            list.Add(new User()
            {
                Name = "Jeff",
                UserId =UID,
                Email ="Jeff@Mynameis.com"

            });*/
            
            //get user with id UID
            var user = _dbcontext.Users.Find(UID);
            list.Add(user);

            return list;
         }



        // Adds a user by posting like this: /api/ClueGoTesting/add/?Name=test&Email=testemail

        [Route("add")]
        [HttpPost]
        public IActionResult AddUser([FromQuery ] User user )
        {
            var newUser = new User();
             newUser.Email = user.Email;
             newUser.Name = user.Name;

             _dbcontext.Users.Add(newUser);
             _dbcontext.SaveChanges();
             return Created("", newUser);
        }


        //Gets all Users In the Users Tabel.

        [Route("get")] //api/ClueGoTesting/get
        [HttpGet]
        public List<User> getUsers()
        {
            var listUsers = new List<User>();
            listUsers = _dbcontext.Users.ToList();
            
            return listUsers;
        }

    }
}
