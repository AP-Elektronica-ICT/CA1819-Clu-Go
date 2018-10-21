using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
namespace ClueGoTesting.Models
{
    public class Game
    {
        [Key]
        public int GameId { get; set; }    
        public int GameDataId { get; set; }
        public int CaseId { get; set; }
    }
}
