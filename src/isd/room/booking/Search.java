/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isd.room.booking;

/**
 *
 * @author user
 */
public class Search {
    String type;
    int capacity;
    String date;
    String from_time;
    String to_time;

    public Search(String type, int capacity, String date, String from_time, String to_time) {
        this.type = type;
        this.capacity = capacity;
        this.date = date;
        this.from_time = from_time;
        this.to_time = to_time;
    }
}
