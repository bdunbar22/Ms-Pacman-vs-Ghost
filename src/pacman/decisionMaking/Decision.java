package pacman.decisionMaking;

import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;

/**
 * Nodes which chose between two children based on a condition can follow this
 * class to chose which child will call make decision.
 *
 * Created by Ben on 2/28/17.
 */
public class Decision implements DecisionTreeNode {

    // Condition variables
    private EntityType entityTypeToCheck;
    private int distanceToLook;

    private int trueNode;
    private int falseNode;

    public Decision(int trueNode, int falseNode, String conditionEntity, int distance) {
        this.trueNode = trueNode;
        this.falseNode = falseNode;
        this.distanceToLook = distance;
        try {
            this.entityTypeToCheck = EntityType.valueOf(conditionEntity);
        }
        catch (Exception e) {
            System.out.println("Error finding entity type: " + conditionEntity);
            this.entityTypeToCheck = EntityType.GHOST;
        }
    }

    private int getBranch(Game game) {
        int foundDistance = Integer.MAX_VALUE;
        int current = game.getPacmanCurrentNodeIndex();
        // Perform the action chosen by the tree.
        switch (entityTypeToCheck) {
            case PILL:
                int[] pills=game.getPillIndices();

                ArrayList<Integer> targets=new ArrayList<Integer>();

                for(int i=0;i<pills.length;i++)
                    if(game.isPillStillAvailable(i))
                        targets.add(pills[i]);

                int[] targetsArray = new int[targets.size()];

                for(int i=0;i<targetsArray.length;i++)
                    targetsArray[i] = targets.get(i);

                foundDistance = game.getShortestPathDistance(current,
                    game.getClosestNodeIndexFromNodeIndex(current, targetsArray, Constants.DM.PATH));
                break;
            case POWER_PILL:
                int[] powerPills=game.getPowerPillIndices();

                ArrayList<Integer> powerTargets=new ArrayList<Integer>();

                for(int i=0;i<powerPills.length;i++)
                    if(game.isPowerPillStillAvailable(i))
                        powerTargets.add(powerPills[i]);

                int[] powerTargetsArray = new int[powerTargets.size()];

                for(int i=0;i<powerTargetsArray.length;i++)
                    powerTargetsArray[i] = powerTargets.get(i);

                foundDistance = game.getShortestPathDistance(current,
                    game.getClosestNodeIndexFromNodeIndex(current, powerTargetsArray, Constants.DM.PATH));
                break;
            case EDIBLE_GHOST:
                int minDistance=Integer.MAX_VALUE;
                Constants.GHOST minGhost=null;

                for(Constants.GHOST ghost : Constants.GHOST.values())
                    if(game.getGhostEdibleTime(ghost)>0)
                    {
                        int distance=game.getShortestPathDistance(current,game.getGhostCurrentNodeIndex(ghost));

                        if(distance<minDistance)
                        {
                            minDistance=distance;
                            minGhost=ghost;
                        }
                    }

                if(minGhost!=null)	//we found an edible ghost
                    foundDistance = minDistance;
                break;
            case GHOST:
            default: //Made RUN by default
                Constants.GHOST closestGhostType = null;
                int closestGhostDistance = Integer.MAX_VALUE;
                for(Constants.GHOST ghost : Constants.GHOST.values())
                    if(game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0) {
                        int currentGhostDistance = game.getShortestPathDistance(current, game
                            .getGhostCurrentNodeIndex(ghost));
                        if(currentGhostDistance < closestGhostDistance) {
                            closestGhostDistance = currentGhostDistance;
                            closestGhostType = ghost;
                        }
                    }

                if(closestGhostType != null) {
                    foundDistance = closestGhostDistance;
                }
        }

        if(foundDistance < distanceToLook) {
            return trueNode;
        } else {
            return falseNode;
        }
    }

    @Override
    public ActionType makeDecision(DecisionTreeNode[] decisionTreeNodes, Game game) {
        int decisionTreeNodeToCall = getBranch(game);
        return decisionTreeNodes[decisionTreeNodeToCall].makeDecision(decisionTreeNodes, game);
    }
}
