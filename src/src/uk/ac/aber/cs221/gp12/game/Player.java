package uk.ac.aber.cs221.gp12.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

/**
 * This is the class which holds all the necessary methods and information
 * for a player to be able to play the game.
 *
 * @author Jason Weller (jaw125), Behrooz Rezvani (ber39)
 * @version 1.0 - Initial development of the class.
 */
public class Player {
    private int index;
    private String name;
    private ChanceCard chanceCard = null;
    private List<CrewCard> crews = new ArrayList<>();
    private ArrayList<Treasure> treasures = new ArrayList<>();
    private int x, y;
    private ShipOrientation orientation = ShipOrientation.NORTH;
    private int moveSpeed = 0;
    private int fightingStrength = 0;
    private Harbour homePort;
    PlayerState playerState;

    /**
     * This method is a player's constructor.
     * @param index - the number a player will have throughout the game.
     */
    public Player(int index) {
        this.index = index;
        playerState = PlayerState.AT_HARBOUR;
    }

    /**
     * This gets the player's home port.
     * @return homePort - The player's home port.
     */
    public Harbour getHomePort() {
        return homePort;
    }

    /**
     * This gets the player's state at any point in the game.
     * @return playerState - The player's state.
     */
    public PlayerState getPlayerState() {
        return playerState;
    }

    /**
     * This gets the index of a player.
     * @return index - The player's index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * This sets the new state of a player when an action happens in the game.
     * @param newPlayerState - The state of the player after the action has happened.
     */
    public void setPlayerState(PlayerState newPlayerState) {
        playerState = newPlayerState;
    }

    /**
     * This sets the position of a player at any point in the game.
     * @param x - the position in the x-axis.
     * @param y - the position in the y-axis.
     */
    public void setPosition(int x, int y) {
        this.x = x; this.y = y;

        if (Main.getGame().getMapObject(this.x, this.y) != null) {
            Main.getGame().triggeredMapObject(this, this.x, this.y);
        }
        else {
            setPlayerState(PlayerState.AT_SEA);
        }

        Main.getUiManager().updatePlayerPosition(index, this.x, this.y);
    }

    public void setOrientation(ShipOrientation shipOrientation) {
        orientation = shipOrientation;
        Main.getUiManager().updatePlayerRotation(index, orientation.angle);
    }

    /**
     * This method gets the orientation/direction the ship is facing during the game.
     * @return the orientation the player's ship is facing.
     */
    public ShipOrientation getOrientation() {
        return orientation;
    }

    /**
     * This sets the name of the player.
     * @param name - name of the player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This get the name of the player.
     * @return name - the name the player has been set-up with.
     */
    public String getName() {
        return name;
    }

    /**
     * This adds the crew cards to a player's ship at any point in the game.
     * @param crewCard - The crew card added to the player's ship.
     */

    /**
     * This gets the list of crew cards a player has in its possession at any point in the game.
     * @return crews - The list of crew cards the player has.
     */
    public List<CrewCard> getCrews() {
        return crews;
    }

    /**
     * This gets the arraylist of treasures a player has in its possession at any point in the game.
     * @return treasures - The arraylist of treasures a player has.
     */
    public ArrayList<Treasure> getTreasures() {
        return treasures;
    }

    /**
     * This gets the x-axis position of a player
     * @return x - The x-axis position of the player.
     */
    public int getX() { return x; }

    /**
     * This gets the y-axis position of a player.
     * @return y - The y-axis position of the player.
     */
    public int getY() { return y; }

    /**
     * This sets the harbour a player's ship is in.
     * @param harbour - The harbour the player is in.
     */
    public void setHarbour(Harbour harbour) {
        this.homePort = harbour;
        setPosition(harbour.harbourDetail.x, harbour.harbourDetail.y);
        setOrientation(harbour.harbourDetail.startDirection);
    }

    /**
     * This adjusts the speed of the movement of a player's ship based on its
     * fighting power, i.e. the amount of crew cards they have in their ship.
     * @param crewCard - the crew card added which increases speed.
     * @param added - the added power
     */
    public void updateMoveSpeed(CrewCard crewCard, boolean added) {
        if(added) moveSpeed+= crewCard.getCrewStrength();
        else moveSpeed-= crewCard.getCrewStrength();
    }

    /**
     * This method gets the speed the player will move in. This is based
     * on the crew cards the player has on its ship at the moment of moving.
     * @return the movement speed of the player.
     */
    public int getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * This gets the fighting strength of a crew which determines the winner in an attack.
     * It is based on the amount and value of crew card a player has in its ship.
     * @return fightingStrength - The fighting strength of each player.
     */
    public int getFightingStrength() {
        return fightingStrength;
    }

    /**
     * This updates the fighting strength of a player based on them taking on more crew cards
     * because of trades, chance cards, docking in islands or attacks.
     * @return the updated fight strength of the player.
     */
    private void updateFightingStrength() {
        int black = 0;
        int red = 0;

        for(CrewCard card : crews) {
            if (card.getCrewCardColour() == CrewCardColour.BLACK)
                black += card.getCrewStrength();
            else
                red += card.getCrewStrength();
        }
        fightingStrength = abs(black- red);
    }

    /**
     * This method determines a list of available moves a player has.
     * @return the list of available moves for the player.
     */
    public List<Integer> availableMovePositions(){
        List<Integer> result = new ArrayList<>();

        for (int i = 1; i < moveSpeed; i++) {
            int newX = x + (orientation.xOffset * i);
            int newY = y + (orientation.yOffset * i);

            if (!Main.getGame().validPosition(newX, newY)) {
                break;
            }

            result.add(newX);
            result.add(newY);
        }

        return result;
    }

    /**
     * This sets up the next move of the player.
     */
    //prompts UI to ask where to move
    public void setupMove() {
        Main.getUiManager().getMovement(index, availableMovePositions());
    }

    /**
     * This sets up the move of a player after they lost during an attack.
     */
    public void setupLoseMove() {
        setPlayerState(PlayerState.LOST_FIGHT);
        setupOrientation();
        setupMove();
    }

    /**
     * This is for a player's move based on the available positions index.
     * @param index - the index needed to see the available moves.
     */
    //move based on available positions index
    public void move(int index) {
        List<Integer> positions = availableMovePositions();
        move(positions.get(index * 2), positions.get((index * 2) + 1));
    }

    /**
     * This sets up the logic needed to make players be able to carry out moves during
     * the game.
     * @param x - moves in the x-axis.
     * @param y - moves in the x-axis.
     */
    //Does movement logic
    public void move(int x, int y) {
        int i = 1;
        while(true) {
            int newX = this.x + (orientation.xOffset * i);
            int newY = this.y + (orientation.yOffset * i);

            if (!Main.getGame().validPosition(newX, newY)) {
                break;
            }

            Player foundPlayer = Main.getGame().triggeredPlayer(this, newX, newY);
            if (x == newX && y == newY) {
                setPosition(x, y);
                if (foundPlayer != null) {
                    attack(foundPlayer, false);
                }
                else {
                    setupOrientation();
                }
                break;
            }
            else {
                if (foundPlayer != null) {
                    if (passingPlayerAttack(foundPlayer)) {
                        setPosition(foundPlayer.getX(), foundPlayer.getY());
                        foundPlayer.attack(this, true);
                        break;
                    }
                }
            }

            i++;
        }
    }

    /**
     * This method is responsible for making the player move into a certain direction
     * and for a numbered amount of squares.
     * @param numberOfMoves - the number of squares the player will move in.
     * @param orientation - the direction the player will move in.
     */
    public void moveInDirection(int numberOfMoves, ShipOrientation orientation) {
        teleport(this.x + (orientation.xOffset * numberOfMoves), y + (orientation.yOffset * numberOfMoves));
    }

    /**
     * This method allows players to be teleported based on outcomes of some chance cards.
     * @param x - the x-axis position the player has been teleported to.
     * @param y - the y-axis position the player has been teleported to.
     */
    public void teleport(int x, int y) {
        if (Main.getGame().validPosition(x, y)) {
            setPosition(x, y);

            Player foundPlayer = Main.getGame().triggeredPlayer(this, x, y);
            if (foundPlayer != null) {
                attack(foundPlayer, false);
            }
        }
    }

    private List<ShipOrientation> availableOrientations() {
        List<ShipOrientation> result = new ArrayList<>();

        for (int i = 0; i < ShipOrientation.values().length; i++) {
            if (Main.getGame().validPosition(x + ShipOrientation.values()[i].xOffset, y + ShipOrientation.values()[i].yOffset)) {
                result.add(ShipOrientation.values()[i]);
            }
        }

        return result;
    }

    /**
     * This allows the UI to ask in which position the player wants to be oriented.
     * The action is shown in the UI.
     */
    //prompts UI to ask where to orientate
    public void setupOrientation() {
        List<String> actions = new ArrayList<>();
        List<ShipOrientation> availableRotations = availableOrientations();

        for(ShipOrientation orien : availableRotations) {
            actions.add(orien.name);
        }

        String result = Main.getUiManager().makePopup(name, "Please select a direction:", actions);
        ShipOrientation resultOrientation = ShipOrientation.NORTH; // just incase
        for(ShipOrientation orient : availableRotations) {
            if (orient.name.equals(result)) {
                resultOrientation = orient;
                break;
            }
        }

        setOrientation(resultOrientation);
        if (playerState != PlayerState.LOST_FIGHT) {
            endTurn();
        }
    }
    /**
     * This allows the player to take the highest valued treasure from the player they
     * defeated in an attack.
     */
    public Treasure getHighestValueTreasure() {
        //List<Treasure> defeatedPlayerTreasures = defeatedPlayer.getTreasures();
        hasTreasureOnBoard();
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
        //addTreasure(defeatedPlayer.removeTreasure(defeatedPlayerTreasures.get(result).getTreasureDetail()));
    }

    /**
     * This allows the player to take the highest valued crew card from the player they
     * defeated in an attack.
     */
    public CrewCard getHighestValueCrew() {
        if(hasCrewOnBoard()) {
            int resultValue = 0;
            int result = 0;
            for (int i = 0; i < crews.size(); i++) {
                int crewStrength = crews.get(i).getCrewStrength();
                if (crewStrength > resultValue) {
                    resultValue = crewStrength;
                    result = i;
                }
            }
            return crews.get(result);
        }
        return null;
    }

    /**
     * This method is responsible for shuffling the crew cards and make sure they're in a random order
     * at the moment the player has them.
     * @return a deck of randomly ordered crew cards.
     */
    public CrewCard randomCrew() {
        if(hasCrewOnBoard()){
            Collections.shuffle(crews);
            return crews.get(0);
        }
        return null;
    }

    private boolean passingPlayerAttack(Player foundPlayer) {
        List<String> options = new ArrayList<>();
        options.add("Yes");
        options.add("No");
        String result = Main.getUiManager().makePopup(foundPlayer.getName(), "Would you like to attack " + name + "?", options);

        if (result.equals("Yes")) { // attacked
            return true;
        }

        return false;
    }

    /**
     * This is the method which shows what happens during an attack move.
     * It contains the logic which decides which player wins and which one
     * loses, based on fighting strength.
     * @param defendingPlayer - the player that lost the attack.
     */
    public void attack(Player defendingPlayer, boolean passingAttack) {
        Player winner = null;
        Player loser = null;
        if (getFightingStrength() > defendingPlayer.getFightingStrength()) {
            winner = this;
            loser = defendingPlayer;
        }
        else if (defendingPlayer.getFightingStrength() >= getFightingStrength()) {
            winner = defendingPlayer;
            loser = this;
        }
        Main.getUiManager().makePopup("Player " + winner.getName() + " won with a fighting strength of " +
                winner.getFightingStrength() + " compared to " + loser.getName() + " fighting strength of " + loser.getFightingStrength());

        if (loser.hasTreasureOnBoard() && canHoldTreasure()) {
            Treasure treasure = loser.getHighestValueTreasure();
            winner.addTreasure(treasure);
            loser.removeTreasure(treasure.getTreasureDetail());
        }
        else {
            for (int i = 0; i < 2; i++) {
                if (loser.getCrews().size() > 0) {
                    CrewCard crewCard = loser.getLowestValueCrew();
                    winner.addCrew(crewCard);
                    loser.removeCrew(crewCard);
                }
            }
        }

        Main.getGame().setupAttackResultState(loser);
        endTurn();
    }

    /**
     * This method decides whether a player's ship can or can't hold another piece
     * of treasure. The limit is 2 pieces per ship.
     * @return the amount of treasures a ship has.
     */
    public boolean canHoldTreasure() {
        //Find space
        if (treasures.size() < 2)
            return true;
        else
            return false;

    }

    /**
     * This method adds a treasure to a player's ship if they found they have enough
     * space for it.
     * @param treasure - the treasure they want to add.
     * @return whether the treasure has been added or not.
     */
    public boolean addTreasure(Treasure treasure) {
        if (canHoldTreasure()) { // if found a slot
            treasures.add(treasure);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This adds the crew cards to a player's ship at any point in the game.
     * @param crewCard - The crew card added to the player's ship.
     */
    public void addCrew(CrewCard crewCard) {
        crews.add(crewCard);
        updateMoveSpeed(crewCard, true);
        updateFightingStrength();
    }

    /**
     * This is the method used to remove crew cards from a player's ship and updates its speed and
     * fighting strength based on the crew cards left.
     * @param crewCard - the crew which is being removed.
     * @return the amount of crew cards left in the ship.
     */
    public CrewCard removeCrew(CrewCard crewCard){
        //choose first
        crews.remove(crewCard);
        updateMoveSpeed(crewCard, false);
        updateFightingStrength();

        return crewCard;
    }

    /**
     * This gets the crew card's information the player is about to pick up.
     * @return the crew card's info the player is going to pick up.
     */
    public CrewCard getCrewCard(){
        //player chooses the card
        if (crews.isEmpty()) {
            return null;
        }else{
            return crews.get(1);
        }
    }

    /**
     * This method is to remove treasure from either islands or players.
     * @param treasureDetail - the value and name of the treasure being removed.
     * @return the treasure left on the place it's been removes from.
     */
    public Treasure removeTreasure(TreasureDetail treasureDetail) {
        for(Treasure treasure : treasures) {
            if (treasure.getTreasureDetail().equals(treasureDetail)) {
                treasures.remove(treasure);
            }
            return treasure;
        }
        return null;
    }

    /**
     * This method sets up a player's turn at the beginning of the game or
     * each time a player's turn goes from one player to another.
     */
    public void setupPlayerTurn() {
        if (getPlayerState() == PlayerState.AT_HARBOUR) {
            Main.getUiManager().passDisplayHarbour((Harbour)Main.getGame().getMapObject(this.x, this.y));
        }

        List<String> actions = new ArrayList<>();

        if (playerState.canMove) {
            actions.add("Move");
        }

        if (playerState.canTurn) {
            actions.add("Turn");
        }

        if (playerState.canTrade) {
            actions.add("Trade");
        }

        Main.getUiManager().passDisplayPlayer(this);
        String result = Main.getUiManager().makePopup(name, "Please select an action:", actions);

        switch (result) {
            case "Move":
                setupMove();
                break;
            case "Turn":
                setupOrientation();
                break;
            case "Trade":
                Harbour harbour = (Harbour)Main.getGame().getMapObject(x, y);
                if (homePort == harbour) {
                    System.out.println("test");
                    PlayerHarbour playerHarbour = (PlayerHarbour) harbour;
                    playerHarbour.trade(this);
                }
                else {
                    harbour.trade(this);
                }
                setupPlayerTurn();
                break;
            default:
                endTurn();
                break;
        }
    }

    /**
     * This method ends the turn of a player and moves it along tot the turn
     * of the next player.
     */
    public void endTurn() {
        Main.getGame().nextGameState();
    }

    /**
     * This method gets the list of chance card present on islands or
     * players hands.
     * @return the list/single chance card present after the method is called.
     */
    public ChanceCard getChanceCards(){
        return chanceCard;
    }

    /**
     * This method shows the crew cards present in a ship or island at any point during the game
     * the method's called.
     */
    public String showCrewCards() {
        List<String> crewList = new ArrayList<>();
        for (CrewCard crew : this.getCrews()) {
            String tempCrew = crew.getCrewCardColour().toString() + " : " + crew.getCrewStrength();
            crewList.add(tempCrew);
        }
        return Main.getUiManager().makePopup("Crews", "Please select a crew", crewList);
    }

    /**
     * This method shows the chance cards present in a ship or island at any point during the game
     * the method's called.
     */
    public String showChanceCards() {
        ArrayList<String> chanceCardName = new ArrayList<>();
        chanceCardName.add(chanceCard.getChanceCardDetail().toString());
        
        return Main.getUiManager().makePopup("Chance Cards", chanceCard.getChanceCardDetail().toString(), chanceCardName);
    }

    /**
     * This returns the numbered distance a player is from another player being targeted.
     * @param targetPlayer - the player you want to know the distance to.
     * @return the distance to the target player.
     */
    public double distanceToAPlayer(Player targetPlayer) {
        int adjacent = abs(this.x-targetPlayer.x);
        int opposite = abs(this.y-targetPlayer.y);
        double distance = sqrt(abs(adjacent*adjacent) + abs(opposite*opposite));
        return distance;
    }

    /**
     * This method gets the lowest value of Treasure found in a ship or Island for the player to acquire it.
     * Some chance cards and actions need this function to work.
     * @return the lowest valued treasure found
     */
    public Treasure getLowestValueTreasure() {
        //List<Treasure> defeatedPlayerTreasures = defeatedPlayer.getTreasures();
        int resultValue = 10;
        int result = 0;
        for(int i = 0; i < treasures.size(); i++) {
            int treasureWorth = treasures.get(i).getTreasureDetail().worth;
            if (treasureWorth < resultValue) {
                resultValue = treasureWorth;
                result = i;
            }
        }
        return treasures.get(result);
        //addTreasure(defeatedPlayer.removeTreasure(defeatedPlayerTreasures.get(result).getTreasureDetail()));
    }

    /**
     * This method gets the lowest value of crew cards found in a ship or Island for the player to acquire it.
     * Some chance cards and actions need this function to work.
     * @return the lowest valued crew card found
     */
    public CrewCard getLowestValueCrew() {
        if(hasCrewOnBoard()) {
            int resultValue = 4;
            int result = 0;
            for (int i = 0; i < crews.size(); i++) {
                int crewStrength = crews.get(i).getCrewStrength();
                if (crewStrength < resultValue) {
                    resultValue = crewStrength;
                    result = i;
                }
            }
            return crews.get(result);
        }
        return null;
    }

    /**
     * This method checks whether a player does or doesn't have treasure on board of their
     * ship.
     * @return true if treasure found, false if they don't
     */
    public boolean hasTreasureOnBoard() {
        return treasures.size() > 0;
    }

    /**
     * This method checks whether a player does or doesn't have crew cards on board of their
     * ship.
     * @return true if crew cards found, false if they don't
     */
    public boolean hasCrewOnBoard() {
        return crews.size() > 0;
    }

    /**
     * This allows a player to select the crew card they want to acquire from another player's
     * deck. Used in attacks and for some chance card actions.
     * @return the crew card the player has selected.
     */
    public CrewCard pickCrewCard() {
        if(hasCrewOnBoard()) {
            List<String> crewList = new ArrayList<>();
            for (CrewCard crew : crews) {
                crewList.add(crew.getCrewCardColour().toString() + " : " + crew.getCrewStrength());
            }
            String selectedCrew;
            selectedCrew = Main.getUiManager().makePopup("Crew Cards", "Please select a crew", crewList);
            String[] crewNameAndStrength = selectedCrew.split(" : ");
             return searchForCrew(crewNameAndStrength[0], Integer.parseInt(crewNameAndStrength[1]));
        }
        return null;
    }

    /**
     * This method looks for the crew card's features in a specific deck.
     * @param CrewColour - the colour of the crew card.
     * @param CrewStrength - the fight strength of the crew card.
     * @return the features of the crew cards in the deck being checked.
     */
    public CrewCard searchForCrew(String CrewColour, int CrewStrength) {
        for(CrewCard crew : crews) {
            if(crew.getCrewCardColour().toString().equalsIgnoreCase(CrewColour) && crew.getCrewStrength() == CrewStrength) {
                return crew;
            }
        }
        return null;
    }
    public boolean hasChanceCard() {
        return chanceCard != null;
    }
    public void addChanceCard(ChanceCard chanceCard) {
        this.chanceCard = chanceCard;
    }
    public void addCrewCardToPlayer(int noOfCrewCards, Player player){
        PirateIsland pirateIsland = Main.getGame().getPirateIsland();
        if (pirateIsland.hasCrewCard()) {
            for (int i = 0; i < noOfCrewCards; i++) {
                pirateIsland.givePlayerCrewCard(player);
            }
        }
    }
    public void removeCrewCardFromPlayerToPirateIsland(Player player) {
        PirateIsland pirateIsland = Main.getGame().getPirateIsland();
        CrewCard crewCardToRemove = player.pickCrewCard();
        if (crewCardToRemove != null){
            player.removeCrew(crewCardToRemove);
            pirateIsland.addCrewCard(crewCardToRemove);
        }
    }
    public void removeCrewCardFromPlayerToPirateIsland(Player player, CrewCard crewCard) {
        PirateIsland pirateIsland = Main.getGame().getPirateIsland();
        player.removeCrew(crewCard);
        pirateIsland.addCrewCard(crewCard);

    }
    public CrewCard returnRandCrew (Player player){
        return player.randomCrew();
    }

    public Player closestPlayer(Player player, Player[] targetPlayers) {
        Player closestPlayer = null;
        double closestDistance = 50;
        for(Player targetPlayer : targetPlayers){
            if(player != targetPlayer) {
                double distance = player.distanceToAPlayer(targetPlayer);
                if(distance == closestDistance){
                    Main.getUiManager().makePopup("Chance Card","Two Players are at the same distance from you.\nChance Card failed.");
                    return null;
                }
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestPlayer = targetPlayer;
                }
            }
        }
        return closestPlayer;
    }
    public int pickTreasureUpTo(int maxValue, Player player) {
        int treasureValue = 0;
        while(true) {
            List<Treasure> treasureList = new ArrayList<>();

            while(true) {
                List<String> options = new ArrayList<>();

                for(Treasure treasure : Main.getGame().getTreasureIsland().getTreasures()) {
                    if (!treasureList.contains(treasure)) {
                        options.add(treasure.getTreasureDetail().name);
                    }
                }

                if (options.size() == 0) {
                    break;
                }

                options.add("Confirm");

                String result = Main.getUiManager().makePopup(player.getName(), "Select treasure:", options);

                for(Treasure treasure : Main.getGame().getTreasureIsland().getTreasures()) {
                    if (result.equals(treasure.getTreasureDetail().name)) {
                        if (!treasureList.contains(treasure)) {
                            treasureList.add(treasure);
                            break;
                        }
                    }
                }

                if (result.equals("Confirm")) {
                    break;
                }
            }

            for(Treasure treasure : treasureList) {
                treasureValue += treasure.getTreasureDetail().worth;
            }

            if (treasureValue == maxValue) {
                for(Treasure treasure : treasureList) {
                    player.addTreasure(Main.getGame().getTreasureIsland().takeTreasure(treasure.getTreasureDetail()));
                }

                break;
            }
        }

        return maxValue;
    }

}
