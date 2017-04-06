package pacman.entries.pacman;

import pacman.controllers.Controller;
import pacman.decisionMaking.ActionType;
import pacman.decisionMaking.DecisionTree;
import pacman.decisionMaking.Util;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.qLearning.QState;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This implementation of pacman uses a Q Learning approach. It is given a text file location
 * to read a stored qMap from and uses this in its decision making. At the end of the game,
 * updated q values can be rewritten into the file.
 */
public class QLPacMan extends Controller<MOVE>
{
	  private MOVE myMove = MOVE.NEUTRAL;
	  private Map<QState, List<Integer>> qMap;

    private int numberOfRuns;

    private ActionType lastAction;
    private QState lastState;
    private int lastScore;

    public QLPacMan(String qMapFileLocation) {
        this.loadQMap(qMapFileLocation);
        lastAction = null;
        lastState = null;
        lastScore = 0;
    }
	
    public MOVE getMove(Game game, long timeDue)
    {
        // Determine state
        QState currentState = QState.getState(game);

        updateQValue(game, currentState);

        // Find Q Map entry and chose an action.
        ActionType chosenAction = choseAction(currentState);

        // Update memory
        lastAction = chosenAction;
        lastState = currentState;
        lastScore = game.getScore();

        // Perform the move appropriate for the action chosen
        myMove = Util.findDirection(chosenAction, game, myMove);
        return myMove;
    }

    /**
     * Read in a text file into a qMap.
     * See "Assignment 4/Game AI HW4" for some more detail about txt file structure.
     * @param qMapFileLocation file name.
     */
	  private void loadQMap(String qMapFileLocation) {
		    this.qMap = new HashMap<>();
		    try
		    {
			      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream
				        (qMapFileLocation + ".txt")));

			      String preambleLine = br.readLine();

			      numberOfRuns = Integer.parseInt(preambleLine);

			      String input = br.readLine();

			      while(input!=null)
			      {
                String[] values = input.split(";");

                String closestGhostDistance = values[0];
                String closestEdibleDistance = values[1];
                String closestPillDistance = values[2];
                String closestPowerDistance = values[3];

                QState stateEntry = new QState(closestGhostDistance, closestEdibleDistance,
                    closestPillDistance, closestPowerDistance);

                List<Integer> scores = new ArrayList<>();
                scores.add(Integer.parseInt(values[4]));
                scores.add(Integer.parseInt(values[5]));
                scores.add(Integer.parseInt(values[6]));
                scores.add(Integer.parseInt(values[7]));

                qMap.put(stateEntry,scores);

                input = br.readLine();
			      }
		    }
		    catch(Exception errorSent)
		    {
			      errorSent.printStackTrace();
		    }
	  }


    private void updateQValue(Game game, QState currentState) {
        if(lastState != null && lastAction != null) {
            Integer reward;
            if(game.wasPacManEaten()) {
                reward = -1;
            } else {
                reward = game.getScore() - lastScore;
            }
            List<Integer> qValues = qMap.get(lastState);
            Integer newQValue;
            switch (lastAction) {
                case NEAREST_POWER_PILL:
                    newQValue = calculateQValue(qValues.get(0), reward, currentState);
                    qValues.set(0, newQValue);
                case NEAREST_PILL:
                    newQValue = calculateQValue(qValues.get(1), reward, currentState);
                    qValues.set(1, newQValue);
                case ATTACK:
                    newQValue = calculateQValue(qValues.get(2), reward, currentState);
                    qValues.set(2, newQValue);
                case RUN_AWAY:
                    newQValue = calculateQValue(qValues.get(3), reward, currentState);
                    qValues.set(3, newQValue);
            }

            //Perform update
            qMap.put(lastState, qValues);
        }
    }

    private Integer calculateQValue(Integer oldQValue, Integer reward, QState currentState) {
        //TODO
        Integer maxCurrentValue = 0;
        List<Integer> currents = qMap.get(currentState);
        for(Integer integer : currents) {
            if(integer > maxCurrentValue) {
                maxCurrentValue = integer;
            }
        }
        Integer newQValue = oldQValue + (int) ((1.0f / numberOfRuns) * reward);


        return newQValue;
    }

    private ActionType choseAction(QState currentState) {
     //TODO
        return ActionType.RUN_AWAY;
    }
}
