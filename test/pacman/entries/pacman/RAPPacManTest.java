package pacman.entries.pacman;

import org.junit.Test;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

import static org.junit.Assert.*;
import static pacman.game.Constants.DELAY;

/**
 * Test the RAP pacman
 * Created by Ben on 3/15/17.
 */
public class RAPPacManTest {
    /**
     * Test that get move will work correctly.
     *
     * @throws Exception
     */
    @Test
    public void getMove() throws Exception {
        String rapFileLocation = "data/decisionMaking/raptext2";
        Controller pacman = new RAPPacMan(rapFileLocation);
        // Put in separate thread so this thread can pause for delay time.
        new Thread(pacman).start();

        // Set a game state that we can analyze. Three ghosts should make moves.
        Game game = new Game(0, 0);
        String testGameState = "0,280,470,280,0,600,RIGHT,2,false,153,0,0,UP,165,0,0,UP,177,0,0,"
            + "RIGHT,270,0,0,UP,1111111111111111111111111111111111011111111111111111111111011111011111011111011111111111111101010101010100000000000000000000000010011001111111110000000011111111111111111111111111111111111111111111111111111111111111111111,1111,-1,false,false,false,false,false,false,false";
        game.setGameState(testGameState);
        GameView gv = null;
        gv = new GameView(game).showGame();

        long timeDue = System.currentTimeMillis() + DELAY;
        pacman.update(game, timeDue);
        Thread.sleep(DELAY);
        //Get the moves
        Object move = pacman.getMove(game, timeDue);
        MOVE chosenMove = (MOVE) move;

        // Give a visual of the game state we created for a test.
        for(int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            gv.repaint();
        }

        // Tests
        assertEquals(chosenMove, MOVE.LEFT);
    }
}
