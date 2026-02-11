package game.actors.Enemys;

import game.actors.Enemy;
import game.actors.EnemyMissile;
import game.util.Sprite;
import game.util.SpriteLoader;
import game.util.SpriteRenderer;
import game.util.Vector2;
import game.level.Formation;

/**
 * Classe représentant un ennemi de type Boss.
 * <p>
 * Le Boss est un ennemi puissant qui patrouille horizontalement en haut de
 * l'écran
 * et tire des rafales de missiles dans plusieurs directions. Il a beaucoup de
 * points
 * de vie et une valeur élevée en points pour le joueur.
 * </p>
 */
public class Boss extends Enemy {

    /** Sprite représentant visuellement le Boss */
    private static final Sprite SPRITE = new Sprite(SpriteLoader.loadSprite("boss.spr"));

    /** Cooldown entre chaque séquence de deux rafales */
    private static final long BURST_COOLDOWN = 2000; // ms

    /** Nombre de missiles tirés par rafale */
    private static final int BURST_MISSILE_COUNT = 12;

    /** Temps de fin de la dernière séquence de rafales */
    private long lastSequenceEndTime = 0;

    /** Indique si le Boss est actuellement en train de tirer une séquence */
    private boolean inBurstSequence = false;

    /** Temps de début de la première rafale de la séquence en cours */
    private long firstBurstTime = 0;

    /** Délai entre les deux rafales d'une séquence */
    private static final long SECOND_BURST_DELAY = 250; // ms

    /** Limites gauche et droite de la patrouille horizontale */
    private double patrolLeft;
    private double patrolRight;

    /** Direction actuelle de la patrouille (1 = droite, -1 = gauche) */
    private int patrolDir = 1;

    /** Portée de patrouille */
    private static final double PATROL_RANGE = 0.15;

    /** Vitesse de patrouille horizontale */
    private static final double PATROL_SPEED = 0.0025;

    /**
     * Crée un Boss.
     *
     * @param x         position horizontale initiale
     * @param y         position verticale initiale
     * @param length    taille du Boss
     * @param speed     vitesse (non utilisée directement, peut être étendue)
     * @param value     valeur en points du joueur lorsqu'il est détruit
     * @param health    points de vie du Boss
     * @param formation formation à laquelle il appartient (peut être ignorée)
     * @param attack    puissance d'attaque
     */
    public Boss(double x, double y, double length, double speed, double value, int health, Formation formation,
            int attack) {
        super(x, y, length, formation, health, attack);
        this.speed = speed;
        this.value = value;
        this.maxConcurrentMissiles = 40;
        this.patrolLeft = Math.max(0.05, x - PATROL_RANGE);
        this.patrolRight = Math.min(0.95, x + PATROL_RANGE);
    }

    /**
     * Crée immédiatement une rafale de missiles dans un éventail de directions.
     */
    private void fireBurst() {
        for (int i = 0; i < BURST_MISSILE_COUNT; i++) {
            double angle = Math.toRadians(180.0 + 180.0 * i / (BURST_MISSILE_COUNT - 1));
            double velocityX = Math.cos(angle) * 0.02;
            double velocityY = Math.sin(angle) * 0.02;

            Vector2 startPos = new Vector2(position.getX(), position.getY());
            Vector2 velocity = new Vector2(velocityX, velocityY);

            missiles.add(new EnemyMissile(startPos, velocity));
            currentMissilesCount++;
        }
    }

    /**
     * Gère la séquence de rafales automatiques en respectant les cooldowns.
     */
    private void burstSequence() {
        long now = System.currentTimeMillis();
        if (!inBurstSequence) {
            if (now - lastSequenceEndTime >= BURST_COOLDOWN) {
                fireBurst();
                inBurstSequence = true;
                firstBurstTime = now;
            }
        } else {
            if (now - firstBurstTime >= SECOND_BURST_DELAY) {
                fireBurst();
                inBurstSequence = false;
                lastSequenceEndTime = now;
            }
        }
    }

    /**
     * Tire des missiles (implémente le tir automatique du Boss).
     */
    @Override
    public void shoot() {
        burstSequence();
    }

    /**
     * Met à jour la position et les missiles du Boss.
     * <p>
     * Effectue la patrouille horizontale et déclenche les rafales de missiles.
     * </p>
     */
    @Override
    public void update() {
        double nextX = position.getX() + patrolDir * PATROL_SPEED;
        if (nextX < patrolLeft || nextX > patrolRight) {
            patrolDir *= -1;
            nextX = position.getX() + patrolDir * PATROL_SPEED;
        }
        move(patrolDir * PATROL_SPEED, 0);

        burstSequence();
        updateMissiles();
    }

    /**
     * Dessine le Boss à l'écran à sa position actuelle.
     */
    @Override
    public void draw() {
        SpriteRenderer.drawSprite(SPRITE, position.getX() - 0.04, position.getY() - 0.04, 0.08);
    }

    /**
     * Actions effectuées lors de la mort du Boss.
     * <p>
     * Met le Boss comme non vivant.
     * </p>
     */
    @Override
    protected void onDeath() {
        setAlive(false);
    }
}
