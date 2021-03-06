package pacman.decisionMaking;

import pacman.game.Constants.MOVE;
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
            this.action = ActionType.RUN_AWAY;
        }
    }

    @Override
    public MOVE makeDecision(DecisionTreeNode[] decisionTreeNodes, Game game, MOVE lastMove) {

        return Util.findDirection(action, game, lastMove);
    }
}
