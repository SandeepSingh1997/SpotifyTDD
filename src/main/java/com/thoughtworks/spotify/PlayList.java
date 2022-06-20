package com.thoughtworks.spotify;

import com.thoughtworks.exceptions.InvalidOperationException;
import com.thoughtworks.exceptions.InvalidRatingException;
import com.thoughtworks.exceptions.SongAlreadyPresentInThePlaylistException;
import com.thoughtworks.exceptions.SongIsNotPresentInThePlaylistException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayList implements Cloneable{
    private User creator;
    private int id;
    private List<Song> songsList;
    private boolean isShared;
    private int rating;
    private boolean isReadOnly;

    public PlayList(int id){
        this.id = id;
        songsList = new ArrayList<>();
        isShared = false;
        isReadOnly = false;
    }

    public PlayList(int id, User creator){
        this.id = id;
        this.songsList = new ArrayList<>();
        this.isShared = false;
        this.isReadOnly = false;
    }

    public void addSong(Song song) throws SongAlreadyPresentInThePlaylistException, InvalidOperationException {
        if(isReadOnly) throw new InvalidOperationException();
        if(songsList.contains(song)) throw new SongAlreadyPresentInThePlaylistException();
        songsList.add(song);
    }

    public int size(){
        return songsList.size();
    }

    public void removeSong(Song song) throws SongIsNotPresentInThePlaylistException, InvalidOperationException {
        if(isReadOnly) throw new InvalidOperationException();
        if(!songsList.contains(song)) throw new SongIsNotPresentInThePlaylistException();
        songsList.remove(song);
    }

    public void share() {
        isShared = true;
    }

    public boolean isShared() {
        return isShared;
    }

    public void rate(int rating) throws InvalidRatingException {
        if(rating > 5 || rating < 1) throw new InvalidRatingException();
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public List<Song> getSongs() throws CloneNotSupportedException {
        Stream<Song> songsStream = songsList.stream();
        List<Song> clonedList = songsStream.map(song -> {
            try {
                return song.clone();
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }).collect(Collectors.toList());
        return clonedList;
    }

    public PlayList getShuffledPlaylist() throws CloneNotSupportedException {
        PlayList shuffledPlaylist = this.clone();
        Collections.shuffle(shuffledPlaylist.songsList);
        return shuffledPlaylist;
    }

    @Override
    protected PlayList clone() throws CloneNotSupportedException {
        PlayList clonedPlaylist = new PlayList(id);
        clonedPlaylist.isReadOnly = true;
        clonedPlaylist.songsList = getSongs();
        return clonedPlaylist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayList playList = (PlayList) o;
        return id == playList.id ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean ifCreatorIs(User user) {
        return user.getId() == this.creator.getId();
    }
}
