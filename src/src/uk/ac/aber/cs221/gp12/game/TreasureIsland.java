package uk.ac.aber.cs221.gp12.game;

import java.util.*;

/**
 * This class is responsible for creating and setting up the information for the Treasure Island
 * to be formed.
 *
 * @author Jason Weller (jaw125), Behrooz Rezvani (ber39)
 * @version 1.0 - Initial development of class.
 */
public class TreasureIsland extends Island {
    public Queue<ChanceCard> chanceCards = new LinkedList<>();
    private List<Treasure> treasures = new ArrayList<>();

    /**
     * This method gets the queue of chance cards present on Treasure Island.
     * @return chance cards present in Treasure Island at player state turn.
     */
    public Queue<ChanceCard> getChanceCards() {
        return chanceCards;
    }

    /**
     * This method gets a list of the treasures present on Treasure Island.
     * @return treasure present in Treasure Island at player's turn.
     */
    public List<Treasure> getTreasures() {
        return treasures;
    }

    /**
     * This method adds treasure objects to a list on Treasure Island.
     * @param treasure - Treasure object
     */
    public void addTreasure(Treasure treasure) {
        treasures.add(treasure);
    }

    /**
     * This method adds a chance card to a queue on Treasure Island.
     * @param chanceCard
     */
    public void addChanceCard(ChanceCard chanceCard) {
        chanceCards.add(chanceCard);
    }

    private boolean canTakeChanceCard() {
        return chanceCards.size() > 0;
    }

    /**
     * This method decides whether a player can or can't take a chance card, and if so, which
     * chance card they'll take.
     * @param player taking the chance card.
     */
    public void takeChanceCard(Player player) {
        if (canTakeChanceCard()) {
            /*
            ChanceCard pulledChanceCard = chanceCards.poll();
            if (pulledChanceCard.getChanceCardDetail().playCard(player)) {
                chanceCards.add(pulledChanceCard); // add back into the bottom`
            }

            */
            ChanceCard chance = new ChanceCard(ChanceCardDetail.ID1);
            ChanceCard pulledChanceCard = chance;
            if (pulledChanceCard.getChanceCardDetail().playCard(player)) {
                chanceCards.add(pulledChanceCard); // add back into the bottom`
            }
        }
    }

    /**
     * This method gets a list of all types of treasure on the island,
     * used for players deciding what treasure to take
     * @return the value/type of treasure they're taking
     */
    //Get list of all types of treasure on the island, used for players deciding what treasure to take
    public List<TreasureDetail> getTreasureTypes() {
        List<TreasureDetail> treasureTypes = new ArrayList<>();

        for (Treasure t : treasures) {
            if (!treasureTypes.contains(t.getTreasureDetail())) {
                treasureTypes.add(t.getTreasureDetail());
            }
        }

        return treasureTypes;
    }

    /**
     * This method decides what type of treasure a player is taking. This also affects the value of the
     * treasure the player will have in its ship.
     * @param treasureDetail - kind of treasure in the island.
     * @return the kind of treasure the player gets.
     */
    public Treasure getTreasureOfType(TreasureDetail treasureDetail) {
        for (Treasure treasure : treasures) {
            if (treasure.getTreasureDetail() == treasureDetail) {
                return treasure;
            }
        }

        return null;
    }

    /**
     * This method allow the player to take treasure from Treasure Island.
     * @param treasureDetail - The treasure's features.
     * @return the kind of treasure to take.
     */
    public Treasure takeTreasure(TreasureDetail treasureDetail) {
        Treasure treasure = getTreasureOfType(treasureDetail);
        treasures.remove(treasure);
        return treasure;
    }

    /**
     * This method checks whether there's any treasure on Treasure Island.
     * @return true if there is treasure on the island, false if there isn't.
     */
    public boolean hasTreasure() {
        if (treasures.size() > 0)
            return true;
        else
            return false;
    }

    /**
     * This method triggers a change of state for the player
     * @param player - The player the state is changing for
     */
    @Override
    public void triggered(Player player) {
        if (player.getPlayerState() != PlayerState.AT_TREASURE_ISLAND) {
            player.setPlayerState(PlayerState.AT_TREASURE_ISLAND);
            takeChanceCard(player);
        }
    }
}
