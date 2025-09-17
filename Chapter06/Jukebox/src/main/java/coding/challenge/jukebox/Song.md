# Song Class Documentation

## Overview
The `Song` class represents a single music track in the jukebox system. It's designed as an immutable value object that contains comprehensive metadata about a song.

## Design Principles

### Immutability
- All fields are `final` to prevent modification after construction
- No setter methods provided
- Ensures thread safety and prevents accidental changes

### Validation
- Constructor validates all input parameters
- Null checks for required fields
- Business rule validation (e.g., positive track numbers)
- Throws `IllegalArgumentException` for invalid inputs

## Class Structure

```java
public class Song {
    private final String title;
    private final String artist;
    private final String album;
    private final Duration duration;
    private final CD cd;
    private final int trackNumber;
}
```

## Key Features

### 1. Rich Metadata
- **Title**: Song name with validation
- **Artist**: Performing artist
- **Album**: Album name
- **Duration**: Song length using `java.time.Duration`
- **Track Number**: Position on the album (must be positive)
- **CD Reference**: Reference to parent CD object

### 2. Formatted Output
- `getFormattedDuration()`: Returns duration in "M:SS" format
- `toString()`: Comprehensive display format with all metadata

### 3. Proper Object Methods
- `equals()`: Based on title, artist, album, track number, and CD
- `hashCode()`: Consistent with equals implementation
- `toString()`: User-friendly display format

## Usage Examples

```java
// Create a song (typically done through CD.addSong())
Duration songDuration = Duration.ofMinutes(3).plusSeconds(45);
Song song = new Song("Yesterday", "The Beatles", "Help!",
                     songDuration, cd, 1);

// Access metadata
String title = song.getTitle();
String artist = song.getArtist();
Duration duration = song.getDuration();
String formatted = song.getFormattedDuration(); // "3:45"

// Display song information
System.out.println(song); // "1. Yesterday - The Beatles (Help!) [3:45]"
```

## Validation Rules

### Constructor Validation
1. **Title**: Cannot be null or empty (trimmed)
2. **Artist**: Cannot be null or empty (trimmed)
3. **Album**: Cannot be null or empty (trimmed)
4. **Duration**: Cannot be null
5. **CD**: Cannot be null
6. **Track Number**: Must be positive (> 0)

### Error Handling
- Throws `IllegalArgumentException` with descriptive messages
- Input strings are automatically trimmed
- Clear error messages help identify validation failures

## Integration Points

### CD Relationship
- Every song must belong to a CD
- CD reference enables navigation back to album information
- Prevents orphaned songs in the system

### Playlist Integration
- Songs can be added to playlists
- Equality implementation ensures proper collection behavior
- Immutability prevents playlist corruption

## Design Considerations

### Why Immutable?
1. **Thread Safety**: Multiple threads can safely access song objects
2. **Caching**: Safe to cache and reuse song objects
3. **Collections**: Prevents issues when songs are stored in collections
4. **Data Integrity**: Prevents accidental modification of metadata

### Duration Choice
- Uses `java.time.Duration` instead of integer seconds
- Provides rich API for time calculations
- Type-safe duration handling
- Supports nanosecond precision if needed

### Circular Reference Handling
- Song references CD, but CD manages songs
- Prevents issues in equals/hashCode by being selective about which fields to include
- toString() avoids infinite recursion

## Future Enhancements

### Potential Additions
- **Genre**: Song-specific genre (may differ from album)
- **Year**: Recording year (may differ from album release)
- **Lyrics**: Song lyrics storage
- **Rating**: User rating system
- **Play Count**: Track how often played
- **File Path**: Reference to audio file location

### Metadata Extensions
- **ISRC**: International Standard Recording Code
- **Composer**: Song composer (different from performer)
- **Producer**: Song producer information
- **BPM**: Beats per minute for DJ applications

## Testing Considerations

### Unit Test Coverage
- Constructor validation with various invalid inputs
- Edge cases (empty strings, zero duration, negative track numbers)
- equals/hashCode contract verification
- toString format verification
- Formatted duration edge cases (hours, zero seconds)

### Integration Testing
- Song creation through CD.addSong()
- Playlist operations with songs
- Collection behavior (Set, List operations)