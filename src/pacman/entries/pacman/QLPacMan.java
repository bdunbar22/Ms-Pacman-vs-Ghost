package pacman.entries.pacman;

import pacman.controllers.Controller;
import pacman.decisionMaking.DecisionTree;
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
 * This is the class uses a decision tree to decide which move to take next.
 * The class has a tree called decisionTree and calls decisionTree.makeDecision(game) to
 * find the best course of action.
 */
public class QLPacMan extends Controller<MOVE>
{
	  private MOVE myMove = MOVE.NEUTRAL;
	  private Map<QState, List<Integer>> qMap;
    private int numberOfRuns;

    public QLPacMan(String qMapFileLocation) {
        this.loadQMap(qMapFileLocation);
    }
	
    public MOVE getMove(Game game, long timeDue)
    {
        // Determine state

        // Find Q Map entry and chose an action.

        // Perform the move chosen by the tree.
        return myMove;
    }

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
}
