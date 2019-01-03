using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ClueGoASP.Models
{
    public class Clue
    {
        
        [Key]
        public int ClueId { get; set; }
        public string ClueName { get; set; }
        public string ClueType { get; set; }
        public string ClueImgUrl { get; set; }
        public string ClueDescription { get; set; }
        public bool Alibi { get; set; }
        public bool Found { set; get; }
        public ICollection<GameClue> GameClues { get; set; }
        public int SusForeignKey { get; set;}
        public Suspect Suspect { get; set; }


    }
}
