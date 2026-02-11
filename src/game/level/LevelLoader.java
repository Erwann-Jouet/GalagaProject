package game.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import game.actors.Enemy;
import game.actors.Player;
import game.actors.Enemys.EnemyType;
import game.util.Vector2;

/**
 * Charge les niveaux depuis des fichiers de configuration.
 * <p>
 * Le format du fichier .lvl attendu est le suivant :
 * 
 * <pre>
 * LevelName formationSpeed attackCooldown(ms, -1 pour désactiver) enemyShootCooldown(ms)
 * EnemyType x y size value speed
 * ...
 * </pre>
 * 
 * Les lignes vides ou commençant par '#' sont ignorées.
 * Chaque ligne d'ennemi décrit un ennemi à instancier dans le niveau.
 * </p>
 */
public class LevelLoader {

    /**
     * Charge un niveau à partir d'un fichier et l'associe à un joueur.
     * <p>
     * Lit le fichier, extrait les paramètres de formation et crée
     * tous les ennemis via {@link EnemyFactory}.
     * </p>
     *
     * @param levelFileName nom du fichier de niveau (ex: "level1.lvl")
     * @param player        joueur associé au niveau
     * @return instance du {@link Level} correspondant
     * @throws IOException si le fichier est introuvable ou non lisible
     */

    /*
     * @IAGENERATIVE
     * 
     * J'ai reçu de l'aide D'une IA pour cette fonction. Ici je n'avais vraiment
     * aucune idée de comment réalsier et par ou commencer pour crée cette fonction.
     * Ainsi que toutes les prossibles erreurs à gerer.
     * Cette méthode, et seulement celle-ci à été réaliser en majeur-partie par
     * L'IA.
     */
    public static Level loadLevel(String levelFileName, Player player) throws IOException {
        String filePath = "ressources/levels/" + levelFileName;

        List<EnemyData> enemyDataList = new ArrayList<>();
        String levelName = "";

        // Première lecture pour récupérer les ennemis et le nom du niveau
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNum = 0;

            while ((line = reader.readLine()) != null) {
                lineNum++;
                line = line.trim();

                // Ignorer lignes vides ou commentaires
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\s+");

                // Première ligne : informations du niveau
                if (lineNum == 1) {
                    levelName = parts[0];
                    continue;
                }

                // Lignes suivantes : données ennemis (Type x y size value speed)
                if (parts.length >= 6) {
                    try {
                        EnemyType type = EnemyType.valueOf(parts[0].toUpperCase());
                        double x = Double.parseDouble(parts[1]);
                        double y = Double.parseDouble(parts[2]);
                        double size = Double.parseDouble(parts[3]);
                        double value = Double.parseDouble(parts[4]);
                        double speed = Double.parseDouble(parts[5]);

                        enemyDataList.add(new EnemyData(type, new Vector2(x, y), size, speed, value));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Erreur ligne " + lineNum + ": type d'ennemi invalide: " + parts[0]);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier: " + filePath);
            throw e;
        }

        // Deuxième lecture pour extraire les paramètres de formation et cooldown
        double formationSpeed = 0.001;
        long attackCooldown = 5000;
        long enemyShootCooldown = 700;

        try (BufferedReader reader2 = new BufferedReader(new FileReader(filePath))) {
            String first = reader2.readLine();
            if (first != null) {
                String[] header = first.trim().split("\\s+");
                if (header.length >= 2) {
                    try {
                        formationSpeed = Double.parseDouble(header[1]);
                    } catch (Exception ex) {
                        // valeur par défaut si parse échoue
                    }
                }
                if (header.length >= 3) {
                    try {
                        double v = Double.parseDouble(header[2]);
                        attackCooldown = (v < 0) ? Long.MAX_VALUE : (long) v;
                    } catch (Exception ex) {
                    }
                }
                if (header.length >= 4) {
                    try {
                        enemyShootCooldown = (long) Double.parseDouble(header[3]);
                    } catch (Exception ex) {
                    }
                }
            }
        } catch (IOException e) {
            // ignore, valeurs par défaut déjà appliquées
        }

        // Création des ennemis et de la formation
        List<Enemy> enemies = new ArrayList<>();
        Formation formation = new Formation(enemies);
        formation.setFormationSpeed(formationSpeed);
        formation.setAttackCooldown(attackCooldown);
        formation.setEnemyShootCooldown(enemyShootCooldown);

        EnemyFactory factory = new EnemyFactory(formation);
        for (EnemyData data : enemyDataList) {
            enemies.add(factory.createEnemy(data));
        }

        Level level = new Level(levelName, enemies, player);

        // Synchroniser les paramètres de la formation dans le Level
        level.getFormation().setFormationSpeed(formationSpeed);
        level.getFormation().setAttackCooldown(attackCooldown);
        level.getFormation().setEnemyShootCooldown(enemyShootCooldown);

        return level;
    }
}
