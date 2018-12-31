using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

using ClueGoASP.Models;
using ClueGoASP.Helper;
using ClueGoASP.Data;
using System.Security.Cryptography;
using System.Text;

namespace ClueGoASP.Services
{
    public interface IUserService
    {
        User GetUserById(int id);
        User Login(string username, string password);
        User CreateUser(User newUser);

        User Deleteuser(int id);
    }
    public class UserService : IUserService
    {
        private GameContext _dbContext;
        public UserService(GameContext context)
        {
            _dbContext = context;
        }
        public User GetUserById(int id)
        {
            return _dbContext.Users.Find(id);
        }

        public User Login(string username, string password)
        {
            var user = _dbContext.Users.SingleOrDefault(c => c.Username == username);

            if (user == null)
                throw new AppException("Username not found");
            else if (user.Password != PasswordHash(password))
                throw new AppException("Wrong password");
            else
                return user;
        }

        public User CreateUser(User newUser)
        {
            string _pwd = newUser.Password;
            //passwoord hashen
            newUser.Password = PasswordHash(newUser.Password);

            bool usernameAlreadyExists = _dbContext.Users.Any(x => x.Username == newUser.Username);
            bool emailAlreadyExists = _dbContext.Users.Any(x => x.Email == newUser.Email);

            //Validation check
            if (_pwd == newUser.Username)
                throw new AppException("Password cannot match username!");
            else if (_pwd.Length < 6)
                throw new AppException("Password must be atleast 6 characters long!");
            else if (usernameAlreadyExists)
                throw new AppException("This username is already registerd!");
            else if (emailAlreadyExists)
                throw new AppException("This e-mail has already been registerd!");
            else
            {
                _dbContext.Users.Add(newUser);
                _dbContext.SaveChanges();
                return (newUser);
            }
        }

        public User Deleteuser(int id)
        {
            var user = _dbContext.Users.SingleOrDefault(x => x.UserId == id);

            if (user == null)
                throw new AppException("User does not exist.");
            else
            {
                _dbContext.Users.Remove(user);
                _dbContext.SaveChanges();
                return null;
            }
        }

       /* public User ClearUsers(int id)
        {
            for (int _id = id; _id < _dbContext.Users.Count(); _id++)
            {
                id = _id;
                var user = _dbContext.Users.SingleOrDefault(x => x.UserId == id);
                if (user == null)
                    return NotFound();

                _dbContext.Users.Remove(user);
                _dbContext.SaveChanges();
            }
            return Content("Delete succes!");
        }*/

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
