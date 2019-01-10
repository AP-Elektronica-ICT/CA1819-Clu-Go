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
        IQueryable<object> GetBriefClue(int id);
        Clue UpdateClue(int clueId, Clue updateClue);
        Clue SetFound(int clueId);
        Clue GetById(int id);


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

        public Clue GetById(int id)
        {
            return _dbContext.Clues.Find(id);
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

        public IQueryable<object> GetBriefClue(int clueId)
        {
            var result = _dbContext.Clues.Select(x => new
            {
                x.ClueId,
                x.ClueName,
                x.ClueDescription,
                x.ClueImgUrl
            })
            .Where(x => x.ClueId == clueId);
            //.ToList();

            return result;
        }
    }
}
