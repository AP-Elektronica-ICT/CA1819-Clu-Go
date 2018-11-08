using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using System.ComponentModel.DataAnnotations;

namespace ClueGoTesting.Models
{
    public class Clues
    {
        
        [Key]
        public int ClueId { get; set; }
        public int Distance { get; set; }
        public string difficulty { get; set; }
        public string description { get; set; }
        public string picture { get; set; }
        }
}
