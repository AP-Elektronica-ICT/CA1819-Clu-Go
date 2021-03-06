﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
namespace ClueGoASP.Models
{
    public class Game
    {
        public User User { get; set; }
        public int UserId { get; set; }
        public string Username { get; set; }
        [Key]
        public int GameId { get; set; }    
        public bool GameWon { get; set; }
        public ICollection<GameLocation> GameLocations { get; set; }
        public IList<GameSuspect> GameSuspects { get; set; }
        public ICollection<GameClue> GameClues { get; set; }



    }
}
