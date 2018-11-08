﻿using System;
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
                    DescriptionClue = "this is a clue",
                    Difficulty ="really hard",
                    Distance = 15,
                    Picture ="somerandomurlyey"

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
                    
                    Description ="description of the suspect",
                    Name ="suspect1",
                    Weapons = "knife"

                };
                context.Suspects.Add(suspect);
                context.SaveChanges();

            }

            if (!context.Users.Any())
            {
                var admin = new User()
                {
                    Username = "WeynsA",
                    Email = "weyns.arno@gmail.com",
                    Password = "123456"
                };

                var admin1 = new User()
                {
                    Username = "MassureA",
                    Email = "s091998@ap.be",
                    Password = "3D9188577CC9BFE9291AC66B5CC872B7"
                };

                var admin2 = new User()
                {
                    Username = "JoppeM",
                    Email = "joppe.mertens@gmail.com",
                    Password = "3D9188577CC9BFE9291AC66B5CC872B7"
                };

                var admin3 = new User()
                {
                    Username = "AlIbra",
                    Email = "s091997@ap.be",
                    Password = "azerty"
                };
                context.Users.Add(admin);
                context.Users.Add(admin1);
                context.Users.Add(admin2);
                context.Users.Add(admin3);

                context.SaveChanges();
            }
            
            if (!context.Locations.Any())
            {

                var location1 = new Location()
                {
                    LocName = "Brabo",
                    LocLat = 51.221228,
                    LocLong = 4.399698,
                    LocDescription = "Standbeeld van Brabo."
                };
                var location2 = new Location()
                {
                    LocName = "Standbeeld Stadhuis",
                    LocLat = 51.220884,
                    LocLong = 4.398995,
                    LocDescription = "Standbeeld Vrijheid blijheid nabij stadhuis."
                };
                var location3 = new Location()
                {
                    LocName = "Het Steen",
                    LocLat = 51.222773,
                    LocLong = 4.397367,
                    LocDescription = "Het Steen"
                };
                var location4 = new Location()
                {
                    LocName = "Pieter Paul Rubens",
                    LocLat = 51.219326,
                    LocLong = 4.401576,
                    LocDescription = "Groenplaats, standbeeld Pieter Paul Rubens."
                };
                context.Locations.Add(location1);
                context.Locations.Add(location2);
                context.Locations.Add(location3);
                context.Locations.Add(location4);
                context.SaveChanges();
            }
        }
    }
}
