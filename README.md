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

## Backend

Our Backend is made in Visual Studio.
We made use of the ASP .net Core Framework
