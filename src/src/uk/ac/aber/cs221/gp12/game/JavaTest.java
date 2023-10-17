package uk.ac.aber.cs221.gp12.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class JavaTest {

    @Test
    void example() {
        assertTrue(true);
    }

    @Test
    void testMove() {
        Main.getGame().getPlayers()[0].teleport(0,0);
        assertTrue(true);
    }

    @Test
    void isPassable() {

    }

    @Test
    void triggered() {
    }

    @Test
    void getChanceCards() {
        assertEquals(Main.getGame().getTreasureIsland().chanceCards, Main.getGame().getTreasureIsland().getChanceCards());
    }

    @Test
    void getTreasures() {

    }

    @Test
    void addTreasure() {
    }

    @Test
    void addChanceCard() {
    }

    @Test
    void takeChanceCard() {
    }

    @Test
    void getTreasureTypes() {
    }

    @Test
    void getTreasureOfType() {
    }

    @Test
    void takeTreasure() {
    }

    @Test
    void hasTreasure() {
    }

    @Test
    void testTriggered() {
    }
}
