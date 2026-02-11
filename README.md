# Projet Galaga - Jeu Java

Membres du monôme :

- Jouet Erwann

Description du projet :
Ce projet est une réplique du jeu Galaga. Le joueur contrôle un vaisseau spatial pour éliminer des vagues d'ennemis tout en évitant leurs tirs. Le projet inclut plusieurs niveaux, un système de score et de highscore, ainsi que des fonctionnalités de respawn et d'invincibilité après une perte de vie.

Fonctionnalités réalisées :

- Déplacement du joueur (flèches gauche/droite)
- Tir du joueur (touche ESPACE)
- Ennemis avec différents types (Bee, Butterfly, Moth, Boss)
- Attaques aléatoires des ennemis et collisions avec le joueur
- Gestion des missiles ennemis et du joueur
- Système de vies et respawn du joueur
- Calcul du score et sauvegarde du meilleur score
- Multiples niveaux chargés depuis des fichiers `.lvl`
- Affichage des sprites du joueur, ennemis et niveaux via `StdDraw`
- Écrans de transition de niveaux, écran titre et écran de victoire
- Limitation du tir avec cooldown et nombre maximal de missiles

Fonctionnalités supplémentaires :

- Effet d'invincibilité temporaire après réapparition

Guide pour exécuter le projet :

1. Ouvrez le projet dans Visual Studio Code
2. Vérifiez que le dossier `src` et `ressources` sont présents
3. Lancez le jeu :
   Run App.java
4. Commandes de jeu :
   - Flèche gauche/droite : déplacer le joueur
   - ESPACE : tirer
5. Le score et le highscore sont affichés en haut de l’écran
6. Le joueur a 3 vies par défaut, et les niveaux se succèdent automatiquement
