package pacman.decisionMaking;

import pacman.game.Game;

/**
 * In the case a node has an action AND will have a child with a decision,
 * this class will allow the functionality.
 *
 * This class won't be in use for Assignment three so the implementations has not been
 * done, but as the need could be a possibility for
 * more advanced decision making algorithms, we have included the class to show
 * understanding.
 *
 * Created by Ben on 2/28/17.
 */
public class ComplexDecisionNode implements DecisionTreeNode {

    // Action variable
    // Condition variable
    private DecisionTreeNode trueNode;
    private DecisionTreeNode falseNode;

    public ComplexDecisionNode(DecisionTreeNode trueNode, DecisionTreeNode falseNode) {
        this.trueNode = trueNode;
        this.falseNode = falseNode;
    }

    private DecisionTreeNode getBranch() {
        return trueNode;
    }

    @Override
    public ActionType makeDecision(DecisionTreeNode[] decisionTreeNodes, Game game) {
        //call action
        DecisionTreeNode childToCall = getBranch();
        return childToCall.makeDecision(decisionTreeNodes, game);
    }
}
