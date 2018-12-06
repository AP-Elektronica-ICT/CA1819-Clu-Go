using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
namespace ClueGoTesting.Models
{
    public class Suspect
    {
        [Key]
        public int SusId { get; set; }
        public string SusName { get; set; }
        public string SusDescription { get; set; }
        public string SusWeapon { get; set; }
        public string SusImgUrl { get; set; }
    }
}
