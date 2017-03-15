package pacman.decisionMaking;

import pacman.game.Constants.MOVE;
import pacman.game.Game;

import java.util.Queue;

/**
 * Allow for using a RAPS
 * approach to decision making.
 *
 * We have determined that for this project, Raps need: goals, preconditions, and task nets
 * Any rap should be able to check its goal for completion, validate its preconditions, and
 * provide either subraps or a move as its task net.
 *
 * Created by Ben on 2/27/17.
 */
public interface IRap {
    /**
     * Determine if the goal of a rap has already been achieved.
     * @param game data to use
     * @return bool
     */
    boolean goalCheck(Queue<Object> executionQueue, Game game, MOVE lastMove);

    /**
     * Determine if the precondition of the rap is met.
     * @param game data to use
     * @return bool
     */
    boolean validityCheck(Game game);

    /**
     * Return the task net found by the rap.
     * Should be a Move or a list of raps or both.
     * @param allRaps the list of raps from the input file.
     * @return the task net
     */
    Object[] taskNetSelector(IRap[] allRaps, Game game, MOVE lastMove);
}
