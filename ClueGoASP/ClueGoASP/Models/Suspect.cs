using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
namespace ClueGoASP.Models
{
    public class Suspect
    {
        [Key]
        public int SusId { get; set; }
        public string SusName { get; set; }
        public string SusDescription { get; set; }
        public string SusWeapon { get; set; }
        public string SusImgUrl { get; set; }
        public bool isMurderer { get; set; }

        public ICollection<GameSuspect> GameSuspects { get; set; }

    }
}
