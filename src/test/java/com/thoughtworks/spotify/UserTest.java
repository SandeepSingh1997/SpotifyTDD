package com.thoughtworks.spotify;

import com.thoughtworks.exceptions.InvalidOperationException;
import com.thoughtworks.exceptions.PlaylistDoesNotExistException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @Test
    public void shouldBeAbletoAddAPlayList(){
        User user = new User(1);
        PlayList playList = new PlayList(1);
        int expectedPlayListsCount = 1;

        user.addPlaylist(playList);
        int actualPlayListsCount = user.playListsSize();

        assertThat(actualPlayListsCount, is(expectedPlayListsCount));
    }

    @Test
    public void shouldBeAbletoAddTwoPlaylists(){
        User user = new User(1);
        PlayList playList1 = new PlayList(1);
        PlayList playList2 = new PlayList(2);
        int expectedPlayListsCount = 2;

        user.addPlaylist(playList1);
        user.addPlaylist(playList2);
        int actualPlayListsCount = user.playListsSize();

        assertThat(actualPlayListsCount, is(expectedPlayListsCount));
    }

    @Test
    public void shouldReturnCountOfSharedPlaylistsAsOneWhenThereIsOneSharedPlaylist(){
        User user = new User(1);
        PlayList playList1 = new PlayList(1);
        PlayList playList2 = new PlayList(2);
        playList1.share();
        user.addPlaylist(playList1);
        user.addPlaylist(playList2);
        int expectedSharedPlaylistsCount = 1;

        int actualSharedPlaylistsCount = user.sharedPlayListsCount();

        assertThat(actualSharedPlaylistsCount, is(expectedSharedPlaylistsCount));
    }

    @Test
    public void shouldBeAbleToShareAPlaylistToAnotherUser() throws CloneNotSupportedException {
        User user1 = new User(1);
        User user2 = new User(2);
        PlayList playList = new PlayList(1);
        int expectedPlaylistSize = 1;

        user1.sharePlaylistTo(playList, user2);
        int actualPlaylistSize = user2.playListsSize();

        assertThat(actualPlaylistSize, is(expectedPlaylistSize));
    }

    @Test
    public void shouldReturnTrueWhenUserIsThePlaylistCreator(){
        User user = new User(1);
        PlayList playList = new PlayList(1);
        user.addPlaylist(playList);
        assertTrue(user.isPlaylistCreater(playList));
    }

    @Test
    public void shouldReturnFalseWhenUserIsNotThePlaylistCreator(){
        User user = new User(1);
        PlayList playList = new PlayList(1);
        assertFalse(user.isPlaylistCreater(playList));
    }

    @Test
    public void shouldBeAbleToDeleteAPlaylistWhichBelongsToTheUser() throws PlaylistDoesNotExistException, InvalidOperationException {
        User user = new User(1);
        PlayList playList = new PlayList(1);
        user.addPlaylist(playList);

        user.deletePlaylist(playList);
        assertFalse(user.has(playList));
    }

    @Test
    public void shouldReturnPlaylistDoesNotExistExceptionWhenPlaylistDoesNotBelongToTheUser(){
        User user = new User(1);
        PlayList playList = new PlayList(1);

        assertThrows(PlaylistDoesNotExistException.class, ()->{
           user.deletePlaylist(playList);
        });
    }

}
