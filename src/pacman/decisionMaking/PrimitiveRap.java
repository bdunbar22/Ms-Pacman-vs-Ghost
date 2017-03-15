package pacman.decisionMaking;

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

    public boolean executeRap(Queue<Object> executionQueue, IRap[] allRaps, Game game) {
        if(executionQueue.contains(goal)) {
            return true;
        }
        // Test precondition.
        int current = game.getPacmanCurrentNodeIndex();
        int foundDistance = Util.conditionTest(preconditionEntityType, game, current);
        if(foundDistance <= preconditionDistance) {
            executionQueue.add(goal);
        }
        executionQueue.add(this);

        return false;
    }
}
