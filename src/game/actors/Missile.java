package game.actors;

import game.util.StdDraw;
import game.util.Vector2;

/**
 * Représente un missile dans le jeu.
 * <p>
 * Un missile est un acteur mobile possédant une vitesse.
 * Il se déplace à chaque mise à jour et est détruit automatiquement
 * lorsqu'il sort des limites verticales de l'écran.
 * </p>
 */
public class Missile extends Actor {

    /** Vecteur vitesse du missile */
    protected Vector2 velocity;

    /**
     * Construit un missile.
     *
     * @param x        position horizontale initiale
     * @param y        position verticale initiale
     * @param length   taille du missile
     * @param velocity vecteur vitesse appliqué à chaque mise à jour
     */
    public Missile(double x, double y, double length, Vector2 velocity) {
        super(x, y, length);
        this.velocity = velocity;
    }

    /**
     * @return vecteur vitesse du missile
     */
    public Vector2 getVelocity() {
        return velocity;
    }

    /**
     * Met à jour la position du missile.
     * <p>
     * Le missile avance selon sa vitesse et est détruit automatiquement
     * lorsqu'il sort des limites verticales de la zone de jeu.
     * </p>
     */
    @Override
    public void update() {
        position = position.add(velocity);

        if (position.getY() < 0 || position.getY() > 1) {
            alive = false;
        }
    }

    /**
     * Dessine le missile à l'écran.
     */
    @Override
    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
    }

    /**
     * Indique si le missile est encore actif.
     *
     * @return {@code true} si le missile est vivant
     */
    @Override
    public boolean isAlive() {
        return alive;
    }
}
