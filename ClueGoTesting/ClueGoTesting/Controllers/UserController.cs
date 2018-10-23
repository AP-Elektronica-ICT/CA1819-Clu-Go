using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using ClueGoTesting.Models;
using System.Text;
using System.Security.Cryptography;

namespace ClueGoTesting.Data
{

    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly GameContext _context;

        public UserController(GameContext context)
        {
            _context = context;

            if (_context.Users.Count() == 0)
            {

                _context.Users.Add(new User { Username = "Admin" });
                _context.SaveChanges();
            }
        }
        

        [HttpGet]
        public ActionResult<List<User>> GetAll()
        {
            return _context.Users.ToList();
        }
        

        [HttpGet("{userId}")]
        public ActionResult<User> GetById(long userId)
        {
            var item = _context.Users.Find(userId);

            return item;
        }

        [HttpGet("inlog/{username}/{password}")]
        public ActionResult<User> GetByEmail(string username, string password)
        {
            var item = _context.Users.SingleOrDefault(c => c.Username == username && (c.Password == PasswordHash(password) || c.Password == password));
            //==================== Pass Validation check =======================


            if (item == null)
            {
                return Content("Email does not match password");
            }

            return Ok("Log in succesful");
        }


        [HttpPost]
        public IActionResult Create(User newUser)
        {
            string serverResponse;
            string _pwd = newUser.Password;
            //passwoord hashen
            newUser.Password = PasswordHash(newUser.Password);

            bool usernameAlreadyExists = _context.Users.Any(x => x.Username == newUser.Username);
            bool emailAlreadyExists = _context.Users.Any(x => x.Email == newUser.Email);

            if (_pwd == newUser.Username)
                serverResponse = "Password cannot match username!";
            else if (_pwd.Length < 6)
                serverResponse = "Password must be atleast 6 characters long!";
            else if (usernameAlreadyExists)
                serverResponse = "This username is already registerd!";
            else if (emailAlreadyExists)
                serverResponse = "This e-mail has already been registerd!";
            else if (ModelState.IsValid)
            {
                _context.Users.Add(newUser);
                _context.SaveChanges();
                return Ok(newUser);
            }
            else
                serverResponse = "Action not allowed";

            return Ok(serverResponse);
        }

        [HttpPut("{username}")]
        public IActionResult Update(string id, User user)
        {
            var todo = _context.Users.Find(id);
            if (todo == null)
            {
                return NotFound();
            }

            todo.Password = user.Password;
            todo.Username = user.Username;

            _context.Users.Update(todo);
            _context.SaveChanges();
            return NoContent();
        }

        [HttpDelete("{userId}")]
        public IActionResult DeleteUser(int id)
        {
            for (int _id = id; _id < _context.Users.Count(); _id++)
            {
                id = _id;
                var user = _context.Users.SingleOrDefault(x => x.UserId == id);
                if (user == null)
                    return NotFound();

                _context.Users.Remove(user);
                _context.SaveChanges();
            }
            return Content("Delete succes!");
        }

        public string PasswordHash(string pwdHash)
        {
            MD5 mD5 = MD5.Create();
            string stringToHash = pwdHash;
            byte[] tmpHash = Encoding.ASCII.GetBytes(stringToHash);
            byte[] hash = mD5.ComputeHash(tmpHash);

            StringBuilder sb = new StringBuilder();
            foreach (var a in hash)
                sb.Append(a.ToString("X2"));

            return sb.ToString();
        }

    }
}