package jukebox;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import static java.lang.System.*;

public class Jukebox {

    private int noOfSongs;

    private int noOfSongsPlayed;

    public static int SIMULATION_RUNS = 50000;

    private ArrayList<Integer> songsB4DuplicateArray;

    private HashMap<String, SongList> jukebox;

    private Song mostPlayedSong;

    private Object[] songArray;

    private long avg = 0;

    private Collection<Song> songCollection;

    private double time;

    int songPlayedLastSim;


    public Jukebox(String filename) throws FileNotFoundException {
        jukebox = new HashMap<>();
        songsB4DuplicateArray = new ArrayList<>();
        Scanner in = new Scanner(new File(filename));
        noOfSongs = 0;
        readData(in);
        songArray = songCollection.toArray();
        noOfSongsPlayed = 0;
        mostPlayedSong = null;
        songPlayedLastSim = 0;
    }

    private void readData(Scanner in){
        songCollection = new TreeSet<>();
        while(in.hasNext()) {
            String line = in.nextLine();
            String[] splitLine = line.split("<SEP>", 4);
            songCollection.add(new Song(splitLine[2], splitLine[3]));
        }
        noOfSongs = songCollection.size();
    }
    private void setMostPlayedSong(Song song){
        if(mostPlayedSong == null){
            mostPlayedSong = song;
        }
        else{
            if(mostPlayedSong.getNumberOfPlays() < song.getNumberOfPlays())
                mostPlayedSong = song;
        }
    }
    public void aMethodForSomething(Random rnd) {
        int i = 0, songsB4duplicate = 0;
        double startTime = currentTimeMillis();
        while (i++ < SIMULATION_RUNS){
            for(int j = 0; j < noOfSongs; j++ ) {
                Song song = (Song) Array.get(this.songArray, rnd.nextInt(this.songArray.length));
                ++noOfSongsPlayed;
                song.incrNumberOfPlays();
                ++songsB4duplicate;
                if (!jukebox.containsKey(song.getArtist())) {
                    song.setSimulationNum(i-1);
                    ArrayList<Song> songArrayList = new ArrayList<>();
                    songArrayList.add(song);
                    SongList newSongList = new SongList(songArrayList);
                    jukebox.put(song.getArtist(), newSongList);
                }
                else {
                    SongList songList = jukebox.get(song.getArtist());

                    if(!songList.contains(song)) {
                        song.setSimulationNum(i-1);
                        songList.add(song);
                    }
                    else{
                        int simNum = song.getSimulationNum();
                        int index = songList.indexOf(song);
                        Song newSong = songList.get(index);
                        newSong.setSimulationNum(i-1);
                        songList.remove(song);
                        songList.add(newSong);
                        if(simNum == i-1){
                            song.decrNumberOfPlays();
                            --songsB4duplicate;
                            --noOfSongsPlayed;
                            avg += songsB4duplicate;
                            songsB4duplicate = 0;
                            j = noOfSongs;
                        }
                    }
                }
                setMostPlayedSong(song);
            }
        }
        double endTime = currentTimeMillis();
        time = (endTime - startTime) / 1000;
        avg += songsB4duplicate;
        avg = avg / SIMULATION_RUNS;
    }
    public void print(){
        out.println("Jukebox of "+ noOfSongs + " songs starts rockin'...");
        out.println("Simulation took "+ time + " second/s");
        out.println("Number of simulations run: " + SIMULATION_RUNS);
        out.println("Total number of songs played: " + noOfSongsPlayed);
        out.println("Average number of songs played per simulation to get duplicate: " + avg);
        out.println("Most played song: " + mostPlayedSong);
        out.println("All songs alphabetically by " + mostPlayedSong.getArtist() + " :");
        jukebox.get(mostPlayedSong.getArtist()).sort();
        SongList songList = jukebox.get(mostPlayedSong.getArtist());
        for(int i = 0; i < songList.getLength(); i++){
            out.println("\t\t" + songList.get(i).getName() + " with " + songList.get(i).getNumberOfPlays() + " plays");
        }
    }

    public static void main(String[] args) {
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
