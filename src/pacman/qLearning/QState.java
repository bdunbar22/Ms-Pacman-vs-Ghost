package pacman.qLearning;

import pacman.decisionMaking.Util;
import pacman.game.Constants.GHOST;
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

    private static int NEAR_THRESHOLD = 12;
    private static int MID_THRESHOLD = 20;

    /*
     * Creating a new default state: all values far.
     */
    private QState() {
        this.closestGhostDistance = DistanceEnum.FAR;
        this.closestEdibleDistance = DistanceEnum.FAR;
        this.closestPillDistance = DistanceEnum.FAR;
        this.closestPowerDistance = DistanceEnum.FAR;
    }

    /*
     * Create a state from 4 strings.
     */
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
        String stateString = "";
        stateString += closestGhostDistance + ";";
        stateString += closestEdibleDistance + ";";
        stateString += closestPillDistance + ";";
        stateString += closestPowerDistance;
        return stateString;
    }

    public static QState getState(Game game) {
        QState state = new QState();
        int current = game.getPacmanCurrentNodeIndex();

        // Closest Ghost
        int ghostDistance = Integer.MAX_VALUE;
        for (GHOST ghost : GHOST.values()) {
            if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0) {
                int currentGhostDistance = game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost));
                if (currentGhostDistance < ghostDistance) {
                    ghostDistance = currentGhostDistance;
                }
            }
        }

        if(ghostDistance < NEAR_THRESHOLD) {
            state.closestGhostDistance = DistanceEnum.NEAR;
        } else if (ghostDistance < MID_THRESHOLD) {
            state.closestGhostDistance = DistanceEnum.MID;
        }

        // Closest Edibile Ghost
        int edibleDistance = Integer.MAX_VALUE;
        for (GHOST ghost : GHOST.values()) {
            if (game.getGhostEdibleTime(ghost) > 0) {
                int distance = game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost));

                if (distance < edibleDistance) {
                    edibleDistance = distance;
                }
            }
        }

        if(edibleDistance < NEAR_THRESHOLD) {
            state.closestEdibleDistance = DistanceEnum.NEAR;
        } else if (edibleDistance < MID_THRESHOLD) {
            state.closestEdibleDistance = DistanceEnum.MID;
        }

        // Closest Pill
        Integer closestPillIndex = Util.getClosestPill(game, current);
        int pillDistance = game.getShortestPathDistance(current, closestPillIndex);

        if(pillDistance < NEAR_THRESHOLD) {
            state.closestPillDistance = DistanceEnum.NEAR;
        } else if (pillDistance < MID_THRESHOLD) {
            state.closestPillDistance = DistanceEnum.MID;
        }


        // Closest Power Pill
        Integer closestPowerIndex = Util.getClosestPowerPill(game, current);
        int powerDistance = game.getShortestPathDistance(current, closestPowerIndex);

        if(powerDistance < NEAR_THRESHOLD) {
            state.closestPowerDistance = DistanceEnum.NEAR;
        } else if (powerDistance < MID_THRESHOLD) {
            state.closestPowerDistance = DistanceEnum.MID;
        }

        return state;
    }
}
