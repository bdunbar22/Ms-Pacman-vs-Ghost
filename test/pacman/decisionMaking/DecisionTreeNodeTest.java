package pacman.decisionMaking;

import org.junit.Test;
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
     *
     * @throws Exception
     */
    @Test
    public void makeDecisionWithDecisionNode() throws Exception {

    }


    /**
     * Test the valid return functionality of an action node.
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

        assertEquals(actionNode.makeDecision(nodes, game), ActionType.RUN_AWAY);
    }
}
