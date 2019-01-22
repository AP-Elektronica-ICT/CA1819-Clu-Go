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

Alle fragmenten worden hier in opgeroepen hier zit ook de menu in
Hier wordt ook alle data vanuit de api lokaal gerangschikt in objecten voor gebruik.


## Backend

Our Backend is made in Visual Studio.
We made use of the ASP .net Core Framework
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


### Android application

Go to the folder containing all directories and open the folder named AndroidStudioProjects/ClueGo in Android studio. Since the game is run on an android application using various api calls in order to communicate with the database we'll have to adjust the url string used inside the application with the one supplied to you by your azure account.
In Android studio locate the  ```values/strings.xml file``` and look for the string called "baseurl". Change this string to the baseurl of your own version of the hosted database. All url's used in the application are base on the baseurl so you won't have to change anything else.


### Adding locations

We've added an angular project that allows users to add locations and clues to the database by accessing a webapplication dashboard. The angular application can be found in the ```CA1819-Clu-Go\adminCluGo file``` located in the repository. Open this application in Visual studio code or another program that will allow you to change the source code. 

Just like with the android application it is important that we change the url's of the http requests within this angular application in order to ensure that all calls are made to the right database. Following is a list of all code lines where an api call is made and where you must change the url to the one of your own hosted database.

  * in ```location.component.ts``` line 32, line 50, line 51
  * in ```locations.service.ts``` line 9

Once this is done host the webapplication on your azure account.

Lastly don't forget to  run ```npm install``` in order to install all node modules used in the application.







