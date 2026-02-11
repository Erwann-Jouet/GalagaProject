package game.level;

import java.util.ArrayList;
import java.util.List;
import game.actors.*;

/**
 * Représente un niveau du jeu.
 * <p>
 * Un Level contient le joueur, les ennemis et leur formation.
 * Il gère les collisions, la mise à jour et le dessin des ennemis,
 * ainsi que le score et la réinitialisation de la formation.
 * </p>
 */
public class Level {

    /** Liste des ennemis présents dans le niveau */
    private List<Enemy> enemies;

    /** Joueur du niveau */
    private Player player;

    /** Formation des ennemis */
    private Formation formation;

    /**
     * Construit un niveau avec un nom, une liste d'ennemis et un joueur.
     *
     * @param name    nom du niveau (non utilisé dans cette version)
     * @param enemies liste d'ennemis
     * @param player  joueur
     */
    public Level(String name, List<Enemy> enemies, Player player) {
        this.enemies = enemies;
        this.player = player;
        this.formation = new Formation(enemies);
    }

    /**
     * @return formation des ennemis
     */
    public Formation getFormation() {
        return formation;
    }

    /**
     * @return liste des ennemis
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Gère toutes les collisions dans le niveau.
     * <p>
     * - Missiles du joueur sur ennemis : inflige des dégâts et récupère la vie si
     * applicable
     * - Missiles des ennemis sur joueur : perte de vie sauf invincibilité
     * - Collisions directes joueur / ennemi : perte de vie ou capture de vie pour
     * Moth
     * </p>
     */

    /*
     * @IAGENERATIVE
     * 
     * J'ai reçu de l'aide D'une IA pour cette fonction, beaucoup de choses à gerer
     * en même temps que je me perdais avec trop d'idée.
     */
    public void handleCollisions() {
        // Missiles du joueur sur ennemis
        for (Missile m : new ArrayList<>(player.getMissiles())) {
            for (Enemy e : enemies) {
                if (m.collidesWith(e)) {
                    if (e.isCapturingLife()) {
                        player.gainLife();
                        e.setCapturingLife(false);
                    }
                    e.takeDamage(player.getAttack());
                    player.removeMissile(m);
                    break;
                }
            }
        }

        // Missiles ennemis sur joueur
        if (!player.isInvincible()) {
            for (Enemy e : enemies) {
                for (Missile m : new ArrayList<>(e.getMissiles())) {
                    if (m.collidesWith(player)) {
                        player.loseLife();
                        player.getMissiles().clear();
                        resetEnemies();
                        e.removeMissile(m);
                        break;
                    }
                }
            }
        }

        // Collisions directes joueur <-> ennemis
        if (!player.isInvincible()) {
            for (Enemy e : new ArrayList<>(enemies)) {
                if (e.collidesWith(player)) {
                    if (e.getClass().getSimpleName().equals("Moth")) {
                        if (!e.isCapturingLife()) {
                            player.loseLife();
                            player.getMissiles().clear();
                            resetEnemies();
                            e.setCapturingLife(true);
                        }
                    } else {
                        player.loseLife();
                        player.getMissiles().clear();
                        resetEnemies();
                        e.takeDamage(e.getHealth());
                    }
                }
            }
        }
    }

    /**
     * Supprime les ennemis morts et calcule le score obtenu.
     *
     * @return points gagnés en détruisant des ennemis
     */
    public int removeDeadActors() {
        int gained = 0;
        List<Enemy> toRemove = new ArrayList<>();
        for (Enemy e : enemies) {
            if (!e.isAlive()) {
                gained += e.getValue();
                toRemove.add(e);
            }
        }
        enemies.removeAll(toRemove);
        return gained;
    }

    /**
     * Réinitialise tous les ennemis à leur position initiale
     * et supprime leurs missiles.
     */
    public void resetEnemies() {
        for (Enemy e : enemies) {
            e.resetToFormation();
        }
        for (Enemy e : enemies) {
            e.getMissiles().clear();
        }
    }

    /**
     * Met à jour les ennemis et leur formation.
     * <p>
     * - Mise à jour de chaque ennemi
     * - Déclenchement d'attaques aléatoires
     * - Mise à jour du déplacement de la formation
     * - Gestion des déplacements horizontaux et tirs des ennemis
     * </p>
     */
    public void updateEnemies() {
        for (Enemy e : enemies) {
            e.update();
        }

        formation.triggerRandomAttack(player.getPosition());
        formation.updateDeplacement();

        for (Enemy e : enemies) {
            if (!e.isAttacking && !e.isReturning) {
                e.move(e.getSpeed(), 0);
            }
        }

        Enemy shooter = formation.getRandomShooter();
        if (shooter != null)
            shooter.shoot();
    }

    /**
     * Dessine tous les ennemis et leurs missiles à l'écran.
     */
    public void drawEnemies() {
        for (Enemy e : enemies) {
            e.draw();
            e.drawMissiles();
        }
    }
}
