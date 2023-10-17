package uk.ac.aber.cs221.gp12.game;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class constructs and describes the features of Pirate Island.
 *
 * @author Jason Weller (jaw125), Behrooz Rezvani (ber39)
 * @version 1.0 - Initial development of the class
 */
public class PirateIsland extends Island {

    private Queue<CrewCard> crewCardDeck = new LinkedList<>();

    /**
     * This gets a queue of crew cards from a crew card deck.
     * @return the queue of crew cards.
     */
    public Queue<CrewCard> getCrewCardDeck() {
        return crewCardDeck;
    }

    /**
     * This method checks whether a player can pull a crew card from Pirate Island or they
     * can't.
     * @return true if they can retrieve them, false if they can't.
     */
    private boolean canPullCrewCard() {
        if (crewCardDeck.size() > 0)
            return true;
        else
            return false;
    }

    /**
     * This method takes the crew cards present in Pirate Island and gives
     * it to the player.
     * @param player - the player taking the crew cards.
     */
    public void givePlayerCrewCard(Player player) {
        if (canPullCrewCard()) {
            player.addCrew(crewCardDeck.poll());
        }
    }

    /**
     * This adds crew cards which are outside of Pirate Island into
     * its queue deck of cards.
     * @param crewCard - The crew card being put into Treasure Island.
     */
    public void addCrewCard(CrewCard crewCard) {
        crewCardDeck.add(crewCard);
    }

    /**
     * This checks whether Pirate Island has or hasn't got any crew cards.
     * @return true if they have crew cards, false if they can't.
     */
    public boolean hasCrewCard() {
        return crewCardDeck.size() > 0;
    }

    /**
     * This method triggers a change of state for the player being in or out of Pirate Island
     * @param player - The player the state is changing for
     */
    @Override
    public void triggered(Player player) {
        if (player.getPlayerState() != PlayerState.AT_PIRATE_ISLAND) {
            player.setPlayerState(PlayerState.AT_PIRATE_ISLAND);

            if (canPullCrewCard()) {
                givePlayerCrewCard(player);
            }
        }
    }
}
