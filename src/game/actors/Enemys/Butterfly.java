package game.actors.Enemys;

import game.actors.Enemy;
import game.level.Formation;
import game.util.Sprite;
import game.util.SpriteLoader;
import game.util.SpriteRenderer;

/**
 * Classe représentant un ennemi de type Butterfly.
 * <p>
 * La Butterfly est un ennemi de taille moyenne qui se déplace horizontalement
 * et peut attaquer le joueur comme les autres ennemis standard.
 * Elle a une valeur en points modérée.
 * </p>
 */
public class Butterfly extends Enemy {

    /** Sprite représentant visuellement la Butterfly */
    private static final Sprite SPRITE = new Sprite(SpriteLoader.loadSprite("butterfly.spr"));

    /**
     * Crée une Butterfly.
     *
     * @param x         position horizontale initiale
     * @param y         position verticale initiale
     * @param length    taille de l’ennemi
     * @param speed     vitesse de déplacement
     * @param value     valeur en points du joueur lorsqu'elle est détruite
     * @param health    points de vie
     * @param formation formation à laquelle elle appartient
     * @param attack    puissance d'attaque
     */
    public Butterfly(double x, double y, double length, double speed, double value, int health, Formation formation,
            int attack) {
        super(x, y, length, formation, health, attack);
        this.speed = speed;
        this.value = value;
    }

    /**
     * Dessine la Butterfly à sa position actuelle.
     */
    @Override
    public void draw() {
        SpriteRenderer.drawSprite(SPRITE, position.getX() - 0.02, position.getY() - 0.02, 0.04);
    }

    /**
     * Actions effectuées lors de la mort de la Butterfly.
     * <p>
     * Met l'ennemi comme non vivant.
     * </p>
     */
    @Override
    protected void onDeath() {
        setAlive(false);
    }
}
