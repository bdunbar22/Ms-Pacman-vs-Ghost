package pacman.decisionMaking;

/**
 * Nodes which perform an action will use this
 * class to be able to perform the action.
 *
 * Created by Ben on 2/28/17.
 */
public class Action implements DecisionTreeNode {

    private ActionType action;

    public Action(String actionInput) {
        // chose action from action input.
    }

    @Override
    public ActionType makeDecision() {
        return action;
    }
}
