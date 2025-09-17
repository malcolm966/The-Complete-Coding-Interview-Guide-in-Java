# Playlist Class Documentation

## Overview
The `Playlist` class manages an ordered collection of songs for sequential playback. It provides navigation, shuffle functionality, and maintains current playback position.

## Design Principles

### State Management
- Tracks current song position
- Maintains shuffle state
- Provides predictable navigation

### Collection Management
- Prevents duplicate songs
- Maintains song order
- Supports dynamic modification

## Class Structure

```java
public class Playlist {
    private final String name;
    private final List<Song> songs;
    private int currentIndex;
    private boolean isShuffled;
    private final Random random;
}
```

## Key Features

### 1. Named Playlists
- Each playlist has a unique name
- Validation ensures non-empty names
- Used for identification and display

### 2. Position Tracking
- Maintains current song index
- Supports circular navigation (wraps around)
- Handles empty playlist edge cases

### 3. Shuffle Functionality
- True random shuffle using `Collections.shuffle()`
- Preserves current song position during shuffle
- Toggle between shuffled and original order

### 4. Navigation Operations
- Next/previous song with wrap-around
- Direct song selection
- Current song access

## Usage Examples

```java
// Create playlist
Playlist playlist = new Playlist("My Favorites");

// Alternative: Create with initial songs
List<Song> initialSongs = Arrays.asList(song1, song2, song3);
Playlist playlist2 = new Playlist("Rock Classics", initialSongs);

// Add/remove songs
playlist.addSong(song1);
playlist.addSong(song2);
boolean removed = playlist.removeSong(song1);

// Navigation
Song current = playlist.getCurrentSong();
Song next = playlist.getNextSong();      // Advances and returns
Song prev = playlist.getPrevSong();      // Goes back and returns

// Shuffle operations
playlist.shuffle();                       // Enable shuffle
playlist.unshuffle();                     // Restore original order

// Information
boolean empty = playlist.isEmpty();
int size = playlist.size();
Duration total = playlist.getTotalDuration();
String formatted = playlist.getFormattedTotalDuration();
```

## Key Methods

### Construction
```java
public Playlist(String name)                    // Empty playlist
public Playlist(String name, List<Song> songs) // Pre-populated playlist
```

### Song Management
```java
public void addSong(Song song)           // Add song (no duplicates)
public boolean removeSong(Song song)     // Remove song, returns success
```

### Navigation
```java
public Song getCurrentSong()             // Get current song
public Song getNextSong()               // Advance and get next
public Song getPrevSong()               // Go back and get previous
public void setCurrentSong(Song song)   // Jump to specific song
```

### Shuffle Operations
```java
public void shuffle()                    // Enable random order
public void unshuffle()                  // Restore original order
public boolean isShuffled()             // Check shuffle state
```

## State Management Details

### Current Index Behavior
- **Empty Playlist**: currentIndex = -1
- **Adding First Song**: currentIndex = 0
- **Removing Current Song**: Adjusts index to maintain valid position
- **Navigation**: Wraps around (circular behavior)

### Shuffle Implementation
```java
public void shuffle() {
    if (songs.size() <= 1) return;

    Song currentSong = getCurrentSong();
    Collections.shuffle(songs, random);
    isShuffled = true;

    // Maintain current song position
    if (currentSong != null) {
        currentIndex = songs.indexOf(currentSong);
    }
}
```

### Unshuffle Implementation
```java
public void unshuffle() {
    isShuffled = false;
    songs.sort((s1, s2) -> {
        // Sort by CD title first, then track number
        int cdComparison = s1.getCd().getTitle().compareTo(s2.getCd().getTitle());
        if (cdComparison != 0) return cdComparison;
        return Integer.compare(s1.getTrackNumber(), s2.getTrackNumber());
    });

    // Update current position
    Song currentSong = getCurrentSong();
    if (currentSong != null) {
        currentIndex = songs.indexOf(currentSong);
    }
}
```

## Navigation Behavior

### Circular Navigation
- **Next from Last**: Goes to first song
- **Previous from First**: Goes to last song
- **Empty Playlist**: Returns null

### Edge Cases
```java
// Empty playlist
playlist.getNextSong();     // Returns null
playlist.getPrevSong();     // Returns null
playlist.getCurrentSong();  // Returns null

// Single song
playlist.getNextSong();     // Returns same song
playlist.getPrevSong();     // Returns same song
```

## Validation and Error Handling

### Constructor Validation
- **Name**: Cannot be null or empty (trimmed)
- **Songs List**: Cannot be null (but can be empty)

### Method Validation
- **addSong()**: Null check, duplicate prevention
- **removeSong()**: Null check, graceful handling if not found
- **setCurrentSong()**: Must exist in playlist

### State Consistency
- Index always valid for non-empty playlists
- Shuffle state correctly maintained
- Collections remain consistent after operations

## Design Decisions

### Why ArrayList for Songs?
1. **Indexed Access**: Fast access by position
2. **Ordered**: Maintains insertion/shuffle order
3. **Dynamic**: Supports add/remove operations
4. **Performance**: Good for sequential access patterns

### Duplicate Prevention
- Uses `List.contains()` to check for duplicates
- Prevents same song appearing multiple times
- Based on Song's equals() implementation

### Shuffle Strategy
- Uses `Collections.shuffle()` with seeded Random
- Preserves current song position across shuffle
- Maintains reference to current song during reordering

### Unshuffle Strategy
- Sorts by CD title first, then track number
- Provides predictable "original" order
- Handles mixed-album playlists sensibly

## Performance Characteristics

### Time Complexity
- **addSong()**: O(n) for duplicate check
- **removeSong()**: O(n) for search and removal
- **getNextSong()/getPrevSong()**: O(1)
- **shuffle()**: O(n log n)
- **unshuffle()**: O(n log n)

### Space Complexity
- O(n) where n is number of songs
- Additional O(1) for state variables

### Optimization Opportunities
- Use HashSet for O(1) duplicate detection
- Cache total duration calculation
- Use LinkedList for frequent add/remove operations

## Integration Points

### CDPlayer Integration
- CDPlayer uses playlist for playback sequence
- Navigation methods called by player controls
- State changes reflected in player behavior

### Jukebox Interface
- Playlist displayed in jukebox UI
- Shuffle button toggles playlist shuffle state
- Add/remove operations triggered from jukebox

## Future Enhancements

### Advanced Features
```java
// Repeat modes
public enum RepeatMode { NONE, ONE, ALL }
private RepeatMode repeatMode;

// Smart shuffle (avoid artist clustering)
public void smartShuffle();

// Playlist statistics
public Map<String, Integer> getArtistCounts();
public Duration getAverageTrackLength();

// Playlist persistence
public void saveToFile(String filename);
public static Playlist loadFromFile(String filename);
```

### User Experience
```java
// Recently played tracking
private Queue<Song> recentlyPlayed;

// Skip counting
private Map<Song, Integer> skipCounts;

// Playlist recommendations
public List<Song> getRecommendedSongs();
```

## Testing Strategy

### Unit Tests
- Empty playlist behavior
- Single song playlist edge cases
- Navigation wrap-around testing
- Shuffle/unshuffle state verification
- Add/remove operations with various states
- Duplicate prevention testing

### Integration Tests
- Playlist usage in CDPlayer
- Jukebox control integration
- Large playlist performance
- Concurrent access scenarios (if multithreaded)