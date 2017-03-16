package pacman.decisionMaking;

import pacman.game.Game;
import pacman.game.Constants.MOVE;


/**
 * Node interface for a decision tree
 * Created by Ben on 2/28/17.
 */
interface DecisionTreeNode {
    /**
     * Perform an action if required.
     * Recursively go through treebased on conditions.
     */
    MOVE makeDecision(DecisionTreeNode[] decisionTreeNodes, Game game, MOVE lastMove);
}
