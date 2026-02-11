package game.actors;

import game.util.StdDraw;
import game.util.Vector2;

/**
 * Représente un missile tiré par le joueur.
 * <p>
 * Un PlayerMissile est un missile vert qui se déplace selon
 * un vecteur vitesse donné et disparaît lorsqu'il sort de l'écran.
 * </p>
 */
public class PlayerMissile extends Missile {

    /**
     * Construit un missile du joueur.
     *
     * @param position position initiale du missile
     * @param velocity vecteur vitesse appliqué à chaque mise à jour
     */
    public PlayerMissile(Vector2 position, Vector2 velocity) {
        super(position.getX(), position.getY(), 0.02, velocity);
    }

    /**
     * Dessine le missile à l'écran.
     * <p>
     * Le missile est représenté par une ligne verticale verte.
     * </p>
     */
    @Override
    public void draw() {
        StdDraw.setPenColor(StdDraw.GREEN);
        double halfLength = 0.02;
        double x = position.getX();
        double y = position.getY();
        StdDraw.line(x, y - halfLength, x, y + halfLength);
    }
}
