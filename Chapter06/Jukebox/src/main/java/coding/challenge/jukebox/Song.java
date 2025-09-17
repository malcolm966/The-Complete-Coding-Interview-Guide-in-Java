package coding.challenge.jukebox;

import java.time.Duration;
import java.util.Objects;

public class Song {

    private final String title;
    private final String artist;
    private final String album;
    private final Duration duration;
    private final CD cd;
    private final int trackNumber;

    public Song(String title, String artist, String album, Duration duration, CD cd, int trackNumber) {
        this.title = validateNotEmpty(title, "Title cannot be null or empty");
        this.artist = validateNotEmpty(artist, "Artist cannot be null or empty");
        this.album = validateNotEmpty(album, "Album cannot be null or empty");
        this.duration = Objects.requireNonNull(duration, "Duration cannot be null");
        this.cd = Objects.requireNonNull(cd, "CD cannot be null");
        this.trackNumber = validateTrackNumber(trackNumber);
    }

    private String validateNotEmpty(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    private int validateTrackNumber(int trackNumber) {
        if (trackNumber <= 0) {
            throw new IllegalArgumentException("Track number must be positive");
        }
        return trackNumber;
    }

    public CD getCd() {
        return cd;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public Duration getDuration() {
        return duration;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public String getFormattedDuration() {
        long totalSeconds = duration.getSeconds();
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Song song = (Song) obj;
        return trackNumber == song.trackNumber &&
               Objects.equals(title, song.title) &&
               Objects.equals(artist, song.artist) &&
               Objects.equals(album, song.album) &&
               Objects.equals(cd, song.cd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist, album, trackNumber, cd);
    }

    @Override
    public String toString() {
        return String.format("%d. %s - %s (%s) [%s]",
            trackNumber, title, artist, album, getFormattedDuration());
    }
}