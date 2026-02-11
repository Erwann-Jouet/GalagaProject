package game.util;

import java.awt.Color;

/**
 * Classe utilitaire pour dessiner des sprites à l'écran.
 * <p>
 * Chaque sprite est une matrice de caractères. Les caractères
 * sont convertis en couleurs via {@link #charToColor(char)}.
 * Les pixels avec le caractère 'N' sont considérés comme
 * transparents.
 * </p>
 */
public class SpriteRenderer {

    /**
     * Dessine un sprite à la position spécifiée avec la taille donnée.
     * <p>
     * La position (posX, posY) correspond au coin inférieur gauche
     * du sprite, et la taille est appliquée uniformément aux axes X et Y.
     * </p>
     *
     * @param sprite sprite à dessiner
     * @param posX   coordonnée X du coin inférieur gauche
     * @param posY   coordonnée Y du coin inférieur gauche
     * @param size   largeur et hauteur totale du sprite
     */
    /*
     * @IAGENERATIVE
     * 
     * J'ai reçu de l'aide D'une IA pour cette fonction, seulement pour la logique
     * mathématiques des pixels.
     */
    public static void drawSprite(Sprite sprite, double posX, double posY, double size) {
        int width = sprite.getWidth();
        int height = sprite.getHeight();

        double pixelWidth = size / width;
        double pixelHeight = size / height;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char c = sprite.getPixel(x, y);
                if (c != 'N') { // 'N' = transparent
                    StdDraw.setPenColor(charToColor(c));
                    // inverser y pour dessiner depuis le coin inférieur gauche
                    StdDraw.filledRectangle(
                            posX + x * pixelWidth + pixelWidth / 2,
                            posY + (height - 1 - y) * pixelHeight + pixelHeight / 2,
                            pixelWidth / 2,
                            pixelHeight / 2);
                }
            }
        }
    }

    /**
     * Convertit un caractère en couleur.
     * <p>
     * Par convention :
     * <ul>
     * <li>R = RED</li>
     * <li>B = BLUE</li>
     * <li>G = GREEN</li>
     * <li>Y = YELLOW</li>
     * <li>W = WHITE</li>
     * <li>N = BLACK (transparent par convention)</li>
     * </ul>
     * Tout autre caractère renvoie BLACK par défaut.
     * </p>
     *
     * @param c caractère à convertir
     * @return couleur correspondante
     */
    public static Color charToColor(char c) {
        return switch (c) {
            case 'R' -> Color.RED;
            case 'B' -> Color.BLUE;
            case 'G' -> Color.GREEN;
            case 'Y' -> Color.YELLOW;
            case 'W' -> Color.WHITE;
            case 'N' -> Color.BLACK;
            default -> Color.BLACK; // fallback
        };
    }
}
