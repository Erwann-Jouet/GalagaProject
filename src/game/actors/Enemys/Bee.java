package game.actors.Enemys;

import game.actors.Enemy;
import game.level.Formation;
import game.util.Sprite;
import game.util.SpriteLoader;
import game.util.SpriteRenderer;

/**
 * Classe représentant un ennemi de type Bee.
 * <p>
 * Les Bees sont des ennemis rapides avec un mouvement en zigzag léger.
 * Ils appartiennent à une formation et peuvent attaquer le joueur.
 * </p>
 */
public class Bee extends Enemy {

    /** Sprite représentant visuellement la Bee */
    private static final Sprite SPRITE = new Sprite(SpriteLoader.loadSprite("bee.spr"));

    /**
     * Crée une Bee.
     *
     * @param x         position horizontale initiale
     * @param y         position verticale initiale
     * @param length    taille de l'ennemi
     * @param speed     vitesse de déplacement de l'ennemi
     * @param value     valeur en points du joueur lorsqu'elle est détruite
     * @param health    points de vie de la Bee
     * @param formation formation à laquelle elle appartient
     * @param attack    puissance d'attaque
     */
    public Bee(double x, double y, double length, double speed, double value,
            int health, Formation formation, int attack) {
        super(x, y, length, formation, health, attack);
        this.speed = speed;
        this.value = value;
    }

    /**
     * Déplacement horizontal en zigzag pendant l'attaque.
     * <p>
     * La Bee se déplace selon une fonction sinusoïdale très légère.
     * </p>
     *
     * @return décalage horizontal à appliquer
     */
    @Override
    protected double getZigZag() {
        return 0.01 * Math.sin(System.currentTimeMillis() / 100.0);
    }

    /**
     * Dessine la Bee à l'écran à sa position actuelle.
     */
    @Override
    public void draw() {
        SpriteRenderer.drawSprite(SPRITE, position.getX() - 0.02, position.getY() - 0.02, 0.04);
    }

    /**
     * Actions effectuées lors de la mort de la Bee.
     * <p>
     * Met l'ennemi comme non vivant.
     * </p>
     */
    @Override
    protected void onDeath() {
        setAlive(false);
    }

}
