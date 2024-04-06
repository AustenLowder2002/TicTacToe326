package problems;

import java.util.List;

public interface Game<S, A> {
    List<A> actions(S state);
    int utility(S state);
    boolean isTerminal(S state);
    S execute(A action, S state);
    S undo(A action, S state);
}
