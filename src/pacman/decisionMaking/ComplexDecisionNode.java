package pacman.decisionMaking;

/**
 * In the case a node has an action AND will have a child with a decision,
 * this class will allow the functionality.
 *
 * This class won't be in use for Assignment three, but could be a possibility for
 * more advanced decision making algorithms, so we have included the code to show
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
    public ActionType makeDecision() {
        //call action
        DecisionTreeNode childToCall = getBranch();
        return childToCall.makeDecision();
    }
}
