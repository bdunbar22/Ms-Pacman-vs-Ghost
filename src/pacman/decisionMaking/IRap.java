package pacman.decisionMaking;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Queue;

/**
 * Allow for reading in a text file and making a RAPS
 * approach to decision making.
 *
 *
 * Created by Ben on 2/27/17.
 */
public interface IRap {
    /**
     * Run a rap. If it succeeds it will return true. If it does not succeed it will return false;
     *
     * @param executionQueue the queue subtasks will be added to. If adding subtasks, a rap
     *                       re-adds itself.
     * @return true or false.
     */
    boolean run(Queue<Object> executionQueue);
}
