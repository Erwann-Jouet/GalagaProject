package game.util;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Gestionnaire du meilleur score.
 * <p>
 * ScoreManager permet de charger, sauvegarder et vérifier
 * le meilleur score dans le fichier :
 * <code>ressources/highscore/highscore.sc</code>.
 * </p>
 */
public class ScoreManager {
    /*
     * @IAGENERATIVE
     * 
     * J'ai reçu de l'aide D'une IA pour cette classe. De l'aide minimes. Simplement
     * pour bien lire les fichiers et qu'elles exceptions gérer. Mais L'IA ma donner
     * une ligne directrice et des consignes, pas le code.
     */

    /** Chemin vers le fichier de sauvegarde du highscore */
    private static final String HIGHSCORE_FILE = "ressources/highscore/highscore.sc";

    /**
     * Charge le meilleur score depuis le fichier.
     * <p>
     * Si le fichier n'existe pas, est vide ou mal formaté,
     * retourne 0.
     * </p>
     *
     * @return le meilleur score
     */
    public static int loadHighScore() {
        try {
            File file = new File(HIGHSCORE_FILE);
            if (!file.exists()) {
                return 0;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                if (line != null && !line.trim().isEmpty()) {
                    try {
                        int score = Integer.parseInt(line.trim());
                        return score;
                    } catch (NumberFormatException e) {
                        System.err.println("Format invalide du highscore, remise à 0");
                        return 0;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du highscore : " + e.getMessage());
        }
        return 0;
    }

    /**
     * Sauvegarde le meilleur score dans le fichier.
     * <p>
     * Crée les répertoires nécessaires si ils n'existent pas.
     * </p>
     *
     * @param score le score à sauvegarder
     */
    public static void saveHighScore(int score) {
        try {
            File file = new File(HIGHSCORE_FILE);
            file.getParentFile().mkdirs(); // créer les répertoires si nécessaire

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(String.valueOf(score));
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde du highscore : " + e.getMessage());
        }
    }

    /**
     * Vérifie si le score actuel est un nouveau record.
     *
     * @param currentScore score actuel
     * @param highScore    meilleur score enregistré
     * @return true si currentScore est supérieur à highScore
     */
    public static boolean isNewHighScore(int currentScore, int highScore) {
        return currentScore > highScore;
    }
}
