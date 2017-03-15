package pacman.decisionMaking;

import pacman.game.Constants.MOVE;
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
    private int preconditionMinDistance;
    private int preconditionMaxDistance;
    private String goal;
    private int[] taskNet;

    public Rap(String conditionEntity, int minDistance, int maxDistance, String goal, int[]
        taskNet) {
        this.goal = goal;
        this.taskNet = taskNet;
        this.preconditionMinDistance = minDistance;
        if(maxDistance == -1)
            maxDistance = Integer.MAX_VALUE;
        this.preconditionMaxDistance = maxDistance;
        try {
            this.preconditionEntityType = EntityType.valueOf(conditionEntity);
        }
        catch (Exception e) {
            System.out.println("Error finding entity type: " + conditionEntity);
            this.preconditionEntityType = EntityType.GHOST;
        }
    }

    @Override
    public boolean goalCheck(Queue<Object> executionQueue, Game game, MOVE lastMove) {
        MOVE[] moves = MOVE.values();
        for(int i = 0; i < moves.length; i++) {
            if(executionQueue.contains(moves[i]))
                return true;
        }
        return false;
    }

    @Override
    public boolean validityCheck(Game game) {
        int current = game.getPacmanCurrentNodeIndex();
        int foundDistance = Util.conditionTest(preconditionEntityType, game, current);
        return (foundDistance <= preconditionMaxDistance && foundDistance >= preconditionMinDistance);
    }

    @Override
    public Object[] taskNetSelector(IRap[] allRaps, Game game, MOVE lastMove) {
        IRap[] subRaps = new IRap[taskNet.length];
        for(int i = 0; i < taskNet.length; i++) {
            subRaps[i] = allRaps[taskNet[i]];
        }
        return subRaps;
    }
}
