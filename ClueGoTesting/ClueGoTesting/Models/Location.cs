using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using System.ComponentModel.DataAnnotations;

namespace ClueGoTesting.Models
{
    public class Location
    {
        public double LocLat { get; set; }
        public double LocLong { get; set; }
        public string LocDescription { get; set; }
        public string LocName { get; set; }

        [Key]
        public int LocId { get; set; }
    }
}
