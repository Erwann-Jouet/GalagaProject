# Galaga Project – Java Game

**Author:** Erwann Jouet  

A modern Java remake of the classic arcade game **Galaga**, where the player controls a spaceship to defeat waves of enemies while dodging their attacks. The game features multiple levels, a scoring system, player respawn, and temporary invincibility mechanics.

---

## Features

### Core Gameplay
- **Player Movement:** Move left and right using arrow keys.  
- **Shooting:** Fire missiles using the SPACE key.  
- **Enemy Types:** Various enemies including Bee, Butterfly, Moth, and Boss.  
- **Enemy Behavior:** Random attacks and collision detection with the player.  
- **Missile Management:** Handles both enemy and player projectiles.  
- **Lives System:** Player has 3 lives with respawn mechanics.  

### Game Mechanics
- **Scoring:** Tracks player score and saves high scores.  
- **Levels:** Multiple levels loaded dynamically from `.lvl` files.  
- **Cooldowns:** Shooting has a cooldown and maximum number of missiles.  
- **Temporary Invincibility:** Player is invincible briefly after respawn.  

### Visuals & UI
- **Sprites:** Player, enemy, and level graphics displayed using `StdDraw`.  
- **Screens:** Includes level transitions, title screen, and victory screen.  

---

## Getting Started

### Prerequisites
- Java 8 or higher installed.  
- An IDE such as Visual Studio Code or IntelliJ IDEA.  

### Running the Game
1. Clone or download the repository.  
2. Ensure the `src` and `resources` folders are present.  
3. Run the main class:  
   ```bash
   Run App.java

### Controls

Arrow Left/Right: Move the spaceship
SPACE: Fire missiles
The score and highscore are displayed at the top of the screen. Levels progress automatically, and the player starts with 3 lives.

### Project Structure

GalagaProject/ <br>
├── src/           # Java source files <br>
├── resources/     # Level files, sprites, and assets <br>
└── README.md      # Project documentation
