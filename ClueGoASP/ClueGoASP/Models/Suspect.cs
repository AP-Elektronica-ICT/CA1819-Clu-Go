using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
namespace ClueGoASP.Models
{
    public class Suspect
    {
        public Suspect()
        {
            this.Clues = new HashSet<Clue>();
        }
        [Key]
        public int SusId { get; set; }
        public string SusName { get; set; }
        public string SusDescription { get; set; }
        public string SusWeapon { get; set; }
        public string SusImgUrl { get; set; }        
        public ICollection<Clue> Clues { get; set; }
        public ICollection<GameSuspect> GameSuspects { get; set; }
        //public ICollection<Clue> SuspectClues { get; set; }
    }
}
