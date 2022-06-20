package com.thoughtworks.spotify;

import com.thoughtworks.exceptions.InvalidOperationException;
import com.thoughtworks.exceptions.InvalidRatingException;
import com.thoughtworks.exceptions.SongAlreadyPresentInThePlaylistException;
import com.thoughtworks.exceptions.SongIsNotPresentInThePlaylistException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class PlayListTest {
    @Test
    public void shouldBeAbleToAddASongToThePlayList() throws SongAlreadyPresentInThePlaylistException, InvalidOperationException {
        PlayList playList = new PlayList(1);
        Song song = new Song(1);
        int expectedSongsCount = 1;

        playList.addSong(song);
        int actualSongsCount = playList.size();

        assertThat(actualSongsCount, is(expectedSongsCount));
    }

    @Test
    public void shouldNotBeAbleToAddASongToThePlaylistWhenTheSongAlreadyExists() throws SongAlreadyPresentInThePlaylistException, InvalidOperationException {
        PlayList playList = new PlayList(1);
        Song song = new Song(1);
        playList.addSong(song);

        assertThrows(SongAlreadyPresentInThePlaylistException.class, () -> {
            playList.addSong(song);
        });
    }

    @Test
    public void shouldBeAbleToRemoveAnExistingSongFromThePlayList() throws SongIsNotPresentInThePlaylistException, SongAlreadyPresentInThePlaylistException, InvalidOperationException {
        PlayList playList = new PlayList(1);
        Song song = new Song(1);
        playList.addSong(song);
        int expectedSongsCount = 0;

        playList.removeSong(song);
        int actualSongsCount = playList.size();

        assertThat(actualSongsCount, is(expectedSongsCount));
    }

    @Test
    public void shouldReturnSongIsNotPresentExceptionWhenSongDoesNotExistInThePlayList() {
        PlayList playList = new PlayList(1);
        Song song = new Song(1);

        assertThrows(SongIsNotPresentInThePlaylistException.class, () -> {
            playList.removeSong(song);
        });
    }

    @Test
    public void shouldBeAbleToMakeAPlaylistShared() {
        PlayList playList = new PlayList(1);

        playList.share();

        assertThat(playList.isShared(), is(true));
    }

    @Test
    public void shouldBeAbleToRateAPlaylistFromOneToFive() throws InvalidRatingException {
        PlayList playList = new PlayList(1);
        int expectedRating = 1;

        playList.rate(1);
        int actualRating = playList.getRating();

        assertThat(actualRating, is(expectedRating));
    }

    @Test
    public void shouldReturnInvalidRatingExceptionIfPlaylistIsRatedOutsideTheRangeOneToFive() {
        PlayList playList = new PlayList(1);
        assertThrows(InvalidRatingException.class, () -> {
            playList.rate(10);
        });
    }

    @Test
    public void shouldReturnTheShuffledPlaylist() throws SongAlreadyPresentInThePlaylistException, CloneNotSupportedException, InvalidOperationException {
        PlayList playList = new PlayList(1);
        Song song1 = new Song(1);
        Song song2 = new Song(2);
        Song song3 = new Song(3);
        playList.addSong(song1);
        playList.addSong(song2);
        playList.addSong(song3);

        PlayList shuffledPlaylist = playList.getShuffledPlaylist();

        boolean isShuffled = isShuffled(playList, shuffledPlaylist);
        assertTrue(isShuffled);
    }

    private boolean isShuffled(PlayList playList, PlayList shuffledPlaylist) throws CloneNotSupportedException {
        boolean isShufffled = false;
        for (int i = 0; i < playList.size(); i++) {
            int id = playList.getSongs().get(i).getId();
            int id2 = shuffledPlaylist.getSongs().get(i).getId();
            if (playList.getSongs().get(i).getId() != shuffledPlaylist.getSongs().get(i).getId()) {
                isShufffled = true;
            }
        }
        return isShufffled;
    }


}
