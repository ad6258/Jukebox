package jukebox;

public class Song implements Comparable<Song> {
    private String artist, name;
    @Override
    public int compareTo(Song o) {
        int result = artist.compareTo(o.artist);
        if(result == 0){
            return name.compareTo(o.name);
        }
        return result;
    }
    public Song(String artist, String name){
        this.name = name;
        this.artist = artist;
    }
    public String getArtist(){
        return this.artist;
    }
    public String getName(){
        return this.name;
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof Song){
            Song otherSong = (Song)o;
            return this.artist.equals(otherSong.artist) && this.name.equals(otherSong.name);
        }
        return false;
    }
    @Override
    public int hashCode(){
        return name.hashCode() + artist.hashCode();
    }
    @Override
    public String toString(){
        return name + " by " + artist;
    }
}
