package pacman.entries.pacman;

import org.junit.Test;
import pacman.controllers.Controller;
import pacman.game.Constants;
import pacman.game.Game;

import static org.junit.Assert.*;
import static pacman.game.Constants.DELAY;

/**
 * Test that the QL pacman operates as expected
 *
 * Created by Ben on 4/11/17.
 */
public class QLPacManTest {

    /**
     * Test that the QL pacman with a learned qmap input file will make a logical move.
     * @throws Exception
     */
    @Test
    public void testGetMove() throws Exception {
        Game game = new Game(0, 0);
        String testGameState = "0,280,470,280,0,600,RIGHT,2,false,153,0,0,UP,165,0,0,UP,177,0,0,"
            + "RIGHT,270,0,0,UP,1111111111111111111111111111111111011111111111111111111111011111011111011111011111111111111101010101010100000000000000000000000010011001111111110000000011111111111111111111111111111111111111111111111111111111111111111111,1111,-1,false,false,false,false,false,false,false";
        game.setGameState(testGameState);

        String qMapFile = "data/qLearning/q_values_trained";

        Controller pacman = new QLPacMan(qMapFile);

        long timeDue = System.currentTimeMillis() + DELAY;
        pacman.update(game, timeDue);
        Thread.sleep(DELAY);
        //Get the moves
        Object move = pacman.getMove(game, timeDue);
        Constants.MOVE chosenMove = (Constants.MOVE) move;

        // Best move based on test game state (towards nearest pill)
        assertEquals(chosenMove, Constants.MOVE.LEFT);
    }
}
