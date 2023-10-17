package uk.ac.aber.cs221.gp12.game;

/**
 * Player state class
 * @author Jason Weller (jaw125), Behrooz Rezvani (ber39)
 * @version 1.0 - Initial development of enum class.
 */
public enum PlayerState {
    AT_HARBOUR(true, true, true, false, true),
    AT_PIRATE_ISLAND(true, true, false, true, true),
    AT_TREASURE_ISLAND(true, true, false, false, true),
    AT_FLAT_ISLAND(true, true, false, true, true),
    AT_MUDBAY(true, true, false, true, true),
    AT_CLIFFCREEK(true, true, false, true, true),
    AT_ANCHORBAY(true, true, false, true, true),
    AT_SEA(true, true, false, true, true),
    LOST_FIGHT(true, true, false, false, false);

    public final boolean canMove, canTurn, canTrade, getAttacked, canAttack;
    PlayerState(boolean canMove, boolean canTurn, boolean canTrade, boolean getAttacked, boolean canAttack) {
        this.canMove = canMove;
        this.canTurn = canTurn;
        this.canTrade = canTrade;
        this.getAttacked = getAttacked;
        this.canAttack = canAttack;
    }
}
