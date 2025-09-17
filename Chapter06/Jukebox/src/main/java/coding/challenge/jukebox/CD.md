# CD Class Documentation

## Overview
The `CD` class represents a music album in the jukebox system. It manages a collection of songs and provides album-level metadata and operations.

## Design Principles

### Encapsulation
- Private song collection with controlled access
- Validation prevents invalid states
- Public methods provide safe operations

### Composition
- Contains and manages Song objects
- Automatic song-to-CD relationship establishment
- Maintains collection integrity

## Class Structure

```java
public class CD {
    private final String title;
    private final String artist;
    private final LocalDate releaseDate;
    private final String genre;
    private final List<Song> songs;
}
```

## Key Features

### 1. Album Metadata
- **Title**: Album name with validation
- **Artist**: Primary artist/band
- **Release Date**: Using `LocalDate` for type safety
- **Genre**: Musical genre classification

### 2. Song Management
- **Add Songs**: `addSong()` method with full metadata
- **Remove Songs**: `removeSong()` by track number
- **Automatic Sorting**: Songs sorted by track number
- **Duplicate Prevention**: Prevents duplicate track numbers

### 3. Collection Operations
- **Unmodifiable Access**: Returns immutable view of songs
- **Song Lookup**: Find songs by track number
- **Statistics**: Track count and total duration

## Usage Examples

```java
// Create a CD
CD cd = new CD("Abbey Road", "The Beatles",
               LocalDate.of(1969, 9, 26), "Rock");

// Add songs
cd.addSong("Come Together", "The Beatles", "Abbey Road",
           Duration.ofMinutes(4).plusSeconds(19), 1);
cd.addSong("Something", "The Beatles", "Abbey Road",
           Duration.ofMinutes(3).plusSeconds(3), 2);

// Access information
List<Song> songs = cd.getSongs(); // Unmodifiable list
Song song1 = cd.getSongByTrackNumber(1);
int trackCount = cd.getTrackCount();
Duration totalTime = cd.getTotalDuration();
String formattedTime = cd.getFormattedTotalDuration(); // "7:22"

// Display CD information
System.out.println(cd); // "Abbey Road - The Beatles (1969) [2 tracks, 7:22]"
```

## Validation Rules

### Constructor Validation
1. **Title**: Cannot be null or empty (trimmed)
2. **Artist**: Cannot be null or empty (trimmed)
3. **Release Date**: Cannot be null
4. **Genre**: Cannot be null or empty (trimmed)

### Song Management Validation
1. **Track Numbers**: Must be unique within the CD
2. **Song Parameters**: All delegated to Song constructor validation
3. **Removal**: No validation needed (safe operation)

## Key Methods

### Song Management
```java
// Add a new song
public void addSong(String title, String artist, String album,
                   Duration duration, int trackNumber)

// Remove song by track number
public void removeSong(int trackNumber)

// Find specific song
public Song getSongByTrackNumber(int trackNumber)
```

### Collection Access
```java
// Get all songs (unmodifiable)
public List<Song> getSongs()

// Get statistics
public int getTrackCount()
public Duration getTotalDuration()
public String getFormattedTotalDuration()
```

## Design Decisions

### Why ArrayList for Songs?
1. **Ordered**: Maintains track order
2. **Indexed Access**: Fast track number lookups
3. **Dynamic Size**: Can add/remove songs
4. **Sorting**: Easy to sort by track number

### Automatic Sorting
- Songs automatically sorted by track number after addition
- Ensures consistent ordering regardless of addition order
- Provides predictable iteration order

### Song Creation in CD
- CD creates Song objects internally
- Establishes proper CD-Song relationship
- Ensures all songs have valid parent CD reference

### Unmodifiable Collections
- Returns unmodifiable views to prevent external modification
- Maintains encapsulation while allowing access
- Prevents collection corruption

## Error Handling

### Duplicate Track Numbers
```java
// Throws IllegalArgumentException
cd.addSong("Title", "Artist", "Album", duration, 1); // OK
cd.addSong("Other", "Artist", "Album", duration, 1); // Exception!
```

### Invalid Parameters
- All validation delegated to Song constructor
- Clear error messages bubble up
- Maintains CD integrity even if song creation fails

## Integration Points

### Jukebox System Integration
- Used by CDPlayer for CD collection management
- Songs automatically available for playlist addition
- CD metadata displayed in jukebox interface

### Playlist Integration
- Songs from CDs can be added to playlists
- CD reference in songs enables navigation back to album
- Supports mixed-album playlists

## Performance Considerations

### Memory Usage
- Stores only necessary metadata
- Song objects shared (not duplicated)
- Efficient collection operations

### Time Complexity
- Add song: O(n log n) due to sorting
- Remove song: O(n) for removal + O(n log n) for sorting
- Find by track: O(n) linear search
- Get statistics: O(n) for duration calculation

### Optimization Opportunities
- Could use TreeSet for automatic sorting
- Could cache total duration calculation
- Could use HashMap for O(1) track number lookup

## Future Enhancements

### Metadata Extensions
```java
// Potential additional fields
private final String label;           // Record label
private final String catalog;         // Catalog number
private final Set<String> producers;  // Producers
private final String barcode;         // UPC/EAN barcode
private final CoverArt coverArt;      // Album artwork
```

### Advanced Operations
```java
// Potential method additions
public List<Song> getSongsByArtist(String artist);
public Duration getAverageTrackLength();
public boolean hasMultipleArtists();
public void reorderTracks(Map<Integer, Integer> trackMapping);
```

### Validation Enhancements
- Release date validation (not in future)
- Genre validation against known genres
- Track number gap detection
- Minimum/maximum track count validation

## Testing Strategy

### Unit Tests
- Constructor validation with invalid inputs
- Song addition with various scenarios
- Duplicate track number prevention
- Song removal edge cases
- Statistics calculation accuracy
- equals/hashCode contract verification

### Integration Tests
- CD usage in CDPlayer
- Song-to-playlist workflows
- Collection operations in larger system
- Performance with large numbers of songs