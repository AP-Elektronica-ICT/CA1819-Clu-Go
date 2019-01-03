using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ClueGoASP.Models;
using Microsoft.EntityFrameworkCore;

namespace ClueGoASP.Data
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
                    SusDescription = "Ms. Vivienne Sakura Scarlet[2][3] (born July 16, 1928) is a vivacious and aspiring actress whose passion for fame, fortune, and especially men will let no one stand in her way. The glamorous daughter of Mrs. Elizabeth Peacock and beautiful girlfriend of Col. Michael Mustard, she resides at Arlington Grange with her mother and Mrs. Blanche White. Ms. Scarlet is very famous and the press in Hollywood often question her about her disastrous love life. She was the former fiancée of Mr. John Boddy, who was mysteriously murdered.",
                    SusImgUrl = "https://i.pinimg.com/originals/95/ce/3d/95ce3da06af8b1c09a4b2d4fa603b7a0.jpg",
                };
                var suspect1 = new Suspect()
                {
                    SusName = "Mr. Green",
                    SusWeapon = "Wooden cross",
                    SusDescription = "Rev. Jonathan Green[2] (born June 18, 1905), is questioned by many on whether he is a saint or sinner. Known for shady dealings on the stock market, he used this money to help the Church of England.",
                    SusImgUrl = "https://pbs.twimg.com/profile_images/447953368271814657/Inf33QvJ.jpeg",
                    };
                var suspect2 = new Suspect()
                {
                    SusName = "Colonel Mustard",
                    SusWeapon = "Gun",
                    SusDescription = "Col. Michael Mustard[2] (born June 29, 1924) is a gallant military hero whose glittering career hides a tarnished past. He learns from the strong and deals quickly with the weak. He believes that casualties are inevitable in war and is usually the sole survivor in battle. The colonel enjoys an adventure and must always succeed in battle, no matter what, even if it means betraying his allies to defeat the enemy. He became the boyfriend of the glamorous actress Ms. Vivienne Scarlet. He was an old friend of Mr. John Boddy from college, who was mysteriously murdered",
                    SusImgUrl = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/07/04/08/unspecified-3.jpg?width=1368&height=912&fit=bounds&format=pjpg&auto=webp&quality=70",
                };
                var suspect3 = new Suspect()
                {
                    SusName = "Dr.Orchid",
                    SusWeapon = "Syringe",
                    SusDescription = "A Doctor, Elegant ",
                    SusImgUrl = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/07/04/08/unspecified-4.jpg?width=1368&height=912&fit=bounds&format=pjpg&auto=webp&quality=70",
                };
                var suspect4 = new Suspect()
                {
                    SusName = "Professor Plum",
                    SusWeapon = "Gun",
                    SusDescription = "Prof. Peter Plum[4] (born August 31, 1922) is a man with a degree of suspicion. Would his research methods stand the scrutiny of a microscope?",
                    SusImgUrl = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/07/04/08/unspecified-6.jpg?width=1368&height=912&fit=bounds&format=pjpg&auto=webp&quality=70",

                };
                var suspect5 = new Suspect()
                {
                    SusName = "Mrs. Peacock",
                    SusWeapon = "Gun",
                    SusDescription = "Mrs. Elizabeth Peacock[2](born January 5, 1906) is the Lady of Arlington Grange and a beautiful society hostess. She constantly tries to live up to her reputation that she would rather forget. She is nicknamed Ma Bluebird because of the mysterious deaths of her husbands.",
                    SusImgUrl = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/07/04/08/unspecified.jpg?width=1368&height=912&fit=bounds&format=pjpg&auto=webp&quality=70",

                };
                var suspect6 = new Suspect()
                {
                    SusName = "Mrs.White",
                    SusWeapon = "Gun",
                    SusDescription = "Mrs. Blanche White[2](born November 1, 1891) is the loyal housekeeper and a devoted confidant. She hides a secret indulgence. She currently lives with her employer, Mrs. Elizabeth Peacock, who is also her close friend. She knows all the news throughout Arlington, MA, some she shouldn't be aware of. She cared for Mr. John Boddy, her former employer, who was mysteriously murdered.",
                    SusImgUrl = "https://static.giantbomb.com/uploads/scale_small/0/5768/698578-white_puzzle.jpg",

                };

                context.Suspects.Add(suspect0);
                context.Suspects.Add(suspect1);
                context.Suspects.Add(suspect2);
                context.Suspects.Add(suspect3);
                context.Suspects.Add(suspect4);
                context.Suspects.Add(suspect5);
                context.Suspects.Add(suspect6);
               
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
                    Password = "123456"
                };
                var admin2 = new User()
                {
                    Username = "JoppeM",
                    Email = "joppe.mertens@gmail.com",
                    Password = "123456"
                };
                var admin3 = new User()
                {
                    Username = "AlIbra",
                    Email = "s091997@ap.be",
                    Password = "123456"
                };
                var admin4 = new User()
                {
                    Username = "Test",
                    Email = "Test@ap.be",
                    Password = "123456"
                };
                context.Users.Add(admin);
                context.Users.Add(admin1);
                context.Users.Add(admin2);
                context.Users.Add(admin3);
                context.Users.Add(admin4);

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
                var location5 = new Location()
                {
                    LocName = "MAS",
                    LocLat = 51.228989,
                    LocLong = 4.40816,
                    LocDescription = "Museum aan de stroom"
                };
                var location6 = new Location()
                {
                    LocName = "Stadswaag",
                    LocLat = 51.223877,
                    LocLong = 4.407136,
                    LocDescription = "Studentencafés."
                };
                var location7 = new Location()
                {
                    LocName = "De Zoo",
                    LocLat = 51.217834,
                    LocLong = 4.423122,
                    LocDescription = "Zoo van Antwerpen."
                };
                var location8 = new Location()
                {
                    LocName = "Centraal Station",
                    LocLat = 51.217433,
                    LocLong = 4.420821,
                    LocDescription = "Centraal station van Antwerpen"
                };
                var location9 = new Location()
                {
                    LocName = "Stadspark",
                    LocLat = 51.21243,
                    LocLong = 4.414481,
                    LocDescription = "Grootste park in centrum Antwerpen"
                };
                var location10 = new Location()
                {
                    LocName = "Stadsschouwburg",
                    LocLat = 51.213204,
                    LocLong = 4.406471,
                    LocDescription = "Stadsschouwburg Antwerpen"
                };
                var location11 = new Location()
                {
                    LocName = "Vrijdagmarkt",
                    LocLat = 51.218377,
                    LocLong = 4.399065,
                    LocDescription = "Rustig pleintje om iets te drinken."
                };
                var location12 = new Location()
                {
                    LocName = "GroenPlaats",
                    LocLat = 51.219237,
                    LocLong = 4.401694,
                    LocDescription = "Groenplaats van Antwerpen"
                };
                var location13 = new Location()
                {
                    LocName = "Rockoxhuis",
                    LocLat = 51.221717,
                    LocLong = 4.406205,
                    LocDescription = "Rockoxhuis"
                };
                var location15 = new Location()
                {
                    LocName = "Fakkeltheater",
                    LocLat = 51.220467,
                    LocLong = 4.398943,
                    LocDescription = "Bekend theaterhuis."
                };
                var location14 = new Location()
                {
                    LocName = "Oudste huis Antwerpen",
                    LocLat = 51.222929,
                    LocLong = 4.401768,
                    LocDescription = "Oudste huis Antwerpen"
                };
                var PoliceOffice = new Location()
                {
                    LocName = "Politiekantoor",
                    LocLat = 51.030754,
                    LocLong = 4.414065,
                    LocDescription = "Politiekantoor"
                };

                context.Locations.Add(location1);
                context.Locations.Add(location2);
                context.Locations.Add(location3);
                context.Locations.Add(location4);
                context.Locations.Add(location5);
                context.Locations.Add(location6);
                context.Locations.Add(location7);
                context.Locations.Add(location8);
                context.Locations.Add(location9);
                context.Locations.Add(location10);
                context.Locations.Add(location11);
                context.Locations.Add(location12);
                context.Locations.Add(location13);
                context.Locations.Add(location14);
                context.Locations.Add(location15);
                context.Locations.Add(PoliceOffice);
                context.SaveChanges();
            }
        }
    }
}
