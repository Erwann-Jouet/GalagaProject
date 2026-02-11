package game.actors.Enemys;

import game.actors.Enemy;
import game.level.Formation;
import game.util.Sprite;
import game.util.SpriteLoader;
import game.util.SpriteRenderer;
import game.util.StdDraw;

/**
 * Classe représentant un ennemi de type {@code Moth}.
 * <p>
 * Le Moth est un ennemi intermédiaire qui peut capturer une vie du joueur.
 * Lorsqu'il capture une vie, un indicateur visuel (petit carré jaune) apparaît
 * au-dessus de lui.
 * </p>
 * <p>
 * Le Moth se déplace comme les autres ennemis et peut subir des dégâts via les
 * missiles du joueur.
 * </p>
 * <p>
 * Hérite de {@link Enemy}.
 * </p>
 */
public class Moth extends Enemy {

    /** Sprite utilisé pour dessiner le Moth */
    private static final Sprite SPRITE = new Sprite(SpriteLoader.loadSprite("catcher.spr"));

    /**
     * Crée un nouveau Moth.
     *
     * @param x         position horizontale initiale
     * @param y         position verticale initiale
     * @param length    taille du Moth
     * @param speed     vitesse de déplacement
     * @param value     valeur en points si détruit
     * @param health    points de vie
     * @param formation formation à laquelle il appartient
     * @param attack    puissance d'attaque
     */
    public Moth(double x, double y, double length, double speed, double value, int health, Formation formation,
            int attack) {
        super(x, y, length, formation, health, attack);
        this.speed = speed;
        this.value = value;
    }

    /**
     * Dessine le Moth à l'écran.
     * <p>
     * Affiche également un indicateur visuel si le Moth a capturé une vie.
     * </p>
     */
    @Override
    public void draw() {
        SpriteRenderer.drawSprite(SPRITE, position.getX() - 0.02, position.getY() - 0.02, 0.04);

        if (isCapturingLife) {
            StdDraw.setPenColor(StdDraw.YELLOW);
            // Petit carré jaune au-dessus du Moth pour indiquer la capture
            StdDraw.filledRectangle(position.getX(), position.getY() + 0.035, 0.008, 0.008);
        }
    }

    /**
     * Actions effectuées lors de la mort du Moth.
     * <p>
     * Met l'ennemi comme non vivant.
     * </p>
     */
    @Override
    protected void onDeath() {
        setAlive(false);
    }
}
