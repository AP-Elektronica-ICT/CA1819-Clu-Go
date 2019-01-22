# CA1819-Clu-Go


dashboard:
https://cluegodashboard.azurewebsites.net/#/location

api:
https://cluego.azurewebsites.net/api/

## Context

Our City Game is about solving a murder that has happened in Antwerp City.

You get a list of suspects and among them is the murderer that you have to catch.

The murderer got sloppy and left some clues around in the city, the places where those clues might be are marked on a map.

by visiting those places you will get some puzzles you have to solve or look around with your phone(camera) to find items/notes who then will be stored in your inventory.

Your inventory is accessible anytime with the items you have found, in the inventory tab there you can check the clues again in case you might have missed something.

Once sure who the murder is you have to walk to a virtual police station and you can turn the murderer in. 
After 3 false guesses you lose the game.

Your lifetime Achievements also get saved so you can boast your feats to your competition.


## Front End

The Front end part of our application is made in Android Studio. 

In This part of the Readme we will explain what each activity and fragment does to give developers a better understanding and insight into the functionality of the application.

Our Applications is made almost entirely out of fragments after you enter your credentials.

We made it that way because it was more fitting, making everything easy to modify and made the project modular. 

Example given, if you want to add a new feature instead of making a new activity and adding a menu you can just use the menu from the original activity and only change the upper part of the activity. So in actuality this whole projects is one activity aside from the login and register pages.

Having said that, lets get into the more detailed information about the application.

### Login Activity

In here the authentication process happens.

When you type your email and your password this data get transformed into a string and is then used to be passed into and url that accesses our api.

If the email and password exist in the database then the response will be valid this is checked in the backend part of the application in the user controller.

If the response is valid then you create a local object of the user data to be passed to other fragments and later on be displayed, afterwards you will get redirected to the start of the game where most o the data management happens.


### Start Game Fragment (Met Vertaling)

In this fragment most of the data gets brought in the only thing that doesnt happen in this fragment (datawise) is the change of dynamic data e.g clues updated from found to not found, guess suspect, ... Those things happen in the respective fragment where the change is required of applied.

You get 3 options in this fragment.

You start you own game - Start New Game

You continue your own game that you haven't finished yet - Continue

You join a friend game without losing your own - Join Game

When you make a new game you get to choose how many suspects you want, this will also be the number of clues and locations there will be in the game. after you enter a number between 3 and 7 this number together with your userid gets added to an url who then communicates with the backend game controller. This controller checks if the user already has a game if he does he gets asked to continue his own game if he doesnt a new one will be made for him and will get loaded.

If you continue a game you fetch a game from the database using your Userid if the Userid is 0 aka not found or doesnt exist, a second try will be made using the username(for joining other players using username).

Wether a game exists or not also gets double checked. First if you get a response but the length of the response is 0 that means no data and second if you get no response aka the game you tried to fetch doesnt exist because none is found with the respective Userid or Username.

If you make a new game or continue an existing one this is the data that will be brought in 

* Locations
* Suspects
* Clues 
* User Statistics

#### Incomplete Vertaling

In deze fragment word het overgrotendeel van je data binnengeroepen het enige wat hier niet word gedaan zijn de dynamische dingen bv. een clue updaten van not found naar found hier worden wel alle not found clues ingeladen voor gebruik.

Het aantal clues en suspects die u te zien krijgt kunt u zelf (tussen 3 en 7) kiezen voor u een game start.

U krijgt hier 3 opties

U Start uw eigen game - Start New Game

U Doet voort met uw game die u niet heeft afgemaakt - Continue

U Doet mee met een vriend zijn game zonder dat u, uw eigen game kwijtraakt - Join Game

Bij het maken van een nieuwe game word uw user id en uw aantal gekozen suspect/clues aan de url toegevoegd die url wordt nagekeken door de game controller of dergelijke user wel een game heeft of niet.

Als de user geen game heeft word er automatisch een nieuwe game aangemaakt en geladen.

Als de user al een game heeft word hij hiervan verwittigd

### Main Activity

This is as the name suggests the main activity the bottom part exists of the menu the upper part the container for the fragments.
In here also the data gets organised in local objects instead of being used directy from the api call this makes it easier to use the data of course this doesnt work with everything, dynamic data still gets modified through calls.

//Alle fragmenten worden hier in opgeroepen hier zit ook de menu in
//Hier wordt ook alle data vanuit de api lokaal gerangschikt in objecten voor gebruik.


## Backend

### General info

Our Backend is made in Visual Studio.
We made use of the ASP .net Core Entinty Framework.
ASP as we all probably know has a layered structure and in this chapter we will be going through them each step of the way.

### Models

What are models?

Models are basically what each row in the table will look like.
Models will contain properties, those will be your "columns" in the database.
When you want to make a new row in the database you have to make and object of the model and add values to the given properties.
Once the object is valid then it can be inserted in the database through the entity framework.
This can be through a controller/service or through the initializer.

Models can also contain Foreign keys or Collections/Lists of other object.

And in our case thats strongly the case and almost true for every table.

For example a game contains much data this would be very tedious and unorganized to all put in one table, Simply maintaining that kind of data would be an administrative nightmare.

So to simple our cause we made use of linking tables to basically "link" or two objects to one and then later on use that object.

Basically a linked table is a join table of 2 different object.

let me continue my example for the game table.
A game has many Clues to be found.
those Clues are bound to a Suspect thats something you can do in the Suspect model.
But if we go further and think how is a game going to know which Clues to use we need to find a solution for that and the solution is the linking table what it does is when you make a new game it gives the gameid to the linking table and adds a fitting clue to the linking table based on the chosen murderer.

Same to location it choses a random location from the database adds a gameid to and that will be the used table in the game. When you reach a location a random clue gets chosen from the clues linked to the game.

### Context

In here the actual table gets made. When you make a statement like 

public DBSet<Clue> Clues {get; set;}

that actually means that you make a table in the database named Clues that contains objects based on the Clue model.

In here you also define the relationships the tables have to eachother.

The relationship the tables have to eachother is easier to explain through a picture

![alt text](https://github.com/AP-Elektronica-ICT/CA1819-Clu-Go/blob/master/images/finaledatamodel.jpg)

### Services

Services are a wonderous thing to use once understood it servers as a medium between controllers and data access.

The best thing about it is that it makes Sql injection very hard if not impossible and in general creates a extra layer of security because it doesnt communicate with the front end directly.

And it really creates that extra layer that makes testing easier and allowes for a higher level oversight over data handling.

We switched to services in the testing phase a real shame we didnt switch earlier because of the ease and oversight it created.

### Controllers

Controller used to do more earlier in the project now they only call the required service to do the job.

# Deployment

This appplication is a City game that can be played in teh city of Antwerp. In order to play the game a user must go through several steps in order to make it functional.


# Installation


## Requirements

  * Android smartphone with Android 8.0 or higher
  * Azure account with hosting capabilities
  * Android Studio
  * Visual Studio 2017
  * VSCode


## Installment

First things first, let's install the dependencies on our computer:
```
 git clone https://github.com/AP-Elektronica-ICT/CA1819-Clu-Go
```
### ASP database
To setup a database, we will use Azure Database hosting. You need an Azure resourcegroup containing a SQLDatabase and a Web App. Once you have the connection string from your Azure database, add it to the application.json file in the ClueGoASP solution. Once you've changed the connectionstring to your own, you are ready to publish the API to your Web Application.

### Android application

Go to the folder containing all directories and open the folder named ```AndroidStudioProjects/ClueGo``` in Android studio. Since the game is run on an android application using various api calls in order to communicate with the database we'll have to adjust the url string used inside the application with the one supplied to you by your azure account.
In Android studio locate the  ```values/strings.xml file``` and look for the string called "baseurl". Change this string to the baseurl of your own version of the hosted database. All url's used in the application are base on the baseurl so you won't have to change anything else.


### Adding locations

We've added an angular project that allows users to add locations and clues to the database by accessing a webapplication dashboard. The angular application can be found in the ```CA1819-Clu-Go\adminCluGo file``` located in the repository. Open this application in Visual studio code or another program that will allow you to change the source code. 

Just like with the android application it is important that we change the url's of the http requests within this angular application in order to ensure that all calls are made to the right database. Following is a list of all code lines where an api call is made and where you must change the url to the one of your own hosted database.

  * in ```location.component.ts``` line 32, line 50, line 51
  * in ```locations.service.ts``` line 9

Once this is done host the webapplication on your azure account.








