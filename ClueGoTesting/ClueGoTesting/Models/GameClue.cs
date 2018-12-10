using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ClueGoTesting.Models
{
    public class GameClue
    {
        public int ClueId { get; set; }
        public int GameId { get; set; }
        public Game Game { get; set; }
        public Clue Clue { get; set; }
    }
}
