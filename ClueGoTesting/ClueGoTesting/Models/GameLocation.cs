using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ClueGoTesting.Models
{
    public class GameLocation

    {
        public int GameId { get; set; }
        public Game Game { get; set; }
        public int locId { get; set; }
        public Location Location { get; set; }
    }
}
