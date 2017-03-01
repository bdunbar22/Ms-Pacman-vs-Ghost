package pacman.decisionMaking;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Allow for reading in a text file and making a RAPS
 * approach to decision making.
 *
 *
 * Created by Ben on 2/27/17.
 */
public class Raps {


    /**
     * Loads all the lines from text file into RAPS
     * File should be of type: RapsText. See data/decisionMaking/readme.txt
     *
     * @param fileName to read
     */
    private void loadRaps(String fileName, String a)
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream
                (fileName + ".txt")));

            /* Don't need to use preamble line */
            String preambleLine = br.readLine();

            String input = br.readLine();

            while(input!=null)
            {
                String[] values = input.split("\t");
                a.concat(values[0]);

                int index = Integer.parseInt(values[0]);
                int x = Integer.parseInt(values[1]);
                int y = Integer.parseInt(values[2]);


                input = br.readLine();
            }
        }
        catch(Exception errorSent)
        {
            errorSent.printStackTrace();
        }
    }
}
