package core_algorithms;

import problems.Game;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Minimax<S, A> {
    private final Game<S, A> game;
    private final boolean pruning;
    private final Set<S> visitedStates; // Keep track of visited states

    public record Best<A>(int value, A action) {}

    public Minimax(Game<S, A> game, boolean pruning) {
        this.game = game;
        this.pruning = pruning;
        this.visitedStates = new HashSet<>();
    }

    public A minimaxSearch(S state) {
        Best<A> max = maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (max.action() == null) {
            // If no valid action is found, choose the first available action
            return game.actions(state).get(0);
        }
        System.out.println("Max value: " + max.value());
        return max.action();
    }

    public Best<A> maxValue(S state, int alpha, int beta) {
        if (visitedStates.contains(state)) {
            // Avoid redundant evaluations
            return new Best<>(Integer.MIN_VALUE, null);
        }
        visitedStates.add(state);

        int maxValue = Integer.MIN_VALUE;
        A maxAction = null;
        if (game.isTerminal(state)) {
            maxValue = game.utility(state);
        } else {
            boolean hasValidAction = false; // Flag to track if a valid action is found
            List<A> actions = game.actions(state);
            for (A action : actions) {
                S newState = game.execute(action, state);
                Best<A> min = minValue(newState, alpha, beta);
                if (min.value() > maxValue) {
                    maxValue = min.value();
                    maxAction = action;
                    hasValidAction = true; // Mark that a valid action is found
                }
                if (pruning) {
                    alpha = Math.max(alpha, maxValue);
                    if (beta <= alpha) {
                        System.out.println("Alpha cut-off: " + alpha + ", Beta: " + beta);
                        break; // Beta cut-off
                    }
                }
                game.undo(action, newState);
            }
            if (!hasValidAction) {
                // If no valid action is found, choose the first available action
                maxAction = actions.get(0);
            }
        }
        return new Best<>(maxValue, maxAction);
    }

    public Best<A> minValue(S state, int alpha, int beta) {
        if (visitedStates.contains(state)) {
            // Avoid redundant evaluations
            return new Best<>(Integer.MAX_VALUE, null);
        }
        visitedStates.add(state);

        int minValue = Integer.MAX_VALUE;
        A minAction = null;
        if (game.isTerminal(state)) {
            minValue = game.utility(state);
        } else {
            boolean hasValidAction = false; // Flag to track if a valid action is found
            List<A> actions = game.actions(state);
            for (A action : actions) {
                S newState = game.execute(action, state);
                Best<A> max = maxValue(newState, alpha, beta);
                if (max.value() < minValue) {
                    minValue = max.value();
                    minAction = action;
                    hasValidAction = true; // Mark that a valid action is found
                }
                if (pruning) {
                    beta = Math.min(beta, minValue);
                    if (beta <= alpha) {
                        System.out.println("Beta cut-off: " + beta + ", Alpha: " + alpha);
                        break; // Alpha cut-off
                    }
                }
                game.undo(action, newState);
            }
            if (!hasValidAction) {
                // If no valid action is found, choose the first available action
                minAction = actions.get(0);
            }
        }
        return new Best<>(minValue, minAction);
    }

}
