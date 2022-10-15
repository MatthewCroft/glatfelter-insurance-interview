package elevatorquestion;

import java.util.List;
import java.util.TreeSet;

public class ElevatorHelper {

    /**
     * Checks to see if there is any pending elevator queues
     */
    public boolean noPendingElevatorQueues(TreeSet<Integer> ascending, List<Integer> ascendingMissed, TreeSet<Integer> descending, List<Integer> descendingMissed) {
        if (ascending.isEmpty() && ascendingMissed.isEmpty()
                && descending.isEmpty() && descendingMissed.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Valid to move up if the elevator is currently moving up and
     * the elevator still has ascending moves to make
     */
    public boolean isValidToMoveUp(Direction currentDirection, TreeSet<Integer> ascending) {
        if (currentDirection == Direction.UP && !ascending.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Valid to move down if the elevator is currently moving down and
     * the elevator still has descending moves to make
     */
    public boolean isValidToMoveDown(Direction currentDirection, TreeSet<Integer> descending) {
        if (currentDirection == Direction.DOWN && !descending.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean shouldAddToCurrentAscendingQueue(int userFloor, Direction userDirection, int floor, Direction currentDirection) {
        if (currentDirection == Direction.UP && userDirection == Direction.UP && floor <= userFloor) {
            return true;
        }
        return false;
    }

    public boolean shouldAddToCurrentDescendingQueue(int userFloor, Direction userDirection, int floor, Direction currentDirection) {
        if (userDirection == Direction.DOWN && currentDirection == Direction.DOWN && userFloor <= floor) {
            return true;
        }
        return false;
    }
}
