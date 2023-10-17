package uk.ac.aber.cs221.gp12.game;

import java.util.ArrayList;
import java.util.List;

/**
 * This enum class constructs and describes the features of all Chance cards.
 *
 * @author Jason Weller (jaw125), Behrooz Rezvani (ber39)
 * @version 1.0 - Initial development of class
 */
public enum ChanceCardDetail {
    ID1("Your ship is blown 5 leagues (5 squares) off the coast of the Treasure Island. If your crew total is 3 or less, take 4 crew cards from the Pirate Island. If the square you are blown to is already occupied, ATTACK!!!"){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 1", ID1.cardDescription);
            IslandBoundary shipLocation = (IslandBoundary) Main.getGame().getMapObject(player.getX(), player.getY());
            player.moveInDirection(5, shipLocation.getDirection());
            if (player.getCrews().size() < 4){
                player.addCrewCardToPlayer(4, player);
            }

            return true;
        }
    },
    ID2("Present this card to any player who must then give you 3 crew cards. This card must be used at once and then replaced in the Chance Card pack."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 2", ID2.cardDescription);
            List<String> playerNames = new ArrayList<>();
            for (Player p : Main.getGame().getPlayers())
                playerNames.add(p.getName());
            String targetPlayerName = Main.getUiManager().makePopup("Chance Card 2",ID2.cardDescription, playerNames);
            Player targetPlayer = Main.getGame().searchPlayer(targetPlayerName);
            for (int i = 0; i < 3; i++) {
                CrewCard crewCard = targetPlayer.pickCrewCard();
                if (crewCard != null) {
                    targetPlayer.removeCrew(crewCard);
                    player.addCrew(crewCard);
                }
            }
            return true;
        }
    },
    ID3("You are blown to Mud Bay. If your crew total is 3 or less, take 4 crew cards from Pirate Island."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 3", ID3.cardDescription);
            player.teleport(Main.getGame().getMudbay().getX(), Main.getGame().getMudbay().getY());
            if (player.getCrews().size() < 4){
                player.addCrewCardToPlayer(4, player);
            }
            return true;
        }
    },
    ID4("You are blown to Cliff Creek. If your crew total is 3 or less, take 4 crew cards from Pirate Island."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 4", ID4.cardDescription);
            player.teleport(Main.getGame().getCliffCreek().getX(), Main.getGame().getCliffCreek().getY());
            if (player.getCrews().size() < 4){
                player.addCrewCardToPlayer(4, player);
            }
            return true;
        }
    },
    ID5("You are blown to your Home Port. If your crew total is 3 or less, take 4 crew cards from Pirate Island."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 5", ID5.cardDescription);
            HarbourDetail harbourDetail = player.getHomePort().getHarbourDetail();
            player.teleport(harbourDetail.x, harbourDetail.y);
            if (player.getCrews().size() < 4){
                player.addCrewCardToPlayer(4, player);
            }
            return true;
        }
    },
    ID6("You are blown to Amsterdam. If your crew total is 3 or less, take 4 crew cards from Pirate Island."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 6", ID6.cardDescription);
            player.teleport(HarbourDetail.AMSTERDAM.x, HarbourDetail.AMSTERDAM.y);
            return true;
        }
    },
    ID7("One treasure from your ship or 2 crew cards from your hand are lost and washed overboard to the nearest ship. If 2 ships are equidistant from yours you may ignore this instruction."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 7", ID7.cardDescription);
            Player closestPlayer = player.closestPlayer(player, Main.getGame().getPlayers());
            if(closestPlayer != null) {
                if(player.hasTreasureOnBoard()){
                    Treasure treasure = player.getLowestValueTreasure();
                    closestPlayer.addTreasure(treasure);
                    player.removeTreasure(treasure.getTreasureDetail());
                }
                else {
                    for (int i = 0; i < 2; i++) {
                        if (player.hasCrewOnBoard()) {
                            CrewCard crewCard = player.getLowestValueCrew();
                            closestPlayer.addCrew(crewCard);
                            player.removeCrew(crewCard);
                        }
                    }
                }
            }
            return true;
        }
    },
    ID8("One treasure from your ship, or 2 crew cards from your hand are lost and washed overboard to Flat Island."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 8", ID8.cardDescription);
            if(player.hasTreasureOnBoard()){
                Treasure treasure = player.getLowestValueTreasure();
                Main.getGame().getFlatIsland().addTreasure(treasure);
                player.removeTreasure(treasure.getTreasureDetail());
            }
            else {
                for (int i = 0; i < 2; i++) {
                    if (player.hasCrewOnBoard()) {
                        CrewCard crewCard = player.getLowestValueCrew();
                        Main.getGame().getFlatIsland().addCrewCard(crewCard);
                        player.removeCrew(crewCard);
                    }
                }
            }
            return true;
        }
    },
    ID9("Your most valuable treasure on board, or if no treasure, the best crew card in your hand is washed overboard to Flat Island."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 9", ID9.cardDescription);
            if(player.hasTreasureOnBoard()){
                Treasure treasure = player.getHighestValueTreasure();
                Main.getGame().getFlatIsland().addTreasure(treasure);
                player.removeTreasure(treasure.getTreasureDetail());
            }
            else {
                CrewCard crewCard = player.getHighestValueCrew();
                Main.getGame().getFlatIsland().addCrewCard(crewCard);
                player.removeCrew(crewCard);
            }
            return true;
        }
    },
    ID10("The best crew card in your hand deserts for Pirate Island. The card must be placed there immediately."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 10", ID10.cardDescription);
            CrewCard crewCard = player.getHighestValueCrew();
            Main.getGame().getPirateIsland().addCrewCard(crewCard);
            player.removeCrew(crewCard);
            return true;
        }
    },
    ID11("Take treasure up to 5 in total value, or 2 crew cards from Pirate Island."){
        @Override
        public boolean playCard(Player player) {
            boolean canHoldTreasure = player.canHoldTreasure();
            boolean pirateIslandHasCard = Main.getGame().getPirateIsland().hasCrewCard();
            boolean treasureIslandHasTreasure = Main.getGame().getTreasureIsland().hasTreasure();
            List<String> options = new ArrayList<>();
            int maxValue = 5;

            if(canHoldTreasure && treasureIslandHasTreasure){
                options.add("Treasure");
            } else if (pirateIslandHasCard) {
                options.add("CrewCard");
            }
            if (options.size() < 1) {
                Main.getUiManager().makePopup("You could not play this card!");
            }
            switch (Main.getUiManager().makePopup("Chance Card 11", ID11.cardDescription, options)) {
                case "Treasure":
                    do {
                        maxValue = player.pickTreasureUpTo(maxValue, player);
                    }while (Main.getGame().getTreasureIsland().hasTreasure() && player.canHoldTreasure() &&maxValue > 0);
                    break;
                case "CrewCard":
                    player.addCrewCardToPlayer(2, player);
                    break;
            }
            return true;
        }
    },
    ID12("Take treasure up to 4 in total value, or 2 crew cards from Pirate Island."){
        @Override
        public boolean playCard(Player player) {

            boolean canHoldTreasure = player.canHoldTreasure();
            boolean pirateIslandHasCard = Main.getGame().getPirateIsland().hasCrewCard();
            boolean treasureIslandHasTreasure = Main.getGame().getTreasureIsland().hasTreasure();
            List<String> options = new ArrayList<>();
            int maxValue = 4;

            if(canHoldTreasure && treasureIslandHasTreasure){
                options.add("Treasure");
            } else if (pirateIslandHasCard) {
                options.add("CrewCard");
            }
            if (options.size() < 1) {
                Main.getUiManager().makePopup("You could not play this card!");
            }
            switch (Main.getUiManager().makePopup("Chance Card 12", ID12.cardDescription, options)) {
                case "Treasure":
                    do {
                        maxValue = player.pickTreasureUpTo(maxValue, player);
                    }while (Main.getGame().getTreasureIsland().hasTreasure() && player.canHoldTreasure() &&maxValue > 0);
                    break;
                case "CrewCard":
                    player.addCrewCardToPlayer(2, player);
                    break;
            }
            return true;
        }
    },
    ID13("Take treasure up to 5 in total value, or 2 crew cards from Pirate Island."){
        @Override
        public boolean playCard(Player player) {
            boolean canHoldTreasure = player.canHoldTreasure();
            boolean pirateIslandHasCard = Main.getGame().getPirateIsland().hasCrewCard();
            boolean treasureIslandHasTreasure = Main.getGame().getTreasureIsland().hasTreasure();
            List<String> options = new ArrayList<>();
            int maxValue = 5;

            if(canHoldTreasure && treasureIslandHasTreasure){
                options.add("Treasure");
            } else if (pirateIslandHasCard) {
                options.add("CrewCard");
            }
            if (options.size() < 1) {
                Main.getUiManager().makePopup("You could not play this card!");
            }
            switch (Main.getUiManager().makePopup("Chance Card 13", ID13.cardDescription, options)) {
                case "Treasure":
                    do {
                        maxValue = player.pickTreasureUpTo(maxValue, player);
                    }while (Main.getGame().getTreasureIsland().hasTreasure() && player.canHoldTreasure() &&maxValue > 0);
                    break;
                case "CrewCard":
                    player.addCrewCardToPlayer(2, player);
                    break;
            }
            return true;
        }
    },
    ID14("Take treasure up to 7 in total value, or 3 crew cards from Pirate Island."){
        @Override
        public boolean playCard(Player player) {
            boolean canHoldTreasure = player.canHoldTreasure();
            boolean pirateIslandHasCard = Main.getGame().getPirateIsland().hasCrewCard();
            boolean treasureIslandHasTreasure = Main.getGame().getTreasureIsland().hasTreasure();
            List<String> options = new ArrayList<>();
            int maxValue = 7;

            if(canHoldTreasure && treasureIslandHasTreasure){
                options.add("Treasure");
            } else if (pirateIslandHasCard) {
                options.add("CrewCard");
            }
            if (options.size() < 1) {
                Main.getUiManager().makePopup("You could not play this card!");
            }
            switch (Main.getUiManager().makePopup("Chance Card 14", ID14.cardDescription, options)) {
                case "Treasure":
                    do {
                        maxValue = player.pickTreasureUpTo(maxValue, player);
                    }while (Main.getGame().getTreasureIsland().hasTreasure() && player.canHoldTreasure() &&maxValue > 0);
                    break;
                case "CrewCard":
                    player.addCrewCardToPlayer(2, player);
                    break;
            }
            return true;
        }
    },
    ID15("Take 2 crew cards from Pirate Island."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 15", ID15.cardDescription);
            player.addCrewCardToPlayer(2, player);
            return true;
        }
    },
    ID16("Take Treasure up to 7 in total value and reduce your ship's crew to 10, by taking crew cards from your hand and placing them on Pirate Island."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 16", ID16.cardDescription);
            if (player.canHoldTreasure())
                player.pickTreasureUpTo(7, player);
            while (player.getMoveSpeed() > 10){
                player.removeCrewCardFromPlayerToPirateIsland(player);
            }
            return true;
        }
    },
    ID17("Take Treasure up to 6 in total value, reduce your ship's crew to 11 by taking crew cards from your hand and placing them on Pirate Island."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 17", ID17.cardDescription);
            if (player.canHoldTreasure())
                player.pickTreasureUpTo(7, player);
            while (player.getMoveSpeed() > 11){
                player.removeCrewCardFromPlayerToPirateIsland(player);
            }
            return true;
        }
    },
    ID18("Take treasure up to 4 in total value, or 2 crew cards from Pirate Island."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 18", ID18.cardDescription);
            if (player.canHoldTreasure())
                player.pickTreasureUpTo(4, player);
            else
                player.addCrewCardToPlayer(2, player);
            return true;
        }
    },
    ID19("Exchange all crew cards in your hand as far as possible for the same number of crew cards from Pirate Island."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 19", ID19.cardDescription);
            int i = 0;
            while(player.hasCrewOnBoard()){
                player.removeCrewCardFromPlayerToPirateIsland(player);
                i++;
            }
            if (i > 0) {
                player.addCrewCardToPlayer(i, player);
            }
            return true;
        }
    },
    ID20("If the ship of another player is anchored at Treasure Island, exchange 2 of your crew cards with that player. Both turn your cards face down and take 2 cards from each others hand without looking at them. If there is no other player at Treasure Island, place 2 of your crew cards on Pirate Island."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 20", ID20.cardDescription);
            List<Player> players = new ArrayList<>();
            for (Player p : Main.getGame().getPlayers()) {
                if (p.getPlayerState() == PlayerState.AT_TREASURE_ISLAND) {
                    players.add(p);
                }
            }
            if (players.size() > 0) {
                ArrayList<String> options = new ArrayList<>();
                for(Player p : players){
                    options.add(p.getName());
                }
                String playerNameToExchange = Main.getUiManager().makePopup("Player", "Pick player to exchange cards with", options);
                ArrayList<CrewCard> crewCardsToExchange1 = new ArrayList<>();
                ArrayList<CrewCard> crewCardsToExchange2 = new ArrayList<>();
                Player playerToExchange = Main.getGame().searchPlayer(playerNameToExchange);
                for (int i = 0; i < 2; i++) {
                    CrewCard tempCard = player.returnRandCrew(player);
                    if(tempCard != null){
                        crewCardsToExchange1.add(tempCard);
                        player.removeCrew(tempCard);
                    }
                    tempCard = player.returnRandCrew(playerToExchange);
                    if(tempCard != null) {
                        crewCardsToExchange2.add(tempCard);
                        playerToExchange.removeCrew(tempCard);
                    }
                }
                for (CrewCard c : crewCardsToExchange1)
                    playerToExchange.addCrew(c);
                for (CrewCard c2 : crewCardsToExchange2)
                    player.addCrew(c2);
            }
            else {
                for (int i = 0; i < 2; i++)
                    player.removeCrewCardFromPlayerToPirateIsland(player, player.randomCrew());
            }
            return true;
        }
    },
    ID21("[Long John Silver] <keep> When you arrive at a port where there are crew for sale, you may exchange Long John for up to 5 crew in value. If you land at a Port where Long John has been left, you may take him on payment of one treasure to the Port. Once Long John has been played, he is not returned to the pack."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 21", ID21.cardDescription);
            if (player.hasChanceCard()) {
                Main.getUiManager().makePopup("You already have a chance card");
                return true;
            }
            ChanceCard chanceCard = new ChanceCard(ID21);
            player.addChanceCard(chanceCard);
            return false;
        }
    },
    ID22("[Yellow Fever!] An epidemic of Yellow fever strikes all ships and reduces the number of crew. Every player with more than 7 crew cards in his hand and/or at his Home Port must bury the surplus crew cards at once on Pirate Island. He is at liberty to choose which of his cards shall be buried."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 22", ID22.cardDescription);
            ArrayList<Player> players = new ArrayList<>();
            for (Player p : Main.getGame().getPlayers())
                if(p.getCrews().size() > 7)
                    players.add(p);
            for (Player p : players) {
                while(p.getCrews().size() > 7)
                    player.removeCrewCardFromPlayerToPirateIsland(p);
            }
            return true;
        }
    },
    ID23("[Doubloons] <keep> Trading value 4. This card may be traded for crew or treasure at any port you visit."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 23", ID23.cardDescription);
            if (player.hasChanceCard()) {
                Main.getUiManager().makePopup("You already have a chance card");
                return true;
            }
            ChanceCard chanceCard = new ChanceCard(ID23);
            player.addChanceCard(chanceCard);
            return false;
        }
    },
    ID24("[Pieces of Eight] <keep> Trading value 5. This card may be traded for crew or treasure at any port you visit."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 24", ID24.cardDescription);
            if (player.hasChanceCard()) {
                Main.getUiManager().makePopup("You already have a chance card");
                return true;
            }
            ChanceCard chanceCard = new ChanceCard(ID24);
            player.addChanceCard(chanceCard);
            return false;
        }
    },
    ID25("[Kidd's Chart] <keep> You may sail to the far side of the Pirate Island, on to the square marked with an anchor. Land this chart there and take treasure up to 7 in total value from Treasure Island."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 25", ID25.cardDescription);
            if (player.hasChanceCard()) {
                Main.getUiManager().makePopup("You already have a chance card");
                return true;
            }
            ChanceCard chanceCard = new ChanceCard(ID25);
            player.addChanceCard(chanceCard);
            return false;
        }
    },
    ID26("[Kidd's Chart] <keep> You may sail to the far side of the Pirate Island, on to the square marked with an anchor. Land this chart there and take treasure up to 7 in total value from Treasure Island."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 26", ID26.cardDescription);
            if (player.hasChanceCard()) {
                Main.getUiManager().makePopup("You already have a chance card");
                return true;
            }
            ChanceCard chanceCard = new ChanceCard(ID26);
            player.addChanceCard(chanceCard);
            return false;
        }
    },
    ID27("Take treasure up to 5 in total value, or 3 crew cards from Pirate Island."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 27", ID27.cardDescription);
            if (player.hasChanceCard()) {
                Main.getUiManager().makePopup("You already have a chance card");
                return true;
            }
            ChanceCard chanceCard = new ChanceCard(ID27);
            player.addChanceCard(chanceCard);
            return false;
        }
    },
    ID28("Take 2 crew cards from Pirate Island."){
        @Override
        public boolean playCard(Player player) {
            Main.getUiManager().makePopupChanceCard("Chance Card 28", ID28.cardDescription);
            player.addCrewCardToPlayer(2, player);
            return true;
        }
    };

    public final String cardDescription;

    public boolean playCard(Player player) {
        return true;
    }

    ChanceCardDetail(String cardText){
        this.cardDescription = cardText;
    }
}
