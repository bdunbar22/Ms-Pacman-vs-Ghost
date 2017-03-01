package pacman.decisionMaking;

/**
 * Nodes which chose between two children based on a condition can follow this
 * class to chose which child will call make decision.
 *
 * Created by Ben on 2/28/17.
 */
public class Decision implements DecisionTreeNode {

    // Condition variable
    private DecisionTreeNode trueNode;
    private DecisionTreeNode falseNode;

    public Decision(DecisionTreeNode trueNode, DecisionTreeNode falseNode) {
        this.trueNode = trueNode;
        this.falseNode = falseNode;
    }

    private DecisionTreeNode getBranch() {
        return trueNode;
    }

    @Override
    public ActionType makeDecision() {
        DecisionTreeNode childToCall = getBranch();
        return childToCall.makeDecision();
    }
}
