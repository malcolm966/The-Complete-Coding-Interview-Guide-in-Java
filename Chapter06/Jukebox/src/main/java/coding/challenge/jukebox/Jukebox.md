# Jukebox Class Documentation

## Overview
The `Jukebox` class serves as the primary user interface for the jukebox system. It implements the `Selector` interface and provides a rich, interactive experience with power management, user feedback, and comprehensive control options.

## Design Principles

### Facade Pattern
- Provides simplified interface to complex CDPlayer operations
- Hides implementation details from users
- Centralizes user interaction logic

### State Management
- Power on/off state management
- Prevents operations when powered off
- Clear state transitions and feedback

### User Experience Focus
- Rich console output with emojis and formatting
- Informative status displays
- Clear error messages and confirmations

## Class Structure

```java
public class Jukebox implements Selector {
    private final CDPlayer cdPlayer;
    private boolean isPoweredOn;
}
```

## Key Features

### 1. Power Management
- **Power On/Off**: Complete system power control
- **State Protection**: Prevents operations when powered off
- **Auto-Stop**: Stops playback when powering off

### 2. User Interface Controls
- **Selector Interface**: Standard jukebox button operations
- **Extended Controls**: Additional play/pause and stop functionality
- **Display Operations**: Status, playlist, and collection viewing

### 3. Rich User Feedback
- **Emojis**: Visual indicators for different operations
- **Formatted Output**: Well-structured status displays
- **Progress Indicators**: Clear operation feedback

## Interface Implementation (Selector)

```java
public interface Selector {
    void nextSongBtn();
    void prevSongBtn();
    void addSongToPlaylistBtn(Song song);
    void removeSongFromPlaylistBtn(Song song);
    void shuffleBtn();
}
```

## Usage Examples

```java
// Basic setup
CDPlayer player = new CDPlayer(playlist, cdSet);
Jukebox jukebox = new Jukebox(player);

// Power on and basic operations
jukebox.powerOn();                    // Powers on, shows status
jukebox.playPauseBtn();              // Start playback
jukebox.nextSongBtn();               // Next song
jukebox.prevSongBtn();               // Previous song
jukebox.shuffleBtn();                // Toggle shuffle

// Display operations
jukebox.displayStatus();             // Show full status
jukebox.displayPlaylist();           // Show current playlist
jukebox.displayCDCollection();       // Show CD collection

// Playlist management
jukebox.addSongToPlaylistBtn(song);    // Add song
jukebox.removeSongFromPlaylistBtn(song); // Remove song

// Power management
jukebox.powerOff();                  // Power off system
```

## Key Methods

### Power Management
```java
public void powerOn()                // Power on jukebox
public void powerOff()               // Power off jukebox
public boolean isPoweredOn()         // Check power state
```

### Playback Controls
```java
public void playPauseBtn()           // Toggle play/pause
public void stopBtn()                // Stop playback
public void nextSongBtn()            // Next song (Selector)
public void prevSongBtn()            // Previous song (Selector)
public void shuffleBtn()             // Toggle shuffle (Selector)
```

### Playlist Management
```java
public void addSongToPlaylistBtn(Song song)      // Add to playlist (Selector)
public void removeSongFromPlaylistBtn(Song song) // Remove from playlist (Selector)
```

### Display Operations
```java
public void displayStatus()          // Show system status
public void displayPlaylist()        // Show current playlist
public void displayCDCollection()    // Show CD collection
```

## State Management Details

### Power State Behavior
```java
private void checkPowerState() {
    if (!isPoweredOn) {
        throw new IllegalStateException("Jukebox is powered off. Please power on first.");
    }
}
```

### Power On Sequence
1. Set power state to true
2. Display welcome message
3. Show initial status
4. Enable all operations

### Power Off Sequence
1. Stop any current playback
2. Set power state to false
3. Display shutdown message
4. Disable all operations

## User Interface Design

### Console Output Formatting
```java
// Status display with borders
System.out.println("=".repeat(50));
System.out.println("🎵 JUKEBOX STATUS");
System.out.println("=".repeat(50));

// Playlist display with markers
String marker = song.equals(currentSong) ? "► " : "  ";
System.out.printf("%s%2d. %s%n", marker, index++, song);
```

### Emoji Usage
- 🎵 General music operations
- ⏯️ Play/pause operations
- ⏹️ Stop operations
- 🔀 Shuffle operations
- ✅ Success confirmations
- ❌ Error messages
- 📋 Playlist displays
- 💿 CD collection displays

### Error Handling
```java
try {
    cdPlayer.getPlaylist().addSong(song);
    System.out.println("✅ Song added to playlist: " + song.getTitle());
} catch (Exception e) {
    System.out.println("❌ Failed to add song to playlist: " + e.getMessage());
}
```

## Integration Points

### CDPlayer Integration
- Delegates all core operations to CDPlayer
- Maintains reference to CDPlayer instance
- Provides facade for complex operations

### User Integration
- User class holds reference to Jukebox
- Provides single point of interaction
- Encapsulates all user-facing functionality

## Design Decisions

### Why Implement Selector?
1. **Interface Segregation**: Clean contract for jukebox operations
2. **Extensibility**: Easy to create different jukebox implementations
3. **Testing**: Interface enables easy mocking
4. **Polymorphism**: Can treat different jukeboxes uniformly

### Power State Management
- Prevents undefined behavior when system is off
- Realistic simulation of physical jukebox
- Clear error messages for invalid operations
- Automatic cleanup on shutdown

### Rich User Feedback
- Enhances user experience with visual indicators
- Clear success/failure feedback
- Detailed status information
- Professional console application feel

### Method Naming Convention
- Button-style naming (`nextSongBtn()`, `playPauseBtn()`)
- Matches physical jukebox interface
- Clear intent and functionality
- Consistent with Selector interface

## Performance Considerations

### Console Output
- Extensive console output may impact performance
- Consider logging levels for production use
- Formatted strings create temporary objects

### State Checks
- Power state check on every operation
- Minimal overhead but called frequently
- Could be optimized with state pattern

## Error Handling Strategy

### Power State Errors
```java
// Throws IllegalStateException for unpowered operations
checkPowerState();
```

### Operation Errors
```java
// Graceful handling with user feedback
try {
    // Operation
    System.out.println("✅ Success message");
} catch (Exception e) {
    System.out.println("❌ Error: " + e.getMessage());
}
```

### Null Parameter Handling
```java
// Validation with clear error messages
Objects.requireNonNull(song, "Song cannot be null");
```

## Future Enhancements

### UI Improvements
```java
// Color support
public void setColorMode(boolean enabled);

// Sound effects
public void playSoundEffect(SoundEffect effect);

// Progress bars
public void showProgress(String operation, int percent);
```

### Advanced Controls
```java
// Volume control
public void volumeUp();
public void volumeDown();
public void setVolume(int level);

// Repeat modes
public void toggleRepeatMode();

// Search functionality
public void searchSongs(String query);
```

### Configuration
```java
// User preferences
public void setDisplayPreferences(DisplayPrefs prefs);
public void saveUserSettings();
public void loadUserSettings();
```

### Remote Control
```java
// Network interface
public void enableRemoteControl(int port);
public void processRemoteCommand(RemoteCommand cmd);
```

## Testing Strategy

### Unit Tests
- Power state transitions
- All button operations
- Error handling scenarios
- Display method outputs
- Null parameter handling

### Integration Tests
- Full user workflow testing
- CDPlayer interaction verification
- Complex operation sequences
- Error recovery scenarios

### User Experience Tests
- Output format verification
- Message clarity assessment
- Navigation flow testing
- Edge case user scenarios

## Security Considerations

### Input Validation
- All parameters validated
- Null checks prevent NullPointerException
- Power state prevents unauthorized operations

### State Protection
- Power state cannot be bypassed
- Operations atomic where possible
- Consistent state maintenance

## Documentation Notes

This class serves as the primary entry point for user interaction with the jukebox system. It emphasizes user experience while maintaining clean separation of concerns with the underlying CDPlayer implementation.