package elevatorquestion;

import java.util.*;

public class Elevator {

    private ControlPanel controlPanel = new ControlPanel();
    private Direction direction = Direction.UP;
    private int floor = 1;

    private TreeSet<Integer> ascending = new TreeSet<>();
    private List<Integer> ascendingMissed = new ArrayList<>();

    private TreeSet<Integer> descending = new TreeSet<>();
    private List<Integer> descendingMissed = new ArrayList<>();
    private ElevatorHelper elevatorHelper = new ElevatorHelper();

    /**
     * User request from inside the elevator
     */
    public void userInsideRequest(int userFloor) {
        Direction currentDirection = getDirection();
        int currentFloor = getFloor();
        ControlPanel controlPanel = getControlPanel();

        if(controlPanel.checkInvalidFloor(userFloor)) {
            return;
        }

        if (currentDirection == Direction.UP && userFloor > currentFloor) {
            ascending.add(userFloor);
        } else if (currentDirection == Direction.DOWN && userFloor < currentFloor) {
            descending.add(userFloor);
        }
    }

    /**
     * User request from outside the elevator
     */
    public void userOutsideRequest(int userFloor, Direction userDirection) {
        int currentFloor = getFloor();
        Direction currentDirection = getDirection();
        ControlPanel controlPanel = getControlPanel();

        if(controlPanel.checkInvalidFloor(userFloor)) {
            return;
        }

        if (elevatorHelper.shouldAddToCurrentAscendingQueue(userFloor, userDirection, currentFloor, currentDirection)) {
            ascending.add(userFloor);
        } else if (elevatorHelper.shouldAddToCurrentDescendingQueue(userFloor, userDirection, currentFloor, currentDirection)) {
            descending.add(userFloor);
        } else if (userDirection == Direction.UP) {
            ascendingMissed.add(userFloor);
        } else if (userDirection == Direction.DOWN) {
            descendingMissed.add(userFloor);
        }
    }

    /**
     * Moves the elevator forward one spot in the queue
     * @return the floor the elevator moved to
     */
    public int updateFloor() {
        Direction currentDirection = getDirection();
        TreeSet<Integer> ascendingQueue = getAscending();
        TreeSet<Integer> descendingQueue = getDescending();

        if (elevatorHelper.noPendingElevatorQueues(ascendingQueue, getAscendingMissed(), descendingQueue, getDescendingMissed())) {
            setFloor(1);
            return 1;
        } else if (elevatorHelper.isValidToMoveUp(currentDirection, ascendingQueue)) {
            int currentFloor = ascending.pollFirst();
            setFloor(currentFloor);
            return currentFloor;
        } else if (elevatorHelper.isValidToMoveDown(currentDirection, descendingQueue)) {
            int currentFloor = descending.pollLast();
            setFloor(currentFloor);
            return currentFloor;
        } else {
            directionUpdate();
            updateWorkingQueues();
        }

        return updateFloor();
    }


    /**
     * Checks queues for direction update
     */
    private void directionUpdate() {
        TreeSet<Integer> ascendingQueue = getAscending();
        TreeSet<Integer> descendingQueue = getDescending();

        if (ascendingQueue.isEmpty() && !descendingQueue.isEmpty()) {
            direction = Direction.DOWN;
        }
        if (descendingQueue.isEmpty() && !ascendingQueue.isEmpty()) {
            direction = Direction.UP;
        }
    }

    /**
     * Updates ascending/descending queues
     * with any values from their "missed" lists
     */
    private void updateWorkingQueues() {
        if (getAscending().isEmpty()) {
            ascending.addAll(getAscendingMissed());
            ascendingMissed.clear();
        }

        if (getDescending().isEmpty()) {
            descending.addAll(getDescendingMissed());
            descendingMissed.clear();
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public List<Integer> getAscendingMissed() {
        return ascendingMissed;
    }

    public List<Integer> getDescendingMissed() {
        return descendingMissed;
    }

    public int getFloor() {
        return floor;
    }

    private void setFloor(int nFloor) {
        floor = nFloor;
    }

    public TreeSet<Integer> getAscending() {
        return ascending;
    }

    public TreeSet<Integer> getDescending() {
        return descending;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }
}
