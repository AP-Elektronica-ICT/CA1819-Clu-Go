using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;

namespace ClueGoTesting.Models
{
    public class Case
    {
        [Key]
        public int CaseId { get; set; }
        public string GameInfo { get; set; } // each case has different info
        
      //  public int SuspectId { get; set; }
        public ICollection<Suspects> Suspects { get; set; } // each case gets a few random suspects
    }
}
