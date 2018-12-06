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

        public DbSet<Clue> Clues { get; set; }
        public DbSet<Game> Games { get; set; }
        public DbSet<Suspect> Suspects { get; set; }
        public DbSet<User> Users { get; set; }
        public DbSet<Location> Locations { get; set; }
        public DbSet<GameLocation> GameLocations { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Clue>().ToTable("Clues");
            modelBuilder.Entity<Game>().ToTable("Games");
            modelBuilder.Entity<Suspect>().ToTable("Suspects");
            modelBuilder.Entity<Location>().ToTable("Locations");
            modelBuilder.Entity<GameLocation>().ToTable("GameLocation");

            modelBuilder.Entity<GameLocation>()
                .HasKey(t => new { t.GameId, t.LocId });

            modelBuilder.Entity<GameLocation>()
                .HasOne(x => x.Game)
                .WithMany(x => x.GameLocations)
                .HasForeignKey(x => x.GameId);

            modelBuilder.Entity<GameLocation>()
                .HasOne(l => l.Location)
                .WithMany(gl => gl.GameLocations)
                .HasForeignKey(pt => pt.LocId);
        }

    }
}
