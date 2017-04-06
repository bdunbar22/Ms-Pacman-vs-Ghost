package pacman.qLearning;

import pacman.game.Game;

/**
 * A way to store the state of the game, with an equals operator to make it easy to compare states
 * Created by Ben on 4/5/17.
 */
public class QState {
    private DistanceEnum closestGhostDistance;
    private DistanceEnum closestEdibleDistance;
    private DistanceEnum closestPillDistance;
    private DistanceEnum closestPowerDistance;

    private QState() { }

    public QState(String distance1, String distance2, String distance3, String distance4) {
        this.closestGhostDistance = DistanceEnum.valueOf(distance1);
        this.closestEdibleDistance = DistanceEnum.valueOf(distance2);
        this.closestPillDistance = DistanceEnum.valueOf(distance3);
        this.closestPowerDistance = DistanceEnum.valueOf(distance4);
    }

    public DistanceEnum getClosestGhostDistance() {
        return closestGhostDistance;
    }

    public void setClosestGhostDistance(DistanceEnum closestGhostDistance) {
        this.closestGhostDistance = closestGhostDistance;
    }

    public DistanceEnum getClosestEdibleDistance() {
        return closestEdibleDistance;
    }

    public void setClosestEdibleDistance(DistanceEnum closestEdibleDistance) {
        this.closestEdibleDistance = closestEdibleDistance;
    }

    public DistanceEnum getClosestPillDistance() {
        return closestPillDistance;
    }

    public void setClosestPillDistance(DistanceEnum closestPillDistance) {
        this.closestPillDistance = closestPillDistance;
    }

    public DistanceEnum getClosestPowerDistance() {
        return closestPowerDistance;
    }

    public void setClosestPowerDistance(DistanceEnum closestPowerDistance) {
        this.closestPowerDistance = closestPowerDistance;
    }

    /**
     * Override the equals function to make it easy to compare states.
     * @param obj to compare with
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        try {
            QState other = (QState) obj;
            return (other.getClosestGhostDistance().equals(this.getClosestGhostDistance()) &&
                other.getClosestEdibleDistance().equals(this.getClosestEdibleDistance()) &&
                other.getClosestPillDistance().equals(this.getClosestPillDistance()) &&
                other.getClosestPowerDistance().equals(this.getClosestPowerDistance()));
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Override the to string function to make it easy to re write the q map file.
     */
    @Override
    public String toString() {

        //TODO
        return "";
    }

    public static QState getState(Game game) {
        // TODO
        return new QState();
    }
}
