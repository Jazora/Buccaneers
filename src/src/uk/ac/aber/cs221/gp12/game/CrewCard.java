package uk.ac.aber.cs221.gp12.game;

/**
 * This is the class responsible for creating Crew Card objects.
 *
 * @author Behrooz Rezvani (ber39), Jason Weller (jaw125)
 * @version 1.0 - Initial development of the class
 */
public class CrewCard {
    private int crew;
    private CrewCardColour crewCardColour;

    /**
     * Empty constructor for crew cards.
     */
    public CrewCard() {
    }

    /**
     * This method is responsible for constructing crew cards.
     */
    public CrewCard(int crew, CrewCardColour crewCardColour) {
        this.crew = crew;
        this.crewCardColour = crewCardColour;
    }

    /**
     * This method gets the crew cards fighting strength.
     * @return the fighting strength of crew cards.
     */
    public int getCrewStrength() {
        return crew;
    }

    /**
     * This method gets the card colours of crew cards.
     * @return the crew card's colour.
     */
    public CrewCardColour getCrewCardColour() {
        return crewCardColour;
    }

    /**
     * This method gets the name of the colour and the name of the
     * crew of the crew card.
     * @return the crew card's name
     */
    public String getName() {
        return crewCardColour.name() + " " + crew;
    }
}