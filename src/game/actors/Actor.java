package game.actors;

import java.util.ArrayList;
import java.util.List;
import game.util.Vector2;

/**
 * Classe abstraite représentant un acteur du jeu.
 * <p>
 * Un acteur est toute entité pouvant être affichée à l'écran,
 * se déplacer, posséder une position et éventuellement tirer des missiles
 * (joueur ou ennemi).
 * </p>
 * 
 * Cette classe fournit les fonctionnalités communes :
 * <ul>
 * <li>Gestion de la position et du déplacement</li>
 * <li>Gestion des missiles</li>
 * <li>Détection de collisions</li>
 * <li>Cycle de vie (vivant / mort)</li>
 * </ul>
 */
public abstract class Actor {

    /** Position actuelle de l'acteur dans l'espace de jeu */
    protected Vector2 position;

    /** Taille de l'acteur (utilisée pour l'affichage et les collisions) */
    public double length;

    /** Vitesse de déplacement de l'acteur */
    protected double speed;

    /** Nombre maximum de missiles pouvant être actifs simultanément */
    protected int maxConcurrentMissiles;

    /** Nombre actuel de missiles actifs */
    protected int currentMissilesCount;

    /** Temps minimal entre deux tirs (en millisecondes) */
    protected long cooldown;

    /** Instant du dernier tir */
    protected long lastShotTime;

    /** Liste des missiles tirés par l'acteur */
    protected List<Missile> missiles = new ArrayList<>();

    /** Indique si l'acteur est encore vivant */
    protected boolean alive = true;

    /**
     * Constructeur d'un acteur.
     *
     * @param x      position horizontale initiale
     * @param y      position verticale initiale
     * @param length taille de l'acteur
     */
    public Actor(double x, double y, double length) {
        this.position = new Vector2(x, y);
        this.length = length;
    }

    /**
     * Retourne la taille de l'acteur.
     *
     * @return la longueur de l'acteur
     */
    public double getLength() {
        return length;
    }

    /**
     * Retourne la position actuelle de l'acteur.
     *
     * @return position sous forme de Vector2
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Indique si l'acteur est vivant.
     *
     * @return true si l'acteur est vivant, false sinon
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Définit l'état de vie de l'acteur.
     *
     * @param alive nouvel état de vie
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Retourne la liste des missiles tirés par l'acteur.
     *
     * @return liste des missiles
     */
    public List<Missile> getMissiles() {
        return missiles;
    }

    /**
     * Déplace l'acteur d'un vecteur donné.
     *
     * @param dx déplacement horizontal
     * @param dy déplacement vertical
     */
    public void move(double dx, double dy) {
        this.position = new Vector2(
                position.getX() + dx,
                position.getY() + dy);
    }

    /**
     * Retourne la vitesse actuelle de l'acteur.
     *
     * @return vitesse de déplacement
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Modifie la vitesse de déplacement de l'acteur.
     *
     * @param speed nouvelle vitesse
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Met à jour les missiles de l'acteur.
     * <p>
     * Supprime automatiquement les missiles sortant de l'écran
     * et met à jour leur position.
     * </p>
     */
    public void updateMissiles() {
        for (Missile m : new ArrayList<>(missiles)) {
            m.update();
            if (m.getPosition().getY() > 1.2 || m.getPosition().getY() < 0) {
                removeMissile(m);
            }
        }
    }

    /**
     * Dessine tous les missiles de l'acteur à l'écran.
     */
    public void drawMissiles() {
        for (Missile m : missiles) {
            m.draw();
        }
    }

    /**
     * Détecte une collision entre cet acteur et un autre.
     *
     * @param other autre acteur
     * @return true si collision, false sinon
     */
    public boolean collidesWith(Actor other) {
        double dx = this.position.getX() - other.position.getX();
        double dy = this.position.getY() - other.position.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double radius = this.length / 2 + other.length / 2;
        return distance <= radius;
    }

    /**
     * Supprime un missile de la liste et libère un slot de tir.
     *
     * @param m missile à supprimer
     */
    public void removeMissile(Missile m) {
        if (missiles.remove(m)) {
            currentMissilesCount--;
        }
    }

    /**
     * Met à jour l'état de l'acteur (déplacement, attaques, etc.).
     * Méthode appelée à chaque frame du jeu.
     */
    public abstract void update();

    /**
     * Dessine l'acteur à l'écran.
     */
    public abstract void draw();
}
