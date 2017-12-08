package com.ultimanager.models;

/**
 * Enum class for the result of the throw
 */

public enum ThrowResult {

    COMPLETION,
    TURNOVER,
    SCORE,
    THEY_SCORE;

    public String humanName() {
        switch (this) {
            case COMPLETION:
                return "Completion";
            case TURNOVER:
                return "Turnover";
            case SCORE:
                return "Score";
            case THEY_SCORE:
                return "Opponent scored";
            default:
                return "";
        }
    }
}
