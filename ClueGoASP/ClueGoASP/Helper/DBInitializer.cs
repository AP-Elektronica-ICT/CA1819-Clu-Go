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
                List<Suspect> suspects = new List<Suspect>() {

                    new Suspect()
                    {
                        //  SuspectId = 1,
                        SusName = "Miss Scarlett",
                        SusWeapon = "Poison",
                        SusDescription = "Ms. Vivienne Sakura Scarlet is a vivacious and aspiring actress whose passion for fame, fortune, and especially men will let no one stand in her way. The glamorous daughter of Mrs. Elizabeth Peacock and beautiful girlfriend of Col. Michael Mustard. She was the former fiancée of Mr. John Boddy.",
                        SusImgUrl = "https://i.pinimg.com/originals/95/ce/3d/95ce3da06af8b1c09a4b2d4fa603b7a0.jpg",
                    },
                    new Suspect()
                    {
                        SusName = "Mr. Green",
                        SusWeapon = "Silver cross",
                        SusDescription = "Rev. Jonathan Green, is questioned by many on whether he is a saint or sinner. Known for shady dealings on the stock market, he used this money to help the Church of England. His relation with Jhon boddy was at an all time low since Jonathan lost alof of money through investments made by Jhon boddy.",
                        SusImgUrl = "https://pbs.twimg.com/profile_images/447953368271814657/Inf33QvJ.jpeg",
                    },
                    new Suspect()
                    {
                        SusName = "Colonel Mark Sveniers",
                        SusWeapon = "Ceremonial sword",
                        SusDescription = "Col. Mark Sveniers, is a gallant military hero whose glittering career hides a tarnished past.He believes that casualties are inevitable in war and is usually the sole survivor in battle. He became the boyfriend of the glamorous actress Ms. Scarlet. He was an old college friend of Mr. John Boddy from college. They both attended AP Hogeschool back in their day. Jhon and the Colonel are on bad terms ever since Colonel Mark Sveniers started a relation with Miss Scarlet.",
                        SusImgUrl = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/07/04/08/unspecified-3.jpg?width=1368&height=912&fit=bounds&format=pjpg&auto=webp&quality=70",
                    },
                    new Suspect()
                    {
                        SusName = "Dr. Boomgaerts",
                        SusWeapon = "Morphine",
                        SusDescription = "The now divorced Doctor practices medicine at his family practician. He lives in luxury, always has the latest of the latest when it comes to technology and cars. He was anything but happy with the way Jhon boddy went through life. Mainly because his former wife left him for Jhon boddy. The Doctors latest lover is currently unknown.",
                        SusImgUrl = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/07/04/08/unspecified-4.jpg?width=1368&height=912&fit=bounds&format=pjpg&auto=webp&quality=70",
                    },
                     new Suspect()
                    {
                        SusName = "Professor Peter Thompson",
                        SusWeapon = "Revolver",
                        SusDescription = "Prof. Peter Peeters is a man with a degree of suspicion. Before he started his career as a teacher at AP, he was an associate of Jhon boddy at MoneyWell. His abrupt and unlawfull termination left the relationship between these mens in shambles. Would his research methods stand the scrutiny of a microscope?",
                        SusImgUrl = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/07/04/08/unspecified-6.jpg?width=1368&height=912&fit=bounds&format=pjpg&auto=webp&quality=70",

                    },
                    new Suspect()
                    {
                        SusName = "Mrs. Peacock",
                        SusWeapon = "Knife",
                        SusDescription = "Mrs. Elizabeth Peacock is the ex-wife of entrepreneur Roland Hoedts, who died under very suspicious circumstances. She constantly tries to live up to her reputation that she would rather forget. She is nicknamed Ma Bluebird because of the mysterious deaths of her husbands. She went almost bankrupt after investing money with Jhon boddy and had several court cases against him.",
                        SusImgUrl = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/07/04/08/unspecified.jpg?width=1368&height=912&fit=bounds&format=pjpg&auto=webp&quality=70",

                    },
                    new Suspect()
                    {
                        SusName = "Mrs. White",
                        SusWeapon = "Rope",
                        SusDescription = "Mrs. Blanche White is the loyal housekeeper and a devoted confidant. She hides a secret indulgence, we think she might be involved with Mr. Green. She currently lives with her employer, Mrs. Elizabeth Peacock, who is also her close friend. She knows all the news throughout Antwerp. She was the former employee of Jhon boddy, but after a string of sexual complaints, she decided to quit working for him.",
                        SusImgUrl = "https://static.giantbomb.com/uploads/scale_small/0/5768/698578-white_puzzle.jpg",

                    },
                };

                context.Suspects.AddRange(suspects);             
                context.SaveChanges();

            }
            // add clues if none exist
            if (!context.Clues.Any())
            {               
                var clue1 = new Clue()
                {
                    ClueName = "Knife",
                    ClueType = "AR",
                    ClueImgUrl = "https://banner2.kisspng.com/20171216/9d3/sword-png-image-5a3586cb11ea93.6540765515134573550734.jpg",
                    Found = false,    
                    ClueDescription = "The victim died because of severe slashing wounds. Most likely inflicted by a sharp knife.",
                    SusForeignKey = 6
                };
                var clue2 = new Clue()
                {
                    ClueName = "Rope",
                    ClueType = "AR",
                    ClueImgUrl = "https://png.pngtree.com/element_pic/16/12/25/29987abdff19ca380a7933742e2e25a4.jpg",
                    ClueDescription = "The victim died by suffocation. We saw burn marks around the neck of the victim, so we can assume a rope was most likely the murder weapon.",
                    Found = false,
                    SusForeignKey = 7
                };
                var clue3 = new Clue()
                {
                    ClueName = "Poison",
                    ClueType = "AR",
                    ClueImgUrl = "https://image.spreadshirtmedia.net/image-server/v1/mp/designs/13432174,width=178,height=178/poison-bottle-with-a-skull.png",
                    Found = false,
                    ClueDescription = "The autopsy revealed John Boddy died because of an unknown poison. We think the poison was administered by mixing it with a drink.",
                    SusForeignKey = 1
                };
                var clue4 = new Clue()
                {
                    ClueName = "Silver cross",
                    ClueType = "AR",
                    ClueImgUrl = "https://www.clipartmax.com/png/middle/86-868176_wooden-cross-transparent-background.png",
                    Found = false,
                    ClueDescription = "The victim died because of severe slashing wounds. Most likely inflicted by a sharp knife.",
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
                    ClueDescription = "We learned from the autopsy the Jhon boddy died because of an overdose of Morphine.",
                    SusForeignKey = 4
                };
                var clue7 = new Clue()
                {
                    ClueName = "Revolver",
                    ClueType = "AR",
                    ClueImgUrl = "https://b.kisscc0.com/20180718/kwq/kisscc0-revolver-firearm-handgun-heckler-koch-p11-pistol-rusty-revolver-rendered-5b4ece451ee928.4789046315318912691266.jpg",
                    Found = false,
                    ClueDescription = "The victim died from gunshot wounds.",
                    SusForeignKey = 5
                };
                var clue9 = new Clue()
                {
                    ClueName = "Shopping Receipt",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://cdn3.iconfinder.com/data/icons/shopping-70/64/shopping-6-512.png",
                    Alibi = true,
                    ClueDescription = "Mrs white claims she was out doing arrands for Mrs Peacock. She showed a receipt from the Delhaize that places her too far away from the murder scene to be directly involved.",
                    SusForeignKey = 7
                };
                var clue10 = new Clue()
                {
                    ClueName = "Hairdress Appointment",
                    ClueType = "Puzzle",
                    ClueImgUrl = "http://www.hairequipe.be/sites/default/files/ha_an_1.jpg",
                    Alibi = true,
                    ClueDescription = "Mrs Peacock was seen getting a haircut at a local hairsalon around the time of the crime.",
                    SusForeignKey = 6
                };
                var clue11 = new Clue()
                {
                    ClueName = "Mass",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://upload.wikimedia.org/wikipedia/commons/4/48/Catholic_Mass_aboard_USS_Ronald_Reagan.jpg",
                    Alibi = true,
                    ClueDescription = "Mr Green was taking confessions at his curch, so he could not be at the crime scene at the time of murder.", 
                    SusForeignKey = 2
                };
                var clue13 = new Clue()
                {
                    ClueName = "Congress",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://thumb101.shutterstock.com/photos/display_pic_with_logo/920555/208250347.jpg",
                    Alibi = true,
                    ClueDescription = "Doctor Boomgaerts was out of town for a congress about the possible consequences of sex changes.",
                    SusForeignKey = 4
                };
                var clue14 = new Clue()
                {
                    ClueName = "Lecture",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://cdn2.iconfinder.com/data/icons/crazy-paparazzi-collection-svg/100/Noun_Project_100Icon_10px_grid_2-47-512.png",
                    Alibi = true,
                    ClueDescription = "Professor Thompson was giving a lecture about cloud computing at AP when the crime was committed.",
                    SusForeignKey = 5
                };
                var clue15 = new Clue()
                {
                    ClueName = "Fitness club",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://www.basic-fit.com/Cms_Data/Contents/BasicFit_NL-BE/Media/ClubImages/Antwerpen-Sportpaleis/Antwerpen_Sportpaleis_Functioneel.jpg?w=390",
                    Alibi = true,
                    ClueDescription = "Miss Scarlet was seen taking a spinning class at the Basic Fit around the time of the murder.",
                    SusForeignKey = 1
                };
                var clue16 = new Clue()
                {
                    ClueName = "Nord Antwerpen",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://u.tfstatic.com/restaurant_photos/003/294003/169/612/restaurant-du-nord-nord-facade-bc1a5.jpg",
                    Alibi = true,
                    ClueDescription = "Colonel Mark Sveniers has evidence that he checked in at the hotel 'Nord Antwerpen', so we think you can exclude him from your list of possible suspects.",
                    SusForeignKey = 3
                };
                var clue16b = new Clue()
                {
                    ClueName = "Nord Antwerpen",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://u.tfstatic.com/restaurant_photos/003/294003/169/612/restaurant-du-nord-nord-facade-bc1a5.jpg",
                    Alibi = true,
                    ClueDescription = "Mrs White has evidence that he checked in at the hotel 'Nord Antwerpen', so we think you can exclude him from your list of possible suspects.",
                    SusForeignKey = 7
                };
                var clue17 = new Clue()
                {
                    ClueName = "Medipolis",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://i1.wp.com/www.refractivealliance.com/wp-content/uploads/2017/01/Medipolis-Logo.jpg?fit=800%2C450",
                    Alibi = true,
                    ClueDescription = "Mrs Peacock had a doctors appointment at Mediopolis.",
                    SusForeignKey = 6
                };
                var clue17b = new Clue()
                {
                    ClueName = "Medipolis",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://i1.wp.com/www.refractivealliance.com/wp-content/uploads/2017/01/Medipolis-Logo.jpg?fit=800%2C450",
                    Alibi = true,
                    ClueDescription = "Mrs White was doing consults throughout the entire day of the crime.",
                    SusForeignKey = 4
                };
                var clue18 = new Clue()
                {
                    ClueName = "Audition Bourla",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://antwerpconventionbureau.be/wp-content/uploads/2017/10/Foyer-2.jpg",
                    Alibi = true,
                    ClueDescription = "Miss Scarlet was auditioning for a starring role at the 40 - 45 musical at bourla during the entire day of the murder.",
                    SusForeignKey = 1
                };
                var clue20 = new Clue()
                {
                    ClueName = "Philadephia bookshop",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://i.redd.it/iext95hfc8o11.jpg",
                    Alibi = true,
                    ClueDescription = "Professor Thompson has a receipt from the time of murder, placing him at a bookstore across the city.",
                    SusForeignKey = 5
                };
                var clue21 = new Clue()
                {
                    ClueName = "Physiotherapy session",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://cdn2.iconfinder.com/data/icons/crazy-paparazzi-collection-svg/100/Noun_Project_100Icon_10px_grid_2-47-512.png",
                    Alibi = true,
                    SusForeignKey = 4
                };
                var clue21b = new Clue()
                {
                    ClueName = "Employment agency",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://cdn2.iconfinder.com/data/icons/crazy-paparazzi-collection-svg/100/Noun_Project_100Icon_10px_grid_2-47-512.png",
                    Alibi = true,
                    ClueDescription = "Miss Scarlet was at an interview about a possible job in the film industry during the day of the crime.",
                    SusForeignKey = 1
                };
                var clue22 = new Clue()
                {
                    ClueName = "Broker",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://s3-media3.fl.yelpcdn.com/bphoto/YGeenMRN2dGtOrd-S32UWg/ls.jpg",
                    Alibi = true,
                    ClueDescription = "Colonel Mark Sveniers was with an real estage agent going around Antwerp, looking for a possible investment in real estate at the time of murder.",
                    SusForeignKey = 3
                };
                var clue23 = new Clue()
                {
                    ClueName = "City Park",
                    ClueType = "Puzzle",
                    ClueImgUrl = "https://www.euroreizen.be/userfiles/item/stadspark-antwerpen-antwerpen-2866_2866_1_xl.jpg",
                    Alibi = true,
                    ClueDescription = "Mr Green has witnesses placing him at the city park, walking his beloved German Shepard, around the time of murder.",
                    SusForeignKey = 2
                };
                //context.Clues.Add(clue0);
                context.Clues.Add(clue1);
                context.Clues.Add(clue2);
                context.Clues.Add(clue3);
                context.Clues.Add(clue4);
                context.Clues.Add(clue5);
                context.Clues.Add(clue6);
                context.Clues.Add(clue7);
                context.Clues.Add(clue9);
                context.Clues.Add(clue10);
                context.Clues.Add(clue11);
                context.Clues.Add(clue13);
                context.Clues.Add(clue14);
                context.Clues.Add(clue15);
                context.Clues.Add(clue16);
                context.Clues.Add(clue16b);
                context.Clues.Add(clue17);
                context.Clues.Add(clue17b);
                context.Clues.Add(clue18);
                context.Clues.Add(clue20);
                context.Clues.Add(clue23);
                context.Clues.Add(clue22);
                context.Clues.Add(clue21);
                context.Clues.Add(clue21b);
                context.SaveChanges();
            }
            //add user if none exist
            if (!context.Users.Any())
            {
                var admin = new User()
                {
                    Username = "WeynsA",
                    Email = "weyns.arno@gmail.com",
                    Password = "E10ADC3949BA59ABBE56E057F20F883E"
                };
                var admin1 = new User()
                {
                    Username = "MassureA",
                    Email = "s091998@ap.be",
                    Password = "E10ADC3949BA59ABBE56E057F20F883E"
                };
                var admin2 = new User()
                {
                    Username = "JoppeM",
                    Email = "joppe.mertens@gmail.com",
                    Password = "E10ADC3949BA59ABBE56E057F20F883E"
                };
                var admin3 = new User()
                {
                    Username = "AlIbra",
                    Email = "s091997@ap.be",
                    Password = "E10ADC3949BA59ABBE56E057F20F883E"
                };
                var admin4 = new User()
                {
                    Username = "Test",
                    Email = "Test@ap.be",
                    Password = "E10ADC3949BA59ABBE56E057F20F883E"
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
