using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

using ClueGoASP.Models;
using ClueGoASP.Helper;
using ClueGoASP.Data;
using Microsoft.EntityFrameworkCore;

namespace ClueGoASP.Services
{
    public interface ILocationService
    {
        List<Location> GetLocations();
        int GetLocationIdByName(string locName);
        Location UpdateLoction(Location updateLoc);
        string DeleteLocation(int id);
        Location CreateLocation(Location newLoc);
        List<GameLocation> GetGameLocations(int gameId);

    }
    public class LocationService : ILocationService
    {
        private GameContext _dbContext;
        public LocationService(GameContext gameContext)
        {
            _dbContext = gameContext;
        }

        public List<Location> GetLocations()
        {
            return _dbContext.Locations.ToList();
        }

        public string DeleteLocation(int id)
        {
            var location = _dbContext.Locations.Find(id);
            if (location == null)
                throw new AppException("Loctation does not exist.");
            else
            {
                _dbContext.Locations.Remove(location);
                _dbContext.SaveChanges();

                return "Location removed";
            }
        }

        public Location UpdateLoction(Location updateLoc)
        {
            var orgLoc = _dbContext.Locations.Find(updateLoc.LocId);

            if (orgLoc == null)
                throw new AppException("Location does not exist.");
            else
            {
                orgLoc.LocName = updateLoc.LocName;
                orgLoc.LocLat = updateLoc.LocLat;
                orgLoc.LocLong = updateLoc.LocLong;
                orgLoc.LocDescription = updateLoc.LocDescription;

                _dbContext.Locations.Update(orgLoc);
                _dbContext.SaveChanges();
                return orgLoc;
            }
        }

        public Location CreateLocation(Location newLoc)
        {
            //Will return error if location is not inside of antwerp center limits.
            double maxLat = 51.236130;
            double minLat = 51.193742;
            double maxLong = 4.435389;
            double minLong = 4.393227;

            if (newLoc.LocDescription == null)
                throw new AppException("Location description cannot be empty.");
            else if (newLoc.LocName == null)
                throw new AppException("Location name cannot be empty.");
            else if (!(maxLong > newLoc.LocLong && newLoc.LocLong > minLong))
                throw new AppException("Longitude is not in range of Antwerp.");
            else if (!(maxLat > newLoc.LocLat && newLoc.LocLat > minLat))
                throw new AppException("Latitude is not in range of Antwerp.");
            else
            {
                _dbContext.Locations.Add(newLoc);
                _dbContext.SaveChanges();
                return newLoc;
            }
        }

        public List<GameLocation> GetGameLocations(int gameId)
        {
            return _dbContext.GameLocations.Include(x => x.Location).Where(x => x.GameId == gameId).ToList();
        }

        public int GetLocationIdByName(string locName)
        {
            var result = _dbContext.Locations.SingleOrDefault(x => x.LocName == locName);
            return result.LocId;
        }
    }
}
