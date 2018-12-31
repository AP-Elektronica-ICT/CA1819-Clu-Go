using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.Text;
using System.Security.Cryptography;
using Microsoft.EntityFrameworkCore;

using ClueGoASP.Models;
using ClueGoASP.Services;
using ClueGoASP.Helper;

namespace ClueGoASP.Data
{

    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly GameContext _dbContext;
        private IUserService _userService;

        public UserController(GameContext context, IUserService userService)
        {
            _dbContext = context;
            _userService = userService;
        }
        

        [HttpGet]
        public ActionResult<List<User>> GetAll()
        {
            return _dbContext.Users
                        .Include(x => x.Games)
                        .ToList();
        }
        

        [HttpGet("{userId}")]
        public ActionResult<List<User>> GetById(int userId)
        {
            var user = _userService.GetUserById(userId);

            return _dbContext.Users
                        .Include(x => x.Games)
                        .Where(x => x.UserId == userId)
                        .ToList();
        }

        [HttpGet("inlog/{username}/{password}")]
        public IActionResult GetByEmail(string username, string password)
        {
            try
            {
                var user = _userService.Login(username, password);
                return Ok(user);
            }
            catch(AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }


        [HttpPost]
        public IActionResult Create(User newUser)
        {
            try
            {
                var user = _userService.CreateUser(newUser);
                return Ok(newUser);
            }
            catch(AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }

        [HttpPut("{username}")]
        public IActionResult Update(string id, User user)
        {
            var todo = _dbContext.Users.Find(id);
            if (todo == null)
            {
                return NotFound();
            }

            todo.Password = user.Password;
            todo.Username = user.Username;

            _dbContext.Users.Update(todo);
            _dbContext.SaveChanges();
            return NoContent();
        }

        [HttpDelete("{userId}")]
        public IActionResult DeleteUser(int userId)
        {
            try
            {
                var user = _userService.Deleteuser(userId);
                return Ok("User deleted.");
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
        }
    }
}