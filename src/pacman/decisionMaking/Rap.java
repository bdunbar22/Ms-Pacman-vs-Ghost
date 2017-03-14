package pacman.decisionMaking;

import java.util.List;

/**
 * Implement IRap for the cases when a rap will add a task net to the
 * execution queue.
 *
 * Created by Ben on 3/14/17.
 */
public class Rap {
    private EntityType preconditionType;
    private int preconditionDistance;
    private String goal;
    private List<IRap> taskNet;


}
