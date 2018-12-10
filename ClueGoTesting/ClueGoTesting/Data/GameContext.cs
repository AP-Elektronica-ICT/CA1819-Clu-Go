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
        public DbSet<GameSuspect> GameSuspects { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Clue>().ToTable("Clues");
            modelBuilder.Entity<Game>().ToTable("Games");
            modelBuilder.Entity<Suspect>().ToTable("Suspects");
            modelBuilder.Entity<Location>().ToTable("Locations");
            modelBuilder.Entity<GameLocation>().ToTable("GameLocations");
            modelBuilder.Entity<GameSuspect>().ToTable("GameSuspects");

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


            modelBuilder.Entity<GameSuspect>()
                .HasKey(t => new { t.GameId, t.SusId });
            modelBuilder.Entity<GameSuspect>()
                .HasOne(x => x.Game)
                .WithMany(x => x.GameSuspects)
                .HasForeignKey(x => x.GameId);

            modelBuilder.Entity<GameSuspect>()
                .HasOne(s => s.Suspect)
                .WithMany(gs => gs.GameSuspects)
                .HasForeignKey(pt => pt.SusId);

            modelBuilder.Entity<GameClue>()
                .HasKey(t => new { t.GameId, t.ClueId });
            modelBuilder.Entity<GameClue>()
                .HasOne(x => x.Game)
                .WithMany(x => x.GameClues)
                .HasForeignKey(x => x.GameId);
            modelBuilder.Entity<GameClue>()
                .HasOne(s => s.Clue)
                .WithMany(gs => gs.GameClues)
                .HasForeignKey(pt => pt.ClueId);


            modelBuilder.Entity<User>()
                .HasMany(c => c.Games)
                .WithOne(e => e.User)
                .IsRequired();
        }

    }
}
