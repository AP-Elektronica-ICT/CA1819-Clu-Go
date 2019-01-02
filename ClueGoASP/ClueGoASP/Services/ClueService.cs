using ClueGoASP.Data;
using ClueGoASP.Helper;
using ClueGoASP.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ClueGoASP.Services
{
    public interface IClueService
    {
        List<Clue> GetAll();
        Clue UpdateClue(int clueId, Clue updateClue);
        Clue SetFound(int clueId);

    }
    public class ClueService : IClueService
    {
        private GameContext _dbContext;
        public ClueService(GameContext context)
        {
            _dbContext = context;
        }
        public List<Clue> GetAll()
        {
            return _dbContext.Clues.ToList();
        }
        public Clue SetFound(int clueId)
        {
            var orgClue = _dbContext.Clues.SingleOrDefault(x => x.ClueId == clueId);

            if (orgClue == null)
                throw new AppException ("Clue does not exist");
            else
            {
                orgClue.Found = true;

                _dbContext.SaveChanges();
                return orgClue;
            }
        }

        public Clue UpdateClue(int clueId, Clue updateClue)
        {
            var orgClue = _dbContext.Clues.SingleOrDefault(x => x.ClueId == clueId);

            if (orgClue == null)
                throw new AppException("Clue does not exist");
            else
            {
                orgClue.Found = updateClue.Found;

                _dbContext.SaveChanges();
                return orgClue;
            }
        }
    }
}
