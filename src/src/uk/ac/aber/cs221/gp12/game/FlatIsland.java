package uk.ac.aber.cs221.gp12.game;

import java.util.ArrayList;
import java.util.List;

/**
 * This class constructs and describes the features of Flat Island.
 *
 * @author Jason Weller (jaw125), Behrooz Rezvani (ber39)
 * @version 1.0 - Initial development of the class
 */
public class FlatIsland extends Island {
    private List<CrewCard> crewCards = new ArrayList<>();
    private List<Treasure> treasures = new ArrayList<>();

    /**
     * This method gets the crew cards present in Flat Island.
     * @return the crew cards in Flat Island.
     */
    public List<CrewCard> getCrewCards() {
        return crewCards;
    }

    /**
     * This method gets the treasure present in Flat Island.
     * @return the treasure in Flat Island.
     */
    public List<Treasure> getTreasures() {
        return treasures;
    }

    private boolean hasTreasure() {
        if (treasures.size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method allows for treasure to be introduced into Flat Island.
     * @param treasure - the treasure introduced into Flat Island.
     */
    public void addTreasure(Treasure treasure) {
        treasures.add(treasure);
    }

    private Treasure removeTreasure(Treasure treasure) {
        if (treasures.contains(treasure)) {
            treasures.remove(treasure);
            return treasure;
        }

        return null;
    }

    /**
     * This method allows for crew cards to be introduced into Flat Island.
     * @param crewCard - the crew cards introduced into Flat Island.
     */
    public void addCrewCard(CrewCard crewCard) {
        crewCards.add(crewCard);
    }

    private CrewCard removeCrewCard(CrewCard crewCard) {
        if (crewCards.contains(crewCard)) {
            crewCards.remove(crewCard);
            return crewCard;
        }

        return null;
    }

    /**
     * This method allows the game to select the highest valued treasure in Flat
     * Island, so they can hand it to players in specific situations.
     * @return the highest valued treasure in Flat Island.
     */
    public Treasure getHighestValueTreasure() {
        int resultValue = 0;
        int result = 0;
        for(int i = 0; i < treasures.size(); i++) {
            int treasureWorth = treasures.get(i).getTreasureDetail().worth;
            if (treasureWorth > resultValue) {
                resultValue = treasureWorth;
                result = i;
            }
        }
        return treasures.get(result);
    }

    /**
     * This method triggers a change of state for the player being in or out of Flat Island
     * @param player - The player the state is changing for
     */
    @Override
    public void triggered(Player player) {
        if (player.getPlayerState() != PlayerState.AT_FLAT_ISLAND) {
            player.setPlayerState(PlayerState.AT_FLAT_ISLAND);

            for(CrewCard crewCard : crewCards) {
                player.addCrew(removeCrewCard(crewCard));
            }

            if (hasTreasure()) {
                if(player.canHoldTreasure()) {
                    player.addTreasure(removeTreasure(getHighestValueTreasure()));
                }
            }
        }
    }
}
