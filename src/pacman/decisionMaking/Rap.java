package pacman.decisionMaking;

import pacman.game.Game;

import java.util.Queue;

/**
 * Implement IRap for the cases when a rap will add a task net to the
 * execution queue.
 *
 * Created by Ben on 3/14/17.
 */
public class Rap implements IRap{
    private EntityType preconditionEntityType;
    private int preconditionDistance;
    private String goal;
    private int[] taskNet;

    public Rap(String conditionEntity, int distance, String goal, int[] taskNet) {
        this.goal = goal;
        this.taskNet = taskNet;
        this.preconditionDistance = distance;
        try {
            this.preconditionEntityType = EntityType.valueOf(conditionEntity);
        }
        catch (Exception e) {
            System.out.println("Error finding entity type: " + conditionEntity);
            this.preconditionEntityType = EntityType.GHOST;
        }
    }

    public boolean executeRap(Queue<Object> executionQueue, IRap[] allRaps, Game game) {
        ActionType[] actionTypes = ActionType.values();
        for(int i = 0; i < actionTypes.length; i++) {
            if(executionQueue.contains(actionTypes[i]))
                return true;
        }
        // Test precondition.
        int current = game.getPacmanCurrentNodeIndex();
        int foundDistance = Util.conditionTest(preconditionEntityType, game, current);
        if(foundDistance <= preconditionDistance) {
            for(int i = 0; i < taskNet.length; i++) {
                executionQueue.add(allRaps[taskNet[i]]);
            }
            executionQueue.add(this);
        }

        return false;
    }
}