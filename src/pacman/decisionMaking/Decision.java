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
        foundDistance = Util.conditionTest(entityTypeToCheck, game, current);

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
