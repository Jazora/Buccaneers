package uk.ac.aber.cs221.gp12.game;

import uk.ac.aber.cs221.gp12.game.gamestate.*;
;
import uk.ac.aber.cs221.gp12.game.ui.controllers.GameController;

import java.util.*;

/**
 * This class constructs and describes the features of a Game.
 *
 * @author Jason Weller (jaw125), Behrooz Rezvani (ber39)
 * @version 1.0 - Initial development of class
 */
public class Game {

    private GameObject[][] gameMap = new GameObject[20][20];
    private LinkedList<GameState> gameLoop = new LinkedList<>();

    private final int minPos = 0;
    private final int maxPos = 19;
    private final int chanceCardCount = 28;
    private final int eachCrewCardCount = 5;
    private final int playerStartCrewCount = 5;
    private final int eachTreasureCount = 5;
    private final int crewCardAtHarbour = 2;

    private Player[] players = new Player[4];
    private Harbour[] harbours = new Harbour[6];
    private PirateIsland pirateIsland;
    private TreasureIsland treasureIsland;
    private FlatIsland flatIsland;
    private Mudbay mudbay;
    private CliffCreek cliffCreek;
    private AnchorBay anchorBay;

    private void addState(int index, GameState gameState) {
        gameLoop.add(index, gameState);
    }

    private void addState(GameState gameState) {
        gameLoop.add(gameState);
    }

    /**
     * This is the method which sets and changes the state to start the game.
     */
    public void startGame() {
        addState(new StartGameState());
        nextGameState();
    }

    /**
     * This method is used to load the saved states of the game from a json file.
     * The game will save the state and start running from there.
     */
    public void loadGame() {
        for(Player player : players) {
            player.setPosition(player.getX(), player.getY());
            player.setOrientation(player.getOrientation());
            Main.getUiManager().setIndividualPlayerName(player.getName(), player.getIndex());
        }

        Main.getUiManager().drawHarbour();
        ((GameController)Main.getUiManager().getControllerList().get(1)).rollAnimation(true);
        setupPlayerTurns();
    }

    /**
     * This method is the one which defaults all the player stats, starts up the map, introduces the
     * crew card deck and gives some to players, inputs the treasures, and starts up player turns.
     */
    public void setupGameSettings() {
        pirateIsland = new PirateIsland();
        treasureIsland = new TreasureIsland();

        setupPlayers();
        setupMap();
        setupCrewCards();
        setupChanceDeck();
        setupTreasures();
        setupPlayerTurns();
    }

    /**
     * This method gives the players in the game turns, and makes the game go through
     * the players turn states.
     */
    public void setupPlayerTurns() {
        addState(new PlayerTurnGameState(players[0]));
        addState(new PlayerTurnGameState(players[1]));
        addState(new PlayerTurnGameState(players[2]));
        addState(new PlayerTurnGameState(players[3]));
        addState(new EndRoundGameState());
        nextGameState();
    }

    /**
     * This method starts up the endgame state, which finalises the game.
     * @param player - This is the winner of the game, the one who triggers
     *               this state.
     */
    public void setupEndGameState(Player player) {
        addState(new EndGameState(player));
    }

    /**
     * This method sets the state which happens after an attack has taken place.
     * @param losingPlayer - This is the loser in the attack, the player who
     *                     triggers this state.
     */
    public void setupAttackResultState(Player losingPlayer) {
        addState(0, new PlayerAttackLoseGameState(losingPlayer));
    }

    /**
     * This method triggers the state which happens after a game is finished a new one is started.
     * It's only used for new game, or when the game crashes in startup.
     */
    public void nextGameState() {
        GameState gState = gameLoop.getFirst();
        gameLoop.removeFirst();
        gState.Run();
    }

    /**
     * This method checks that the player's original position at the start of the game is valid
     * and within the needed requirements of the game. Making sure they're on the map, and they're assigned
     * ports.
     * @param x - the starting x position of the player.
     * @param y - the starting y position of the player.
     * @return true if the position is valid, false if it isn't.
     */
    public boolean validPosition(int x, int y) {
        if (x >= minPos && x <= maxPos && y >= minPos && y <= maxPos) {

            if (gameMap[x][y] != null) {
                return gameMap[x][y].isPassable();
            }
            else {
                return true;
            }
        }

        return false;
    }

    /**
     * This method makes sure that the map used for the game is complying with the minimum and maximum
     * position set out in the UI section of the game.
     * @param x - the max and min position x can be.
     * @param y - the max and min position y can be.
     * @return the map object for the game.
     */
    public GameObject getMapObject(int x, int y) {
        if (x >= minPos && x <= maxPos && y >= minPos && y <= maxPos) {
            if (gameMap[x][y] != null) {
                return gameMap[x][y];
            }
        }
        return null;
    }

    /**
     * This method makes sure that the map object that was triggered to play the game in
     * works with a player in it and is functional to carry out the game.
     * @param player - the player in the game which triggers the action.
     * @param x - the max and min position x can be.
     * @param y - the max and min position y can be.
     */
    public void triggeredMapObject(Player player, int x, int y) {
        if (x >= minPos && x <= maxPos && y >= minPos && y <= maxPos) {
            if (gameMap[x][y] != null) {
                gameMap[x][y].triggered(player);
            }
        }
    }

    /**
     * This method makes sure that there is an appropriate amount of players, and they're in the
     * right positions at the stater and during the game to follow requirements.
     * @param player - The players in the game.
     * @param x - the max and min position x can be.
     * @param y - the max and min position y can be.
     * @return the players playing the game and a change of state.
     */
    public Player triggeredPlayer(Player player, int x, int y) {
        if (x >= minPos && x <= maxPos && y >= minPos && y <= maxPos) {
            for (Player checkPlayer : players) {
                if (checkPlayer == player) {
                    continue;
                }
                if (checkPlayer.getX() == x && checkPlayer.getY() == y) {
                    if (checkPlayer.getPlayerState().getAttacked && player.getPlayerState().canAttack) {
                        return checkPlayer;
                    }
                }
            }
        }

        return null;
    }

    /**
     * This method gets the minimum position a player can be in during the game.
     * @return the minimum position a player can be during the game
     */
    public int getMinPos() { return minPos; }

    /**
     * This method gets the maximum position a player can be in during the game.
     * @return the maximum position a player can be during the game
     */
    public int getMaxPos() { return maxPos; }

    /**
     * This method gets the array of players playing in the game at all times.
     * @return the array of players in the game.
     */
    public Player[] getPlayers() { return players; }

    /**
     * This method gets the array of harbours in the game.
     * @return the array of players in the game.
     */
    public Harbour[] getHarbours() { return harbours; }

    /**
     * This method gets the crew card info on Pirate Island
     * @return the information on Pirate Island.
     */
    public PirateIsland getPirateIsland() { return pirateIsland; }

    /**
     * This method gets the crew card and treasure info on Treasure Island
     * @return the information on Treasure Island.
     */
    public TreasureIsland getTreasureIsland() { return treasureIsland; }

    /**
     * This method gets the crew card and treasure info on Flat Island
     * @return the information on Flat Island.
     */
    public FlatIsland getFlatIsland() { return flatIsland; }

    /**
     * This method gets info on Mud Bay
     * @return the information on Mud Bay.
     */
    public Mudbay getMudbay() { return mudbay; }

    /**
     * This method gets the info on Cliff's Creek
     * @return the information on Cliff's Creek.
     */
    public CliffCreek getCliffCreek() { return cliffCreek; }

    /**
     * This method gets the info on Anchor Bay
     * @return the information on Anchor Bay.
     */
    public AnchorBay getAnchorBay() { return anchorBay; }

    private void setupPlayers() {

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(i);
        }

        List<String> takenNames = new ArrayList();

        for (int i = 0; i < players.length; i++) {
            while(true) {
                String result = Main.getUiManager().makePopup("Player " + (i + 1), "Please enter your name:");

                if (result.length() == 0) {
                    continue;
                }

                if (result.charAt(0) == ' ') {
                    continue;
                }

                if (takenNames.contains(result)) {
                    continue;
                }

                players[i].setName(result);
                Main.getUiManager().setIndividualPlayerName(players[i].getName(), i);
                if (players[i].getName().length() == 0) { // repeat asking for a correct name
                    i--;
                }
                takenNames.add(result);
                break;
            }
        }
        //((GameController)Main.getUiManager().getControllerList().get(1)).rollAnimation(true);
    }

    private void setupCrewCards() {
        ArrayList<CrewCard> cards = new ArrayList<>();

        for (int i = 0; i < eachCrewCardCount; i++) {
            cards.add(new CrewCard(1, CrewCardColour.BLACK));
            cards.add(new CrewCard(2, CrewCardColour.BLACK));
            cards.add(new CrewCard(3, CrewCardColour.BLACK));

            cards.add(new CrewCard(1, CrewCardColour.RED));
            cards.add(new CrewCard(2, CrewCardColour.RED));
            cards.add(new CrewCard(3, CrewCardColour.RED));
        }

        Collections.shuffle(cards);

        for (Player p: players) {
            //amount of cards
            for (int j = 0; j < playerStartCrewCount; j++) {
                p.addCrew(cards.get(0));
                cards.remove(0);
            }
        }

        //Give crew cards to trade harbours
        for (int i = 4; i < 6; i++) {
            for (int j = 0; j < crewCardAtHarbour; j++) {
                TradeHarbour harbour = (TradeHarbour) harbours[i];
                harbour.addCrew(cards.get(0));
                cards.remove(0);
            }
        }

        pirateIsland.getCrewCardDeck().addAll(cards);
    }

    private void setupChanceDeck() {
        ArrayList<ChanceCard> cards = new ArrayList<>();
        for (int i = 0; i < chanceCardCount; i++) {
            cards.add(new ChanceCard(ChanceCardDetail.values()[i]));
        }
        Collections.shuffle(cards);
        for (int i = 0; i < chanceCardCount; i++) {
            treasureIsland.addChanceCard(cards.get(i));
        }
    }

    private void setupTreasures() {
        List<TreasureDetail> treasureDetails = getTreasureDetails();

        for (int i = 0; i < eachTreasureCount; i++) {
            for (TreasureDetail tDetail : treasureDetails) {
                treasureIsland.addTreasure(new Treasure(tDetail));
            }
        }

        //Give treasure to trade harbours
        for (int i = 4; i < 6; i++) {
            Harbour harbour = harbours[i];
            int harbourValue = 0;

            for(CrewCard crewCard : harbour.getCrewCards()) {
                harbourValue+= crewCard.getCrewStrength();
            }

            while(harbourValue != 8) {
                for(TreasureDetail tDetail : treasureDetails) {
                    if ((harbourValue + tDetail.worth) <= 8) {
                        harbour.addTreasure(treasureIsland.takeTreasure(tDetail));
                        harbourValue = harbourValue + tDetail.worth;
                    }
                }
            }
        }
    }

    private void setupMap() {
        setupHarbours();
        setupTreasureIsland();
        setupPirateIsland();
        setupFlatIsland();

        mudbay = new Mudbay();
        gameMap[0][0] = mudbay;

        cliffCreek = new CliffCreek();
        gameMap[19][19] = cliffCreek;

        anchorBay = new AnchorBay();
        gameMap[19][0] = anchorBay;
    }

    private void setHarbour(Harbour harbour, HarbourDetail harbourType) {
        gameMap[harbourType.x][harbourType.y] = harbour;
    }

    private void setupHarbours() {
        List<Integer> portsAlreadyTaken = new ArrayList<>();

        //Set random port for each player
        for (int i = 0; i < players.length; i++) {
            while (true) {
                Random random = new Random();
                int randomInt = random.nextInt(4);
                if (!portsAlreadyTaken.contains(randomInt)) {
                    HarbourDetail harbourType = HarbourDetail.values()[randomInt];
                    Harbour harbour = new PlayerHarbour(harbourType);
                    harbours[randomInt] = harbour;
                    setHarbour(harbour, harbourType);
                    players[i].setHarbour(harbour);
                    portsAlreadyTaken.add(randomInt);
                    break;
                }
            }
        }

        for (int i = 4; i < harbours.length ; i++) {
            //todo add treasure/cards to ports
            HarbourDetail harbourType = HarbourDetail.values()[i];
            Harbour harbour = new TradeHarbour(harbourType);
            harbours[i] = harbour;
            setHarbour(harbour, harbourType);
        }

        Main.getUiManager().drawHarbour();
    }

    private void setIsland(Island island, int x, int y) {
        gameMap[x][y] = island;
    }

    private void setupTreasureIsland() {
        treasureIsland = new TreasureIsland();

        //todo constants
        for (int y = 8; y <= 11; y++) {
            for (int x = 8; x <= 11; x++) {
                setIsland(treasureIsland, x, y);
            }
        }

        IslandBoundary islandBoundaryWest = new IslandBoundary(treasureIsland, ShipOrientation.WEST);
        IslandBoundary islandBoundaryEast = new IslandBoundary(treasureIsland, ShipOrientation.EAST);
        for(int y = 8; y < 12; y++) {
            setIsland(islandBoundaryWest, 7, y);
            setIsland(islandBoundaryEast, 12, y);
        }
        IslandBoundary islandBoundaryNorthEast = new IslandBoundary(treasureIsland, ShipOrientation.NORTH_EAST);
        setIsland(islandBoundaryNorthEast, 12, 12);

        IslandBoundary islandBoundaryNorthWest = new IslandBoundary(treasureIsland, ShipOrientation.NORTH_WEST);
        setIsland(islandBoundaryNorthWest, 7, 12);

        IslandBoundary islandBoundarySouthEast = new IslandBoundary(treasureIsland, ShipOrientation.SOUTH_EAST);
        setIsland(islandBoundarySouthEast, 12, 7);

        IslandBoundary islandBoundarySouthWest = new IslandBoundary(treasureIsland, ShipOrientation.SOUTH_WEST);
        setIsland(islandBoundarySouthWest, 7, 7);

        IslandBoundary islandBoundaryNorth = new IslandBoundary(treasureIsland, ShipOrientation.NORTH);
        IslandBoundary islandBoundarySouth = new IslandBoundary(treasureIsland, ShipOrientation.SOUTH);
        for(int x = 8; x < 12; x++) {
            setIsland(islandBoundarySouth, x, 7);
            setIsland(islandBoundaryNorth, x, 12);
        }
    }

    private void setupPirateIsland() {
        pirateIsland = new PirateIsland();

        //todo constants
        for (int y = 1; y <= 4; y++) {
            for (int x = 16; x <= 18; x++) {
                setIsland(pirateIsland, x, y);
            }
        }

        IslandBoundary islandBoundaryWest = new IslandBoundary(pirateIsland, ShipOrientation.WEST);
        IslandBoundary islandBoundaryEast = new IslandBoundary(pirateIsland, ShipOrientation.EAST);
        for(int y = 0; y <= 4; y++) {
            setIsland(islandBoundaryWest, 15, y);
            setIsland(islandBoundaryEast, 19, y);
        }

        IslandBoundary islandBoundaryNorth = new IslandBoundary(pirateIsland, ShipOrientation.NORTH);
        IslandBoundary islandBoundarySouth = new IslandBoundary(pirateIsland, ShipOrientation.SOUTH);
        for(int x = 16; x <= 18; x++) {
            setIsland(islandBoundarySouth, x, 0);
            setIsland(islandBoundaryNorth, x, 5);
        }
    }

    private void setupFlatIsland() {
        flatIsland = new FlatIsland();

        //todo constants
        for (int y = 15; y <= 18; y++) {
            for (int x = 1; x <= 3; x++) {
                setIsland(flatIsland, x, y);
            }
        }

        IslandBoundary islandBoundaryWest = new IslandBoundary(flatIsland, ShipOrientation.WEST);
        IslandBoundary islandBoundaryEast = new IslandBoundary(flatIsland, ShipOrientation.EAST);
        for(int y = 14; y <= 19; y++) {
            setIsland(islandBoundaryWest, 0, y);
            setIsland(islandBoundaryEast, 4, y);
        }

        IslandBoundary islandBoundaryNorth = new IslandBoundary(flatIsland, ShipOrientation.NORTH);
        IslandBoundary islandBoundarySouth = new IslandBoundary(flatIsland, ShipOrientation.SOUTH);
        for(int x = 1; x <= 3; x++) {
            setIsland(islandBoundarySouth, x, 14);
            setIsland(islandBoundaryNorth, x, 19);
        }
    }

    /**
     * This method gets a list of all the treasure's names and values present in the game.
     * @return the info of all the treasures in the game.
     */
    public List<TreasureDetail> getTreasureDetails() {
        List<TreasureDetail> details = new ArrayList<>();
        details.add(TreasureDetail.DIAMOND);
        details.add(TreasureDetail.RUBY);
        details.add(TreasureDetail.GOLD_BAR);
        details.add(TreasureDetail.PEARL);
        details.add(TreasureDetail.BARREL_OF_RUM);
        return details;
    }

    private void pickPlayer(){
        List<String> playerList = new ArrayList<>();
        for (Player player : players) {
            playerList.add(player.getName());
        }
        String chosenPlayer;
        chosenPlayer = Main.getUiManager().makePopup("Players", "Please select a player", playerList);
    }

    /**
     * This method looks for a specific player in the game based on a name search.
     * @param name - The name of the player the game is looking for.
     * @return the player which name matches the search.
     */
    public Player searchPlayer(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }
}
