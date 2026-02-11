package game.util;

/**
 * Représente un sprite sous forme de tableau de caractères.
 * <p>
 * Chaque sprite contient une matrice de pixels, où chaque pixel
 * est représenté par un caractère. La position (0,0) correspond
 * au coin supérieur gauche du sprite.
 * </p>
 */
public class Sprite {

    /** Matrice des pixels du sprite */
    private final char[][] pixels;

    /** Largeur du sprite (nombre de colonnes) */
    private final int width;

    /** Hauteur du sprite (nombre de lignes) */
    private final int height;

    /**
     * Construit un sprite à partir d'une matrice de caractères.
     *
     * @param pixels matrice de caractères représentant le sprite
     */
    public Sprite(char[][] pixels) {
        this.pixels = pixels;
        this.height = pixels.length;
        this.width = (height > 0) ? pixels[0].length : 0;
    }

    /**
     * @return largeur du sprite
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return hauteur du sprite
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retourne le caractère à la position (x, y) dans le sprite.
     * <p>
     * La coordonnée (0,0) correspond au coin supérieur gauche.
     * Si les coordonnées sont hors limites, retourne 'N' (transparent).
     * </p>
     *
     * @param x position horizontale
     * @param y position verticale
     * @return caractère du pixel ou 'N' si hors limites
     */
    public char getPixel(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return 'N'; // transparent par défaut si hors limites
        }
        return pixels[y][x];
    }
}
