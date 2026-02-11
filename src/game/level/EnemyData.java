package game.level;

import game.actors.Enemys.EnemyType;
import game.util.Vector2;

/**
 * Classe représentant les données d'un ennemi dans un niveau.
 * <p>
 * Cette classe contient les informations nécessaires pour
 * créer et positionner un ennemi : type, position initiale,
 * taille, vitesse et valeur en points.
 * </p>
 */
public class EnemyData {

    /** Type d'ennemi */
    private final EnemyType type;

    /** Position initiale de l'ennemi */
    private final Vector2 position;

    /** Taille de l'ennemi */
    private final double length;

    /** Vitesse de déplacement de l'ennemi */
    private final double speed;

    /** Valeur en points que rapporte l'ennemi */
    private final double value;

    /**
     * Construit les données d'un ennemi.
     *
     * @param type     type de l'ennemi
     * @param position position initiale de l'ennemi
     * @param length   taille de l'ennemi
     * @param speed    vitesse de déplacement
     * @param value    valeur en points
     */
    public EnemyData(EnemyType type, Vector2 position, double length, double speed, double value) {
        this.type = type;
        this.position = position;
        this.length = length;
        this.speed = speed;
        this.value = value;
    }

    /**
     * @return type de l'ennemi
     */
    public EnemyType getType() {
        return type;
    }

    /**
     * @return position initiale de l'ennemi (nouvel objet pour éviter les
     *         modifications externes)
     */
    public Vector2 getPosition() {
        return new Vector2(position.getX(), position.getY());
    }

    /**
     * @return taille de l'ennemi
     */
    public double getLength() {
        return length;
    }

    /**
     * @return vitesse de déplacement de l'ennemi
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @return valeur en points de l'ennemi
     */
    public double getValue() {
        return value;
    }
}
