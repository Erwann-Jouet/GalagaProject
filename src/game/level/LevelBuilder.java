package game.level;

import java.io.IOException;
import game.actors.Player;

/**
 * Classe utilitaire pour construire des niveaux prédéfinis.
 * <p>
 * LevelBuilder fournit des méthodes statiques pour créer
 * différents niveaux du jeu à partir de fichiers de niveau.
 * En cas d'erreur de lecture, un niveau vide est retourné.
 * </p>
 */
public class LevelBuilder {

    /**
     * Construit et retourne le niveau 1.
     *
     * @param player joueur à associer au niveau
     * @return instance du niveau 1, ou niveau vide en cas d'erreur
     */
    public static Level buildLevel1(Player player) {
        try {
            return LevelLoader.loadLevel("level1.lvl", player);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du niveau 1");
            e.printStackTrace();
            return new Level("Level1", new java.util.ArrayList<>(), player);
        }
    }

    /**
     * Construit et retourne le niveau 2.
     *
     * @param player joueur à associer au niveau
     * @return instance du niveau 2, ou niveau vide en cas d'erreur
     */
    public static Level buildLevel2(Player player) {
        try {
            return LevelLoader.loadLevel("level2.lvl", player);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du niveau 2");
            e.printStackTrace();
            return new Level("Level2", new java.util.ArrayList<>(), player);
        }
    }

    /**
     * Construit et retourne le niveau 3.
     *
     * @param player joueur à associer au niveau
     * @return instance du niveau 3, ou niveau vide en cas d'erreur
     */
    public static Level buildLevel3(Player player) {
        try {
            return LevelLoader.loadLevel("level3.lvl", player);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du niveau 3");
            e.printStackTrace();
            return new Level("Level3", new java.util.ArrayList<>(), player);
        }
    }
}
