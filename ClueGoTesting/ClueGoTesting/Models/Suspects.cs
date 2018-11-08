using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
namespace ClueGoTesting.Models
{
    public class Suspects
    {
        [Key]
        public int SuspectId { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public string Weapons { get; set; }
        public int ClueId { get; set; }

        public ICollection<Clues> Clues { get; set; }
    }
}
