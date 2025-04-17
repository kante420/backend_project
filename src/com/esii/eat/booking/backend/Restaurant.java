package com.esii.eat.booking.backend;

import static com.esii.eat.booking.backend.Table.capacity;
import java.util.Arrays;

public class Restaurant {
    private final int additionalTables = 2;
    private int availableTables;
    private int currentTableIndex = 0;
    private String name;
    private Table[] tables;
    private int totalTables;
    private int availableExpandedTables; //TEST

    //Constructs a Restaurant object with a given name for it and a number of tables
    public Restaurant(String name, int totalTables){
        this.name = name;
        this.totalTables = totalTables;
        this.availableTables = totalTables + additionalTables;
        this.availableExpandedTables = totalTables + additionalTables; //TEST
        //Initialization of the Table Array
        this.tables = new Table[totalTables];
        for(int i = 0; i < totalTables; i++) {
            this.tables[i] = new Table();
        }
    }

    //Getters and Setters
    public int getAvailableTables() {
        return availableTables;
    }
    public void setAvailableTables(int availableTables) {
        this.availableTables = availableTables;
    }
    public int getCurrentTableIndex() {
        return currentTableIndex;
    }
    public void setCurrentTableIndex(int currentTableIndex) {
        this.currentTableIndex = currentTableIndex;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Table[] getTables() {
        return tables;
    }
    public void setTables(Table[] tables) {
        this.tables = tables;
    }
    public int getTotalTables() {
        return totalTables;
    }
    public void setTotalTables(int totalTables) {
        this.totalTables = totalTables;
    }

    //Method to reserve a table - FINISHED
    public boolean reserveTables(int numberOfPeople, String reservationName) {
        int tables_needed = (int) Math.ceil((double) numberOfPeople / capacity); //Rounding up
        int last_table_number_people = numberOfPeople - ((tables_needed - 1) * capacity); //Calculation of the number of people in the last table
        int tables_already_reserved = 0; //Counter for the tables already reserved

        if (tables_needed > availableTables || currentTableIndex >= totalTables || numberOfPeople <= 1 ||
                (totalTables == 0 && tables_needed == 1 && availableTables > 0) || reservationName == null ||
                (tables_needed == 1 && availableTables == additionalTables)) {
            return false;
        }

        if (numberOfPeople <= capacity) { //If the reservation is for less than 6 people, we reserve just one table
            for (int i = currentTableIndex; i < totalTables && tables_already_reserved < tables_needed; i++) {
                if (!tables[i].isOccupied()) { //If the table is not ocuppied.
                    tables[i].reserve(numberOfPeople, reservationName); //We reserve one table
                    availableTables--; //Update the available tables
                    availableExpandedTables--;
                    return true;
                }
            }
        }
        else { //If the reservation is for more than 6 people, we reserve more than one table
            // Si la reserva requiere más de una mesa
            int counter_for_available=0;
            for(int i =0; i < totalTables; i++) {
                if(!tables[i].isOccupied()) {
                    counter_for_available++;
                }
            }
            if(counter_for_available < tables_needed) {
                if(tables_needed <= counter_for_available+additionalTables){
                    Table[] expanded_tables = Arrays.copyOf(tables, tables.length + additionalTables);
                    tables = expanded_tables;
                    for(int i = currentTableIndex; i < totalTables+2; i++) {
                        tables[i] = new Table();
                    }
                }
            }

            for (int i = currentTableIndex; i < totalTables+additionalTables && tables_already_reserved < tables_needed; i++) {
                if (!tables[i].isOccupied()) { //If the table is not occupied
                    if (tables_already_reserved == tables_needed - 1) { //If it´s the last table to reserve
                        tables[i].reserve(last_table_number_people, reservationName); //We asign the number of people for the last table
                        availableTables--; //Update the available tables
                        availableExpandedTables--;
                    } else { //If it is not the last table, we asign the maxCapacity number of people (6)
                        tables[i].reserve(tables[i].getCapacity(), reservationName);
                        availableTables--; //Update the available tables
                        availableExpandedTables--;
                    }
                    tables_already_reserved++; //Increment one table reserved
                }
            }

            if (tables_already_reserved == tables_needed) { //If we have reserved all the tables needed
                currentTableIndex += tables_needed; //Update the current index
                return true;
            }
        }
        return false;
    }

    //Get Name Method - FINISHED
    public String getName(){
        return name;
    }

    //Method to check is there are available tables - FINISHED
    public boolean hasAvailableTables(int numberOfPeople){
        int tables_needed = (int) Math.ceil((double) numberOfPeople /6); //Rounding up
        if(tables_needed > availableTables || currentTableIndex >= totalTables || numberOfPeople <= 1 || (totalTables==0 && tables_needed==1 && availableTables>0) || (tables_needed == 1 && availableTables == additionalTables)){
            return false;
        }

        return true;
    }

    //Available Tables Info Method
    public String availableTablesInfo (int numberOfPeople){
        int tables_needed = (int) Math.ceil((double) numberOfPeople /6); //Rounding up
        int counter = 0;

        StringBuilder return_string_available = new StringBuilder();

        if(tables_needed > availableTables){
            return "There are no available tables for your reservation. Sorry!";
        }
        else{
            for(int i = currentTableIndex; i < totalTables; i++){
                if(counter == tables_needed){
                    break;
                }
                if(!tables[i].isOccupied()){
                    return_string_available.append(" - Table ").append(i + 1).append(": Available \n\n");
                    counter++;
                }

            }
        }
        return return_string_available.toString();
    }

    //To String Method - FINISHED
    @Override
    public String toString(){
        StringBuilder return_string_restaurant = new StringBuilder();

        for(int i=0; i<  tables.length; i++){
            if(i == tables.length - 1){
                if(tables[i].isOccupied()){
                    return_string_restaurant.append(" Table ").append(i + 1).append("\n Occupation: Occupied \n");
                    return_string_restaurant.append("Number of dinners: ").append(tables[i].getOccupiedSeats());
                    return_string_restaurant.append("\n Reservation Name: ").append(tables[i].getReservationName());
                    return return_string_restaurant.toString();
                }
                else{
                    return_string_restaurant.append(" Table ").append(i + 1).append("\n Occupation: Available");
                    return return_string_restaurant.toString();
                }
            }
            else{
                if(tables[i].isOccupied()){
                    return_string_restaurant.append(" Table ").append(i + 1).append("\n Occupation: Occupied \n");
                    return_string_restaurant.append("Number of dinners: ").append(tables[i].getOccupiedSeats());
                    return_string_restaurant.append("\n Reservation Name: ").append(tables[i].getReservationName());
                    return_string_restaurant.append("\n--------------------------------------\n");
                }
                else{
                    return_string_restaurant.append(" Table ").append(i + 1).append("\n Occupation: Available");
                    return_string_restaurant.append("\n--------------------------------------\n");
                }
            }
        }

        return "Error while obtaining the data";
    }
}