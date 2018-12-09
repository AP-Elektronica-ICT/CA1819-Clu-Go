using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ClueGoTesting.Models
{
    public class GameLocation
    {
        public int GameId { get; set; }
        public int LocId { get; set; }

        public Game Game { get; set; }
        public Location Location { get; set; }
    }
}
