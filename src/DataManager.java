import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private List<State> states;

    public DataManager() {
        states = new ArrayList<>();
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public State getAlreadyExistingState(String stateToCheck) {
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i).getName().equals(stateToCheck)) {
                return states.get(i);
            }
        }
        return null;
    }

    public void add(State s) {
        states.add(s);
    }
}
