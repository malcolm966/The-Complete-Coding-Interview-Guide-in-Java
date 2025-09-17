package coding.challenge.jukebox;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CD {

    private final String title;
    private final String artist;
    private final LocalDate releaseDate;
    private final String genre;
    private final List<Song> songs;

    public CD(String title, String artist, LocalDate releaseDate, String genre) {
        this.title = validateNotEmpty(title, "CD title cannot be null or empty");
        this.artist = validateNotEmpty(artist, "Artist cannot be null or empty");
        this.releaseDate = Objects.requireNonNull(releaseDate, "Release date cannot be null");
        this.genre = validateNotEmpty(genre, "Genre cannot be null or empty");
        this.songs = new ArrayList<>();
    }

    private String validateNotEmpty(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    public void addSong(String title, String artist, String album, Duration duration, int trackNumber) {
        if (songs.stream().anyMatch(song -> song.getTrackNumber() == trackNumber)) {
            throw new IllegalArgumentException("Track number " + trackNumber + " already exists");
        }
        Song song = new Song(title, artist, album, duration, this, trackNumber);
        songs.add(song);
        sortSongsByTrackNumber();
    }

    public void removeSong(int trackNumber) {
        songs.removeIf(song -> song.getTrackNumber() == trackNumber);
    }

    private void sortSongsByTrackNumber() {
        songs.sort((s1, s2) -> Integer.compare(s1.getTrackNumber(), s2.getTrackNumber()));
    }

    public List<Song> getSongs() {
        return Collections.unmodifiableList(songs);
    }

    public Song getSongByTrackNumber(int trackNumber) {
        return songs.stream()
            .filter(song -> song.getTrackNumber() == trackNumber)
            .findFirst()
            .orElse(null);
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public int getTrackCount() {
        return songs.size();
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
        CD cd = (CD) obj;
        return Objects.equals(title, cd.title) &&
               Objects.equals(artist, cd.artist) &&
               Objects.equals(releaseDate, cd.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist, releaseDate);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%d) [%d tracks, %s]",
            title, artist, releaseDate.getYear(), getTrackCount(), getFormattedTotalDuration());
    }
}