package jukebox;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import static java.lang.System.*;

/**
 * Jukebox class represents a jukebox and runs the simulation for finding average number of songs
 * before a duplicate is encountered 50000 times
 *
 * @author Atharva Dhupkar
 */
public class Jukebox {
    /**
     * number of unique songs in the jukebox
     */
    private int noOfSongs;
    /**
     * keeps track of number of songs played
     */
    private int noOfSongsPlayed;
    /**
     * constant for total number of simulations to be run
     */
    public static final int SIMULATION_RUNS = 50000;
    /**
     * HashMap to store songs with key as name of the artist and
     * value as song of type Song
     */
    private HashMap<String, SongList> jukebox;
    /**
     * keeps track of the most played song
     */
    private Song mostPlayedSong;
    /**
     * holds songs in the jukebox in array form for random access
     */
    private Object[] songArray;
    /**
     * keeps track of the number of songs before a duplicate is encountered for all simulations
     * this number is divided by the SIMULATION_RUNS to find average
     */
    private long avg = 0;
    /**
     * a collection of songs in the jukebox (unique songs read from the file)
     */
    private Collection<Song> songCollection;
    /**
     * time taken to run SIMULATION_RUNS(here 50000) number of simulations
     */
    private double time;
    /**
     * constructor to initializes the fields and calls readData to read songs from a file and
     * puts them in a HashMap with artist name as key and song of type Song as value
     */
    public Jukebox(String filename) throws FileNotFoundException {
        jukebox = new HashMap<>();
        Scanner in = new Scanner(new File(filename));
        noOfSongs = 0;
        readData(in);
        songArray = songCollection.toArray();
        noOfSongsPlayed = 0;
        mostPlayedSong = null;
    }

    /**
     * reads songs from a file and puts them in a HashMap with artist name as key and song of type Song as value
     * @param in Scanner to read from file
     */
    private void readData(Scanner in){
        songCollection = new TreeSet<>();
        while(in.hasNext()) {
            String line = in.nextLine();
            String[] splitLine = line.split("<SEP>", 4);
            songCollection.add(new Song(splitLine[2], splitLine[3]));
        }
        noOfSongs = songCollection.size();
        in.close();
    }

    /**
     * this function is used to set the most played song
     * it checks the field numberOfPlays of both song and mostPlayedSong to determine
     * if it needs to be changed or remain same
     * @param song Song
     */
    private void setMostPlayedSong(Song song){
        if(mostPlayedSong == null){
            mostPlayedSong = song;
        }
        else{
            if(mostPlayedSong.getNumberOfPlays() < song.getNumberOfPlays())
                mostPlayedSong = song;
        }
    }

    /**
     * this method runs one simulation
     * it gets a random song from the song array
     * increments noOfSongsPlayed, numberOfPlays for the song and songsB4duplicate
     *
     * Step 1) if the map doesn't have a key of the same artist, it adds to it
     *
     * Step 2) if the map has the artist as the key,
     *       then it checks if the value of the key (songList by that artist) has the song
     *            Step 3) if not, it adds the song to the songList
     *            Step 4) if yes, then it checks if the song was played during the same simulation by getting the simulationNum
     *                 Step 5) if yes, it decrements noOfSongsPlayed, numberOfPlays for the song and songsB4duplicate,
     *                 adds songsB4duplicate to avg and sets songB4duplicate to 0
     *                 then stops the loop and goes on to the next simulation
     *
     * this method also finds the most played song by calling the function setMostPlayedSong
     * @param rnd Random to pick random songs
     * @param songsB4duplicate counter for number of songs played before duplicate is encountered
     * @param currSimNum current simulation number
     */
    public void simulation(Random rnd, int songsB4duplicate, int currSimNum){
        for(int j = 0; j < noOfSongs; j++ ) {
            Song song = (Song) Array.get(this.songArray, rnd.nextInt(this.songArray.length));
            ++noOfSongsPlayed;
            song.incrNumberOfPlays();
            ++songsB4duplicate;
            // Step 1
            if (!jukebox.containsKey(song.getArtist())) {
                song.setSimulationNum(currSimNum-1);
                HashSet<Song> songSet = new HashSet<>();
                songSet.add(song);
                SongList newSongList = new SongList(songSet);
                jukebox.put(song.getArtist(), newSongList);
                if(currSimNum == 0){
                    setMostPlayedSong(song);
                }
            }
            // Step 2
            else {
                SongList songList = jukebox.get(song.getArtist());
                // Step 3
                if(!songList.contains(song)) {
                    song.setSimulationNum(currSimNum-1);
                    songList.add(song);
                }
                // Step 4
                else{
                    int simNum = song.getSimulationNum();
                    song.setSimulationNum(currSimNum-1);
                    songList.remove(song);
                    songList.add(song);
                    // Step 5
                    if(simNum == currSimNum-1){
                        song.decrNumberOfPlays();
                        --songsB4duplicate;
                        --noOfSongsPlayed;
                        avg += songsB4duplicate;
                        songsB4duplicate = 0;
                        j = noOfSongs;
                    }
                    setMostPlayedSong(song);
                }
            }
        }
    }

    /**
     * this function runs the jukebox simulation SIMULATION_RUNS (50000) times by calling simulation each time
     * it also calculate time takes and average number of songs before a duplicate is encountered.
     *
     * @param rnd Random to pick random songs
     */
    public void simulationRunner(Random rnd) {
        int i = 0, songsB4duplicate = 0;
        double startTime = currentTimeMillis();
        while (i < SIMULATION_RUNS){
                simulation(rnd, songsB4duplicate, i);
                ++i;
        }
        double endTime = currentTimeMillis();
        time = (endTime - startTime) / 1000;
        avg += songsB4duplicate;
        avg = avg / SIMULATION_RUNS;
    }

    /**
     * prints statistics
     * sorts songs by artist of mostPlayedSong and prints them
     */
    public void print(){
        out.println("Jukebox of "+ noOfSongs + " songs starts rockin'...");
        out.println("Simulation took "+ (int) time + " second/s");
        out.println("Number of simulations run: " + SIMULATION_RUNS);
        out.println("Total number of songs played: " + noOfSongsPlayed);
        out.println("Average number of songs played per simulation to get duplicate: " + avg);
        out.println("Most played song: " + mostPlayedSong);
        out.println("All songs alphabetically by " + mostPlayedSong.getArtist() + " :");
        SongList songArray = jukebox.get(mostPlayedSong.getArtist());
        songArray.sort();
        SongList songList = jukebox.get(mostPlayedSong.getArtist());
        songList.print();

    }

    /**
     * main method makes a new object of type Jukebox and calls simulationRunner to run simulation
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if(args.length == 2) {
            String filename = args[0];
            try {
                Jukebox jukebox = new Jukebox(filename);
                long seed = Long.parseLong(args[1]);
                Random rnd = new Random(seed);
                jukebox.simulationRunner(rnd);
                jukebox.print();
            } catch (FileNotFoundException fe) {
                err.println("File: " + filename +  " not found...");
            }
        }
        else{
            out.println("Usage: java Jukebox filename seed");
        }
    }
}
