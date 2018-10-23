using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using ClueGoTesting.Models;



namespace ClueGoTesting.Data
{
    public class GameContext :DbContext
    {
        public GameContext(DbContextOptions<GameContext> options) : base(options)
        {

        }

        public DbSet<Case> Cases { get; set; }
        public DbSet<Clues> Clues { get; set; }
        public DbSet<Game> Games { get; set; }
        public DbSet<GameData> GameDatas { get; set; }  
        public DbSet<Suspects> Suspects { get; set; }
        public DbSet<User> Users { get; set; }

        protected override void OnModelCreating(ModelBuilder modelbuilder)
        {
            modelbuilder.Entity<Case>().ToTable("Cases");
            modelbuilder.Entity<Clues>().ToTable("Clues");
            modelbuilder.Entity<Game>().ToTable("Games");
            modelbuilder.Entity<GameData>().ToTable("GameDatas");
            modelbuilder.Entity<Suspects>().ToTable("Suspects");
        }

    }
}
