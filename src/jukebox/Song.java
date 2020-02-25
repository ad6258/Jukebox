package jukebox;

public class Song implements Comparable<Song> {
    private String artist, name;
    private int numberOfPlays;
    private int simulationNum;
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
        this.numberOfPlays = 0;
        this.simulationNum = -1;
    }
    public void setSimulationNum(int simulationNum){
        this.simulationNum = simulationNum;
    }
    public int getSimulationNum(){
        return this.simulationNum;
    }
    public Song(String artist, String name, int numberOfPlays){
        this.name = name;
        this.artist = artist;
        this.numberOfPlays = numberOfPlays;
    }
    public int getNumberOfPlays(){
        return numberOfPlays;
    }

    public void incrNumberOfPlays(){
        ++numberOfPlays;
    }
    public void decrNumberOfPlays(){
        --numberOfPlays;
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
