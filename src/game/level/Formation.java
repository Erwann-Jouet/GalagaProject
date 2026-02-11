package game.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.actors.Enemy;
import game.util.Vector2;

/**
 * Représente une formation d'ennemis.
 * <p>
 * Une formation gère un groupe d'ennemis en colonne, leur
 * déplacement collectif, les attaques aléatoires vers le joueur
 * et la logique de tir.
 * </p>
 */
public class Formation {

    /** Liste des ennemis dans la formation */
    private List<Enemy> enemies;

    /** Instant du dernier déclenchement d'attaque */
    private long lastAttackTime = 0;

    /** Instant du dernier tir d'un ennemi */
    private long lastEnemyShotTime = 0;

    /** Vitesse de déplacement horizontal de la formation */
    private double formationSpeed = 0.001;

    /** Cooldown entre tirs d'ennemis (aléatoire initialement) */
    private long enemyShootCooldown = new Random().nextInt(700);

    /** Largeur maximale d'une colonne pour regrouper les ennemis */
    private static final double COLUMN_WIDTH = 0.06;

    /** Cooldown minimum entre deux attaques de la formation */
    private long attackCooldown = 5000;

    /** Nombre maximum d'ennemis attaquant simultanément */
    private static final int MAX_ATTACKERS = 1;

    /**
     * Construit une formation avec une liste d'ennemis.
     *
     * @param enemies liste d'ennemis appartenant à la formation
     */
    public Formation(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    /**
     * Définit la vitesse de déplacement horizontal de la formation.
     *
     * @param speed vitesse horizontale
     */
    public void setFormationSpeed(double speed) {
        this.formationSpeed = speed;
    }

    /**
     * Définit le cooldown entre deux attaques de la formation.
     *
     * @param ms durée du cooldown en millisecondes
     */
    public void setAttackCooldown(long ms) {
        this.attackCooldown = ms;
    }

    /**
     * Définit le cooldown entre tirs des ennemis.
     *
     * @param ms durée du cooldown en millisecondes
     */
    public void setEnemyShootCooldown(long ms) {
        this.enemyShootCooldown = ms;
    }

    /* ========================= */
    /* ==== MÉTHODES INTERNES === */
    /* ========================= */

    /**
     * Regroupe les ennemis par colonnes.
     * <p>
     * Les ennemis dont les positions X sont proches (inférieure à
     * {@link #COLUMN_WIDTH})
     * sont regroupés dans la même colonne.
     * </p>
     *
     * @return liste de colonnes, chaque colonne étant une liste d'ennemis
     */

    /*
     * @IAGENERATIVE
     * 
     * J'ai reçu de l'aide D'une IA pour cette fonction, j'avais du mal à recuperer
     * la ligne d'ennemis du bas sans bug lors de l'excution du jeu.
     */
    private List<List<Enemy>> getColumns() {
        List<List<Enemy>> columns = new ArrayList<>();

        for (Enemy e : enemies) {
            boolean placed = false;

            for (List<Enemy> col : columns) {
                Enemy first = col.get(0);
                if (Math.abs(first.getPosition().getX() - e.getPosition().getX()) < COLUMN_WIDTH) {
                    col.add(e);
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                List<Enemy> newCol = new ArrayList<>();
                newCol.add(e);
                columns.add(newCol);
            }
        }

        return columns;
    }

    /**
     * Retourne les ennemis les plus bas de chaque colonne.
     *
     * @return liste des ennemis en bas de chaque colonne
     */
    public List<Enemy> getBottomEnemies() {
        List<Enemy> result = new ArrayList<>();
        for (List<Enemy> col : getColumns()) {
            Enemy bottom = col.get(0);
            for (Enemy e : col) {
                if (e.getPosition().getY() < bottom.getPosition().getY()) {
                    bottom = e;
                }
            }
            result.add(bottom);
        }
        return result;
    }

    /**
     * Indique si un ennemi peut tirer selon le cooldown.
     *
     * @return {@code true} si un tir peut être effectué
     */
    public boolean canShoot() {
        long now = System.currentTimeMillis();
        return now - lastEnemyShotTime >= enemyShootCooldown;
    }

    /**
     * Retourne un ennemi aléatoire pouvant tirer.
     * <p>
     * Seul un ennemi en bas de colonne et dont le cooldown est passé
     * peut être choisi.
     * </p>
     *
     * @return ennemi sélectionné pour tirer, ou {@code null} si aucun
     */
    public Enemy getRandomShooter() {
        if (!canShoot())
            return null;

        List<Enemy> bottomEnemies = getBottomEnemies();
        if (bottomEnemies.isEmpty())
            return null;

        Random random = new Random();
        int index = random.nextInt(bottomEnemies.size());
        Enemy shooter = bottomEnemies.get(index);

        lastEnemyShotTime = System.currentTimeMillis();
        return shooter;
    }

    /**
     * Met à jour le déplacement horizontal de la formation.
     * <p>
     * La formation rebondit sur les bords gauche/droit et met à jour
     * la vitesse de chaque ennemi non attaquant.
     * </p>
     */
    public void updateDeplacement() {
        boolean hitLeft = false, hitRight = false;
        for (Enemy e : enemies) {
            if (!e.isAttacking) {
                if (e.getPosition().getX() <= 0.05)
                    hitLeft = true;
                if (e.getPosition().getX() >= 0.95)
                    hitRight = true;
            }
        }

        if (hitRight)
            formationSpeed = -Math.abs(formationSpeed);
        if (hitLeft)
            formationSpeed = Math.abs(formationSpeed);

        for (Enemy e : enemies) {
            if (!e.isAttacking && !e.isReturning) {
                e.setSpeed(formationSpeed);
            }
        }
    }

    /**
     * Déclenche une attaque aléatoire vers le joueur.
     * <p>
     * Seuls les ennemis bas de colonne et non attaquants peuvent attaquer.
     * Respecte le cooldown global et la limite {@link #MAX_ATTACKERS}.
     * </p>
     *
     * @param playerPos position du joueur à attaquer
     */
    public void triggerRandomAttack(Vector2 playerPos) {
        long now = System.currentTimeMillis();

        if (now - lastAttackTime < attackCooldown)
            return;

        if (countActiveAttackers() >= MAX_ATTACKERS)
            return;

        List<Enemy> bottom = getBottomEnemies();
        if (bottom.isEmpty())
            return;

        List<Enemy> candidates = new ArrayList<>();
        for (Enemy e : bottom) {
            if (!e.isAttacking && !e.isReturning) {
                candidates.add(e);
            }
        }

        if (candidates.isEmpty())
            return;

        Enemy attacker = candidates.get(new Random().nextInt(candidates.size()));
        attacker.startAttack(playerPos);
        lastAttackTime = now;
    }

    /**
     * Calcule la position Y moyenne des ennemis de la formation
     * qui ne sont pas en attaque ou en retour.
     *
     * @return position Y moyenne
     */
    public double getFormationY() {
        double sum = 0;
        for (Enemy e : enemies) {
            if (!e.isAttacking && !e.isReturning) {
                sum += e.getPosition().getY();
            }
        }
        return sum / enemies.size();
    }

    /**
     * Compte le nombre d'ennemis actuellement en attaque ou en retour.
     *
     * @return nombre d'attaquants actifs
     */
    private int countActiveAttackers() {
        int count = 0;
        for (Enemy e : enemies) {
            if (e.isAttacking || e.isReturning) {
                count++;
            }
        }
        return count;
    }
}
