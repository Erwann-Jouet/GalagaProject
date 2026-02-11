package game.util;

/**
 * Représente un vecteur ou une position 2D.
 * <p>
 * Cette classe est immuable : toutes les opérations renvoient
 * un nouveau Vector2 sans modifier l'instance originale.
 * </p>
 */
public final class Vector2 {

    /** Coordonnée X */
    private final double x;

    /** Coordonnée Y */
    private final double y;

    /**
     * Crée un vecteur 2D avec des coordonnées données.
     *
     * @param x coordonnée X
     * @param y coordonnée Y
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return coordonnée X
     */
    public double getX() {
        return x;
    }

    /**
     * @return coordonnée Y
     */
    public double getY() {
        return y;
    }

    /**
     * Additionne ce vecteur avec un autre.
     * <p>
     * Ne modifie pas l'instance actuelle ; renvoie un nouveau Vector2.
     * </p>
     *
     * @param other vecteur à ajouter
     * @return nouveau vecteur résultant de l'addition
     */
    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    /**
     * Multiplie ce vecteur par un facteur.
     * <p>
     * Ne modifie pas l'instance actuelle ; renvoie un nouveau Vector2.
     * </p>
     *
     * @param factor facteur de mise à l'échelle
     * @return nouveau vecteur mis à l'échelle
     */
    public Vector2 scale(double factor) {
        return new Vector2(this.x * factor, this.y * factor);
    }

    /**
     * Calcule la distance euclidienne entre ce vecteur et un autre.
     *
     * @param other vecteur cible
     * @return distance euclidienne
     */
    public double distance(Vector2 other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
