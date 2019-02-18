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
public class Room {
    String roomid;
    String name;
    int capacity;
    String type;
    
    
    public Room() { 
    }

    public Room(String roomid, String name, int capacity, String type) {
        this.roomid = roomid;
        this.name = name;
        this.capacity = capacity;
        this.type = type;
    }

    public String getRoomid() {
        return roomid;
    }    
}

