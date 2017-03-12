package pacman.decisionMaking;

/**
 * Possible actions for PacMan to take.
 *
 * Created by Ben on 2/28/17.
 */
public enum ActionType {
    NEAREST_POWER_PILL,
    NEAREST_PILL,
    ATTACK,
    RUN;

    ActionType() {}

    @Override
    public String toString() {
        return super.toString();
    }
}
