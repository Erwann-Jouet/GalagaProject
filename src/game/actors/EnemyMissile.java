package game.actors;

import game.util.StdDraw;
import game.util.Vector2;

/**
 * Représente un missile tiré par un ennemi.
 * <p>
 * Le missile se déplace verticalement vers le bas et est affiché
 * sous la forme d'une ligne rouge.
 * </p>
 */
public class EnemyMissile extends Missile {

    /**
     * Construit un missile ennemi.
     *
     * @param position position initiale du missile
     * @param velocity vecteur de déplacement du missile
     */
    public EnemyMissile(Vector2 position, Vector2 velocity) {
        super(position.getX(), position.getY(), 0.02, velocity);
        this.velocity = velocity;
    }

    /**
     * Dessine le missile à l'écran.
     * <p>
     * Le missile est représenté par une ligne verticale rouge.
     * </p>
     */
    @Override
    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);

        double halfLength = 0.02; // Demi-longueur de la ligne
        double x = position.getX();
        double y = position.getY();

        StdDraw.line(
                x, y - halfLength,
                x, y + halfLength);
    }
}
