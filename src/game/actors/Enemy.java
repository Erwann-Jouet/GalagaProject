package game.actors;

import game.level.Formation;
import game.util.Vector2;

/**
 * Classe abstraite représentant un ennemi du jeu.
 * <p>
 * Un ennemi appartient à une formation, peut attaquer le joueur,
 * tirer des missiles et revenir à sa position initiale.
 * </p>
 */
public abstract class Enemy extends LivingActor {

    /** Formation à laquelle appartient l'ennemi */
    protected Formation formation;

    /** Indique si l'ennemi est en phase d'attaque */
    public boolean isAttacking = false;

    /** Indique si l'ennemi est en phase de retour à la formation */
    public boolean isReturning = false;

    /** Cible actuelle de l'attaque */
    protected Vector2 attackTarget;

    /** Valeur en points de l'ennemi */
    protected double value;

    /** Vitesse de descente pendant l'attaque */
    protected double attackSpeed = 0.03;

    /** Vitesse de retour vers la formation */
    protected double returnSpeed = 0.01;

    /** Position X de la colonne d'origine */
    protected double columnX;

    /** Position Y initiale dans la formation */
    protected double originalY;

    /** Indique si l'ennemi capture une vie (cas spécial) */
    protected boolean isCapturingLife = false;

    /**
     * Construit un ennemi.
     *
     * @param x         position horizontale initiale
     * @param y         position verticale initiale
     * @param length    taille de l'ennemi
     * @param formation formation associée
     * @param health    points de vie
     * @param attack    puissance d'attaque
     */
    public Enemy(double x, double y, double length, Formation formation, int health, int attack) {
        super(x, y, length, health, attack);
        this.attack = 1;
        this.formation = formation;
        this.maxConcurrentMissiles = 1;
        this.currentMissilesCount = 0;
        this.lastShotTime = 0;
        this.speed = 0.01;
        this.cooldown = 500;
        this.columnX = x;
        this.originalY = y;
    }

    /**
     * Replace l'ennemi exactement à sa position initiale
     * dans la formation.
     */
    public void resetToFormation() {
        position = new Vector2(columnX, originalY);
        missiles.clear();
        currentMissilesCount = 0;
        isAttacking = false;
        isReturning = false;
    }

    /**
     * @return {@code true} si l'ennemi capture une vie
     */
    public boolean isCapturingLife() {
        return isCapturingLife;
    }

    /**
     * Définit si l'ennemi capture une vie.
     *
     * @param capturing nouvel état
     */
    public void setCapturingLife(boolean capturing) {
        this.isCapturingLife = capturing;
    }

    /**
     * Déplacement horizontal spécial pendant l'attaque (zigzag).
     * <p>
     * Par défaut, aucun déplacement.
     * </p>
     *
     * @return déplacement horizontal
     */
    protected double getZigZag() {
        return 0;
    }

    /**
     * Fait tirer l'ennemi si les conditions le permettent.
     */
    public void shoot() {
        if (!formation.canShoot())
            return;

        long now = System.currentTimeMillis();
        if (now - lastShotTime < cooldown)
            return;

        if (currentMissilesCount >= maxConcurrentMissiles)
            return;

        Vector2 startPos = new Vector2(position.getX(), position.getY() - length / 2);
        Vector2 velocity = new Vector2(0, -0.022);

        missiles.add(new EnemyMissile(startPos, velocity));
        currentMissilesCount++;
        lastShotTime = now;
    }

    /**
     * Lance l'attaque de l'ennemi vers une cible donnée.
     *
     * @param target cible de l'attaque
     */
    public void startAttack(Vector2 target) {
        if (!isAttacking && !isReturning) {
            this.attackTarget = target;
            this.isAttacking = true;
        }
    }

    /**
     * Met à jour la logique d'attaque et de retour à la formation.
     */
    public void updateAttack() {

        if (isAttacking && attackTarget != null) {

            double nextY = position.getY() - attackSpeed;

            if (nextY <= attackTarget.getY()) {
                position = new Vector2(position.getX(), attackTarget.getY());
                isAttacking = false;
                isReturning = true;
                attackTarget = null;
                return;
            }

            double zigzag = getZigZag();
            move(zigzag, -attackSpeed);
        }

        else if (isReturning) {

            double targetY = originalY;
            double dx = columnX - position.getX();

            if (Math.abs(dx) <= returnSpeed) {
                position = new Vector2(columnX, position.getY());
            } else {
                move(Math.signum(dx) * returnSpeed, 0);
                return;
            }

            double nextY = position.getY() + returnSpeed;

            if (nextY >= targetY) {
                position = new Vector2(position.getX(), targetY);
                isReturning = false;
            } else {
                move(0, returnSpeed);
            }
        }
    }

    @Override
    public void update() {
        updateAttack();
        updateMissiles();
    }

    @Override
    protected void onDeath() {
        setAlive(false);
    }

    /**
     * @return valeur en points de l'ennemi
     */
    public int getValue() {
        return (int) value;
    }

    /**
     * Dessine l'ennemi à l'écran.
     */
    public abstract void draw();
}
