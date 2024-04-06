package core_algorithms;

import problems.Game;

public class Minimax<S, A> {
    private final Game<S, A> game;
    private final boolean pruning;
    private final int maxDepth;

    public record Best<A>(int value, A action) {
    }

    public Minimax(Game<S, A> game, boolean pruning, int maxDepth) {
        this.game = game;
        this.pruning = pruning;
        this.maxDepth = maxDepth;
    }

    public A minimaxSearch(S state) {
        Best<A> max = maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        return max.action();
    }

    public Best<A> maxValue(S state, int alpha, int beta, int depth) {
        if (depth >= maxDepth || game.isTerminal(state)) {
            return new Best<>(game.utility(state), null);
        }

        int maxValue = Integer.MIN_VALUE;
        A maxAction = null;
        for (A action : game.actions(state)) {
            S newState = game.execute(action, state);
            Best<A> min = minValue(newState, alpha, beta, depth + 1);
            if (min.value() > maxValue) {
                maxValue = min.value();
                maxAction = action;
            } else if (min.value() == maxValue && Math.random() < 0.5) {
                maxAction = action;
            }
            game.undo(action, newState);

            if (pruning && maxValue >= beta) {
                return new Best<>(maxValue, maxAction);
            }

            alpha = Math.max(alpha, maxValue);
        }

        return new Best<>(maxValue, maxAction);
    }

    public Best<A> minValue(S state, int alpha, int beta, int depth) {
        if (depth >= maxDepth || game.isTerminal(state)) {
            return new Best<>(game.utility(state), null);
        }

        int minValue = Integer.MAX_VALUE;
        A minAction = null;
        for (A action : game.actions(state)) {
            S newState = game.execute(action, state);
            Best<A> max = maxValue(newState, alpha, beta, depth + 1);
            if (max.value() < minValue) {
                minValue = max.value();
                minAction = action;
            } else if (max.value() == minValue && Math.random() < 0.5) {
                minAction = action;
            }
            game.undo(action, newState);

            if (pruning && minValue <= alpha) {
                return new Best<>(minValue, minAction);
            }

            beta = Math.min(beta, minValue);
        }

        return new Best<>(minValue, minAction);
    }
}


