package uk.ac.aber.cs221.gp12.game.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uk.ac.aber.cs221.gp12.game.Game;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This is the class used to serialise the information and states of the game.
 * It is saved in json files.
 *
 * @author Behrooz Rezvani (ber39), Jason Weller (jaw125)
 * @version 1.0 - Initial development of the class.
 */
public class Serialize {

    /**
     * This method serializes the game object at its use.
     * @param fileName - The file where the game is being serialized.
     * @return the serialized file.
     */
    public static void serializeGameObject(Game game, String fileName) {

        try {
            Writer writer = Files.newBufferedWriter(Path.of(fileName));
            Gson gson = new Gson();
            gson.toJson(game, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static void saveSerialize(String fileName, String jsonStr) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(jsonStr);
            out.close();
            fileOut.close();
            //System.out.printf("Serialized data is saved in " + fileName);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
