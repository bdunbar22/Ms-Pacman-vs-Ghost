package pacman.decisionMaking;

import pacman.game.Constants.MOVE;
import pacman.game.Game;

import java.util.Queue;

/**
 * Primitive rap for when an action should be added to the queue instead
 * of a task net.
 *
 * Created by Ben on 3/14/17.
 */
public class PrimitiveRap implements IRap{
    private EntityType preconditionEntityType;
    private int preconditionDistance;
    private ActionType goal;

    public PrimitiveRap(String conditionEntity, int distance, String goal) {
        if(distance == -1)
            distance = Integer.MAX_VALUE;
        this.preconditionDistance = distance;
        try {
            this.preconditionEntityType = EntityType.valueOf(conditionEntity);
        }
        catch (Exception e) {
            System.out.println("Error finding entity type: " + conditionEntity);
            this.preconditionEntityType = EntityType.GHOST;
        }
        try {
            this.goal = ActionType.valueOf(goal);
        }
        catch (Exception e) {
            this.goal = ActionType.RUN_AWAY;
        }
    }

    @Override
    public boolean goalCheck(Queue<Object> executionQueue, Game game, MOVE lastMove) {
        MOVE desiredMove = Util.findDirection(goal, game, lastMove);
        return (executionQueue.contains(desiredMove));
    }

    @Override
    public boolean validityCheck(Game game) {
        int current = game.getPacmanCurrentNodeIndex();
        int foundDistance = Util.conditionTest(preconditionEntityType, game, current);
        return (foundDistance <= preconditionDistance);
    }

    @Override
    public Object[] taskNetSelector(IRap[] allRaps, Game game, MOVE lastMove) {
        return new Object[]{Util.findDirection(goal, game, lastMove)};
    }
}
