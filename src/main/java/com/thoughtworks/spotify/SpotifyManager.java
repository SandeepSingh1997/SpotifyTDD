package com.thoughtworks.spotify;

import java.util.ArrayList;
import java.util.List;

public class SpotifyManager {
    private List<User> userList;

    public SpotifyManager(){
        userList = new ArrayList<>();
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public int userCount() {
        return userList.size();
    }

    public int countOpenPlaylists() {
        int openPlayListsCount = 0;
        for(User user : userList){
            openPlayListsCount += user.sharedPlayListsCount();
        }
        return openPlayListsCount;
    }

    public List<PlayList> getOpenPlaylists() throws CloneNotSupportedException {
        List<PlayList> openPlaylistList = new ArrayList<>();
        for(User user : userList){
            for(PlayList openPlaylist : user.getSharedPlaylists()){
                openPlaylistList.add(openPlaylist);
            }
        }
        return openPlaylistList;
    }
}
