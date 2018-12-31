using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
namespace ClueGoASP.Models
{
    public class User
    {

        //attributes
        public string Username { get; set; }
        public string Password { get; set; }
        public string Email { get; set; }
        public int GamesPlayed { get; set; }
        public int cluesFound { get; set; }
        public int distanceWalked { get; set; }

        public virtual ICollection<Game> Games { get; set; }
        //PK
        [Key]
        public int UserId { get; set; }

    }
}
