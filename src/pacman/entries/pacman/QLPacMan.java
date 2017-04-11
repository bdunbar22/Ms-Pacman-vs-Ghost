package pacman.entries.pacman;

import pacman.controllers.Controller;
import pacman.decisionMaking.ActionType;
import pacman.decisionMaking.Util;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.qLearning.DistanceEnum;
import pacman.qLearning.QState;
import java.util.Map.Entry;

import java.io.*;
import java.util.*;

/**
 * This implementation of pacman uses a Q Learning approach. It is given a text file location
 * to read a stored qMap from and uses this in its decision making. At the end of the game,
 * updated q values can be rewritten into the file.
 */
public class QLPacMan extends Controller<MOVE>
{
	  private MOVE myMove = MOVE.NEUTRAL;
	  private Map<QState, List<Integer>> qMap;
    private Random rnd = new Random();
    private Random rndChoice = new Random();

    private int numberOfRuns;

    private ActionType lastAction;
    private QState lastState;
    private int actionTakenCount;

    private int lastScore;

    public QLPacMan(String qMapFileLocation) {
        this.loadQMap(qMapFileLocation);
        lastAction = null;
        lastState = null;
        lastScore = 0;
        actionTakenCount = 0;
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
		    this.qMap = new HashMap<QState, List<Integer>>();
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

                String powerPillBool = values[0];
                String closestGhostDistance = values[1];
                String closestEdibleDistance = values[2];
                String closestPillDistance = values[3];
                String closestPowerDistance = values[4];

                QState stateEntry = new QState(powerPillBool, closestGhostDistance,
                    closestEdibleDistance, closestPillDistance, closestPowerDistance);

                List<Integer> scores = new ArrayList<>();
                scores.add(Integer.parseInt(values[5]));
                scores.add(Integer.parseInt(values[6]));
                scores.add(Integer.parseInt(values[7]));
                scores.add(Integer.parseInt(values[8]));

                qMap.put(stateEntry,scores);

                input = br.readLine();
			      }
		    }
		    catch(Exception errorSent)
		    {
			      errorSent.printStackTrace();
		    }
	  }

    /**
     * Save the updated q Map to the desired txt file.
     * @param qMapFileLocation file name.
     */
    public String saveQMap(String qMapFileLocation) {
        try {
            String fileLocation = qMapFileLocation + ".txt";

            PrintWriter writer = new PrintWriter(fileLocation, "UTF-8");

            writer.println(Integer.toString(numberOfRuns + 1));

            Set<Entry<QState, List<Integer>>> entriesOfQMap = qMap.entrySet();

            for (Entry<QState, List<Integer>> entry : entriesOfQMap) {
                String qMapEntry = "";
                qMapEntry += entry.getKey().toString();
                for(Integer score : entry.getValue()) {
                    qMapEntry += ";" + score.toString();
                }

                writer.println(qMapEntry);
            }

            writer.close();
            return fileLocation;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * Based on the most recent reward, update the q value for last state and action pair which
     * was chosen.
     *
     * @param game current game
     * @param currentState current summarized state
     */
    private void updateQValue(Game game, QState currentState) {
        if(lastState != null && lastAction != null) {
            Integer reward = 0;
            if(game.wasPacManEaten()) {
                reward = lastAction.equals(ActionType.RUN_AWAY) ? 0: -50; // Penalty for death
            } else if(game.wasPowerPillEaten() && lastAction.equals(ActionType.NEAREST_POWER_PILL)) {
                reward = 25;  // Reward power pill
            } else if(currentState.getClosestGhostDistance().equals(
                DistanceEnum.NEAR) && lastAction.equals(ActionType.RUN_AWAY)) {
                reward = 10;  // Reward trying to escape
            } else if(lastAction.equals(ActionType.ATTACK)) {
                reward = 10 * game.getNumGhostsEaten();
            } else if(lastAction.equals(ActionType.NEAREST_PILL)) {
                reward = 5;  // Reward for normal pills as that is the game objective
            }
            List<Integer> qValues = qMap.get(lastState);
            Integer newQValue;
            switch (lastAction) {
                case NEAREST_POWER_PILL:
                    newQValue = calculateQValue(qValues.get(0), reward, currentState);
                    qValues.set(0, newQValue);
                    break;
                case NEAREST_PILL:
                    newQValue = calculateQValue(qValues.get(1), reward, currentState);
                    qValues.set(1, newQValue);
                    break;
                case ATTACK:
                    newQValue = calculateQValue(qValues.get(2), reward, currentState);
                    qValues.set(2, newQValue);
                    break;
                case RUN_AWAY:
                    newQValue = calculateQValue(qValues.get(3), reward, currentState);
                    qValues.set(3, newQValue);
            }

            //Perform update
            qMap.put(lastState, qValues);
            //Scale q values if needed (otherwise they approach infinity)
            scaleQValues();
        }
    }

    /**
     * Calculate Q value based on current value and the prediction error based on the possible
     * choices at the current state and the most recent reward. Note that prediction error is
     * scaled by learning rate.
     * @param oldQValue old q value
     * @param reward most recent reward
     * @param currentState current summarized game state
     * @return new q value
     */
    private Integer calculateQValue(Integer oldQValue, Integer reward, QState currentState) {
        Integer maxCurrentValue = Integer.MIN_VALUE;
        List<Integer> currents = qMap.get(currentState);
        for(Integer integer : currents) {
            if(integer > maxCurrentValue) {
                maxCurrentValue = integer;
            }
        }
        float learningRate = (1.0f / (numberOfRuns + 1));
        float gamma = 0.5f;
        float prediction = reward + gamma * maxCurrentValue - oldQValue;
        float newQValue = oldQValue + (learningRate * prediction);

        return (int) newQValue;
    }

    /**
     * Chose actions based on the idea that at first we are just exploring, but as the Q map
     * gets more filled out then we can start to chose the best paths. However still allowing
     * to choose other options in the hope of finding a better option. As time goes on, the
     * probability to explore new options will decrease.
     * @param currentState of the game
     * @return the chosen action
     */
    private ActionType choseAction(QState currentState) {
        //float randomValue = rnd.nextFloat();
        //float probabilityToExplore = 0.0f;
        //if(numberOfRuns < 900) {
        //    probabilityToExplore = 1.0f;
        //}
        // Explore with probablilty = to probability to explore
        // While exploring:
        // keep last action for 15 moves so that you can actually see the affects over time
        // after that choose a new action.
        if(numberOfRuns < 600) {
            if(lastAction != null && actionTakenCount < 15) {
                actionTakenCount++;
                return lastAction;
            } else {
                actionTakenCount = 0;
                float choice = rndChoice.nextFloat() * 4.0f;
                int intChoice = (int) choice;
                if(intChoice > 3) intChoice = 3;

                return ActionType.values()[intChoice];
            }
        } else {
            // Choose the best option
            List<Integer> scores = qMap.get(currentState);
            Integer maxCurrentValue = Integer.MIN_VALUE;
            Integer maxLocation = 0;
            for(int i = 0; i < scores.size(); i++) {
                if(scores.get(i) > maxCurrentValue) {
                    maxCurrentValue = scores.get(i);
                    maxLocation = i;
                }
            }

            return ActionType.values()[maxLocation];
        }
    }

    private void scaleQValues() {
        Set<Entry<QState, List<Integer>>> entries = qMap.entrySet();
        int max = 100;
        float scale = 1.0f;
        List<Integer> integerList = new ArrayList<>();
        for(Entry<QState, List<Integer>> entry : entries) {
            for (Integer score : entry.getValue()) {
                if(score > max) {
                    max = score;
                    scale = 100.f / score;
                }
            }
        }
        // If numbers need to be scaled, do it.
        // Allow numbers to grow until 1000 then shrink back to 100.
        // Not constraining to 100 every run will save time.
        if(max > 400) {
            for(Entry<QState, List<Integer>> entry : entries) {
                integerList.clear();
                for (int i = 0; i < 4; i++) {
                    integerList.add((int) (scale * entry.getValue().get(i)));
                }

                qMap.put(entry.getKey(), integerList);
            }
        }
    }
}
