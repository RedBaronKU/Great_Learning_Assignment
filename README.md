# HappyMindAssignment


Hi Reviewers,

Sorry, couldn't add test cases this time, because I had to fix a lot of incompactible types on editor for Java 15.
Also, I implemented naming for each player, as Player-2 sounds outrageous. 

## **How to Run:**
> Save the file as HappyMinds.java and compile with a Java Compiler.


### ***Some feature:*** 

>#### Added error hadling for user inputs.
>#### Checking if target is valid ie, >0 and a number
>#### Applied OOPS concept in design, to prevent unwanted data access
>#### To make the Game in a Single file, used nested class which looks bulky.
>#### Randomizing using util.Random library

## Game Rules:->

*Let us code “The Game of Dice" 
The "Game of Dice" is a multiplayer game where N players roll a 6 faced dice in a round-robin 
fashion. Each time a player rolls the dice their points increase by the number (1 to 6) achieved 
by the roll.*  
 
*As soon as a player accumulates M points they complete the game and are assigned a rank. 
Remaining players continue to play the game till they accumulate at least M points. The game 
ends when all players have accumulated at least M points.* 
 
###***Rules of the game*** 

>The order in which the users roll the dice is decided randomly at the start of the game. 

>If a player rolls the value "6" then they immediately get another chance to roll again and move 
ahead in the game. 

>If a player rolls the value "1" two consecutive times then they are forced to skip their next turn 
as a penalty.

## Problem Statement:

**Implement a standalone program in your favorite programming language which takes the 
values N (number of players) and M (points of accumulate) as command line arguments.** 
> *Name the players as Player-1 to Player-N and randomly assign the order in which they 
will roll the dice. 

> *When it's the turn for Player-X to roll the dice prompt a message like “Player-3 its your 
turn (press ‘r’ to roll the dice) 

> *Randomly simulate a dice roll, display the points achieved and add the points to the 
user’s score. 

> *Print the current rank table which displays the points of all users and their rank after 
each roll. 

> *If the user gets another chance because they rolled a ‘6’ or they are penalised because 
they rolled ‘1’ twice consecutively then print appropriate message on standard output to 
inform the user. 

> *If a user completes the game, print an appropriate message on the output displaying 
their rank
