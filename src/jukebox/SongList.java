package jukebox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;

/**
 * This class represents a collection of songs by a single artist
 *
 * @author Atharva Dhupkar
 */
public class SongList {
    /**
     * HashSet of songs
     */
    private HashSet<Song> songSet;
    /**
     * ArrayList of songs
     */
    private ArrayList<Song> songArray;

    /**
     * construstor to set songSet
     * @param songSet HashSet of songs
     */
    public SongList(HashSet<Song> songSet){
         this.songSet = songSet;
    }

    /**
     * removes the given song from the set
     * @param song song to be removed
     */
    public void remove(Song song){
        songSet.remove(song);
    }

    /**
     * checks if songSet contains songSet
     * @param song song to be checked
     * @return true if song is in songSet
     *         false if it is not
     */
    public boolean contains(Song song){
        return songSet.contains(song);
    }

    /**
     * adds a song to songSet
     * @param song song to added
     */
    public void add(Song song){
        songSet.add(song);
    }

    /**
     * sorts the songSet by converting it to an array
     */
    public void sort(){
        songArray = new ArrayList<>(songSet);
        Collections.sort(songArray);

    }

    /**
     * prints every song with number of times it has been played
     */
    public void print(){
        for(Song o : songArray){
            System.out.println("\t\t" + "\"" + o.getName() + "\"" + " with " + o.getNumberOfPlays() + " plays");
        }
    }
}

