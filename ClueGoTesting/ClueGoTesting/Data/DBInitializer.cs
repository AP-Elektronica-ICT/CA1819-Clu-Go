using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ClueGoTesting.Models;
using Microsoft.EntityFrameworkCore;

namespace ClueGoTesting.Data
{
    public class DBInitializer
    {
        public static void Initialize(GameContext context)
        {
            //Create the DB if not yet exists
            context.Database.EnsureCreated();

            //are there items present?

            if (!context.Clues.Any())
            {
                var clue = new Clues()
                {
                   // ClueId = 1,
                    description = "this is a clue",
                    difficulty ="really hard",
                    Distance = 15,
                    picture ="somerandomurlyey"

                };
                context.Clues.Add(clue);
                context.SaveChanges();
            }
            if(!context.Cases.Any())
            {
                var case1 = new Case()
                {
                  //  CaseId = 1,
                    GameInfo = "here is some random info about the case",
                    SuspectId = 1

                };
                context.Cases.Add(case1);
                context.SaveChanges();
            }
            if(!context.GameDatas.Any())
            {
                var gamedata1 = new GameData()
                {
                  //  GameDataId = 1,
                    GamesLost = 0,
                    GamesWon = 0,
                    CluesFound = 0,
                    UserScore = 0,
                    UserId = 1

                };
                context.GameDatas.Add(gamedata1);
                context.SaveChanges();

            }
            if (!context.Games.Any())
            {
                var game = new Game()
                {
                  CaseId = 1,
                    GameDataId = 1,
                   // GameId = 1
                };
                context.Games.Add(game);
                context.SaveChanges();

            }
            if (!context.Suspects.Any())
            {
                var suspect = new Suspects()
                {
                  //  SuspectId = 1,
                    ClueId =1,
                    Description ="description of the suspect",
                    Name ="suspect1",
                    Weapons = "knife"

                };
                context.Suspects.Add(suspect);
                context.SaveChanges();

            }

            if(!context.Users.Any())
            {
                var user = new User()
                {
                    Name = "Jeff",
                    Email ="randomemail@email.com",
                  // UserId =1
                };
                context.Users.Add(user);
                context.SaveChanges();
            }            
        }
    }
}
