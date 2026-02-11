package game.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Charge des sprites depuis des fichiers texte.
 * <p>
 * Chaque fichier de sprite est lu ligne par ligne et converti
 * en matrice de caractères. Chaque caractère représente un pixel
 * du sprite.
 * </p>
 */
public class SpriteLoader {

    /**
     * Charge un sprite depuis un fichier.
     *
     * @param fileName nom du fichier de sprite (ex: "ship.spr")
     * @return matrice de caractères représentant le sprite
     * @throws RuntimeException si le fichier est introuvable ou en cas d'erreur de
     *                          lecture
     */
    public static char[][] loadSprite(String fileName) {

        File file = new File("ressources/sprites/" + fileName);

        if (!file.exists()) {
            throw new RuntimeException("Sprite introuvable : " + file.getAbsolutePath());
        }

        try (Scanner scanner = new Scanner(file)) {

            List<char[]> lines = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line.toCharArray());
            }

            return lines.toArray(new char[0][]);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la lecture du sprite : " + fileName, e);
        }
    }
}
