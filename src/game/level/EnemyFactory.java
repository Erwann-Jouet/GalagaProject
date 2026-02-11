package game.level;

import game.actors.Enemy;
import game.actors.Enemys.Bee;
import game.actors.Enemys.Butterfly;
import game.actors.Enemys.Moth;
import game.actors.Enemys.Boss;
import game.util.Vector2;

/**
 * Usine pour créer des ennemis à partir de données.
 * <p>
 * L'EnemyFactory reçoit une {@link Formation} et
 * peut instancier différents types d'ennemis
 * (Bee, Butterfly, Moth, Boss) en fonction des {@link EnemyData}.
 * </p>
 */
public class EnemyFactory {

    /** Formation à laquelle les ennemis créés appartiennent */
    private Formation formation;

    /**
     * Construit une usine d'ennemis avec la formation associée.
     *
     * @param formation formation à laquelle les ennemis appartiendront
     */
    public EnemyFactory(Formation formation) {
        this.formation = formation;
    }

    /**
     * Crée un ennemi à partir des données fournies.
     *
     * @param data données de l'ennemi (type, position, taille, vitesse, valeur)
     * @return nouvel ennemi correspondant aux données
     * @throws IllegalArgumentException si le type d'ennemi est inconnu
     */
    public Enemy createEnemy(EnemyData data) {
        Vector2 pos = data.getPosition();
        switch (data.getType()) {
            case BEE:
                return new Bee(
                        pos.getX(),
                        pos.getY(),
                        data.getLength(),
                        data.getSpeed(),
                        data.getValue(),
                        50,
                        formation,
                        1);
            case BUTTERFLY:
                return new Butterfly(
                        pos.getX(),
                        pos.getY(),
                        data.getLength(),
                        data.getSpeed(),
                        data.getValue(),
                        70,
                        formation,
                        1);
            case MOTH:
                return new Moth(
                        pos.getX(),
                        pos.getY(),
                        data.getLength(),
                        data.getSpeed(),
                        data.getValue(),
                        100,
                        formation,
                        1);
            case BOSS:
                return new Boss(
                        pos.getX(),
                        pos.getY(),
                        data.getLength(),
                        data.getSpeed(),
                        data.getValue(),
                        1500,
                        formation,
                        1);
            default:
                throw new IllegalArgumentException("Unknown enemy type: " + data.getType());
        }
    }
}
