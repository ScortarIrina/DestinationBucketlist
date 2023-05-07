package com.example.destinationbucketlist.model;

import java.util.List;

public class PrivateList {

    private List<PrivateDestination> privateDestinationList; // this can't be an entity because of using the list container
    private int favouriteID = -1; // -1 marks that no favourite has been chosen yet

    public PrivateList(List<PrivateDestination> privateDestinationList, int favouriteID) {
        this.privateDestinationList = privateDestinationList;
        this.favouriteID = favouriteID;
    }

    public PrivateList() {
    }

    public void setFavouriteID(int favouriteID) {
        this.favouriteID = favouriteID;
    }

    public void setPrivateDestinationList(List<PrivateDestination> privateDestinationList) {
        this.privateDestinationList = privateDestinationList;
    }

    public List<PrivateDestination> getPrivateDestinationList() {
        return privateDestinationList;
    }

    public int getFavouriteID() {
        return favouriteID;
    }
}
