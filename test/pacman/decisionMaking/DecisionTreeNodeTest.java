package pacman.decisionMaking;

import org.junit.Test;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

import static org.junit.Assert.*;

/**
 * Test that nodes are loaded correctly and make decision works at the tree level.
 * Created by Ben on 3/15/17.
 */
public class DecisionTreeNodeTest {
    /**
     * Test that a decision nodes comparisons work and send the right child.
     *
     * In the scenario here, a decision is made to run away if a ghost is within 20 units and
     * go to the nearest if a ghost is not within 20 units.
     *
     * In the given game state, a ghost is not within 20 units so the action returned should be
     * nearest pill. The nearest pill will be above the pacman in the image "Test_Game_State",
     * but pacman must first go left a unit, so the move returned will be LEFT!
     *
     * @throws Exception
     */
    @Test
    public void makeDecisionWithDecisionNode() throws Exception {
        DecisionTreeNode[] children = new DecisionTreeNode[2];
        children[0] = new Action("RUN_AWAY");
        children[1] = new Action("NEAREST_PILL");

        DecisionTreeNode decisionNode = new Decision(0, 1, "GHOST", 20);

        Game game = new Game(0, 0);
        String testGameState = "0,280,470,280,0,600,RIGHT,2,false,153,0,0,UP,165,0,0,UP,177,0,0,"
            + "RIGHT,270,0,0,UP,1111111111111111111111111111111111011111111111111111111111011111011111011111011111111111111101010101010100000000000000000000000010011001111111110000000011111111111111111111111111111111111111111111111111111111111111111111,1111,-1,false,false,false,false,false,false,false";
        game.setGameState(testGameState);

        assertEquals(decisionNode.makeDecision(children, game, MOVE.RIGHT), MOVE.LEFT);
    }


    /**
     * Test the valid return functionality of an action node.
     * For run away in the test state, pacman should go right.
     */
    @Test
    public void makeDecisionWithActionNode() throws Exception {
        DecisionTreeNode actionNode = new Action("RUN_AWAY");

        Game game = new Game(0, 0);
        String testGameState = "0,280,470,280,0,600,RIGHT,2,false,153,0,0,UP,165,0,0,UP,177,0,0,"
            + "RIGHT,270,0,0,UP,1111111111111111111111111111111111011111111111111111111111011111011111011111011111111111111101010101010100000000000000000000000010011001111111110000000011111111111111111111111111111111111111111111111111111111111111111111,1111,-1,false,false,false,false,false,false,false";
        game.setGameState(testGameState);

        DecisionTreeNode[] nodes = new DecisionTreeNode[1];
        nodes[0] = actionNode;

        assertEquals(actionNode.makeDecision(nodes, game, MOVE.RIGHT), MOVE.RIGHT);
    }
}
