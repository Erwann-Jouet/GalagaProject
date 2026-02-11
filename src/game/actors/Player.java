package game.actors;

import game.util.Sprite;
import game.util.SpriteLoader;
import game.util.SpriteRenderer;
import game.util.StdDraw;
import game.util.Vector2;

/**
 * Classe représentant le joueur.
 * <p>
 * Le joueur est un acteur vivant contrôlé par le clavier.
 * Il peut se déplacer horizontalement, tirer des missiles,
 * perdre et gagner des vies, et bénéficie de phases de
 * respawn et d'invincibilité temporaire.
 * </p>
 */
public class Player extends LivingActor {

    /** Vitesse de déplacement horizontal du joueur */
    private double speed = 0.015;

    /** Temps minimum entre deux tirs (en millisecondes) */
    private long cooldown = 100;

    /** Instant du dernier tir */
    private long lastShotTime = 0;

    /** Sprite du joueur */
    private static final Sprite SPRITE = new Sprite(SpriteLoader.loadSprite("ship.spr"));

    /** Nombre de vies restantes */
    private int lives = 3;

    /** Position de réapparition */
    private double spawnX;
    private double spawnY;

    /* ===================== */
    /* ===== RESPAWN ======= */
    /* ===================== */

    /** Indique si le joueur est en phase de respawn */
    private boolean isRespawning = false;

    /** Instant de début du respawn */
    private long respawnStartTime = 0;

    /** Durée du cooldown de respawn (en millisecondes) */
    private static final long RESPAWN_COOLDOWN = 2000;

    /* ========================= */
    /* ==== INVINCIBILITY ====== */
    /* ========================= */

    /** Indique si le joueur est invincible */
    private boolean isInvincible = false;

    /** Instant de début de l'invincibilité */
    private long invincibilityStartTime = 0;

    /** Durée de l'invincibilité après réapparition (en millisecondes) */
    private static final long INVINCIBILITY_DURATION = 1000;

    /**
     * Construit un joueur.
     *
     * @param x      position horizontale initiale
     * @param y      position verticale initiale
     * @param length taille du joueur
     * @param health points de vie initiaux
     * @param attack puissance d'attaque
     */
    public Player(double x, double y, double length, int health, int attack) {
        super(x, y, length, health, attack);
        this.health = 1;
        this.maxConcurrentMissiles = 3;
        this.currentMissilesCount = 0;
        this.spawnX = x;
        this.spawnY = y;
    }

    /**
     * @return nombre de vies restantes
     */
    public int getLives() {
        return lives;
    }

    /**
     * @return {@code true} si le joueur est en phase de respawn
     */
    public boolean isRespawning() {
        return isRespawning;
    }

    /**
     * @return {@code true} si le joueur est invincible
     */
    public boolean isInvincible() {
        return isInvincible;
    }

    /**
     * Fait perdre une vie au joueur.
     * <p>
     * Déclenche soit la mort définitive, soit une phase
     * de respawn avec invincibilité temporaire.
     * </p>
     */
    public void loseLife() {
        lives--;
        missiles.clear();
        currentMissilesCount = 0;

        if (lives <= -1) {
            onDeath();
        } else {
            isRespawning = true;
            respawnStartTime = System.currentTimeMillis();
            isInvincible = true;
            setAlive(false);
        }
    }

    /**
     * Ajoute une vie au joueur.
     */
    public void gainLife() {
        lives++;
    }

    /**
     * Met à jour la logique de respawn du joueur.
     */
    public void updateRespawn() {
        if (!isRespawning) {
            return;
        }

        long elapsedTime = System.currentTimeMillis() - respawnStartTime;
        if (elapsedTime >= RESPAWN_COOLDOWN) {
            isRespawning = false;
            position = new Vector2(spawnX, spawnY);
            setAlive(true);
            isInvincible = true;
            invincibilityStartTime = System.currentTimeMillis();
        }
    }

    /**
     * Met à jour la durée de l'invincibilité.
     */
    public void updateInvincibility() {
        if (isRespawning) {
            isInvincible = true;
            return;
        }

        if (!isInvincible) {
            return;
        }

        long elapsedTime = System.currentTimeMillis() - invincibilityStartTime;
        if (elapsedTime >= INVINCIBILITY_DURATION) {
            isInvincible = false;
        }
    }

    /**
     * Fait tirer un missile au joueur si les conditions le permettent.
     */
    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShotTime < cooldown)
            return;
        if (currentMissilesCount >= maxConcurrentMissiles)
            return;

        Vector2 startPos = new Vector2(position.getX(), position.getY() + length / 2);
        Vector2 velocity = new Vector2(0, 0.03);

        missiles.add(new PlayerMissile(startPos, velocity));
        currentMissilesCount++;
        lastShotTime = now;
    }

    /**
     * Met à jour la logique du joueur (déplacement, tir, états).
     */
    @Override
    public void update() {
        updateRespawn();
        updateInvincibility();

        if (!alive || isRespawning) {
            return;
        }

        double radius = length / 2;
        double x = position.getX();

        if (StdDraw.isKeyPressed(37)) {
            move(-speed, 0);
        }
        if (StdDraw.isKeyPressed(39)) {
            move(speed, 0);
        }

        if (x - radius < 0) {
            position = new Vector2(radius, position.getY());
        }
        if (x + radius > 1) {
            position = new Vector2(1 - radius, position.getY());
        }

        if (StdDraw.isKeyPressed(32)) {
            shoot();
        }

        updateMissiles();
    }

    /**
     * Gère la mort définitive du joueur.
     */
    @Override
    protected void onDeath() {
        setAlive(false);
    }

    /**
     * Dessine le joueur, ses missiles et ses vies restantes.
     */
    @Override
    public void draw() {
        if (isRespawning) {
            return;
        }

        drawMissiles();

        if (isInvincible) {
            long elapsedTime = System.currentTimeMillis() - invincibilityStartTime;
            if ((elapsedTime / 100) % 2 == 0) {
                SpriteRenderer.drawSprite(
                        SPRITE,
                        position.getX() - 0.02,
                        position.getY() - 0.02,
                        0.04);
            }
        } else {
            SpriteRenderer.drawSprite(
                    SPRITE,
                    position.getX() - 0.02,
                    position.getY() - 0.02,
                    0.04);
        }

        drawLives();
    }

    /**
     * Dessine les icônes représentant les vies restantes.
     */
    private void drawLives() {
        double size = 0.03;
        double startX = 0.03;
        double startY = 0.04;
        double gap = 0.05;

        for (int i = 0; i < lives; i++) {
            double x = startX + i * gap;
            SpriteRenderer.drawSprite(
                    SPRITE,
                    x - size / 2,
                    startY - size / 2,
                    size);
        }
    }
}
