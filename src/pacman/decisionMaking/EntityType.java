package pacman.decisionMaking;

/**
 * Different types of entities on the map.
 *
 * Created by Ben on 3/1/17.
 */
public enum EntityType {
    GHOST,
    EDIBLE_GHOST,
    POWER_PILL,
    PILL;

    EntityType() {}

    @Override
    public String toString() {
        return super.toString();
    }
}
