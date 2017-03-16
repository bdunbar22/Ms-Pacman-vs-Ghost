package pacman.decisionMaking;

import org.junit.Test;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

import static org.junit.Assert.*;

/**
 * Test util functions for decision making
 *
 * Created by Ben on 3/15/17.
 */
public class UtilTest {
    @Test
    public void conditionTest() throws Exception {
        //Ghosts distance from pacman
        Game game = new Game(0, 0);
        String testGameState = "0,280,470,280,0,600,RIGHT,2,false,153,0,0,UP,165,0,0,UP,177,0,0,"
            + "RIGHT,270,0,0,UP,1111111111111111111111111111111111011111111111111111111111011111011111011111011111111111111101010101010100000000000000000000000010011001111111110000000011111111111111111111111111111111111111111111111111111111111111111111,1111,-1,false,false,false,false,false,false,false";
        game.setGameState(testGameState);

        int ghostDistance = Util.conditionTest(EntityType.GHOST, game, game
            .getPacmanCurrentNodeIndex());

        //Pills distance from pacman
        int pillDistance = Util.conditionTest(EntityType.PILL, game, game
            .getPacmanCurrentNodeIndex());

        //Power pills distance from pacman
        int powerPillsDistance = Util.conditionTest(EntityType.POWER_PILL, game, game
            .getPacmanCurrentNodeIndex());

        //No edible ghosts
        int edibleGhostDistance = Util.conditionTest(EntityType.EDIBLE_GHOST, game, game
            .getPacmanCurrentNodeIndex());

        assertTrue(ghostDistance == 77);
        assertTrue(pillDistance == 25);
        assertTrue(powerPillsDistance == 81);
        assertTrue(edibleGhostDistance == Integer.MAX_VALUE);
    }

    @Test
    public void findDirection() throws Exception {
        Game game = new Game(0, 0);
        String testGameState = "0,280,470,280,0,600,RIGHT,2,false,153,0,0,UP,165,0,0,UP,177,0,0,"
            + "RIGHT,270,0,0,UP,1111111111111111111111111111111111011111111111111111111111011111011111011111011111111111111101010101010100000000000000000000000010011001111111110000000011111111111111111111111111111111111111111111111111111111111111111111,1111,-1,false,false,false,false,false,false,false";
        game.setGameState(testGameState);

        MOVE directionRunAway = Util.findDirection(ActionType.RUN_AWAY, game, MOVE.NEUTRAL);
        MOVE directionAttack = Util.findDirection(ActionType.ATTACK, game, MOVE.NEUTRAL);
        MOVE directionPill = Util.findDirection(ActionType.NEAREST_PILL, game, MOVE.NEUTRAL);
        MOVE directionPower = Util.findDirection(ActionType.NEAREST_POWER_PILL, game, MOVE.NEUTRAL);

        assertEquals(directionRunAway, MOVE.RIGHT);
        assertEquals(directionAttack, MOVE.NEUTRAL);
        assertEquals(directionPill, MOVE.LEFT);
        assertEquals(directionPower, MOVE.LEFT);

    }
}
