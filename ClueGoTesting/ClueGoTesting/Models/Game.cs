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
        
        public ICollection<GameLocation> GameLocations { get; set; }
        public IList<GameSuspect> GameSuspects { get; set; }
        public ICollection<GameClue> GameClues { get; set; }

        public User User { get; set; }

    }
}
