import java.util.*;

// This is the thing we're iterating over. Nothing fancy, just holds a title.
class Video {
    String title;

    public Video(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

// Here's the core idea of the Iterator pattern: any collection that implements
// this interface promises it CAN give you an iterator, without telling you
// how it stores its data internally. Could be an ArrayList, a LinkedList,
// an array, whatever. You don't care. You just call createIterator().
interface Playlist {
    PlaylistIterator createIterator();
}

// The actual playlist. It happens to use an ArrayList under the hood.
// The point of the pattern is that nobody outside this class needs to know
// that.
class YouTubePlaylist implements Playlist {
    private List<Video> videos = new ArrayList<>();

    public void addVideo(Video video) {
        videos.add(video);
    }

    // Instead of exposing the List directly (which would let callers mess
    // with it, or force them to learn how ArrayList iteration works),
    // we hand back an iterator object whose only job is to walk the list.
    @Override
    public PlaylistIterator createIterator() {
        return new YouTubePlaylistIterator(videos);
    }
}

// This is the "how do I move through the collection" contract.
// hasNext() answers "is there more?" and next() gives you the next item.
// That's it. Two methods, and any client using this doesn't need to know
// if it's looping over an array, a list, or a tree underneath.
interface PlaylistIterator {
    boolean hasNext();

    Video next();
}

// The class that actually does the walking. It keeps a private "position"
// pointer so it remembers where it left off between next() calls.
// This is the whole trick of the pattern: the traversal state (position)
// lives HERE, not in the playlist and not in the code using the playlist.
class YouTubePlaylistIterator implements PlaylistIterator {
    private List<Video> videos;
    private Integer position;

    public YouTubePlaylistIterator(List<Video> videos) {
        this.videos = videos;
        position = 0; // start at the beginning, obviously
    }

    // Just checks if there's a video left at the current position.
    @Override
    public boolean hasNext() {
        return position < videos.size();
    }

    // Grabs the current video, then bumps position forward for next time.
    // If someone calls this without checking hasNext() first, we just
    // return null instead of blowing up with an IndexOutOfBounds.
    @Override
    public Video next() {
        return hasNext() ? videos.get(position++) : null;
    }
}

class Main {
    public static void main(String[] args) {
        YouTubePlaylist playlist = new YouTubePlaylist();
        playlist.addVideo(new Video("LLD Tutorial"));
        playlist.addVideo(new Video("System Design Basics"));

        // Notice we're talking to the playlist only through the Playlist
        // and PlaylistIterator interfaces. If tomorrow YouTubePlaylist
        // switches from ArrayList to a LinkedList, this loop doesn't change
        // at all. That's the entire payoff of the pattern.
        PlaylistIterator videoIterator = playlist.createIterator();
        while (videoIterator.hasNext()) {
            System.out.println(videoIterator.next().getTitle());
        }
    }
}