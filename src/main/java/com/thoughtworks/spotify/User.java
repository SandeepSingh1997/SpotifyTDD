package com.thoughtworks.spotify;

import com.thoughtworks.exceptions.InvalidOperationException;
import com.thoughtworks.exceptions.PlaylistDoesNotExistException;

import java.util.ArrayList;
import java.util.List;

public class User {
    int id;
    private final List<PlayList> playLists;

    public User(int id){
        this.id = id;
        playLists = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public void addPlaylist(PlayList playList) {
        playLists.add(playList);
    }

    public int playListsSize() {
        return playLists.size();
    }

    public int sharedPlayListsCount() {
        int sharedPlaylistsCount = 0;
        for(PlayList playList : playLists){
            if(playList.isShared()){
                sharedPlaylistsCount++;
            }
        }
        return sharedPlaylistsCount;
    }

    public List<PlayList> getSharedPlaylists() throws CloneNotSupportedException {
        List<PlayList> sharedPlaylists = new ArrayList<>();
        for(PlayList playList : playLists){
            if(playList.isShared())
            { sharedPlaylists.add(playList.clone());
            }
        }
        return sharedPlaylists;
    }

    public void sharePlaylistTo(PlayList playList, User user2) throws CloneNotSupportedException {
        user2.addPlaylist(playList.clone());
    }

    public boolean isPlaylistCreater(PlayList playList) {
        return this.playLists.contains(playList);
    }

    public void deletePlaylist(PlayList playList) throws PlaylistDoesNotExistException, InvalidOperationException {
        if(!playList.ifCreatorIs(this)){
            throw new InvalidOperationException();
        }
        if(!this.playLists.contains(playList)){
            throw new PlaylistDoesNotExistException();
        }
        this.playLists.remove(playList);
    }

    public boolean has(PlayList playList) {
        return this.playLists.contains(playList);
    }
}

