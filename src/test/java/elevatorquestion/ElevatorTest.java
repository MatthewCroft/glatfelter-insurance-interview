package elevatorquestion;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElevatorTest {

    @Test
    public void testElevatorReturnsToFloorOneWhenQueuesEmpty() {
        Elevator elevator = new Elevator();

        assertEquals(1, elevator.updateFloor());
    }

    @Test
    public void testCannotRequestInvalidFloor() {
        Elevator elevator = new Elevator();

        elevator.userOutsideRequest(22, Direction.UP);
        elevator.userInsideRequest(-7);

        // check all queues are empty
        assertEquals(0, elevator.getDescendingMissed().size());
        assertEquals(0, elevator.getAscendingMissed().size());
        assertEquals(0, elevator.getAscending().size());
        assertEquals(0, elevator.getDescending().size());
    }

    @Test
    public void testUserRequestSameFloorMultipleTimesOnlyAddsOneToQueue() {
        Elevator elevator = new Elevator();

        elevator.userOutsideRequest(7, Direction.UP);
        elevator.userOutsideRequest(7, Direction.UP);

        assertEquals(1, elevator.getAscending().size());
    }

    @Test
    public void testElevatorAscendsFromStart() {
        Elevator elevator = new Elevator();

        elevator.userOutsideRequest(2, Direction.UP);
        elevator.userOutsideRequest(6, Direction.UP);

        assertEquals(2, elevator.updateFloor());
        assertEquals(6, elevator.updateFloor());
        assertEquals(Direction.UP, elevator.getDirection());
    }

    @Test
    public void testElevatorDescendsFromStart() {
        Elevator elevator = new Elevator();

        elevator.userOutsideRequest(9, Direction.DOWN);
        elevator.userOutsideRequest(3, Direction.DOWN);

        assertEquals(9, elevator.updateFloor());
        assertEquals(3, elevator.updateFloor());
        assertEquals(Direction.DOWN, elevator.getDirection());
    }

    @Test
    public void testElevatorCanAscendThenDescendThenAscend() {
        Elevator elevator = new Elevator();

        elevator.userOutsideRequest(2, Direction.UP);
        elevator.userOutsideRequest(6, Direction.UP);
        elevator.userOutsideRequest(9, Direction.DOWN);
        elevator.userOutsideRequest(3, Direction.DOWN);

        assertEquals(2, elevator.updateFloor());
        assertEquals(6, elevator.updateFloor());
        assertEquals(Direction.UP, elevator.getDirection());

        assertEquals(9, elevator.updateFloor());
        assertEquals(3, elevator.updateFloor());
        assertEquals(Direction.DOWN, elevator.getDirection());

        elevator.userOutsideRequest(3, Direction.UP);

        assertEquals(3, elevator.updateFloor());
        assertEquals(Direction.UP, elevator.getDirection());
    }

    @Test
    public void testElevatorUpdateDuringAscendingWhenRequestGreaterThanCurrentFloor() {
        Elevator elevator = new Elevator();

        elevator.userOutsideRequest(3, Direction.UP);

        // currently ascending
        assertEquals(3, elevator.updateFloor());
        assertEquals(Direction.UP, elevator.getDirection());

        elevator.userInsideRequest(5);
        elevator.userOutsideRequest(4, Direction.UP);

        // cannot add floor 1 to current ascending queue. is less than current floor 3
        assertEquals(0, elevator.getAscendingMissed().size());
        elevator.userOutsideRequest(1, Direction.UP);
        assertEquals(1, elevator.getAscendingMissed().size());;

        assertEquals(4, elevator.updateFloor());
        assertEquals(5, elevator.updateFloor());
        assertEquals(Direction.UP, elevator.getDirection());
    }

    @Test
    public void testElevatorUpdatesDuringDescendingWhenRequestLowerThanCurrentFloor() {
        Elevator elevator = new Elevator();

        elevator.userOutsideRequest(6, Direction.DOWN);

        // currently descending
        assertEquals(6, elevator.updateFloor());
        assertEquals(Direction.DOWN, elevator.getDirection());
        elevator.userInsideRequest(4);

        // cannot add 9 to current descending queue. is greater than current floor 6
        assertEquals(0, elevator.getDescendingMissed().size());
        elevator.userOutsideRequest(9, Direction.DOWN);
        assertEquals(1, elevator.getDescendingMissed().size());

        // queue descending adds floor 2 since its less than 6
        elevator.userOutsideRequest(2, Direction.DOWN);
        assertEquals(4, elevator.updateFloor());
        assertEquals(2, elevator.updateFloor());
    }

}
