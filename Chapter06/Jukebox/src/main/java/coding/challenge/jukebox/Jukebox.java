package coding.challenge.jukebox;

import java.util.Objects;

public class Jukebox implements Selector {

    private final CDPlayer cdPlayer;
    private boolean isPoweredOn;

    public Jukebox(CDPlayer cdPlayer) {
        this.cdPlayer = Objects.requireNonNull(cdPlayer, "CD Player cannot be null");
        this.isPoweredOn = false;
    }

    public void powerOn() {
        if (!isPoweredOn) {
            isPoweredOn = true;
            System.out.println("🎵 Jukebox powered ON");
            System.out.println("Welcome to the Digital Jukebox!");
            displayStatus();
        } else {
            System.out.println("Jukebox is already powered on.");
        }
    }

    public void powerOff() {
        if (isPoweredOn) {
            if (cdPlayer.isPlaying() || cdPlayer.isPaused()) {
                cdPlayer.stop();
            }
            isPoweredOn = false;
            System.out.println("🎵 Jukebox powered OFF");
        } else {
            System.out.println("Jukebox is already powered off.");
        }
    }

    private void checkPowerState() {
        if (!isPoweredOn) {
            throw new IllegalStateException("Jukebox is powered off. Please power on first.");
        }
    }

    @Override
    public void nextSongBtn() {
        checkPowerState();
        System.out.println("🎵 Next Song button pressed...");
        cdPlayer.playNextSong();
    }

    @Override
    public void prevSongBtn() {
        checkPowerState();
        System.out.println("🎵 Previous Song button pressed...");
        cdPlayer.playPrevSong();
    }

    @Override
    public void addSongToPlaylistBtn(Song song) {
        checkPowerState();
        Objects.requireNonNull(song, "Song cannot be null");

        try {
            cdPlayer.getPlaylist().addSong(song);
            System.out.println("✅ Song added to playlist: " + song.getTitle());
        } catch (Exception e) {
            System.out.println("❌ Failed to add song to playlist: " + e.getMessage());
        }
    }

    @Override
    public void removeSongFromPlaylistBtn(Song song) {
        checkPowerState();
        Objects.requireNonNull(song, "Song cannot be null");

        boolean removed = cdPlayer.getPlaylist().removeSong(song);
        if (removed) {
            System.out.println("✅ Song removed from playlist: " + song.getTitle());
        } else {
            System.out.println("❌ Song not found in playlist: " + song.getTitle());
        }
    }

    @Override
    public void shuffleBtn() {
        checkPowerState();
        System.out.println("🔀 Shuffle button pressed...");

        Playlist playlist = cdPlayer.getPlaylist();
        if (playlist.isEmpty()) {
            System.out.println("❌ Cannot shuffle: Playlist is empty");
            return;
        }

        if (playlist.isShuffled()) {
            playlist.unshuffle();
            System.out.println("✅ Playlist unshuffled - songs restored to original order");
        } else {
            playlist.shuffle();
            System.out.println("✅ Playlist shuffled randomly");
        }
    }

    public void playPauseBtn() {
        checkPowerState();
        System.out.println("⏯️ Play/Pause button pressed...");

        if (cdPlayer.isPlaying()) {
            cdPlayer.pause();
        } else if (cdPlayer.isPaused()) {
            cdPlayer.resume();
        } else {
            if (!cdPlayer.getPlaylist().isEmpty()) {
                Song currentSong = cdPlayer.getPlaylist().getCurrentSong();
                if (currentSong != null) {
                    cdPlayer.playSong(currentSong);
                } else {
                    cdPlayer.playNextSong();
                }
            } else {
                System.out.println("❌ Cannot play: Playlist is empty");
            }
        }
    }

    public void stopBtn() {
        checkPowerState();
        System.out.println("⏹️ Stop button pressed...");
        cdPlayer.stop();
    }

    public void displayStatus() {
        checkPowerState();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🎵 JUKEBOX STATUS");
        System.out.println("=".repeat(50));
        cdPlayer.displayCurrentStatus();
        System.out.println("=".repeat(50) + "\n");
    }

    public void displayPlaylist() {
        checkPowerState();
        Playlist playlist = cdPlayer.getPlaylist();

        System.out.println("\n📋 " + playlist.toString());
        System.out.println("-".repeat(40));

        if (playlist.isEmpty()) {
            System.out.println("No songs in playlist");
        } else {
            int index = 1;
            for (Song song : playlist.getSongs()) {
                String marker = song.equals(playlist.getCurrentSong()) ? "► " : "  ";
                System.out.printf("%s%2d. %s%n", marker, index++, song);
            }
        }
        System.out.println("-".repeat(40) + "\n");
    }

    public void displayCDCollection() {
        checkPowerState();
        System.out.println("\n💿 CD COLLECTION");
        System.out.println("-".repeat(40));

        if (cdPlayer.getCds().isEmpty()) {
            System.out.println("No CDs in collection");
        } else {
            int index = 1;
            for (CD cd : cdPlayer.getCds()) {
                String marker = cd.equals(cdPlayer.getCurrentCD()) ? "► " : "  ";
                System.out.printf("%s%2d. %s%n", marker, index++, cd);
            }
        }
        System.out.println("-".repeat(40) + "\n");
    }

    public CDPlayer getCdPlayer() {
        return cdPlayer;
    }

    public boolean isPoweredOn() {
        return isPoweredOn;
    }
}