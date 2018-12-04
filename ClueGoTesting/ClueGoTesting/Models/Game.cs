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
        public int GameDataId { get; set; } // each game is linked to gamedata of a user.
        public int CaseId { get; set; } // each game gets a random caseId, case is the "story"

        public ICollection<GameLocation> gameLocations { get; set; } // each game gets a list of locations depending on where you are.

    }
}
