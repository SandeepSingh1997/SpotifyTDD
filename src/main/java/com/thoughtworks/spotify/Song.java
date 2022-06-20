package com.thoughtworks.spotify;

import java.util.Objects;

public class Song implements Cloneable{
    private int id;

    public Song(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    protected Song clone() throws CloneNotSupportedException {
        return new Song(this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id == song.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
