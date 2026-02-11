package game.actors.Enemys;

/**
 * Enumération des types d’ennemis présents dans le jeu.
 * <p>
 * Chaque type correspond à une classe d’ennemi spécifique avec son comportement
 * et ses caractéristiques propres.
 * </p>
 */
public enum EnemyType {
    /** Ennemi de type Bee : petit, agile, se déplace en zigzag */
    BEE,

    /** Ennemi de type Butterfly : taille moyenne, se déplace horizontalement */
    BUTTERFLY,

    /**
     * Ennemi de type Moth : ennemi plus coriace, peut capturer une vie du joueur
     */
    MOTH,

    /**
     * Ennemi de type Boss : puissant, tir en rafale, patrouille en haut de l’écran
     */
    BOSS
}
