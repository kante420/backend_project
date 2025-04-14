package com.esii.eat.booking.backend;

public class Chain {
    private String chainName;
    private int numberOfRestaurants;
    private Restaurant[] restaurants;

    //Constructor
    public Chain(String chainName, int numberOfRestaurants) {
        this.chainName = chainName;
        this.numberOfRestaurants = numberOfRestaurants;
        //Initialization of the Restaurant Array
        restaurants = new Restaurant[numberOfRestaurants];
    }

    //Add Restaurant
    public boolean addRestaurant(Restaurant restaurant){
        for(int i = 0; i < numberOfRestaurants; i++){
            if(restaurants[i] == null){
                restaurants[i] = restaurant;
                return true;
            }
        }
        return false;
    }

    //Get Restaurant
    public Restaurant getRestaurant(String name){
        if(getRestaurantPosition(name) == -1){
            return null;
        }
        else{
            return restaurants[getRestaurantPosition(name)];
        }
    }

    //Get Restaurant Position
    private int getRestaurantPosition(String name){
        for(int i = 0; i < numberOfRestaurants; i++){
            if(restaurants[i] != null && restaurants[i].getName().equals(name)){
                return i;
            }
        }
        return -1;
    }

    //Reserve Restaurant
    public boolean reserveRestaurant(int numberOfPeople, String restaurantName, String reservationName){
        Restaurant restaurant = getRestaurant(restaurantName);

        if (restaurant == null){
            return false;
        }
        else{

            return restaurant.reserveTables(numberOfPeople, reservationName);
        }

    }

    //Search Restaurant
    public Restaurant searchRestaurant(int numberOfPeople, String restaurantName){
        Restaurant restaurant = getRestaurant(restaurantName);

        if (restaurant != null) {
            int position = getRestaurantPosition(restaurantName);
            int startIndex = (position + 1) % numberOfPeople;

            for (int i = startIndex; i != position; i=(i+1)%numberOfRestaurants) {
                if (restaurants[i].hasAvailableTables(numberOfPeople)) {
                    return restaurants[i];
                }
            }
        }
        return null;
    }
}