package elevatorquestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControlPanel {
    public List<Integer> floors = new ArrayList<Integer>() {{
        add(1);
        add(2);
        add(3);
        add(4);
        add(5);
        add(6);
        add(7);
        add(8);
        add(9);
        add(10);
    }};

    public boolean checkInvalidFloor(int requestedFloor) {
        return !floors.contains(requestedFloor);
    }
}
