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
   public class UserTestShould
    {
        private static UserService userService;
        private readonly DbContextOptions<GameContext> options;
        public readonly User tempUser;
        public UserTestShould()
        {
            //Arrange For the all tests
            options = new DbContextOptionsBuilder<GameContext>().UseInMemoryDatabase(databaseName: "UnitTestDB").Options;
        }

        [Fact]
        public void Throw_AppExceptionShortUsername_For_3Characters()
        {
            //arrange
            using (var context = new GameContext(options))
            {
                userService = new UserService(context);
                var tempUser = new User()
                {
                    Username = "iii",
                    Password = "123456",
                    UserId = 1234
                };

                //act and assert 

                Exception ex = Assert.Throws<AppException>(() => userService.CreateUser(tempUser));
                Assert.Contains("atleast 4 characters long", ex.Message);
            }
        }

        [Fact]
        public void Throw_AppException_EmailAlreadyExists()
        {
            //arrange
            using (var context = new GameContext(options))
            {
                userService = new UserService(context);
                var tempUser1 = new User()
                {
                    Username = "FakeUser1",
                    Password = "123456",
                    Email = "dublicate@gmail.com",
                    UserId = 1
                };
                var tempUser2 = new User()
                {
                    Username = "FakeUser2",
                    Password = "123456",
                    Email = "dublicate@gmail.com",
                    UserId = 2
                };
                userService.CreateUser(tempUser1);

                //act and assert 
                Exception ex = Assert.Throws<AppException>(() => userService.CreateUser(tempUser2));
                Assert.Contains("e-mail", ex.Message);

                //Cleanup
                userService.Deleteuser(tempUser1.UserId);
            }
        }
        [Fact]
        public void Throw_AppException_UsernameAlreadyExists()
        {
            //arrange
            using (var context = new GameContext(options))
            {
                userService = new UserService(context);
                var tempUser1 = new User()
                {
                    Username = "FakeUser4",
                    Password = "123456",
                    Email = "nondublicate1@gmail.com",
                    UserId = 5
                };
                var tempUser2 = new User()
                {
                    Username = "FakeUser4",
                    Password = "123456",
                    Email = "nondublicate@gmail.com",
                    UserId = 6
                };
                userService.CreateUser(tempUser1);

                //act and assert 
                Exception ex = Assert.Throws<AppException>(() => userService.CreateUser(tempUser2));
                Assert.Contains("username", ex.Message);

                //Cleanup
                userService.Deleteuser(tempUser1.UserId);
            }
        }

        [Fact]
        public void Pass_CreateNewUser()
        {
            using (var context = new GameContext(options))
            {
                userService = new UserService(context);

                var tempUser = new User()
                {
                    Username = "TestUser",
                    Password = "123456",
                    Email = "tempUser@gmail.com",
                    UserId = 123456
                };
                var result = userService.CreateUser(tempUser);

                //Assert
                Assert.Equal("TestUser", result.Username);
                //Cleanup
                userService.Deleteuser(tempUser.UserId);
            }
        }

        [Fact]
        public void Pass_Return1User()
        {
            using (var context = new GameContext(options))
            {
                //Arrange
                userService = new UserService(context);

                var tempUser = new User()
                {
                    Username = "TestUser",
                    Password = "123456",
                    Email = "tempUser@gmail.com",
                    UserId = 123456
                };
                userService.CreateUser(tempUser);
                //Act
                var result = userService.GetUserById(123456);

                //Assert
                Assert.Equal("TestUser", result.Username);

                //Cleanup
                userService.Deleteuser(tempUser.UserId);
            }
        }

        [Fact]
        public void Pass_ReturnAllUsers()
        {
            using (var context = new GameContext(options))
            {
                //Arrange
                userService = new UserService(context);

                var tempUser = new User()
                {
                    Username = "TestUser",
                    Password = "123456",
                    Email = "tempUser@gmail.com",
                    UserId = 1
                };
                var tempUser2 = new User()
                {
                    Username = "TestUser1",
                    Password = "123456",
                    Email = "tempUser1@gmail.com",
                    UserId = 2
                };
                userService.CreateUser(tempUser);
                userService.CreateUser(tempUser2);
                //Act
                var result = userService.GetAllUsers();

                //Assert
                Assert.Equal(2, result.Count);

                //Cleanup
                userService.Deleteuser(tempUser.UserId);
                userService.Deleteuser(tempUser2.UserId);
            }
        }

        [Fact]
        public void Pass_DeleteUser()
        {
            using (var context = new GameContext(options))
            {
                //Arrange
                userService = new UserService(context);
                var tempUser = new User()
                {
                    Username = "tempUser",
                    Password = "123456",
                    Email = "iets@gmail.com",
                    UserId = 4
                };
                userService.CreateUser(tempUser);

                //Act
                var result = userService.Deleteuser(tempUser.UserId);

                //Assert
                Assert.Null(result);
            }
        }

        [Fact]
        public void Pass_LoginUser()
        {
            using (var context = new GameContext(options))
            {
                //Arrange
                userService = new UserService(context);
                var tempUser = new User()
                {
                    Username = "tempUser",
                    Password = "123456",
                    Email = "iets@gmail.com",
                    UserId = 4
                };

                tempUser = userService.CreateUser(tempUser);

                //Act
                var userLogintest = userService.Login("tempUser", "123456");

                //Assert
                Assert.Equal(tempUser.UserId, userLogintest.UserId);

                //Cleanup
                userService.Deleteuser(tempUser.UserId);
            }
        }

        [Fact]
        public void Throw_AppException_WrongPassword()
        {
            using (var context = new GameContext(options))
            {
                //Arrange
                userService = new UserService(context);
                var tempUser = new User()
                {
                    Username = "tempUser",
                    Password = "123456",
                    Email = "iets1@gmail.com",
                    UserId = 3
                };

                tempUser = userService.CreateUser(tempUser);

                //Act and Assert
                Exception ex = Assert.Throws<AppException>(() => userService.Login("tempUser", "wrongpassword"));
                Assert.Equal("Wrong password", ex.Message);

                //Cleanup
                userService.Deleteuser(tempUser.UserId);
            }
        }

        [Fact]
        public void Throw_AppException_ShortPassword()
        {
            using (var context = new GameContext(options))
            {
                //Arrange
                userService = new UserService(context);
                var tempUser = new User()
                {
                    Username = "tempUser",
                    Password = "12",
                    Email = "iets2@gmail.com",
                    UserId = 999
                };               

                //Act and Assert
                Exception ex = Assert.Throws<AppException>(() => userService.CreateUser(tempUser));
                Assert.Contains("atleast 6 characters long", ex.Message);
            }
        }
    }
}
