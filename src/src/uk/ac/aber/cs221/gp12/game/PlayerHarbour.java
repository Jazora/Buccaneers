package uk.ac.aber.cs221.gp12.game;

import java.util.ArrayList;
import java.util.List;

/**
 * This class sets up the Home Port for each player. It also makes ways for the player
 * to store treasure once the player gets to port.
 *
 * @author Jason Weller (jaw125), Behrooz Rezvani (ber39)
 * @version 1.0 - Initial development of the class
 */
public class PlayerHarbour extends Harbour {
    Player owningPlayer;
    List<Treasure> treasureAtSafeZone = new ArrayList<>();

    /**
     * This method gets the player's information of their harbour.
     * @param harbourDetail - the details of the harbour.
     */
    public PlayerHarbour(HarbourDetail harbourDetail) {
        super(harbourDetail);
    }

    private void addTreasureToSafeZone(Treasure treasure) {
        treasureAtSafeZone.add(treasure);
    }

    /**
     * This method is in charge of adding the treasure into the player's harbour.
     * @param treasure - the piece of treasure being added to the harbour
     */
    @Override
    public void addTreasure(Treasure treasure) {
        super.addTreasure(treasure);
        saveTreasures();

        int value = 0;
        for(Treasure t : treasures) {
            value += t.getTreasureDetail().worth;
        }

        for(Treasure t : treasureAtSafeZone) {
            value += t.getTreasureDetail().worth;
        }
        //win the game
        if (value >= 20) {
            Main.getGame().setupEndGameState(owningPlayer);
        }
    }

    /**
     * This method is responsible for safely storing the treasure each player
     * brings back to port. This is the treasure taken into account for the game
     * to decide which player is winning.
     */
    public void saveTreasures() {
        int diamond = 0;
        int BARREL_OF_RUM = 0;
        int PEARL = 0;
        int GOLD_BAR = 0;
        int RUBY = 0;
        for(Treasure t : treasures){
            if (t.getTreasureDetail() == TreasureDetail.DIAMOND)
                diamond++;
            else if (t.getTreasureDetail() == TreasureDetail.BARREL_OF_RUM)
                BARREL_OF_RUM++;
            else if (t.getTreasureDetail() == TreasureDetail.PEARL)
                PEARL++;
            else if (t.getTreasureDetail() == TreasureDetail.GOLD_BAR)
                GOLD_BAR++;
            else if (t.getTreasureDetail() == TreasureDetail.RUBY)
                RUBY++;
        }
        if (diamond >= 3) {
            for (int i = 0; i < 3; i++) {
                addTreasureToSafeZone(takeTreasure(TreasureDetail.DIAMOND));
            }
        }
        if (BARREL_OF_RUM >= 3) {
            for (int i = 0; i < 3; i++) {
                addTreasureToSafeZone(takeTreasure(TreasureDetail.BARREL_OF_RUM));
            }
        }
        if (PEARL >= 3) {
            for (int i = 0; i < 3; i++) {
                addTreasureToSafeZone(takeTreasure(TreasureDetail.PEARL));
            }
        }
        if (GOLD_BAR >= 3) {
            for (int i = 0; i < 3; i++) {
                addTreasureToSafeZone(takeTreasure(TreasureDetail.GOLD_BAR));
            }
        }
        if (RUBY >= 3) {
            for (int i = 0; i < 3; i++) {
                addTreasureToSafeZone(takeTreasure(TreasureDetail.RUBY));
            }
        }
    }

    /**
     * This method is responsible for handling the trade logic and functioning within
     * player harbours.
     * @param player - The player involved in this trades.
     */
    @Override
    public void trade(Player player) {
        if (!player.hasTreasureOnBoard()) {
            return;
        }

        List<String> options = new ArrayList<>();

        if (player.hasTreasureOnBoard()) {
            for(Treasure t : treasures){
                if (t.getTreasureDetail() == TreasureDetail.DIAMOND)
                    options.add("Diamond");
                else if (t.getTreasureDetail() == TreasureDetail.BARREL_OF_RUM)
                    options.add("Rum");
                else if (t.getTreasureDetail() == TreasureDetail.PEARL)
                    options.add("Pearl");
                else if (t.getTreasureDetail() == TreasureDetail.GOLD_BAR)
                    options.add("Gold Bar");
                else if (t.getTreasureDetail() == TreasureDetail.RUBY)
                    options.add("Ruby");
            }
        }

        String result = Main.getUiManager().makePopup(player.getName(), "Select which treasure to deposit:", options);

        switch (result) {
            case "Diamond":
                addTreasure(player.removeTreasure(TreasureDetail.DIAMOND));
                break;
            case "Rum":
                addTreasure(player.removeTreasure(TreasureDetail.BARREL_OF_RUM));
                break;
            case "Pearl":
                addTreasure(player.removeTreasure(TreasureDetail.PEARL));
                break;
            case "Gold Bar":
                addTreasure(player.removeTreasure(TreasureDetail.GOLD_BAR));
                break;
            case "Ruby":
                addTreasure(player.removeTreasure(TreasureDetail.RUBY));
                break;
        }
    }
}