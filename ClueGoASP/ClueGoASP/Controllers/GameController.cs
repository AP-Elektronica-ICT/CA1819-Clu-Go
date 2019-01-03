﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ClueGoASP.Services;
using ClueGoASP.Models;
using ClueGoASP.Data;
using ClueGoASP.Helper;

namespace ClueGoASP.Controllers
{
    [Route("api/[controller]")]
    [ApiController]

    public class GameController : ControllerBase
    {
        private readonly GameContext _dbContext;
        private IGameService _gameService;
        private IUserService _userService;


        public GameController(GameContext dbcontext, IGameService gameService, IUserService userService)
        {
            _dbContext = dbcontext;
            _gameService = gameService;
            _userService = userService;
        }

        [HttpGet]
        public ActionResult<List<Game>> GetAll()
        {
            return Ok(_dbContext.Games
                .Include(x => x.GameLocations)
                .ThenInclude(x => x.GameId)
                .ToList());
        }

        [HttpDelete("{gameId}")]
        public IActionResult Delete(int gameId)
        {
            int amt = _gameService.GetGameInfo(gameId);
            try
            {
                UpdateEndGame(gameId, amt);

                var game = _gameService.DeleteGame(gameId);
                return Ok(game);
            }
            catch (AppException ex)
            {
                return BadRequest(new { message = ex.Message });
            }            
        }

        [HttpGet("{gameId}")]
        public ActionResult<List<Game>> GetGameById(int gameId)
        {
            var item = _dbContext.Games.Find(gameId);

            return Ok(_gameService.GetGameById(gameId));
        }

        [HttpGet("create/{userId}/{amtSus}")]
        public ActionResult<Game> CreateGame(int userId, int amtSus)
        {
            try
            {
                return Ok(_gameService.CreateGame(userId, amtSus));
            }
            catch(AppException ex)
            {
                return BadRequest(new { message = ex.Message});
            }            
        }

        [HttpPut("updateEndGame/{userId}/{amtSus}")]
        public IActionResult UpdateEndGame(int userId, int amtSus)
        {
            _userService.AddWonGame(userId);
            _userService.AddDistanceWalked(amtSus, userId);
            _userService.AddFoundClues(amtSus, userId);

            return Ok("User stats have been updated.");            
        }
    }
}
