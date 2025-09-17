package coding.challenge.jukebox;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CDPlayer {

    public enum PlayerState {
        STOPPED, PLAYING, PAUSED
    }

    private CD currentCD;
    private final Set<CD> cds;
    private final Playlist playlist;
    private PlayerState state;
    private Song currentlyPlayingSong;

    public CDPlayer(Playlist playlist, Set<CD> cds) {
        this.playlist = Objects.requireNonNull(playlist, "Playlist cannot be null");
        this.cds = new HashSet<>(Objects.requireNonNull(cds, "CDs set cannot be null"));
        this.state = PlayerState.STOPPED;
        this.currentlyPlayingSong = null;
        this.currentCD = null;
    }

    public CDPlayer(Playlist playlist) {
        this(playlist, new HashSet<>());
    }

    public void playNextSong() {
        if (playlist.isEmpty()) {
            System.out.println("Playlist is empty. Cannot play next song.");
            return;
        }

        Song nextSong = playlist.getNextSong();
        if (nextSong != null) {
            playSong(nextSong);
        }
    }

    public void playPrevSong() {
        if (playlist.isEmpty()) {
            System.out.println("Playlist is empty. Cannot play previous song.");
            return;
        }

        Song prevSong = playlist.getPrevSong();
        if (prevSong != null) {
            playSong(prevSong);
        }
    }

    public void playSong(Song song) {
        Objects.requireNonNull(song, "Song cannot be null");

        if (!playlist.getSongs().contains(song)) {
            throw new IllegalArgumentException("Song not found in current playlist");
        }

        playlist.setCurrentSong(song);
        currentlyPlayingSong = song;
        currentCD = song.getCd();
        state = PlayerState.PLAYING;

        System.out.println("Now playing: " + song);
    }

    public void pause() {
        if (state == PlayerState.PLAYING) {
            state = PlayerState.PAUSED;
            System.out.println("Playback paused: " +
                (currentlyPlayingSong != null ? currentlyPlayingSong.getTitle() : "Unknown"));
        } else {
            System.out.println("No song is currently playing to pause.");
        }
    }

    public void resume() {
        if (state == PlayerState.PAUSED && currentlyPlayingSong != null) {
            state = PlayerState.PLAYING;
            System.out.println("Resuming playback: " + currentlyPlayingSong.getTitle());
        } else {
            System.out.println("No paused song to resume.");
        }
    }

    public void stop() {
        state = PlayerState.STOPPED;
        currentlyPlayingSong = null;
        System.out.println("Playback stopped.");
    }

    public void addCD(CD cd) {
        Objects.requireNonNull(cd, "CD cannot be null");
        cds.add(cd);
        System.out.println("CD added to collection: " + cd.getTitle());
    }

    public boolean removeCD(CD cd) {
        Objects.requireNonNull(cd, "CD cannot be null");

        if (cd.equals(currentCD)) {
            stop();
            currentCD = null;
        }

        boolean removed = cds.remove(cd);
        if (removed) {
            System.out.println("CD removed from collection: " + cd.getTitle());

            playlist.getSongs().stream()
                .filter(song -> song.getCd().equals(cd))
                .forEach(playlist::removeSong);
        }

        return removed;
    }

    public void loadCD(CD cd) {
        Objects.requireNonNull(cd, "CD cannot be null");

        if (!cds.contains(cd)) {
            throw new IllegalArgumentException("CD not found in collection. Add it first.");
        }

        currentCD = cd;
        System.out.println("CD loaded: " + cd.getTitle());
    }

    public void ejectCD() {
        if (currentCD != null) {
            if (state == PlayerState.PLAYING || state == PlayerState.PAUSED) {
                stop();
            }
            System.out.println("CD ejected: " + currentCD.getTitle());
            currentCD = null;
        } else {
            System.out.println("No CD to eject.");
        }
    }

    public void displayCurrentStatus() {
        System.out.println("=== CD Player Status ===");
        System.out.println("State: " + state);
        System.out.println("Current CD: " + (currentCD != null ? currentCD.getTitle() : "None"));
        System.out.println("Currently Playing: " +
            (currentlyPlayingSong != null ? currentlyPlayingSong.getTitle() : "None"));
        System.out.println("Playlist: " + playlist.getName() + " (" + playlist.size() + " songs)");
        System.out.println("CDs in collection: " + cds.size());
        System.out.println("========================");
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public CD getCurrentCD() {
        return currentCD;
    }

    public Set<CD> getCds() {
        return Collections.unmodifiableSet(cds);
    }

    public PlayerState getState() {
        return state;
    }

    public Song getCurrentlyPlayingSong() {
        return currentlyPlayingSong;
    }

    public boolean isPlaying() {
        return state == PlayerState.PLAYING;
    }

    public boolean isPaused() {
        return state == PlayerState.PAUSED;
    }

    public boolean isStopped() {
        return state == PlayerState.STOPPED;
    }
}