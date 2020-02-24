package jukebox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.lang.Integer;
public class Jukebox {

    private int noOfSongs;

    private int noOfSongsPlayed;

    public static int SIMULATION_RUNS = 10;

    private ArrayList<Integer> songsB4Duplicate;

    private HashMap<Song, Integer> playedSong = new HashMap();

    private long avg = 0;

    Collection<Song> jukebox;

    public Jukebox(String filename) throws FileNotFoundException {
        songsB4Duplicate = new ArrayList<>();
        Scanner in = new Scanner(new File(filename));
        noOfSongs = 0;
        readData(in);
        noOfSongsPlayed = 0;
    }

    private void readData(Scanner in){
        jukebox = new TreeSet<Song>();
        while(in.hasNext()) {
            noOfSongs++;
            String line = in.nextLine();
            String[] splitLine = line.split("<SEP>");
            jukebox.add(new Song(splitLine[2], splitLine[3]));
        }
    }

    public void aMethodForSomething(){
        int i = 0, j = 1;
        Integer timesPlayed;

        while(i < SIMULATION_RUNS){
            for(Object thing : jukebox){
                ++noOfSongsPlayed;
                Song song = (Song)thing;
                if(!playedSong.containsKey(thing)){
                    playedSong.put(song, 1);
                }
                else{
                    songsB4Duplicate.add(j);
                    j = 0;
                    timesPlayed = playedSong.get(song);
                    ++timesPlayed;
                    playedSong.remove(song);
                    playedSong.put(song, timesPlayed);
                }
            }
            i++;
            j++;
        }
        for(Integer num : songsB4Duplicate){
            avg += num;
        }
        avg = avg/SIMULATION_RUNS;
    }
    public void print(){
        System.out.println("Jukebox of "+ noOfSongs + " songs starts rockin'...");
        System.out.println("Simulation took 0 second/s");
        System.out.println("Number of simulations run: " + SIMULATION_RUNS);
        System.out.println("Total number of songs played: " + noOfSongsPlayed);
        System.out.println("Average number of songs played per simulation to get duplicate: " + avg);
        System.out.println("Most played song: " );

    }

    public static void main(String[] args) throws FileNotFoundException {
        String filename = args[0];
        Jukebox jukebox = new Jukebox(filename);
        jukebox.aMethodForSomething();
        jukebox.print();
    }
}
