package pacman.qLearning;

import pacman.game.Constants.MOVE;

/**
 * A way to store the state of the game, with an equals operator to make it easy to compare states
 * Created by Ben on 4/5/17.
 */
public class QState {
    private MOVE closestDirection;
    private DistanceEnum closestDistance;
    private Boolean closestEdible;
    private MOVE closestPillDirection;
    private MOVE closestPowerPillDirection;


    public QState(String direction1, String distance, String edible, String pillDirection,
                  String powerPillDirection) {
        this.closestDirection = MOVE.valueOf(direction1);
        this.closestDistance = DistanceEnum.valueOf(distance);
        this.closestEdible = Boolean.getBoolean(edible);
        this.closestPillDirection = MOVE.valueOf(pillDirection);
        this.closestPillDirection = MOVE.valueOf(powerPillDirection);
    }


    public MOVE getClosestDirection() {
        return closestDirection;
    }

    public void setClosestDirection(MOVE closestDirection) {
        this.closestDirection = closestDirection;
    }

    public DistanceEnum getClosestDistance() {
        return closestDistance;
    }

    public void setClosestDistance(DistanceEnum closestDistance) {
        this.closestDistance = closestDistance;
    }

    public Boolean getClosestEdible() {
        return closestEdible;
    }

    public void setClosestEdible(Boolean closestEdible) {
        this.closestEdible = closestEdible;
    }

    public MOVE getClosestPillDirection() {
        return closestPillDirection;
    }

    public void setClosestPillDirection(MOVE closestPillDirection) {
        this.closestPillDirection = closestPillDirection;
    }

    public MOVE getClosestPowerPillDirection() {
        return closestPowerPillDirection;
    }

    public void setClosestPowerPillDirection(MOVE closestPowerPillDirection) {
        this.closestPowerPillDirection = closestPowerPillDirection;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            QState other = (QState) obj;
            return (other.getClosestEdible().equals(this.getClosestEdible()) &&
                other.getClosestDirection().equals(this.getClosestDirection()) &&
                other.getClosestDistance().equals(this.getClosestDistance()) &&
                other.getClosestPillDirection().equals(this.getClosestPillDirection()) &&
                other.getClosestPowerPillDirection().equals(this.getClosestPowerPillDirection()));
        }
        catch (Exception e) {
            return false;
        }
    }
}
