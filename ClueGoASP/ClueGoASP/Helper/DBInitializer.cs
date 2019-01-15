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

            //add suspectsd if none exist
            if (!context.Suspects.Any())
            {
                var suspect0 = new Suspect()
                {
                    //  SuspectId = 1,
                    SusName = "Miss Scarlett",
                    SusWeapon = "Poison",
                    SusDescription = "Ms. Vivienne Sakura Scarlet is a vivacious and aspiring actress whose passion for fame, fortune, and especially men will let no one stand in her way. The glamorous daughter of Mrs. Elizabeth Peacock and beautiful girlfriend of Col. Michael Mustard. She was the former fiancée of Mr. John Boddy.",
                    SusImgUrl = "https://i.pinimg.com/originals/95/ce/3d/95ce3da06af8b1c09a4b2d4fa603b7a0.jpg",
                };
                var suspect1 = new Suspect()
                {
                    SusName = "Mr. Green",
                    SusWeapon = "Silver cross",
                    SusDescription = "Rev. Jonathan Green, is questioned by many on whether he is a saint or sinner. Known for shady dealings on the stock market, he used this money to help the Church of England. His relation with Jhon boddy was at an all time low since Jonathan lost alof of money through investments made by Jhon boddy.",
                    SusImgUrl = "https://pbs.twimg.com/profile_images/447953368271814657/Inf33QvJ.jpeg",
                    };
                var suspect2 = new Suspect()
                {
                    SusName = "Colonel Verdoot",
                    SusWeapon = "Ceremonial sword",
                    SusDescription = "Col. Michael Mustard, is a gallant military hero whose glittering career hides a tarnished past.He believes that casualties are inevitable in war and is usually the sole survivor in battle. He became the boyfriend of the glamorous actress Ms. Scarlet. He was an old college friend of Mr. John Boddy from college. They both attended AP Hogeschool back in their day. Jhon and the Colonel are on bad terms ever since Colonel Mustard started a relation with Miss Scarlet.",
                    SusImgUrl = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/07/04/08/unspecified-3.jpg?width=1368&height=912&fit=bounds&format=pjpg&auto=webp&quality=70",
                };
                var suspect3 = new Suspect()
                {
                    SusName = "Dr. Boomgaerts",
                    SusWeapon = "Morphine",
                    SusDescription = "The now divorced Doctor practices medicine at his family practician. He lives in luxury, always has the latest of the latest when it comes to technology and cars. He was anything but happy with the way Jhon boddy went through life. Mainly because his former wife left him for Jhon boddy. The Doctors latest lover is currently unknown.",
                    SusImgUrl = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/07/04/08/unspecified-4.jpg?width=1368&height=912&fit=bounds&format=pjpg&auto=webp&quality=70",
                };
                var suspect4 = new Suspect()
                {
                    SusName = "Professor Peeters",
                    SusWeapon = "Revolver",
                    SusDescription = "Prof. Peter Peeters is a man with a degree of suspicion. Before he started his career as a teacher at AP, he was an associate of Jhon boddy at MoneyWell. His abrupt and unlawfull termination left the relationship between these mens in shambles. Would his research methods stand the scrutiny of a microscope?",
                    SusImgUrl = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/07/04/08/unspecified-6.jpg?width=1368&height=912&fit=bounds&format=pjpg&auto=webp&quality=70",

                };
                var suspect5 = new Suspect()
                {
                    SusName = "Mrs. Peacock",
                    SusWeapon = "Knife",
                    SusDescription = "Mrs. Elizabeth Peacock is the ex-wife of entrepreneur Roland Hoedts, who died under very suspicious circumstances. She constantly tries to live up to her reputation that she would rather forget. She is nicknamed Ma Bluebird because of the mysterious deaths of her husbands. She went almost bankrupt after investing money with Jhon boddy and had several court cases against him.",
                    SusImgUrl = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/07/04/08/unspecified.jpg?width=1368&height=912&fit=bounds&format=pjpg&auto=webp&quality=70",

                };
                var suspect6 = new Suspect()
                {
                    SusName = "Mrs. White",
                    SusWeapon = "Rope",
                    SusDescription = "Mrs. Blanche White is the loyal housekeeper and a devoted confidant. She hides a secret indulgence, we think she might be involved with Mr. Green. She currently lives with her employer, Mrs. Elizabeth Peacock, who is also her close friend. She knows all the news throughout Antwerp. She was the former employee of Jhon boddy, but after a string of sexual complaints, she decided to quit working for him.",
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
            // add clues if none exist
            if (!context.Clues.Any())
            {
                /* var clue0 = new Clue()
                 {
                     ClueName = "RansomPuzzle",
                     ClueDescription = "Puzzle from ransom note",
                     ClueImgUrl = "https://exodusescaperoom.com/wp-content/uploads/2016/05/shutterstock_238275508.jpg",
                     Found = false
                 };*/
                var clue1 = new Clue()
                {
                    ClueName = "Knife",
                    ClueType = "AR",
                    ClueImgUrl = "https://banner2.kisspng.com/20171216/9d3/sword-png-image-5a3586cb11ea93.6540765515134573550734.jpg",
                    Found = false,                   
                    SusForeignKey = 6
                };
                var clue2 = new Clue()
                {
                    ClueName = "Rope",
                    ClueType = "AR",
                    ClueImgUrl = "https://png.pngtree.com/element_pic/16/12/25/29987abdff19ca380a7933742e2e25a4.jpg",
                    Found = false,
                    SusForeignKey = 7
                };
                var clue3 = new Clue()
                {
                    ClueName = "Poison",
                    ClueType = "AR",
                    ClueImgUrl = "https://image.spreadshirtmedia.net/image-server/v1/mp/designs/13432174,width=178,height=178/poison-bottle-with-a-skull.png",
                    Found = false,
                    SusForeignKey = 1
                };
                var clue4 = new Clue()
                {
                    ClueName = "Wooden cross",
                    ClueType = "AR",
                    ClueImgUrl = "https://www.clipartmax.com/png/middle/86-868176_wooden-cross-transparent-background.png",
                    Found = false,
                    SusForeignKey = 2
                };
                var clue5 = new Clue()
                {
                    ClueName = "Ceremonial sword",
                    ClueType = "AR",
                    ClueImgUrl = "https://www.darkknightarmoury.com/images/Product/medium/600970.png",
                    Found = false,
                    SusForeignKey = 3
                };
                var clue6 = new Clue()
                {
                    ClueName = "Morphine",
                    ClueType = "AR",
                    ClueImgUrl = "http://www.stickpng.com/assets/images/580b585b2edbce24c47b2921.png",
                    Found = false,
                    SusForeignKey = 4
                };
                var clue7 = new Clue()
                {
                    ClueName = "Revolver",
                    ClueType = "AR",
                    ClueImgUrl = "https://b.kisscc0.com/20180718/kwq/kisscc0-revolver-firearm-handgun-heckler-koch-p11-pistol-rusty-revolver-rendered-5b4ece451ee928.4789046315318912691266.jpg",
                    Found = false,
                    SusForeignKey = 5
                };
                var clue8 = new Clue()
                {
                    ClueName = "ScarletPhoto",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://banner2.kisspng.com/20171216/9d3/sword-png-image-5a3586cb11ea93.6540765515134573550734.jpg",
                    Alibi = true,
                    SusForeignKey = 1
                };
                var clue9 = new Clue()
                {
                    ClueName = "ShoppingReceipt",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://cdn3.iconfinder.com/data/icons/shopping-70/64/shopping-6-512.png",
                    Alibi = true,
                    SusForeignKey = 7
                };
                var clue10 = new Clue()
                {
                    ClueName = "HairdressAppointment",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://ih0.redbubble.net/image.197718137.1264/poster%2C210x230%2Cf8f8f8-pad%2C210x230%2Cf8f8f8.lite-1u2.jpg",
                    Alibi = true,
                    SusForeignKey = 6
                };
                var clue11 = new Clue()
                {
                    ClueName = "Mass",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://upload.wikimedia.org/wikipedia/commons/4/48/Catholic_Mass_aboard_USS_Ronald_Reagan.jpg",
                    Alibi = true,
                    SusForeignKey = 2
                };
                var clue12 = new Clue()
                {
                    ClueName = "RansomPuzzle",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://cdn3.iconfinder.com/data/icons/cafe/512/cafe16-512.png",
                    Alibi = true,
                    SusForeignKey = 3
                };
                var clue13 = new Clue()
                {
                    ClueName = "Congress",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://thumb101.shutterstock.com/photos/display_pic_with_logo/920555/208250347.jpg",
                    Alibi = true,
                    SusForeignKey = 4
                };
                var clue14 = new Clue()
                {
                    ClueName = "Lecture",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://cdn2.iconfinder.com/data/icons/crazy-paparazzi-collection-svg/100/Noun_Project_100Icon_10px_grid_2-47-512.png",
                    Alibi = true,
                    SusForeignKey = 5
                };
                //context.Clues.Add(clue0);
                context.Clues.Add(clue1);
                context.Clues.Add(clue2);
                context.Clues.Add(clue3);
                context.Clues.Add(clue4);
                context.Clues.Add(clue5);
                context.Clues.Add(clue6);
                context.Clues.Add(clue7);
                context.Clues.Add(clue8);
                context.Clues.Add(clue9);
                context.Clues.Add(clue10);
                context.Clues.Add(clue11);
                context.Clues.Add(clue12);
                context.Clues.Add(clue13);
                context.Clues.Add(clue14);
                context.SaveChanges();
            }
            //add user if none exist
            if (!context.Users.Any())
            {
                var admin = new User()
                {
                    Username = "WeynsA",
                    Email = "weyns.arno@gmail.com",
                    Password = "E10ADC3949BA59ABBE56E057F20F833E"
                };
                var admin1 = new User()
                {
                    Username = "MassureA",
                    Email = "s091998@ap.be",
                    Password = "E10ADC3949BA59ABBE56E057F20F833E"
                };
                var admin2 = new User()
                {
                    Username = "JoppeM",
                    Email = "joppe.mertens@gmail.com",
                    Password = "E10ADC3949BA59ABBE56E057F20F833E"
                };
                var admin3 = new User()
                {
                    Username = "AlIbra",
                    Email = "s091997@ap.be",
                    Password = "E10ADC3949BA59ABBE56E057F20F833E"
                };
                var admin4 = new User()
                {
                    Username = "Test",
                    Email = "Test@ap.be",
                    Password = "E10ADC3949BA59ABBE56E057F20F833E"
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
                    LocLat = 51.220754,
                    LocLong = 4.404065,
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
