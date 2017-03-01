package pacman.decisionMaking;

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

    public Decision(int trueNode, int falseNode) {
        this.trueNode = trueNode;
        this.falseNode = falseNode;
    }

    private int getBranch() {
        return trueNode;
    }

    @Override
    public ActionType makeDecision(DecisionTreeNode[] decisionTreeNodes) {
        int decisionTreeNodeToCall = getBranch();
        return decisionTreeNodes[decisionTreeNodeToCall].makeDecision(decisionTreeNodes);
    }
}
