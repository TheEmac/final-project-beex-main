overview of the feature that I am implementing


part 1 
When the game was condition to be able to jump and double jump was if the players vertical velocity was close to or equal to 0. This is bad as anytime the player hits the ceiling they would refresh their air jump which is unintended. The proper fix is to make a collision hitbox underneath the player that checks for ground objects, and if the box detects a collision grounded is set to true and the user can jump off the ground and air jump is refreshed to 1.
part 2
I made these changes as well as a button in the pause menu that that toggle the ability to double jump in level one.

pseudo code:

part1
groundSensorGraphic = new GRect(player width, 4);
groundSensorGraphic.setFilled(true);
groundSensorGraphic.setColor(red);
program.add(groundSensorGraphic);
groundSensorGraphic.sendToFront();

groundSensorGraphic.setLocation(playerlocationx, playerlocation y + pixel to feet)


private boolean isGroundSensorTouching():
    // Get sensor bounds
    double sensorX = playerBody.getX();
    double sensorY = playerBody.getY();

    b in all objects
        if sensor location is within b:
            return true;
    
    return false;


grounded = isGroundSensorTouching();
if (grounded) {
    airjump = true; // reset double jump when on ground
}

part 2
Add image for pause menu for air jump

in pausemenu.java

airjump = new GImage(path to airjump, x, y)

public void showContents() {
program.add(airjump);
}

public void hideContents() {
    program.remove(airjump);
}

in levelone.java:
add allowDoubleJump variable to jump condition for airjump

public void setDoubleJump(boolean toggle) {
	    allowDoubleJump = toggle;
}


if mouse pressed:
if (obj == pauseMenu.airjump) {
	setDoubleJump(!allowDoubleJump);
	resume();
} 


# Elemental Expedition
Starter Project for Comp 55

You will use this project as your base.
Make sure that you understand the two files provided here

<h1> Requirements Statement </h1>
Our video game Elemental Expedition is here to provide an entertaining experience for anyone who might be on the hunt for a fun and challenging 2D platformer. Whether it be to simply kill time or test your own skills in an enjoyable atmosphere, this game was created with the open intent of letting anyone and everyone unwind, enjoy themselves, and play. The game is reminiscent of those within the same genre, such as "Fireboy & Watergirl" or "Super Mario Bros." You'll be controlling the player character as you jump from platform to platform, all the while using elemental abilities that can assist you in discovering ways to complete each level. The true challenge for players comes from having to balance both your wits and your skills to accomplish that task and make perfect use of the elements at your disposal. Elemental Expedition was built in Java and makes use of a pixel art aesthetic that matches perfectly with the original soundtrack that our team made for each and every level.

<br><br/>

The main menu screen of the game!

<img src="media/git_menu_preview.png" width="400" height="300">

Gameplay from level two!

<img src="media/git_gameplay_preview.png" width="400" height="300">



## UML Class Diagram for files provided
![](media/55GroupProjectUML.jpg)

## UML Sequence Diagram for files provided
![](media/55GroupProjectSequenceDiagram.png)

## Interaction Diagram
![](media/55GroupProjectInteractionDiagram.png)

The patterns used in this design are typical to a normal platformer. All games start at the main menu, as this is the point where you can go between all points of the game.
From there, the player can go to the level select, setting, and the credits of the game. Having a button to start the game is also very normal for games that are level based, as it makes playing the game quickly accessible, and not as much of a hassle. Having a level select is also nothing out of the ordinary to a level based game, as being able to select any individual level is important so that there is a quicker easier way to get to a level of choice, instead of needing to complete each level in order. Lastly, sending the player to the victory screen once they touch the door (and press spacebar to enter) is also justifiable, since going to the next level or to the main menu will always be the next step after completing a level.

## References
* We took inspiration from the game series "Fireboy and Watergirl", which are games where two players will control the characters Fireboy and Watergirl to use their perspective abilities to solve puzzles in many unique levels. [Play the game here](https://www.coolmathgames.com/0-fireboy-and-water-girl-in-the-forest-temple)
* We used the ACM library to render our character/avatar and other objects in our level, while it can't be used to do anything else really, it is relatively easy to use and really helpful for us. [Link](https://cs.stanford.edu/people/eroberts/jtf/javadoc/student/)
* We also used the dyn4j library/physics engine to apply collision physics to all the objects we placed in the levels. We couldn't figure out how to reliably implement a consistent physics presence in our game, and we had to resort to finding a new library that allows easy implementation of physics to objects in the game, and we found dyn4j. [Link](https://dyn4j.org)
