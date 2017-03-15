package pacman.entries.pacman;

import pacman.controllers.Controller;
import pacman.decisionMaking.*;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

import static pacman.game.Constants.DELAY;

/**
 * This class uses Raps to try and find acceptable moves for pacman to take. The class has
 * an array of raps, and each turn it adds the non primitive Raps to the queue. The class
 * then goes through the execution queue as described in the paper by R. James Firby. In order to
 * easily stop before the turn ends, the loop will stop when either the queue is empty or a
 * Rap finished successfully, since in this implementation that would mean a move has been found.
 */
public class RAPPacMan extends Controller<MOVE>
{
	private IRap[] raps;
	private Queue<Object> executionQueue = new LinkedList<>();
	private MOVE myMove = MOVE.NEUTRAL;

	public RAPPacMan(String rapFileLocation) {
		this.loadRaps(rapFileLocation);
	}
	
	public MOVE getMove(Game game, long timeDue) 
	{
		executionQueue.clear();
		for(int i = 0; i < raps.length; i++) {
			if(raps[i] instanceof Rap)
				executionQueue.add(raps[i]);
		}

		/**
		 * Execute raps until queue is empty or time is up.
		 * If a move is found, break the loop
		 * Note: in a more complex system which wasn't turn based the execution queue would
		 * just send found commands to hardware and continue going through raps, but in this
		 * implementation it makes more sense to take the move found and break the loop.
		 */
		while (!executionQueue.isEmpty() && System.currentTimeMillis() < timeDue - 1) {
			Object popped = executionQueue.poll();
			if (popped instanceof ActionType) {
				ActionType actionType = (ActionType) popped;
				myMove = Util.findDirection(actionType, game, myMove);
				break;
			} else if (popped instanceof IRap) {
				IRap rap = (IRap) popped;
				rap.executeRap(executionQueue, raps, game);
			} else {
				System.out.println("Unexpected queued item type in rap execution queue.");
			}
		}

		return myMove;
	}

	/**
	 * Loads all the lines from text file into Raps
	 * File should be of type: RapsText. See data/decisionMaking/readme.txt
	 *
	 * @param fileName to read
	 */
	private void loadRaps(String fileName)
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream
				(fileName + ".txt")));

			String preambleLine = br.readLine();

			int numberOfRaps = Integer.parseInt(preambleLine);
			raps = new IRap[numberOfRaps];

			String input = br.readLine();
			int count = 0;

			while(input!=null && count < numberOfRaps)
			{
				String[] values = input.split(",");

				int index = Integer.parseInt(values[0]);
				if(index != count) {
					throw new Exception("Malformed rap text file. Index error: " +
						fileName);
				}

				String conditionEntity;
				String goal;
				String rapType = values[1];
				if(rapType.equals("PRIMITIVE")) {
					conditionEntity = values[2];
					int conditionDistance = Integer.parseInt(values[3]);
					goal = values[4];
					raps[index] = new PrimitiveRap(conditionEntity, conditionDistance, goal);
				} else if(rapType.equals("RAP")) {
					conditionEntity = values[2];
					int conditionMinDistance = Integer.parseInt(values[3]);
					int conditionMaxDistance = Integer.parseInt(values[4]);
					goal = values[5];

					int[] taskNet = new int[values.length - 6];
					for(int i = 0; i < taskNet.length; i++)
						taskNet[i] = Integer.parseInt(values[i + 6]);

					raps[index] = new Rap(conditionEntity, conditionMinDistance, conditionMaxDistance, goal, taskNet);
				} else {
					throw new Exception("Malformed rap text file. File: " +
						fileName + "Format error on index: " + index);
				}

				input = br.readLine();
				count ++;
			}
		}
		catch(Exception errorSent)
		{
			errorSent.printStackTrace();
		}
	}
}
