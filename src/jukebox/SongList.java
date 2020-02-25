package jukebox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;

public class SongList {
    private HashSet<Song> songSet;
    private ArrayList<Song> songArray;
    public SongList(HashSet<Song> songList){
         this.songSet = songList;
    }
    public void remove(Song song){
        songSet.remove(song);
    }
    public boolean contains(Song song){
        return songSet.contains(song);
    }
    public void add(Song song){
        songSet.add(song);
    }
    public void sort(){
        songArray = new ArrayList<>(songSet);
        Collections.sort(songArray);
    }
    public void print(){
        for(Song o : songArray){
            System.out.println("\t\t" + o.getName() + " with " + o.getNumberOfPlays() + " plays");
        }
    }
}

