package jukebox;

/**
 * Song represents a song in a jukebox with name, artist, number of times t was played and the simulation
 * it was last played in
 *
 * @author Atharva Dhupkar
 */
public class Song implements Comparable<Song> {
    /**
     * name and artist of a song
     */
    private String artist, name;
    /**
     * number of times the song was played
     */
    private int numberOfPlays;
    /**
     * the simulation number the song was last played in
     */
    private int simulationNum;

    /**
     * constructor to initialize all the fields
     * numberOfPlays if 0 to start with
     * simulationNum is -1 to start with
     * @param artist
     * @param name
     */
    public Song(String artist, String name){
        this.name = name;
        this.artist = artist;
        this.numberOfPlays = 0;
        this.simulationNum = -1;
    }

    /**
     * compareTo compares artists first and then song names
     * @param o Song to be compared to
     * @return int 0 if both songs are same
     *         int <0 if this is smaller than o
     *         int >0 if this is bigger than o
     */
    @Override
    public int compareTo(Song o) {
        int result = artist.compareTo(o.artist);
        if(result == 0){
            return name.compareTo(o.name);
        }
        return result;
    }

    /**
     * sets the simulationNum
     * @param simulationNum simulation number
     */
    public void setSimulationNum(int simulationNum){
        this.simulationNum = simulationNum;
    }

    /**
     * returns the simulation number of the song
     * @return simulationNum
     */
    public int getSimulationNum(){
        return this.simulationNum;
    }

    /**
     * returns the number of times this song was played
     * @return numberOfPlays
     */
    public int getNumberOfPlays(){
        return numberOfPlays;
    }

    /**
     * increments numberOfPlays
     */
    public void incrNumberOfPlays(){
        ++numberOfPlays;
    }

    /**
     * decrements numberOfPlays
     */
    public void decrNumberOfPlays(){
        --numberOfPlays;
    }

    /**
     * returns artist
     * @return artist
     */
    public String getArtist(){
        return this.artist;
    }

    /**
     * returns name
     * @return name
     */
    public String getName(){
        return this.name;
    }

    /**
     * if two songs have same name and artist they're equal
     * @param o other song
     * @return true if this and o are equal
     *         false if not
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof Song){
            Song otherSong = (Song)o;
            return this.artist.equals(otherSong.artist) && this.name.equals(otherSong.name);
        }
        return false;
    }

    /**
     * calculates hashCode and returns it by adding hashCode of artist and hashCode of name
     * @return int hash code
     */
    @Override
    public int hashCode(){
        return name.hashCode() + artist.hashCode();
    }

    /**
     * returns song in the form ""(song name)" by "(artist)""
     * @return String to represent song
     */
    @Override
    public String toString(){
        return "\"" + name + "\"" + " by " + "\"" + artist + "\"" ;
    }
}
