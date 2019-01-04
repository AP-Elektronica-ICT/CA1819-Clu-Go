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
    public interface ISuspectService
    {
        List<Suspect> GetAll();
        Suspect UpdateSuspect(Suspect updateSus);
        string DeleteSuspect(int id);
        Suspect CreateSuspect(Suspect newSus);
    }
    public class SuspectService : ISuspectService
    {
        private GameContext _dbContext;
        public SuspectService(GameContext gameContext)
        {
            _dbContext = gameContext;
        }
        public Suspect CreateSuspect(Suspect newSus)
        {
            if (newSus.SusDescription == null)
                throw new AppException("Description cannot be empty.");
            else if (newSus.SusName == null)
                throw new AppException("Name cannot be empty.");           
            else
            {
                _dbContext.Suspects.Add(newSus);
                _dbContext.SaveChanges();
                return newSus;
            }
        }

        public string DeleteSuspect(int id)
        {
            var suspect = _dbContext.Suspects.Find(id);
            if (suspect == null)
                throw new AppException("Suspect does not exist.");
            else
            {
                _dbContext.Suspects.Remove(suspect);
                _dbContext.SaveChanges();

                return "Suspect removed";
            }
        }

        public List<Suspect> GetAll()
        {
            /*var result = _dbContext.Clues.Select(clues => new
             {
                 clues = clues,
                 Susname = clues.Suspect.SusName
             }).ToList();*/

            var result = _dbContext.Suspects
                .Include(x => x.Clues)
                
                //.Where(c => c.Clues.Any(a => a.SusForeignKey == 6))
                .ToList();
            return result;
        }

        public Suspect UpdateSuspect(Suspect updateSus)
        {
            var orgSus = _dbContext.Suspects.Find(updateSus.SusId);

            if (orgSus == null)
                throw new AppException("Suspect does not exist.");
            else
            {
                orgSus.SusName = updateSus.SusName;
                orgSus.SusDescription = updateSus.SusDescription;

                _dbContext.Suspects.Update(orgSus);
                _dbContext.SaveChanges();
                return orgSus;
            }
        }
    }
}
