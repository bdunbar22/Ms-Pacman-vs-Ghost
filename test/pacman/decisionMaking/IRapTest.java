package pacman.decisionMaking;

import org.junit.Test;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Testing the functionality of raps.
 *
 * Created by Ben on 3/15/17.
 */
public class IRapTest {
    @Test
    public void goalCheck() throws Exception {
        IRap actionRap = new PrimitiveRap("POWER_PILL", 50, "NEAREST_POWER_PILL");

        Game game = new Game(0, 0);
        String testGameState = "0,280,470,280,0,600,RIGHT,2,false,153,0,0,UP,165,0,0,UP,177,0,0,"
            + "RIGHT,270,0,0,UP,1111111111111111111111111111111111011111111111111111111111011111011111011111011111111111111101010101010100000000000000000000000010011001111111110000000011111111111111111111111111111111111111111111111111111111111111111111,1111,-1,false,false,false,false,false,false,false";
        game.setGameState(testGameState);

        Queue<Object> executionQueue1 = new LinkedList<>();
        Queue<Object> executionQueue2 = new LinkedList<>();
        executionQueue2.add(MOVE.LEFT);

        //The goal is that the move towards the nearest power pill is on the queue.
        //queue 2 should pass and queue 1 should fail.
        assertTrue(actionRap.goalCheck(executionQueue2, game, MOVE.RIGHT));
        assertFalse(actionRap.goalCheck(executionQueue1, game, MOVE.RIGHT));
    }

    @Test
    public void validityCheck() throws Exception {
        IRap actionRap = new PrimitiveRap("POWER_PILL", 50, "NEAREST_POWER_PILL");

        Game game = new Game(0, 0);
        String testGameState = "0,280,470,280,0,600,RIGHT,2,false,153,0,0,UP,165,0,0,UP,177,0,0,"
            + "RIGHT,270,0,0,UP,1111111111111111111111111111111111011111111111111111111111011111011111011111011111111111111101010101010100000000000000000000000010011001111111110000000011111111111111111111111111111111111111111111111111111111111111111111,1111,-1,false,false,false,false,false,false,false";
        game.setGameState(testGameState);

        //There is no power pill within 50, so it shouldn't meet the precondition
        assertFalse(actionRap.validityCheck(game));
    }

    @Test
    public void taskNetSelector() throws Exception {
        IRap actionRap = new PrimitiveRap("POWER_PILL", 50, "NEAREST_POWER_PILL");

        Game game = new Game(0, 0);
        String testGameState = "0,280,470,280,0,600,RIGHT,2,false,153,0,0,UP,165,0,0,UP,177,0,0,"
            + "RIGHT,270,0,0,UP,1111111111111111111111111111111111011111111111111111111111011111011111011111011111111111111101010101010100000000000000000000000010011001111111110000000011111111111111111111111111111111111111111111111111111111111111111111,1111,-1,false,false,false,false,false,false,false";
        game.setGameState(testGameState);

        try {
            MOVE taskMove = (MOVE) actionRap.taskNetSelector(new IRap[0], game, MOVE.RIGHT)[0];

            assertEquals(taskMove, MOVE.LEFT);

        } catch (Exception e) {
            assertTrue(false);
        }

        IRap[] actions = new IRap[2];
        actions[0] = new PrimitiveRap("POWER_PILL", 50, "NEAREST_POWER_PILL");
        actions[1] =  new PrimitiveRap("GHOST", 20, "RUN_AWAY");
        IRap rap = new Rap("PILL", 0, 80, "Test task net", new int[]{0, 1});

        try {
            IRap[] taskNet = (IRap[]) rap.taskNetSelector(actions, game, MOVE.RIGHT);

            assertEquals(taskNet[0], actions[0]);
            assertEquals(taskNet[1], actions[1]);

        } catch (Exception e) {
            assertTrue(false);
        }


    }

}
