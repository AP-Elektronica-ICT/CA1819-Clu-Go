using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
namespace ClueGoTesting.Models
{
    public class User
    {

        //attributes
        public string Username { get; set; }
        public string Password { get; set; }
        public string Email { get; set; }

        List<Game> GameList = new List<Game>();
        //PK
        [Key]
        public long UserId { get; set; }

    }
}
