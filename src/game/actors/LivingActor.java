package game.actors;

/**
 * Classe abstraite représentant un acteur vivant du jeu.
 * <p>
 * Un acteur vivant possède des points de vie et une puissance d'attaque.
 * Il peut subir des dégâts et déclencher un comportement spécifique
 * lorsqu'il meurt.
 * </p>
 */
public abstract class LivingActor extends Actor {

    /** Points de vie actuels de l'acteur */
    protected int health;

    /** Puissance d'attaque de l'acteur */
    protected int attack;

    /**
     * Construit un acteur vivant.
     *
     * @param x      position horizontale initiale
     * @param y      position verticale initiale
     * @param length taille de l'acteur
     * @param health points de vie initiaux
     * @param attack puissance d'attaque
     */
    public LivingActor(double x, double y, double length, int health, int attack) {
        super(x, y, length);
        this.health = health;
        this.attack = attack;
    }

    /**
     * @return points de vie actuels de l'acteur
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return puissance d'attaque de l'acteur
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Inflige des dégâts à l'acteur.
     * <p>
     * Si les points de vie atteignent zéro, la méthode {@link #onDeath()}
     * est appelée automatiquement.
     * </p>
     *
     * @param amount quantité de dégâts subis
     */
    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            health = 0;
            onDeath();
        }
    }

    /**
     * Indique si l'acteur est encore en vie.
     *
     * @return {@code true} si l'acteur est vivant
     */
    @Override
    public boolean isAlive() {
        return alive;
    }

    /**
     * Méthode appelée lorsque les points de vie atteignent zéro.
     * <p>
     * Chaque sous-classe définit son propre comportement
     * (explosion, disparition, score, animation, etc.).
     * </p>
     */
    protected abstract void onDeath();
}
