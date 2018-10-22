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
        public string Name { get; set; }
        public string Password { get; set; }
        public string Email { get; set; }
        //PK
        [Key]
        public int UserId { get; set; }

    }
}
