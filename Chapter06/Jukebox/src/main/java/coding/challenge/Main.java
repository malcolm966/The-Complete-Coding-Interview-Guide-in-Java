package coding.challenge;

import coding.challenge.jukebox.CD;
import coding.challenge.jukebox.CDPlayer;
import coding.challenge.jukebox.Jukebox;
import coding.challenge.jukebox.Playlist;
import coding.challenge.user.User;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        System.out.println("🎵 Digital Jukebox System Demo 🎵\n");

        CD cd1 = new CD("Abbey Road", "The Beatles", LocalDate.of(1969, 9, 26), "Rock");
        cd1.addSong("Come Together", "The Beatles", "Abbey Road", Duration.ofMinutes(4).plusSeconds(19), 1);
        cd1.addSong("Something", "The Beatles", "Abbey Road", Duration.ofMinutes(3).plusSeconds(3), 2);
        cd1.addSong("Maxwell's Silver Hammer", "The Beatles", "Abbey Road", Duration.ofMinutes(3).plusSeconds(27), 3);

        CD cd2 = new CD("Dark Side of the Moon", "Pink Floyd", LocalDate.of(1973, 3, 1), "Progressive Rock");
        cd2.addSong("Speak to Me", "Pink Floyd", "Dark Side of the Moon", Duration.ofMinutes(1).plusSeconds(13), 1);
        cd2.addSong("Breathe", "Pink Floyd", "Dark Side of the Moon", Duration.ofMinutes(2).plusSeconds(43), 2);
        cd2.addSong("On the Run", "Pink Floyd", "Dark Side of the Moon", Duration.ofMinutes(3).plusSeconds(36), 3);

        Playlist rockPlaylist = new Playlist("Rock Classics");
        for (var song : cd1.getSongs()) {
            rockPlaylist.addSong(song);
        }
        for (var song : cd2.getSongs()) {
            rockPlaylist.addSong(song);
        }

        CDPlayer cdPlayer = new CDPlayer(rockPlaylist, Set.of(cd1, cd2));
        Jukebox jukebox = new Jukebox(cdPlayer);
        User user = new User(jukebox);

        demonstrateJukeboxFunctionality(user);
    }

    private static void demonstrateJukeboxFunctionality(User user) {
        Jukebox jukebox = user.getJukebox();

        System.out.println("Demo: Powering on the jukebox...");
        jukebox.powerOn();

        System.out.println("\nDemo: Displaying playlist...");
        jukebox.displayPlaylist();

        System.out.println("Demo: Displaying CD collection...");
        jukebox.displayCDCollection();

        System.out.println("Demo: Playing first song...");
        jukebox.playPauseBtn();

        System.out.println("\nDemo: Playing next song...");
        jukebox.nextSongBtn();

        System.out.println("\nDemo: Pausing playback...");
        jukebox.playPauseBtn();

        System.out.println("\nDemo: Resuming playback...");
        jukebox.playPauseBtn();

        System.out.println("\nDemo: Shuffling playlist...");
        jukebox.shuffleBtn();
        jukebox.displayPlaylist();

        System.out.println("Demo: Unshuffling playlist...");
        jukebox.shuffleBtn();
        jukebox.displayPlaylist();

        System.out.println("Demo: Stopping playback...");
        jukebox.stopBtn();

        System.out.println("\nDemo: Final status check...");
        jukebox.displayStatus();

        System.out.println("Demo: Powering off jukebox...");
        jukebox.powerOff();

        System.out.println("\n🎵 Demo completed! 🎵");
    }
}