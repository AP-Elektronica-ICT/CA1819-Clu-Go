using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
namespace ClueGoTesting.Models
{
    public class GameData
    {
        
        //PK
        [Key]
        public int GameDataId { get; set; }

        //FK
        public int UserId { get; set; }

        //attributes
        public int CluesFound { get; set; }
        public int GamesWon { get; set; }
        public int GamesLost { get; set; }
        public int UserScore { get; set; }
    }
}
