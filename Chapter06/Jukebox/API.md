# Jukebox System API Reference

## Quick Start Guide

```java
// 1. Create CDs with songs
CD cd = new CD("Album Name", "Artist", LocalDate.of(2023, 1, 1), "Rock");
cd.addSong("Song Title", "Artist", "Album", Duration.ofMinutes(3), 1);

// 2. Create playlist and add songs
Playlist playlist = new Playlist("My Playlist");
playlist.addSong(cd.getSongs().get(0));

// 3. Create player and jukebox
CDPlayer player = new CDPlayer(playlist, Set.of(cd));
Jukebox jukebox = new Jukebox(player);

// 4. Use the jukebox
jukebox.powerOn();
jukebox.playPauseBtn();
```

## Core Classes

### Song
Immutable representation of a music track.

**Constructor:**
```java
Song(String title, String artist, String album, Duration duration, CD cd, int trackNumber)
```

**Key Methods:**
- `String getTitle()`
- `String getArtist()`
- `String getAlbum()`
- `Duration getDuration()`
- `String getFormattedDuration()` // Returns "M:SS" format
- `int getTrackNumber()`
- `CD getCd()`

### CD
Represents a music album containing songs.

**Constructor:**
```java
CD(String title, String artist, LocalDate releaseDate, String genre)
```

**Key Methods:**
- `void addSong(String title, String artist, String album, Duration duration, int trackNumber)`
- `void removeSong(int trackNumber)`
- `List<Song> getSongs()` // Unmodifiable list
- `Song getSongByTrackNumber(int trackNumber)`
- `int getTrackCount()`
- `Duration getTotalDuration()`
- `String getFormattedTotalDuration()`

### Playlist
Manages ordered collection of songs for playback.

**Constructors:**
```java
Playlist(String name)
Playlist(String name, List<Song> songs)
```

**Key Methods:**
- `void addSong(Song song)`
- `boolean removeSong(Song song)`
- `Song getCurrentSong()`
- `Song getNextSong()` // Advances position
- `Song getPrevSong()` // Goes back
- `void setCurrentSong(Song song)`
- `void shuffle()`
- `void unshuffle()`
- `boolean isShuffled()`
- `boolean isEmpty()`
- `int size()`

### CDPlayer
Core playback engine with state management.

**Constructors:**
```java
CDPlayer(Playlist playlist)
CDPlayer(Playlist playlist, Set<CD> cds)
```

**Key Methods:**
- `void playNextSong()`
- `void playPrevSong()`
- `void playSong(Song song)`
- `void pause()`
- `void resume()`
- `void stop()`
- `void addCD(CD cd)`
- `boolean removeCD(CD cd)`
- `void loadCD(CD cd)`
- `void ejectCD()`
- `PlayerState getState()` // STOPPED, PLAYING, PAUSED
- `boolean isPlaying()`
- `boolean isPaused()`
- `boolean isStopped()`

### Jukebox
User interface facade implementing Selector interface.

**Constructor:**
```java
Jukebox(CDPlayer cdPlayer)
```

**Power Management:**
- `void powerOn()`
- `void powerOff()`
- `boolean isPoweredOn()`

**Playback Controls:**
- `void playPauseBtn()`
- `void stopBtn()`
- `void nextSongBtn()`
- `void prevSongBtn()`
- `void shuffleBtn()`

**Playlist Management:**
- `void addSongToPlaylistBtn(Song song)`
- `void removeSongFromPlaylistBtn(Song song)`

**Display Operations:**
- `void displayStatus()`
- `void displayPlaylist()`
- `void displayCDCollection()`

## Usage Patterns

### Creating and Managing CDs
```java
// Create CD
CD cd = new CD("Abbey Road", "The Beatles",
               LocalDate.of(1969, 9, 26), "Rock");

// Add songs
cd.addSong("Come Together", "The Beatles", "Abbey Road",
           Duration.ofMinutes(4).plusSeconds(19), 1);
cd.addSong("Something", "The Beatles", "Abbey Road",
           Duration.ofMinutes(3).plusSeconds(3), 2);

// Access songs
List<Song> allSongs = cd.getSongs();
Song firstTrack = cd.getSongByTrackNumber(1);
```

### Building Playlists
```java
// Create empty playlist
Playlist playlist = new Playlist("Rock Favorites");

// Add songs from different CDs
for (Song song : cd1.getSongs()) {
    playlist.addSong(song);
}
for (Song song : cd2.getSongs()) {
    playlist.addSong(song);
}

// Navigation
Song current = playlist.getCurrentSong();
Song next = playlist.getNextSong();
```

### Controlling Playback
```java
CDPlayer player = new CDPlayer(playlist, Set.of(cd1, cd2));

// Direct playback control
player.playSong(song);
player.pause();
player.resume();
player.stop();

// Navigation
player.playNextSong();
player.playPrevSong();
```

### Using the Jukebox Interface
```java
Jukebox jukebox = new Jukebox(player);

// Power management
jukebox.powerOn();

// Playback
jukebox.playPauseBtn();  // Toggle play/pause
jukebox.nextSongBtn();   // Next song
jukebox.stopBtn();       // Stop playback

// Playlist management
jukebox.shuffleBtn();    // Toggle shuffle
jukebox.addSongToPlaylistBtn(newSong);

// Information display
jukebox.displayStatus();
jukebox.displayPlaylist();
```

## Error Handling

### Common Exceptions
- `IllegalArgumentException`: Invalid parameters (null, empty strings, negative values)
- `IllegalStateException`: Invalid operations (jukebox powered off)
- `NullPointerException`: Null parameter validation failures

### Validation Rules
- **Strings**: Cannot be null or empty (automatically trimmed)
- **Durations**: Cannot be null
- **Track Numbers**: Must be positive
- **Dates**: Cannot be null
- **Objects**: Cannot be null where required

### Example Error Handling
```java
try {
    jukebox.addSongToPlaylistBtn(song);
    System.out.println("✅ Song added successfully");
} catch (IllegalArgumentException e) {
    System.out.println("❌ Invalid song: " + e.getMessage());
} catch (IllegalStateException e) {
    System.out.println("❌ Operation not allowed: " + e.getMessage());
}
```

## State Management

### CDPlayer States
- **STOPPED**: No playback, no current song
- **PLAYING**: Active playback
- **PAUSED**: Playback paused, can resume

### Jukebox Power States
- **OFF**: All operations disabled except powerOn()
- **ON**: All operations available

### Playlist States
- **Empty**: No songs, currentIndex = -1
- **Normal**: Has songs, valid currentIndex
- **Shuffled**: Random order, isShuffled = true

## Performance Notes

### Time Complexity
- **Song lookup by track**: O(n)
- **Playlist navigation**: O(1)
- **CD song addition**: O(n log n) due to sorting
- **Playlist shuffle**: O(n log n)

### Memory Usage
- Songs store references to parent CDs
- Playlists store references to songs (no duplication)
- Collections return unmodifiable views

### Best Practices
- Reuse Song objects across playlists
- Use unmodifiable collections for safety
- Validate inputs early
- Handle state transitions carefully

## Extension Points

### Custom Implementations
```java
// Custom jukebox interface
class WebJukebox implements Selector {
    // Web-based implementation
}

// Custom playlist types
class SmartPlaylist extends Playlist {
    // Auto-generating playlists
}
```

### Event Handling
```java
// Add listener support
interface JukeboxListener {
    void onSongChanged(Song song);
    void onStateChanged(PlayerState state);
}
```

## Testing Examples

### Unit Test Setup
```java
@Test
void testSongCreation() {
    CD cd = new CD("Test Album", "Test Artist",
                   LocalDate.now(), "Test Genre");

    assertDoesNotThrow(() -> {
        cd.addSong("Test Song", "Test Artist", "Test Album",
                   Duration.ofMinutes(3), 1);
    });

    assertEquals(1, cd.getTrackCount());
}
```

### Integration Test Example
```java
@Test
void testFullJukeboxWorkflow() {
    // Setup
    CD cd = createTestCD();
    Playlist playlist = new Playlist("Test", cd.getSongs());
    CDPlayer player = new CDPlayer(playlist, Set.of(cd));
    Jukebox jukebox = new Jukebox(player);

    // Test workflow
    jukebox.powerOn();
    assertTrue(jukebox.isPoweredOn());

    jukebox.playPauseBtn();
    assertTrue(player.isPlaying());

    jukebox.nextSongBtn();
    assertNotNull(player.getCurrentlyPlayingSong());
}
```