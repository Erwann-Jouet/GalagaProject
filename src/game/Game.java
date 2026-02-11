package game;

import javax.swing.plaf.FontUIResource;

import game.actors.Player;
import game.level.Level;
import game.level.LevelBuilder;
import game.util.StdDraw;
import game.util.ScoreManager;

/**
 * Classe principale du jeu.
 * <p>
 * Gère la boucle principale, les niveaux, le joueur, les scores,
 * et les écrans d'affichage (titre, transitions, game over, victoire).
 * </p>
 */
public class Game {

    /** Joueur actuel */
    private Player player;

    /** Niveau en cours */
    private Level currentLevel;

    /** Numéro du niveau actuel */
    private int currentLevelNumber = 1;

    /** Nombre de niveaux complétés */
    private int levelsCompleted = 0;

    /** Score actuel du joueur */
    private int score = 0;

    /** Meilleur score enregistré */
    private int highScore = 0;

    /**
     * Initialise le jeu.
     * <p>
     * Charge le meilleur score et prépare les données initiales.
     * </p>
     */
    public Game() {
        this.highScore = ScoreManager.loadHighScore();
    }

    /**
     * Initialise l'espace de jeu pour un niveau donné.
     *
     * @param levelNumber numéro du niveau à initialiser
     */
    private void initLevel(int levelNumber) {
        LevelBuilder levelBuilder = new LevelBuilder();
        switch (levelNumber) {
            case 1 -> currentLevel = levelBuilder.buildLevel1(player);
            case 2 -> currentLevel = levelBuilder.buildLevel2(player);
            case 3 -> currentLevel = levelBuilder.buildLevel3(player);
            default -> currentLevel = null;
        }
    }

    /**
     * Prépare la fenêtre et le joueur pour le démarrage d'une partie.
     */
    private void init() {
        StdDraw.setCanvasSize(700, 700);
        StdDraw.enableDoubleBuffering();
        player = new Player(0.5, 0.1, 0.05, 1, 25);
        currentLevelNumber = 1;
        levelsCompleted = 0;
        initLevel(currentLevelNumber);
        displayLevelTransition();
    }

    /**
     * Lance le jeu et la boucle principale.
     * <p>
     * Affiche l'écran titre, gère les niveaux, les collisions,
     * le score, et les écrans de victoire ou game over.
     * </p>
     */
    public void launch() {
        displayTitleScreen();

        while (isGameRunning()) {
            init();

            while (player.isAlive() || player.isRespawning()) {
                if (currentLevel.getEnemies().isEmpty()) {
                    levelsCompleted++;
                    currentLevelNumber++;
                    displayLevelTransition();
                    initLevel(currentLevelNumber);
                    if (currentLevel == null) {
                        displayGameWonScreen();
                        player.setAlive(false);
                        continue;
                    }
                    continue;
                }

                StdDraw.clear(StdDraw.BLACK);

                update();
                draw();

                StdDraw.show();
                StdDraw.pause(30);
            }

            if (!player.isAlive()) {
                handleGameOver();
            }
        }
    }

    /**
     * Affiche l'écran titre du jeu.
     */
    private void displayTitleScreen() {
        StdDraw.setCanvasSize(1000, 1000);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new FontUIResource("Arial", 80, 80));
        StdDraw.text(0.5, 0.6, "GALAGA");

        StdDraw.setFont(new FontUIResource("Arial", 14, 14));
        StdDraw.text(0.5, 0.3, "Appuyez sur ESPACE pour commencer");

        StdDraw.show();
        while (!StdDraw.isKeyPressed(32)) {
            StdDraw.pause(20);
        }
    }

    /**
     * Affiche l'écran de transition pour le niveau suivant.
     */
    private void displayLevelTransition() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new FontUIResource("Arial", 32, 36));
        StdDraw.text(0.5, 0.6, "NIVEAU " + currentLevelNumber);
        StdDraw.show();
        StdDraw.pause(2000);
    }

    /**
     * Affiche l'écran de victoire lorsque tous les niveaux sont terminés.
     */
    private void displayGameWonScreen() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new FontUIResource("Arial", 32, 32));
        StdDraw.text(0.5, 0.6, "VOUS AVEZ GAGNE !");
        StdDraw.setFont(new FontUIResource("Arial", 14, 14));
        StdDraw.text(0.5, 0.5, "SCORE: " + score);
        StdDraw.text(0.5, 0.45, "HIGHSCORE: " + highScore);
        StdDraw.show();
        StdDraw.pause(2500);

        StdDraw.setFont(new FontUIResource("Arial", 14, 24));
        StdDraw.text(0.5, 0.35, "Appuyez sur ESPACE pour recommencer");
        StdDraw.show();
        while (!StdDraw.isKeyPressed(32)) {
            StdDraw.pause(20);
        }
    }

    /**
     * Affiche les niveaux complétés sous forme de sprites en bas à droite.
     */
    private void drawCompletedLevels() {
        for (int i = 0; i < levelsCompleted; i++) {
            try {
                char[][] spriteData = game.util.SpriteLoader.loadSprite("level.spr");
                if (spriteData != null) {
                    game.util.Sprite levelSprite = new game.util.Sprite(spriteData);
                    double posX = 0.95 - (i * 0.05);
                    game.util.SpriteRenderer.drawSprite(levelSprite, posX - 0.02, 0.03, 0.04);
                }
            } catch (Exception e) {
                // ignorer
            }
        }
    }

    /**
     * Dessine tous les éléments du jeu (joueur, ennemis, score, niveaux complétés).
     */
    public void draw() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new FontUIResource("Arial", 14, 14));
        StdDraw.text(0.5, 0.95, "SCORE: " + score);
        StdDraw.text(0.5, 0.92, "HIGHSCORE: " + highScore);

        drawCompletedLevels();
        player.draw();
        currentLevel.drawEnemies();
    }

    /**
     * Met à jour tous les éléments du jeu : joueur, ennemis, collisions et score.
     */
    private void update() {
        player.update();
        currentLevel.updateEnemies();
        currentLevel.handleCollisions();
        int gained = currentLevel.removeDeadActors();
        if (gained > 0) {
            score += gained;
        }
    }

    /**
     * Vérifie si le jeu est en cours.
     *
     * @return toujours true (aucune condition d'arrêt définie)
     */
    private boolean isGameRunning() {
        return true;
    }

    /**
     * Gère l'écran Game Over et la sauvegarde du highscore.
     */
    private void handleGameOver() {
        if (ScoreManager.isNewHighScore(score, highScore)) {
            highScore = score;
            ScoreManager.saveHighScore(highScore);
        }

        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setFont(new FontUIResource("Arial", 23, 24));
        StdDraw.text(0.5, 0.6, "GAME OVER");

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new FontUIResource("Arial", 14, 14));
        StdDraw.text(0.5, 0.5, "SCORE: " + score);
        StdDraw.text(0.5, 0.45, "HIGHSCORE: " + highScore);
        StdDraw.show();
        StdDraw.pause(2500);

        StdDraw.setFont(new FontUIResource("Arial", 14, 24));
        StdDraw.text(0.5, 0.4, "Appuyez sur ESPACE pour recommencer");
        StdDraw.show();

        while (!StdDraw.isKeyPressed(32)) {
            StdDraw.pause(20);
        }

        player.setAlive(true);
        score = 0;
    }
}
