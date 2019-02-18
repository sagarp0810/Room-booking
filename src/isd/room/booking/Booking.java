/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isd.room.booking;

import java.sql.SQLException;

/**
 *
 * @author user
 */
public class Booking {
    int req_id = 0; 
    String room_id = null;
    String uid = null;
    String date = null;
    String from_time = null;
    String to_time = null;

    public Booking(int req_id, String room_id, String uid, String date, String from_time, String to_time) throws SQLException {
        this.req_id = req_id;
        this.room_id = room_id;
        this.uid = uid;
        this.date = date;
        this.from_time = from_time;
        this.to_time = to_time;
    }
    
    public int getReq_id() {
        return req_id;
    }
}
