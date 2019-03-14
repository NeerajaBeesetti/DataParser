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

    public boolean containsAlreadyExistingState(String stateToCheck) {
        boolean out = false;
        for (int i = 0; i < states.size(); i++) {
           if (states.get(i).equals(stateToCheck)) {
               out = true;
           }
        }
        return out;
    }

    public void add(State s) {
        states.add(s);
    }
}
