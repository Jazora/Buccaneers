package uk.ac.aber.cs221.gp12.game;

import java.util.ArrayList;
import java.util.List;

/**
 * This class constructs and describes the features of a Harbour.
 *
 * @author Jason Weller (jaw125), Behrooz Rezvani (ber39)
 * @version 1.0 - Initial development of class
 */
public class Harbour extends GameObject {
    HarbourDetail harbourDetail;
    protected List<Treasure> treasures;
    protected List<CrewCard> crewCards;

    /**
     * This method checks whether a position in the game is passable or not.
     * @return true if passable, false if it isn't.
     */
    @Override
    public boolean isPassable() {
        return true;
    }

    /**
     * This method describes how to construct a Harbour object.
     * @param harbourDetail - features of harbour.
     */
    public Harbour(HarbourDetail harbourDetail) {
        this.harbourDetail = harbourDetail;
        this.treasures = new ArrayList<>();
        this.crewCards = new ArrayList<>();
    }

    /**
     * This method gets the crew cards information present in a harbour.
     * @return the info of crew cards.
     */
    public List<CrewCard> getCrewCards() {
        return crewCards;
    }

    /**
     * This method will add crew cards into harbours because of trades, chance cards
     * or some other action.
     * @param crewCard - the crew card being added into the harbour.
     */
    public void addCrew(CrewCard crewCard) {
        crewCards.add(crewCard);
    }

    /**
     * This method will remove crew cards from harbours because of trades, chance cards
     * or some other action.
     * @param crewCard - the crew card being removed from the harbour.
     */
    public void removeCrew(CrewCard crewCard) {
        if (crewCards.contains(crewCard)) {
            crewCards.add(crewCard);
        }
    }

    /**
     * This method triggers a change of state for the player being in or out of a harbour.
     * @param player - The player the state is changing for
     */
    @Override
    public void triggered(Player player) {
        player.setPlayerState(PlayerState.AT_HARBOUR);
    }

    /**
     * This method gets the details from a harbour.
     * @return the details of the harbour they're getting.
     */
    public HarbourDetail getHarbourDetail() {
        return harbourDetail;
    }

    /**
     * This method gets the list of treasures present at the harbour.
     * @return the treasure in the harbour.
     */
    public List<Treasure> getTreasures() {
        return treasures;
    }

    /**
     * This adds treasure to the harbour.
     * @param treasure - the piece of treasure being added to the harbour
     */
    public void addTreasure(Treasure treasure) {
        treasures.add(treasure);
    }

    /**
     * This method removes treasures from the harbour.
     * @param treasure - The piece of treasure being removed from the harbour.
     * @return the amount of treasure left in the harbour.
     */
    public Treasure removeTreasure(Treasure treasure) {
        if (treasures.contains(treasure)) {
            treasures.remove(treasure);
            return treasure;
        }
        return null;
    }

    /**
     * This allows the harbour to get the treasure of a specific type.
     * @param treasureDetail - The details of the getting into the treasure.
     * @return the treasure they're getting the details from.
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
     * This method allows the harbour to take the treasure into the harbour.
     * @param treasureDetail - The details of the treasure the class is getting.
     * @return the treasure the harbour has taken.
     */
    public Treasure takeTreasure(TreasureDetail treasureDetail) {
        Treasure treasure = getTreasureOfType(treasureDetail);
        return treasure;
    }

    /**
     * This method contains the logic to check whether a harbour can or can't trade,
     * and if they can, have the logic to make trading possible.
     * @param player - the player making the trade.
     * @param offeredTreasures - the treasures offered in the trade.
     * @param offeredCrewCards - the crew cards offered in the trade.
     * @param requestedTreasures - the treasures requested in the trade.
     * @param requestedCrewCards - the crew cards requested in the trade.
     * @return the final outcome in terms of crew cards and treasure after the trade has been made.
     */
    private Boolean attemptTrade(Player player, List<Treasure> offeredTreasures, List<CrewCard> offeredCrewCards,
                                 List<Treasure> requestedTreasures, List<CrewCard> requestedCrewCards) {
        int value = 0;

        for (Treasure t : offeredTreasures) {
            value += t.getTreasureDetail().worth;
        }

        for (CrewCard c : offeredCrewCards) {
            value += c.getCrewStrength();
        }

        for (Treasure t : requestedTreasures) {
            value -= t.getTreasureDetail().worth;
        }

        for (CrewCard c : requestedCrewCards) {
            value += c.getCrewStrength();
        }

        if (value == 0) {
            for (Treasure offered : offeredTreasures) {
                addTreasure(player.removeTreasure(offered.getTreasureDetail()));
            }
            for (Treasure requested : requestedTreasures) {
                player.addTreasure(removeTreasure(requested));
            }

            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This is the class responsible for all the logic behind the trading in harbours, the trade
     * will include both treasure and crew cards. The player will have a choice between the 2.
     * @param player - the player involved in the trade in the harbour.
     */
    public void trade(Player player) {
        List<Treasure> offeredTreasure = new ArrayList<>();
        List<CrewCard> offeredCrew = new ArrayList<>();
        while(true) {
            List<String> options = new ArrayList<>();

            for(Treasure treasure : player.getTreasures()) {
                if (!offeredTreasure.contains(treasure)) {
                    options.add(treasure.getTreasureDetail().name);
                }
            }

            for(CrewCard crewCard : player.getCrews()) {
                if (!offeredCrew.contains(crewCard)) {
                    options.add(crewCard.getName());
                }
            }

            if (options.size() == 0) {
                break;
            }

            options.add("Confirm");

            String result = Main.getUiManager().makePopup(player.getName(), "Select what items to offer:", options);

            for(Treasure treasure : player.getTreasures()) {
                if (result.equals(treasure.getTreasureDetail().name)) {
                    if (!offeredTreasure.contains(treasure)) {
                        offeredTreasure.add(treasure);
                        break;
                    }
                }
            }

            for(CrewCard crewCard : player.getCrews()) {
                if (result.equals(crewCard.getName())) {
                    if (!offeredCrew.contains(crewCard)) {
                        offeredCrew.add(crewCard);
                        break;
                    }
                }
            }

            if (result.equals("Confirm")) {
                break;
            }
        }

        List<Treasure> requestedTreasure = new ArrayList<>();
        List<CrewCard> requestedCrew = new ArrayList<>();

        while(true) {
            List<String> options = new ArrayList<>();

            for(Treasure treasure : treasures) {
                if (!requestedTreasure.contains(treasure)) {
                    options.add(treasure.getTreasureDetail().name);
                }
            }

            for(CrewCard crewCard : crewCards) {
                if (!requestedCrew.contains(crewCard)) {
                    options.add(crewCard.getName());
                }
            }

            if (options.size() == 0) {
                break;
            }

            options.add("Confirm");

            String result = Main.getUiManager().makePopup(player.getName(), "Select what items to offer:", options);

            for(Treasure treasure : treasures) {
                if (result.equals(treasure.getTreasureDetail().name)) {
                    if (!requestedTreasure.contains(treasure)) {
                        requestedTreasure.add(treasure);
                        break;
                    }
                }
            }

            for(CrewCard crewCard : crewCards) {
                if (result.equals(crewCard.getName())) {
                    if (!requestedCrew.contains(crewCard)) {
                        requestedCrew.add(crewCard);
                        break;
                    }
                }
            }

            if (result.equals("Confirm")) {
                break;
            }
        }

        if (attemptTrade(player, offeredTreasure, offeredCrew, requestedTreasure, requestedCrew)) {
            Main.getUiManager().makePopup("Trade successful!");
        }
        else {
            Main.getUiManager().makePopup("Trade failed!");
        }
    }
}
