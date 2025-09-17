# Jukebox System Design Documentation

## Overview

This document describes the design and architecture of a digital jukebox system implemented in Java. The system simulates a traditional jukebox that can play music from a collection of CDs, manage playlists, and provide user interaction through various controls.

## Table of Contents

1. [System Architecture](#system-architecture)
2. [Design Patterns](#design-patterns)
3. [Class Descriptions](#class-descriptions)
4. [Key Features](#key-features)
5. [Usage Examples](#usage-examples)
6. [Extension Points](#extension-points)

## System Architecture

The jukebox system follows a layered architecture with clear separation of concerns:

```
┌─────────────────────────────────────────┐
│                User Layer               │
│  ┌─────────┐                           │
│  │  User   │                           │
│  └─────────┘                           │
└─────────────────────────────────────────┘
┌─────────────────────────────────────────┐
│           User Interface Layer          │
│  ┌─────────┐     ┌──────────────┐      │
│  │ Jukebox │────▶│   Selector   │      │
│  └─────────┘     │ (Interface)  │      │
└─────────────────────────────────────────┘
┌─────────────────────────────────────────┐
│            Business Logic Layer         │
│  ┌──────────┐    ┌──────────────┐      │
│  │CDPlayer  │────│   Playlist   │      │
│  └──────────┘    └──────────────┘      │
└─────────────────────────────────────────┘
┌─────────────────────────────────────────┐
│             Data Model Layer            │
│  ┌─────────┐     ┌──────────────┐      │
│  │   CD    │────▶│     Song     │      │
│  └─────────┘     └──────────────┘      │
└─────────────────────────────────────────┘
```

### Layer Responsibilities

- **User Layer**: Represents the end user interacting with the system
- **User Interface Layer**: Handles user input and provides feedback
- **Business Logic Layer**: Implements core functionality and state management
- **Data Model Layer**: Represents the domain entities and their relationships

## Design Patterns

### 1. Strategy Pattern (Selector Interface)
The `Selector` interface defines a contract for different types of user interactions. The `Jukebox` class implements this interface, allowing for different UI implementations in the future.

### 2. State Pattern (Player State)
The `CDPlayer` uses an enum-based state pattern to manage playback states (STOPPED, PLAYING, PAUSED), ensuring valid state transitions and behavior.

### 3. Composite Pattern (CD and Song Relationship)
CDs contain collections of songs, demonstrating a composite relationship where CDs manage their song collections.

### 4. Builder Pattern (Implied in Construction)
Classes use constructor validation and step-by-step object construction to ensure valid object states.

## Class Descriptions

### Song Class
**Purpose**: Represents a single music track with comprehensive metadata.

**Key Features**:
- Immutable design with validation
- Rich metadata (title, artist, album, duration, track number)
- Formatted duration display
- Proper equals/hashCode implementation

**Design Decisions**:
- Immutable to prevent accidental modification
- Validation ensures data integrity
- Reference to parent CD for navigation

### CD Class
**Purpose**: Represents a music album containing multiple songs.

**Key Features**:
- Manages collection of songs
- Prevents duplicate track numbers
- Automatic sorting by track number
- Metadata management (title, artist, release date, genre)
- Duration calculations

**Design Decisions**:
- Encapsulated song management
- Validation prevents invalid states
- Immutable metadata after construction

### Playlist Class
**Purpose**: Manages an ordered collection of songs for playback.

**Key Features**:
- Named playlists with validation
- Current song tracking
- Navigation (next/previous)
- Shuffle/unshuffle functionality
- Add/remove operations

**Design Decisions**:
- Maintains current position for continuous playback
- Shuffle preserves current song position
- Defensive copying for collection access

### CDPlayer Class
**Purpose**: Core playback engine with state management.

**Key Features**:
- State-based playback control
- CD collection management
- Integration with playlists
- Playback operations (play, pause, stop, resume)

**Design Decisions**:
- Clear state management with enum
- Separation of concerns between playback and UI
- Robust error handling

### Jukebox Class
**Purpose**: User interface layer providing interaction controls.

**Key Features**:
- Power state management
- Button simulation
- Status display
- User feedback with emojis and clear messages

**Design Decisions**:
- Implements Selector interface for extensibility
- Power state prevents operations when off
- Rich user feedback for better experience

## Key Features

### 1. Robust Validation
- All inputs validated at construction time
- Null checks using Objects.requireNonNull()
- Custom validation for business rules
- Clear error messages

### 2. State Management
- CDPlayer maintains clear state (STOPPED, PLAYING, PAUSED)
- Jukebox has power state (ON/OFF)
- Playlist tracks current song position
- State transitions are validated

### 3. User Experience
- Rich console output with emojis and formatting
- Clear status displays
- Informative error messages
- Interactive demonstration

### 4. Extensibility
- Interface-based design allows for different implementations
- Enum-based states can be easily extended
- Modular architecture supports feature additions

## Usage Examples

### Basic Setup
```java
// Create CDs with songs
CD cd = new CD("Album Name", "Artist", LocalDate.now(), "Genre");
cd.addSong("Song Title", "Artist", "Album", Duration.ofMinutes(3), 1);

// Create playlist and player
Playlist playlist = new Playlist("My Playlist");
CDPlayer player = new CDPlayer(playlist, Set.of(cd));
Jukebox jukebox = new Jukebox(player);

// Use the jukebox
jukebox.powerOn();
jukebox.playPauseBtn();
jukebox.nextSongBtn();
```

### Advanced Operations
```java
// Shuffle operations
jukebox.shuffleBtn(); // Enable shuffle
jukebox.shuffleBtn(); // Disable shuffle

// Playlist management
jukebox.addSongToPlaylistBtn(song);
jukebox.removeSongFromPlaylistBtn(song);

// Status monitoring
jukebox.displayStatus();
jukebox.displayPlaylist();
jukebox.displayCDCollection();
```

## Extension Points

### 1. New Player Types
Implement the `Selector` interface to create different UI experiences:
- Web-based interface
- Mobile app interface
- Voice-controlled interface

### 2. Additional Media Types
Extend the system to support:
- Digital files (MP3, FLAC)
- Streaming services
- Radio stations

### 3. Enhanced Features
- Volume control
- Equalizer settings
- Playlist sharing
- User accounts and preferences
- Playback history

### 4. Persistence
Add data persistence capabilities:
- Database integration
- File-based storage
- Cloud synchronization

## Conclusion

This jukebox system demonstrates solid object-oriented design principles with clear separation of concerns, robust error handling, and extensible architecture. The implementation provides a foundation that can be easily extended with additional features while maintaining code quality and reliability.