package jukebox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class SongList {
    private ArrayList<Song> songList;
    public SongList(ArrayList<Song> songList){
         this.songList = songList;
    }
    public ArrayList getSongList(){
        return songList;
    }
    public void setSongList(ArrayList<Song> songList){
        this.songList = songList;
    }
//    public void addToSongList(Song song){
//        if(!songList.contains(song))
//            songList.add(song);
//        else {
//            int index = songList.indexOf(song);
//            Song newSong = songList.get(index);
//            newSong.incrNumberOfPlays();
//            songList.remove(song);
//            songList.add(newSong);
//        }
//    }
    public void remove(Song song){
        songList.remove(song);
    }
    public boolean contains(Song song){
        return songList.contains(song);
    }
    public int indexOf(Song song){
        return songList.indexOf(song);
    }
    public Song get(int index){
        return songList.get(index);
    }
    public void add(Song song){
        songList.add(song);
    }
    public void sort(){
        Collections.sort(songList);
    }
    public int getLength(){
        return songList.size();
    }

}

