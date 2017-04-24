package example.com.sunshine.Thread;

import java.util.Comparator;

/**
 * Created by qianxiangsen on 2017/4/17.
 */

public class ArtistCompare implements Comparator<Song> {

    @Override
    public int compare(Song one, Song two) {
        return one.getArtist().compareTo(two.getArtist());
    }
}
