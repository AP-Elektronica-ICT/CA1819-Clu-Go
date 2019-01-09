using ClueGoASP.Data;
using ClueGoASP.Models;
using ClueGoASP.Services;
using System;
using Xunit;
using System.Collections.Generic;
using ClueGoASP.Helper;
using Microsoft.EntityFrameworkCore;

namespace XUnitTestClueGo
{
    public class GameTestShould
    {
        private static GameService gameService;
        private static UserService userService;

        private readonly DbContextOptions<GameContext> options;
        public readonly Game testGame;
        public readonly User testUser;
        public Location testLocation;


        public GameTestShould()
        {
     
            //Arrange For the all tests
            options = new DbContextOptionsBuilder<GameContext>().UseInMemoryDatabase(databaseName: "GameTestDB").Options;
            //arrange
            using (var globalContext = new GameContext(options))
            {
                gameService = new GameService(globalContext);
                userService = new UserService(globalContext);

                var testUser = new User()
                {
                    Username = "tempUser",
                    Password = "123456"
                };
                globalContext.Users.Add(testUser);
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
                var location5 = new Location()
                {
                    LocName = "Politiekantoor",
                    LocLat = 51.230754,
                    LocLong = 4.4174065,
                    LocDescription = "Politiekantoor"
                };
                var location6 = new Location()
                {
                    LocName = "TestLocation6"
                };
                var location7 = new Location()
                {
                    LocName = "TestLocation7"
                };
                var location8 = new Location()
                {
                    LocName = "TestLocation8"
                };
                var location9 = new Location()
                {
                    LocName = "TestLocation9"
                };
                var location10 = new Location()
                {
                    LocName = "TestLocation10"
                };
                globalContext.Add(location2);
                globalContext.Add(location3);
                globalContext.Add(location4);
                globalContext.Add(location5);
                globalContext.Add(location6);
                globalContext.Add(location7);
                globalContext.Add(location8);
                globalContext.Add(location9);
                globalContext.Add(location10);

                var suspect0 = new Suspect()
                {
                    SusName = "Miss Scarlett",
                    SusWeapon = "Rope",
                    SusDescription = "Ms. Vivienne Sakura Scarlet",
                    SusImgUrl = "https://i.pinimg.com/originals/95/ce/3d/95ce3da06af8b1c09a4b2d4fa603b7a0.jpg",
                };
                var suspect1 = new Suspect()
                {
                    SusName = "Mr. Green",
                    SusWeapon = "Wooden cross",
                    SusDescription = "Rev. Jonathan Green.",
                    SusImgUrl = "https://pbs.twimg.com/profile_images/447953368271814657/Inf33QvJ.jpeg",
                };
                var suspect2 = new Suspect()
                {
                    SusName = "Colonel Mustard",
                    SusWeapon = "Gun",
                    SusDescription = "Col. Michael Mustard",
                    SusImgUrl = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/07/04/08/unspecified-3.jpg?width=1368&height=912&fit=bounds&format=pjpg&auto=webp&quality=70",
                };

                var suspect3 = new Suspect()
                {
                    SusName = "Dr.Orchid",
                    SusWeapon = "Syringe",
                    SusDescription = "A Doctor, Elegant ",
                    SusImgUrl = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/07/04/08/unspecified-4.jpg?width=1368&height=912&fit=bounds&format=pjpg&auto=webp&quality=70",
                };
                globalContext.Suspects.Add(suspect0);
                globalContext.Suspects.Add(suspect1);
                globalContext.Suspects.Add(suspect2);
                globalContext.Suspects.Add(suspect3);

                var clue0 = new Clue()
                {
                    ClueName = "RansomPuzzle"
                };
                var clue1 = new Clue()
                {
                    ClueName = "ARKnife"
                };
                var clue2 = new Clue()
                {
                    ClueName = "ARRope"
                };
                globalContext.Clues.Add(clue0);
                globalContext.Clues.Add(clue1);
                globalContext.Clues.Add(clue2);
                globalContext.SaveChanges();


            }
        }

       [Fact]
        public void Throw_AppExceptionTooManyItems_Less3ItemsAdded()
        {
            //Arrange
            var options = new DbContextOptionsBuilder<GameContext>().UseInMemoryDatabase(databaseName: "DBtest1").Options; 

            using (var globalContext = new GameContext(options))
            {
                gameService = new GameService(globalContext);
                //act and assert 
                Exception ex = Assert.Throws<AppException>(() => gameService.CreateGame(1, 2));
                Assert.Contains("between", ex.Message);
            }            
        }

       [Fact]
        public void Throw_AppExceptionTooManyItems_More8ItemsAdded()
        {
            //Arrange
            var options = new DbContextOptionsBuilder<GameContext>().UseInMemoryDatabase(databaseName: "DBtest3").Options; 

            using (var globalContext = new GameContext(options))
            {
                gameService = new GameService(globalContext);
                //act and assert 
                Exception ex = Assert.Throws<AppException>(() => gameService.CreateGame(1, 9));
                Assert.Contains("between", ex.Message);
            }            
        }

       [Fact]
        public void Throw_AppExceptionWrongUser()
        {
            //Arrange
            var options = new DbContextOptionsBuilder<GameContext>().UseInMemoryDatabase(databaseName: "DBtest2").Options;

            using (var globalContext = new GameContext(options))
            {
                gameService = new GameService(globalContext);
                //act and assert 
                Exception ex = Assert.Throws<AppException>(() => gameService.CreateGame(2, 3));
                Assert.Equal("User does not exist.", ex.Message);
            }            
        }
        [Fact]
        public void Pass_SetClueFound()
        {
            using (var globalContext = new GameContext(options))
            {
                gameService = new GameService(globalContext);
                //act
                var result1 = gameService.SetGameClueFound(3);
                var result = gameService.GetFoundClues(3).Count;

                //Assert
                Assert.Equal(1, result);
                Assert.Contains("changed to found", result1);
            }
        }
        [Fact]
        public void Pass_GetFoundClues()
        {
            var options = new DbContextOptionsBuilder<GameContext>().UseInMemoryDatabase(databaseName: "GameClueTests").Options;

            using (var globalContext = new GameContext(options))
            {
                gameService = new GameService(globalContext);
                //act
                gameService.CreateGameFull(1, 3);
                var game = gameService.CreateGameFull(1, 3);
                var result = gameService.GetFoundClues(3).Count;
                //Assert
                Assert.Equal(1, result);
            }
        }
        [Fact]
        public void Pass_GetNotFoundClues()
        {
            using (var globalContext = new GameContext(options))
            {
                gameService = new GameService(globalContext);
                //act
                var result = gameService.GetFoundClues(3).Count;
                //Assert
                Assert.Equal(2, result);
            }
        }

        //NEEDS TO BE TESTED SEPERATE!!
        [Fact]
        public void Pass_CreateGame()
        {
            //Arrange
            //var options = new DbContextOptionsBuilder<GameContext>().UseInMemoryDatabase(databaseName: "CreateGame").Options;

            using (var globalContext = new GameContext(options))
            {
                gameService = new GameService(globalContext);
                //act
                var result = gameService.CreateGameFull(1, 3);
                //Assert
                Assert.Equal("tempUser", result.User.Username);
            }            
        }




    }
}
