package pacman.qLearning;

import org.junit.Test;
import pacman.game.Game;

import static org.junit.Assert.*;

/**
 * Test that the QState class operates correctly.
 *
 * Created by Ben on 4/11/17.
 */
public class QStateTest {
    @Test
    public void testEquals() throws Exception {
        QState testState = new QState("true", "MID", "FAR", "NEAR", "MID");
        QState testState2 = new QState("true", "MID", "FAR", "NEAR", "MID");
        assertEquals(testState, testState2);
    }

    @Test
    public void testToString() throws Exception {
        QState testState = new QState("true", "MID", "FAR", "NEAR", "MID");

        String testString = "true;MID;FAR;NEAR;MID";

        assertEquals(testState.toString(), testString);
    }

    @Test
    public void testMakeState() throws Exception {
        QState testState = new QState("true", "FAR", "FAR", "MID", "FAR");

        Game game = new Game(0, 0);
        String testGameState = "0,280,470,280,0,600,RIGHT,2,false,153,0,0,UP,165,0,0,UP,177,0,0,"
            + "RIGHT,270,0,0,UP,1111111111111111111111111111111111011111111111111111111111011111011111011111011111111111111101010101010100000000000000000000000010011001111111110000000011111111111111111111111111111111111111111111111111111111111111111111,1111,-1,false,false,false,false,false,false,false";
        game.setGameState(testGameState);

        QState testState2 = QState.getState(game);
        assertEquals(testState, testState2);
    }
}
