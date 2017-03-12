package pacman.decisionMaking;

import pacman.game.Game;

/**
 * Nodes which perform an action will use this
 * class to be able to perform the action.
 *
 * Created by Ben on 2/28/17.
 */
public class Action implements DecisionTreeNode {

    private ActionType action;
    private Game game;

    public Action(String actionInput) {
        try {
            this.action = ActionType.valueOf(actionInput);
        }
        catch (Exception e) {
            this.action = ActionType.RUN;
        }
    }

    @Override
    public ActionType makeDecision(DecisionTreeNode[] decisionTreeNodes, Game game) {
        return action;
    }
}
