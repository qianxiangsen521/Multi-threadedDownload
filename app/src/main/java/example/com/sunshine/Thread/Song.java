package example.com.sunshine.Thread;

/**
 * Created by qianxiangsen on 2017/4/17.
 */

public class Song implements Comparable<Song> {

    private String title;
    private String artist;



//    @Override
//    public boolean equals(Object aSong) {
//        Song s = (Song)aSong;
//        return getTitle().equals(s.getTitle());
//    }



    public Song(String t,String a ){
        title = t;
        artist = a;
    }
    //HashSet 通过 hashCode 来排除重复
//    @Override
//    public int hashCode() {
//        return title.hashCode();
//    }

    public String getArtist() {
        return artist;
    }


    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
    public int compareTo(Song o){

        return title.compareTo(o.getTitle());
    }
}
