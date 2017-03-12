package pacman.decisionMaking;

import pacman.game.Game;

/**
 * Node interface for a decision tree
 * Created by Ben on 2/28/17.
 */
interface DecisionTreeNode {
    /**
     * Perform an action if required.
     * Recursively go through treebased on conditions.
     */
    ActionType makeDecision(DecisionTreeNode[] decisionTreeNodes, Game game);
}
