package com.example.destinationbucketlist.model;

import jakarta.persistence.Entity;

@Entity
public class PrivateDestination extends Destination {
    private boolean isFavourite; // this marks if the destination is considered favourite or not
                                 // to make a destination favourite see below makeFavourite()

    public PrivateDestination()
    {
        isFavourite = false;
    }

    public PrivateDestination(boolean fav)
    {
        isFavourite = fav;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public void makeFavourite()
    {
        this.isFavourite = true;
    }
}
