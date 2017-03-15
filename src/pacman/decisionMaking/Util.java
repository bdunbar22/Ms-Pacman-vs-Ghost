package pacman.decisionMaking;

import pacman.game.Constants;
import pacman.game.Constants.MOVE;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;

import pacman.game.Game;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A util class for decision making to prevent repeated code.
 *
 * Created by Ben on 3/14/17.
 */
public final class Util {
    private Util() {}
    static int conditionTest(EntityType entityTypeToCheck, Game game, int current) {
        switch (entityTypeToCheck) {
            case PILL:
                int[] pills = game.getPillIndices();

                ArrayList<Integer> targets = new ArrayList<Integer>();

                for (int i = 0; i < pills.length; i++)
                    if (game.isPillStillAvailable(i))
                        targets.add(pills[i]);

                int[] targetsArray = new int[targets.size()];

                for (int i = 0; i < targetsArray.length; i++)
                    targetsArray[i] = targets.get(i);

                return game.getShortestPathDistance(current,
                    game.getClosestNodeIndexFromNodeIndex(current, targetsArray, DM.PATH));
            case POWER_PILL:
                int[] powerPills = game.getPowerPillIndices();

                ArrayList<Integer> powerTargets = new ArrayList<Integer>();

                for (int i = 0; i < powerPills.length; i++)
                    if (game.isPowerPillStillAvailable(i))
                        powerTargets.add(powerPills[i]);

                int[] powerTargetsArray = new int[powerTargets.size()];

                for (int i = 0; i < powerTargetsArray.length; i++)
                    powerTargetsArray[i] = powerTargets.get(i);

                if (powerTargetsArray.length < 1) {
                    return Integer.MAX_VALUE;
                } else {
                    return game.getShortestPathDistance(current,
                        game.getClosestNodeIndexFromNodeIndex(current, powerTargetsArray,
                            DM.PATH));
                }
            case EDIBLE_GHOST:
                int minDistance = Integer.MAX_VALUE;
                GHOST minGhost = null;

                for (GHOST ghost : GHOST.values())
                    if (game.getGhostEdibleTime(ghost) > 0) {
                        int distance = game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost));

                        if (distance < minDistance) {
                            minDistance = distance;
                            minGhost = ghost;
                        }
                    }

                if (minGhost != null)  //we found an edible ghost
                    return minDistance;
                break;
            case GHOST:
            default:
                GHOST closestGhostType = null;
                int closestGhostDistance = Integer.MAX_VALUE;
                for (GHOST ghost : GHOST.values())
                    if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0) {
                        int currentGhostDistance = game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost));
                        if (currentGhostDistance < closestGhostDistance) {
                            closestGhostDistance = currentGhostDistance;
                            closestGhostType = ghost;
                        }
                    }

                if (closestGhostType != null) {
                   return closestGhostDistance;
                }
        }
        return Integer.MAX_VALUE;
    }

    public static MOVE findDirection(ActionType action, Game game, MOVE lastMove) {
        int current = game.getPacmanCurrentNodeIndex();
        // Perform the action chosen by the tree.
        switch (action) {
            case NEAREST_PILL:
                int closestPill = getClosestPill(game, current);

                return game.getNextMoveTowardsTarget(current, closestPill, DM.PATH);
            case NEAREST_POWER_PILL:
                int closestPowerPill = getClosestPowerPill(game, current);

                return game.getNextMoveTowardsTarget(current, closestPowerPill, DM.PATH);
            case ATTACK:
                int minDistance = Integer.MAX_VALUE;
                GHOST minGhost = null;

                for (GHOST ghost : GHOST.values())
                    if (game.getGhostEdibleTime(ghost) > 0) {
                        int distance = game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost));

                        if (distance < minDistance) {
                            minDistance = distance;
                            minGhost = ghost;
                        }
                    }

                if (minGhost != null)  //we found an edible ghost
                    return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),
                                game.getGhostCurrentNodeIndex(minGhost),
                                DM.PATH);
                break;
            case RUN_AWAY:
            default: //Made RUN_AWAY by default
                return runAway(game, current);
        }

        return lastMove;
    }

    /**
     * Method to determine best route to run away from the ghosts.
     */
    private static MOVE runAway(Game game, int current) {
        int closestGhostDistance = Integer.MAX_VALUE;
        for(GHOST ghost : GHOST.values()) {
            if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0) {
                int currentGhostDistance = game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost));
                if (currentGhostDistance < closestGhostDistance) {
                    closestGhostDistance = currentGhostDistance;
                }
            }
        }

        MOVE[] moves = game.getPossibleMoves(current);
        ArrayList<MOVE> goodMoves = new ArrayList<>(Arrays.asList(moves));
        for (GHOST ghost : GHOST.values()) {
            if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0) {
                int currentGhostDistance = game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost));
                if (currentGhostDistance < closestGhostDistance + 25) {
                    for (MOVE move : moves) {
                        if (move.equals(game.getNextMoveTowardsTarget(
                            current,
                            game.getGhostCurrentNodeIndex(ghost),
                            Constants.DM.PATH))) {
                            goodMoves.remove(move);
                        }
                    }
                }
            }
        }
        ArrayList<MOVE> betterMoves = new ArrayList<>();
        for (MOVE move : goodMoves) {
            int closestPill = getClosestPill(game, current);
            MOVE nextPill = null;
            MOVE nextPowerPill = null;
            if(closestPill >= 0) {
                nextPill = game.getNextMoveTowardsTarget(current, closestPill, Constants.DM.PATH);
            }
            int closestPowerPill = getClosestPowerPill(game, current);
            if(closestPowerPill >= 0) {
                nextPowerPill = game.getNextMoveTowardsTarget(current, closestPowerPill, Constants.DM
                    .PATH);
            }
            if((nextPill != null && move.equals(nextPill))
                || (nextPowerPill != null && move.equals(nextPowerPill))) {
                betterMoves.add(move);
            }
        }

        if(!betterMoves.isEmpty()) {
            return betterMoves.get(0);
        }
        else if(!goodMoves.isEmpty()) {
            return goodMoves.get(0);
        } else {
            int[] junctions = game.getJunctionIndices();
            int goal = game.getClosestNodeIndexFromNodeIndex(current, junctions, Constants.DM
                .PATH);
            return game.getNextMoveTowardsTarget(current, goal, Constants.DM.PATH);
        }
    }

    private static int getClosestPill(Game game, int current) {
        int[] pills = game.getPillIndices();

        ArrayList<Integer> targets = new ArrayList<Integer>();

        for (int i = 0; i < pills.length; i++)
            if (game.isPillStillAvailable(i))
                targets.add(pills[i]);

        int[] targetsArray = new int[targets.size()];

        for (int i = 0; i < targetsArray.length; i++)
            targetsArray[i] = targets.get(i);

        return game.getClosestNodeIndexFromNodeIndex(current, targetsArray, Constants.DM.PATH);
    }

    private static int getClosestPowerPill(Game game, int current) {
        int[] powerPills = game.getPowerPillIndices();

        ArrayList<Integer> powerTargets = new ArrayList<Integer>();

        for (int i = 0; i < powerPills.length; i++)
            if (game.isPowerPillStillAvailable(i))
                powerTargets.add(powerPills[i]);

        int[] powerTargetsArray = new int[powerTargets.size()];

        for (int i = 0; i < powerTargetsArray.length; i++)
            powerTargetsArray[i] = powerTargets.get(i);

        return game.getClosestNodeIndexFromNodeIndex(current, powerTargetsArray, Constants.DM.PATH);
    }
}
