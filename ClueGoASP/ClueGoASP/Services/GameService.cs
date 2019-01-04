using ClueGoASP.Data;
using ClueGoASP.Helper;
using ClueGoASP.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ClueGoASP.Services
{
    public interface IGameService
    {
        string DeleteGame(int gameId);
        List<Game> GetGameById(int gameId);
        Game CreateGameFull(int userId, int amtSus);
        string CreateGame(int userId, int amtSus);
        int GetGameInfo(int gameId);
        Game GetBriefGame(int gameId);
        List<Game> GetPuzzleCluesByGame(int gameId);
    }
    public class GameService : IGameService
    {
        private GameContext _dbContext;
        public GameService(GameContext context)
        {
            _dbContext = context;
        }

        public Game CreateGameFull(int userId, int amtSus)
        {
            var game = new Game();
            var clues = new List<Clue>();
            var user = _dbContext.Users.SingleOrDefault(x => x.UserId == userId);               //Get the right user

            if (amtSus < 3 || amtSus >= 9)
                throw new AppException("Number of suspects needs to be between 3 and 9");
            else if (_dbContext.Games.Find(userId) != null)
            {
                throw new AppException("User already has a game");                
            }
            else if(user == null)
            {
                throw new AppException("User does not exist.");
            }
            else
            {
                var locations = _dbContext.Locations.OrderBy(x => Guid.NewGuid()).ToList();     //Randomize location list
                game.GameLocations = new List<GameLocation>();
                for (int i = 0; i < amtSus; i++)                                                //Add given amount of locations to a game.
                {
                    if (locations[i].LocName != "Politiekantoor")                               //Exclude policestation so it doesn't get added twice.
                    {
                        game.GameLocations.Add(new GameLocation
                        {
                            Location = locations[i]
                        });
                    }
                }

                game.GameLocations.Add(new GameLocation                                         //Add the police office to the game.
                {
                    Location = _dbContext.Locations.SingleOrDefault(x => x.LocName == "Politiekantoor")
                });

                var suspects = _dbContext.Suspects.OrderBy(x => Guid.NewGuid()).ToList();       //Randomize Suspect list

                game.GameSuspects = new List<GameSuspect>();
                //game.GameClues = new List<GameClue>();
                for (int i = 0; i < amtSus; i++)                                                //Add suspects to a game.
                {
                    game.GameSuspects.Add(new GameSuspect
                    {
                        Suspect = suspects[i]
                    });
                    if (i == 0)
                    { 
                    game.GameSuspects[0].isMurderer = true;
                    clues.Add(_dbContext.Clues.SingleOrDefault(x => x.SusForeignKey == suspects[i].SusId && !x.Alibi));
                    }
                    else
                        clues.Add(_dbContext.Clues.SingleOrDefault(x => x.SusForeignKey == suspects[i].SusId && x.Alibi));
                }

                //Create list from all clues from the suspects in the game.
                game.GameClues = new List<GameClue>();
                for (int i = 0; i < amtSus; i++)
                {
                    game.GameClues.Add(new GameClue
                    {
                        Clue = clues[i]
                        
                    });
                }

                game.User = user;                                                                 //Setting params for readability in requests.
                game.UserId = userId;
                game.Username = user.Username;

                _dbContext.Games.Add(game);
                _dbContext.SaveChanges();

                return game;
            }
        }

        public string CreateGame(int userId, int amtSus)
        {
            var game = CreateGameFull(userId, amtSus);
            if (game == null)
                throw new AppException("Game could not be created");
            else
                return "Game created for userId: " + userId;
        }

        public string DeleteGame(int gameId)
        {
            var game = _dbContext.Games.Find(gameId);
            if (game == null)
                throw new AppException("Game with id " + gameId + " does not exist.");
            else
            {
                _dbContext.Games.Remove(game);
                _dbContext.SaveChanges();
                return "Game removed.";
            }
        }

        public Game GetBriefGame(int gameId)
        {
            var game = _dbContext.Games.Find(gameId);
            if (game == null)
                throw new AppException("User " + gameId + " does not have a current game.");
            else
                return game;
        }

        public List<Game> GetGameById(int gameId)
        {
            return _dbContext.Games
                            .Include(x => x.GameLocations)
                            .ThenInclude(x => x.Location)
                            .Where(x => x.GameId == gameId)

                            .Include(x => x.GameSuspects)
                            .ThenInclude(x => x.Suspect)
                            .Where(x => x.GameId == gameId)

                            .Include(x => x.GameClues)
                            .ThenInclude(x => x.Clue)
                            .Where(x => x.GameId == gameId)
                            .ToList();
        }

        public int GetGameInfo(int gameId)
        {          
            var game = _dbContext.GameLocations.Where(x => x.GameId == gameId);
            return game.Count();
        }

        public List<Game> GetPuzzleCluesByGame(int gameId)
        {
            var game = _dbContext.Games
                .Include(x => x.GameClues)
                .Where(y => y.GameId == gameId)
                .ToList();
            


            return game;
        }
    }
}
