package jukebox;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
//import java.lang.*;
import static java.lang.System.*;

public class Jukebox {

    private int noOfSongs;

    private int noOfSongsPlayed;

    public static int SIMULATION_RUNS = 3;

    private ArrayList<Integer> songsB4Duplicate;

    private HashMap<Song, Integer> playedSongs;

    private HashMap<String, HashSet<String>> artistMap;

    private Song mostPlayedSong;

    private Object[] songArray;

    private long avg = 0;

    private Collection<Song> jukebox;

    private double time;

    public Jukebox(String filename) throws FileNotFoundException {
        artistMap = new HashMap<>();
        playedSongs = new HashMap();
        songsB4Duplicate = new ArrayList<>();
        Scanner in = new Scanner(new File(filename));
        noOfSongs = 0;
        readData(in);
        songArray = jukebox.toArray();
        noOfSongsPlayed = 0;
        mostPlayedSong = null;
    }

    private void readData(Scanner in){
        jukebox = new TreeSet<Song>();
        while(in.hasNext()) {
            noOfSongs++;
            String line = in.nextLine();
            String[] splitLine = line.split("<SEP>", 4);
            jukebox.add(new Song(splitLine[2], splitLine[3]));
        }
    }
    private void setMostPlayedSong(Song song){
        if(mostPlayedSong == null){
            mostPlayedSong = song;
        }
        else{
            if(playedSongs.get(mostPlayedSong) < playedSongs.get(song))
                mostPlayedSong = song;
        }
    }
    public void aMethodForSomething(Random rnd){
        int i = 0, j = 1;
        Integer timesPlayed;
        HashSet<String> songsByArtist = new HashSet<>();
        double startTime = currentTimeMillis();
        // runs SIMULATION_RUNS times (now 50000)
        while(i < SIMULATION_RUNS){
            for(Song o : jukebox) {
                if(o != null) {
                    //increment noOfSongsPlayed to keep count of total songs played
                    ++noOfSongsPlayed;

                    //get the next random song
                    Song song = (Song) Array.get(songArray, rnd.nextInt(songArray.length));

                    //if song was not played played before
                    if (!playedSongs.containsKey(song)) {

                        //add to playedSongs
                        playedSongs.put(song, 1);

                        //add to artistMap that collects all songs by the same artist
                        songsByArtist.add(song.getName());
                        artistMap.put(song.getArtist(), songsByArtist);
                        songsByArtist = new HashSet<>();
                    }

                    // if song is already played
                    else {
                        // add to ArrayList that keeps track of number of songs before duplicate is encountered
                        songsB4Duplicate.add(j);

                        //reset j which keeps track of number of song before duplicate is encountered
                        j = 0;

                        // increment value by 1
                        timesPlayed = playedSongs.get(song);
                        ++timesPlayed;
                        playedSongs.remove(song);
                        playedSongs.put(song, timesPlayed);

                        //add song to list of songs by the same artist in artistMap
                        songsByArtist = artistMap.get(song.getArtist());
                        songsByArtist.add(song.getName());
                        artistMap.remove(song);
                        artistMap.put(song.getArtist(), songsByArtist);
                        songsByArtist = new HashSet<>();

                        //check if this is most played song
                        setMostPlayedSong(song);
                    }
                    j++;
                }
            }
            i++;
        }
        double endTime = currentTimeMillis();
        time = (endTime - startTime)/100;
        for(Integer num : songsB4Duplicate){
            avg += num;
        }
        avg = avg/SIMULATION_RUNS;
//        printPlayedSongs();
    }

//    private void printPlayedSongs(){
//        for(Song song : playedSong){
//          System.out.println("Most played song: " + song + " : " + playedSong.get(song)) ;
//        }
//    }
    public void print(){
        out.println("Jukebox of "+ noOfSongs + " songs starts rockin'...");
        out.println("Simulation took "+ time + " second/s");
        out.println("Number of simulations run: " + SIMULATION_RUNS);
        out.println("Total number of songs played: " + noOfSongsPlayed);
        out.println("Average number of songs played per simulation to get duplicate: " + avg);
        out.println("Most played song: " + mostPlayedSong + " : " + playedSongs.get(mostPlayedSong));
        out.println("All songs alphabetically by " + mostPlayedSong.getArtist() + " :");
        sortSongs();

    }

    private void sortSongs(){
        HashSet<String> songSet= artistMap.get(mostPlayedSong.getArtist());
        Object[] songArray = songSet.toArray();
        Arrays.sort(songArray);
        for(Object song: songArray){
            out.println("\t\t" + song + " with " + getSongPlays() + " plays");
        }
    }

    private int getSongPlays(){
        return 0;
    }
    public static void main(String[] args) throws FileNotFoundException {
        if(args.length == 2) {
            String filename = args[0];
            try {
                Jukebox jukebox = new Jukebox(filename);
                long seed = Long.parseLong(args[1]);
                Random rnd = new Random(seed);
                jukebox.aMethodForSomething(rnd);
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
