package com.ultimanager.models;


/**
 * Enumeration of the different roles a player can have.
 */
public enum PlayerRole {
    CUTTER,
    HANDLER,
    OTHER;

    public String humanName() {
        switch (this) {
            case CUTTER:
                return "Cutter";
            case HANDLER:
                return "Handler";
            case OTHER:
                return "Other";
            default:
                return "";
        }
    }
}
