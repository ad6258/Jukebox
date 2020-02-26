package jukebox;

import java.util.*;

/**
 * This class represents a collection of songs by a single artist
 *
 * @author Atharva Dhupkar
 */
public class SongList {
    /**
     * HashSet of songs
     */
    private HashSet<Song> songHashSet;

    /**
     * constructor to set songSet
     * @param songHashSet HashSet of songs
     */
    public SongList(HashSet<Song> songHashSet){
         this.songHashSet = songHashSet;
    }

    /**
     * removes the given song from the set
     * @param song song to be removed
     */
    public void remove(Song song){
        songHashSet.remove(song);
    }

    /**
     * checks if songSet contains songSet
     * @param song song to be checked
     * @return true if song is in songSet
     *         false if it is not
     */
    public boolean contains(Song song){
        return songHashSet.contains(song);
    }

    /**
     * adds a song to songSet
     * @param song song to added
     */
    public void add(Song song){
        songHashSet.add(song);
    }

    /**
     * prints every song with number of times it has been played by converting HashSet to TreeSet
     */
    public void print(){
        TreeSet<Song> songTreeSet = new TreeSet<>(songHashSet);
        for(Song o : songTreeSet){
            System.out.println("\t\t" + "\"" + o.getName() + "\"" + " with " + o.getNumberOfPlays() + " plays");
        }

    }
}

