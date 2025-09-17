package coding.challenge.jukebox;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Playlist {

    private final String name;
    private final List<Song> songs;
    private int currentIndex;
    private boolean isShuffled;
    private final Random random;

    public Playlist(String name) {
        this.name = validateNotEmpty(name, "Playlist name cannot be null or empty");
        this.songs = new ArrayList<>();
        this.currentIndex = -1;
        this.isShuffled = false;
        this.random = new Random();
    }

    public Playlist(String name, List<Song> songs) {
        this.name = validateNotEmpty(name, "Playlist name cannot be null or empty");
        this.songs = new ArrayList<>(Objects.requireNonNull(songs, "Songs list cannot be null"));
        this.currentIndex = songs.isEmpty() ? -1 : 0;
        this.isShuffled = false;
        this.random = new Random();
    }

    private String validateNotEmpty(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    public void addSong(Song song) {
        Objects.requireNonNull(song, "Song cannot be null");
        if (!songs.contains(song)) {
            songs.add(song);
            if (currentIndex == -1) {
                currentIndex = 0;
            }
        }
    }

    public boolean removeSong(Song song) {
        Objects.requireNonNull(song, "Song cannot be null");
        int index = songs.indexOf(song);
        if (index == -1) {
            return false;
        }

        songs.remove(index);

        if (songs.isEmpty()) {
            currentIndex = -1;
        } else if (index < currentIndex || currentIndex >= songs.size()) {
            currentIndex = Math.max(0, Math.min(currentIndex - 1, songs.size() - 1));
        }

        return true;
    }

    public void shuffle() {
        if (songs.size() <= 1) {
            return;
        }

        Song currentSong = getCurrentSong();
        Collections.shuffle(songs, random);
        isShuffled = true;

        if (currentSong != null) {
            currentIndex = songs.indexOf(currentSong);
        }
    }

    public void unshuffle() {
        isShuffled = false;
        songs.sort((s1, s2) -> {
            int cdComparison = s1.getCd().getTitle().compareTo(s2.getCd().getTitle());
            if (cdComparison != 0) {
                return cdComparison;
            }
            return Integer.compare(s1.getTrackNumber(), s2.getTrackNumber());
        });

        Song currentSong = getCurrentSong();
        if (currentSong != null) {
            currentIndex = songs.indexOf(currentSong);
        }
    }

    public Song getNextSong() {
        if (songs.isEmpty()) {
            return null;
        }

        currentIndex = (currentIndex + 1) % songs.size();
        return songs.get(currentIndex);
    }

    public Song getPrevSong() {
        if (songs.isEmpty()) {
            return null;
        }

        currentIndex = (currentIndex - 1 + songs.size()) % songs.size();
        return songs.get(currentIndex);
    }

    public Song getCurrentSong() {
        if (songs.isEmpty() || currentIndex < 0 || currentIndex >= songs.size()) {
            return null;
        }
        return songs.get(currentIndex);
    }

    public void setCurrentSong(Song song) {
        Objects.requireNonNull(song, "Song cannot be null");
        int index = songs.indexOf(song);
        if (index == -1) {
            throw new IllegalArgumentException("Song not found in playlist");
        }
        currentIndex = index;
    }

    public boolean hasNext() {
        return !songs.isEmpty();
    }

    public boolean hasPrevious() {
        return !songs.isEmpty();
    }

    public String getName() {
        return name;
    }

    public List<Song> getSongs() {
        return Collections.unmodifiableList(songs);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public boolean isEmpty() {
        return songs.isEmpty();
    }

    public int size() {
        return songs.size();
    }

    public boolean isShuffled() {
        return isShuffled;
    }

    public Duration getTotalDuration() {
        return songs.stream()
            .map(Song::getDuration)
            .reduce(Duration.ZERO, Duration::plus);
    }

    public String getFormattedTotalDuration() {
        Duration total = getTotalDuration();
        long totalMinutes = total.toMinutes();
        long seconds = total.getSeconds() % 60;
        return String.format("%d:%02d", totalMinutes, seconds);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Playlist playlist = (Playlist) obj;
        return Objects.equals(name, playlist.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("Playlist: %s [%d songs, %s]%s",
            name, size(), getFormattedTotalDuration(), isShuffled ? " (shuffled)" : "");
    }
}