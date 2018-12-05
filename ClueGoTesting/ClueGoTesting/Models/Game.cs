using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
namespace ClueGoTesting.Models
{
    public class Game
    {
        [Key]
        public int GameId { get; set; }    
        public bool GameWon { get; set; }

        public IList<GameLocation> GameLocations { get; set; }
        //public ICollection<Suspect> GameSuspects { get; set; }
        //public ICollection<Clue> GetClues { get; set; }

    }
}
