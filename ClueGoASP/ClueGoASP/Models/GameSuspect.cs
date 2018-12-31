using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ClueGoASP.Models
{
    public class GameSuspect
    {
        public int GameId { get; set; }
        public int SusId { get; set; }
        public bool isMurderer { get; set; }

        public Game Game { get; set; }
        public Suspect Suspect { get; set; }
    }
}
