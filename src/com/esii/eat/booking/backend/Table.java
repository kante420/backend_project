package com.esii.eat.booking.backend;

public class Table {
    static final int capacity = 6;
    private boolean isOccupied;
    private int occupiedSeats;
    private String reservationName;

    //Constructor that initializes the table as unoccupied and with zero occupied seats
    public Table() {
        isOccupied = false;
        occupiedSeats = 0;
        reservationName = "";
    }

    //Getters and Setters
    public int getCapacity() {
        return capacity;
    }
    public void setIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }
    public int getOccupiedSeats() {
        return occupiedSeats;
    }
    public void setOccupiedSeats(int occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }
    public String getReservationName() {
        return reservationName;
    }
    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

    //Method to reserve a table
    public void reserve(int numberOfPeople, String reservationName) {
        if(numberOfPeople >= 1 && numberOfPeople <= capacity && reservationName != null && !reservationName.trim().isEmpty()) {
            this.reservationName = reservationName;
            this.isOccupied = true;
            this.occupiedSeats = numberOfPeople;
        }
    }

    //Method to check is the table is occupied
    public boolean isOccupied() {
        return isOccupied;
    }

    //To string method
    @Override
    public String toString(){
        if(isOccupied){
            return "Name: " + reservationName + ", Number of Dinners: " + occupiedSeats;
        }
        else{
            return "Available";
        }
    }
}