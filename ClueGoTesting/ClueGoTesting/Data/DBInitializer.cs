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


            // add clues if none exist
            if (!context.Clues.Any())
            {
                var clue0 = new Clue()
                {
                    ClueName = "RansomPuzzle",
                    ClueDescription = "Puzzle from ransom note",
                    ClueImgUrl = "https://exodusescaperoom.com/wp-content/uploads/2016/05/shutterstock_238275508.jpg",
                    Found = false
                };
                var clue1 = new Clue()
                {
                    ClueName = "ARKnife",
                    ClueDescription = "AR vision from Knife",
                    ClueImgUrl = "https://banner2.kisspng.com/20171216/9d3/sword-png-image-5a3586cb11ea93.6540765515134573550734.jpg",
                    Found = true
                };
                var clue2 = new Clue()
                {
                    ClueName = "ARRope",
                    ClueDescription = "AR vision from Rope",
                    ClueImgUrl = "https://png.pngtree.com/element_pic/16/12/25/29987abdff19ca380a7933742e2e25a4.jpg",
                    Found = false
                };
                context.Clues.Add(clue0);
                context.Clues.Add(clue1);
                context.Clues.Add(clue2);
                context.SaveChanges();
            }

            //add suspectsd if none exist
            if (!context.Suspects.Any())
            {
                var suspect0 = new Suspect()
                {
                    //  SuspectId = 1,
                    SusName = "Miss Scarlett",
                    SusWeapon = "Rope",
                    SusDescription = "Femme fatale, young, cunning, and highly attractive.",
                    SusImgUrl = "https://i.pinimg.com/originals/95/ce/3d/95ce3da06af8b1c09a4b2d4fa603b7a0.jpg",
                    isMurderer = true


                };
            var suspect1 = new Suspect()
            {
                SusName = "Mr. Green",
                SusWeapon = "Wooden cross",
                SusDescription = "Bald, keeps to himself, elderly priest",
                SusImgUrl = "https://pbs.twimg.com/profile_images/447953368271814657/Inf33QvJ.jpeg",
                isMurderer = false


                };
                var suspect2 = new Suspect()
                {
                    SusName = "Colonel Mustard",
                    SusWeapon = "Gun",
                    SusDescription = "A military man both dignified, dapper and dangerous",
                    SusImgUrl = "https://pbs.twimg.com/profile_images/447953368271814657/Inf33QvJ.jpeg",
                    isMurderer = false


    };
                //https://pbs.twimg.com/profile_images/745749442670669824/l_6cM8YA_400x400.jpg


                context.Suspects.Add(suspect0);
                context.Suspects.Add(suspect1);
                context.Suspects.Add(suspect2);
                context.SaveChanges();

            }
            //add user if none exist
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
            //add location if none exist
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
                var PoliceOffice = new Location()
                {
                    LocName = "Politiekantoor",
                    LocLat = 51.030754,
                    LocLong = 4.474065,
                    LocDescription = "Politiekantoor"
                };


                context.Locations.Add(location1);
                context.Locations.Add(location2);
                context.Locations.Add(location3);
                context.Locations.Add(location4);
                context.Locations.Add(PoliceOffice);
                context.SaveChanges();
            }
        }
    }
}
