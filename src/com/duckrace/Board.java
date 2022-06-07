package com.duckrace;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Lookup table of ids whe student names.
 * when a DUckRacer wins for the 1st time we need to find out who that is .
 *
 * Map<Integer, String> studentIdMap.
 * id    name
 * --   ---
 * 1   Cat
 * 2    Chris
 * 3    David
 * 4    Davida
 * we need some sort of data structure (collection) to hold our race result.
 * This collection should facilitate eass lookup and retrieval of DuckRaces, so
 * that we can easily update the collection with new winners.
 * * Map<Integer, DuckRacer> raceMap.
 *  * id   DuckRacer
 *  * --   ---
 *  *       id   name   wins  rewards
 *  * 17    17   Chris  2    PRIZES,PRIZES
 *  * 5     5   Mannie 2    PRIZES,DEBIT_CARD
 *  * 11    11   Kris   1    DEBIT_CARD
 */

public class Board implements Serializable {
    private static final String studentIdPath = "data/student-ids.csv";
    private static final String bordPath = "data/board.dat";

    /**
     * VERY FIRST TIME application is run ever, this is not data/board.data.file.
     * Therefore, we will create return a "new Board()".
     *
     * EVERY SUBSEQUENT TIME  the application is run there IS a data /board.data file.
     * And in this case, we will "retrieve" the Board object saved in that binary file.
     */
    public static Board getInstance() {
        Board board = null;

        if (Files.exists(Paths.get(bordPath))) { // data file exist, read Board from file
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(bordPath))){
                board = (Board) in.readObject();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            board = new Board();
        }
        return board;
    }
    private Map<Integer, String> studentIdMap = loadStudentIdMap();
    private Map<Integer,DuckRacer> racerMap = new TreeMap<>();



    /**
     * Updates the board(Map) with the given id and Reward.
     * Implementation note:
     * 1. find out if the given id is already in the map
     *   a. if so, retrieve the DuckRacer next to that id and make it "win" the given Rewar
     *   b. if not, create new DuckRacer whit id, and make it "win" the given Reward
     *   also,don't forget to insert the new DuckRacer into the map (easy to forget)
     */
    public void update(int id, Reward reward) {
        DuckRacer racer = null; // either pulled from the map (if existing), or ner(if not)

        if (racerMap.containsKey(id)) { //  id already in Map, so fetch that DuckRacer
            racer = racerMap.get(id);
        }
        else {                          // id not present in Map
            racer = new DuckRacer(id, studentIdMap.get(id)); // id, name
            racerMap.put(id, racer);     // id, DuckRacer (newly created, first-time winner)
        }
        //finally, make it win
        racer.win(reward);
        save();
    }

    private void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(bordPath))) {
            out.writeObject(this); // 'this'  is the Board object!!!
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads data/student-ids.cvs, parse each line of text into id and name,
     * populates this Map with those values, the returns it.
     */
    private Map<Integer, String> loadStudentIdMap() {
        Map<Integer, String> idMap = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(studentIdPath));
            for (String line : lines){
                String[] tokens = line.split(",");
                Integer id = Integer.valueOf(tokens[0]); // Integer object
                String name = tokens[1];
                idMap.put(id,name);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return idMap;
    }

    // Shows the board in a friendly, human way.
    public void show() {
        if (racerMap.isEmpty()) {
            System.out.println("There are currently not results to show");
        }
        else {
            Collection<DuckRacer> racers = racerMap.values(); // right-hands side of the Map
            StringBuilder header = new StringBuilder(512)
                    .append("\nDuck Race Result\n")
                    .append("=================================");
            StringBuilder columnHeading = new StringBuilder(256)
                    .append("id   name   wins   rewards\n")
                    .append("--   ----   ----   -------");

            System.out.println(header + "\n");
            System.out.println(columnHeading);


            for (DuckRacer racer : racers) {
                System.out.println(racer.getId() + "    " + racer.getName()
                        + "    " + racer.getWins() + "    " + racer.getRewards()); // toString automatically called
            }
        }

    }
}