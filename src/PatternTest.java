import java.util.ArrayList;
import java.util.List;

class Song {
    String title;
    String artist;
    boolean isPlaying; // дали моментално се пушта
    boolean isPaused;  // дали е паузирана

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
        this.isPlaying = false;
        this.isPaused = false;
    }

    @Override
    public String toString() {
        return "Song{" + "title=" + title + ", artist=" + artist +"}";
    }
}
class MP3Player {
    List<Song> songs;
    int currentIndex;

    public MP3Player(List<Song> songs) {
        this.songs = songs;
        this.currentIndex = 0;
    }

    public void pressPlay() {
        Song currentSong = songs.get(currentIndex);

        if (currentSong.isPlaying) {
            System.out.println("Song is already playing");
        } else {
            currentSong.isPlaying = true;
            currentSong.isPaused = false;
            System.out.println("Song " + currentIndex + " is playing");
        }
    }

    public void pressStop() {
        Song currentSong = songs.get(currentIndex);

        if (currentSong.isPlaying) {
            currentSong.isPlaying = false;
            currentSong.isPaused = true;
            System.out.println("Song " + currentIndex + " is paused");
        } else if (currentSong.isPaused) {
            for (Song s : songs) {
                s.isPlaying = false;
                s.isPaused = false;
            }
            currentIndex = 0;
            System.out.println("Songs are stopped");
        } else {
            System.out.println("Songs are already stopped");
        }
    }

    public void pressFWD() {
        Song currentSong = songs.get(currentIndex);
        if (currentSong.isPlaying) {
            currentSong.isPlaying = false;
            currentSong.isPaused = true;
        }
        currentIndex = (currentIndex + 1) % songs.size();
        System.out.println("Forward...");
    }

    public void pressREW() {
        Song currentSong = songs.get(currentIndex);
        if (currentSong.isPlaying) {
            currentSong.isPlaying = false;
            currentSong.isPaused = true;
        }
        currentIndex = (currentIndex - 1 + songs.size()) % songs.size();
        System.out.println("Reward...");
    }

    public void printCurrentSong() {
        System.out.println(songs.get(currentIndex));
    }

    @Override
    public String toString() {
        return "MP3Player{currentSong = " + currentIndex + ", songList = " + songs + '}';
    }
}
public class PatternTest {
    public static void main(String args[]) {
        List<Song> listSongs = new ArrayList<Song>();
        listSongs.add(new Song("first-title", "first-artist"));
        listSongs.add(new Song("second-title", "second-artist"));
        listSongs.add(new Song("third-title", "third-artist"));
        listSongs.add(new Song("fourth-title", "fourth-artist"));
        listSongs.add(new Song("fifth-title", "fifth-artist"));
        MP3Player player = new MP3Player(listSongs);


        System.out.println(player.toString());
        System.out.println("First test");


        player.pressPlay();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Second test");


        player.pressStop();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Third test");


        player.pressFWD();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
    }
}

//Vasiot kod ovde