/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isd.room.booking;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public final class DbHelper {
    
    private Connection conn;
    
    private static final String URL = "jdbc:mysql://localhost:3306/room_booking";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "qwerty";
    
    private Statement checkUserExistence;
    private Statement getBookingList;
    private Statement getRoomList;
    private Statement getBookings;
    private Statement getAccessories;
    private Statement deleteRooms;
    private Statement deleteBookings;
    private Statement deleteAccessories;
    private Statement addRooms;
    private Statement addBookings;
    private Statement addAccessory;
    private Statement searchRooms;
    private Statement selectRequests;
    
    public DbHelper() {
        connect();
    }
    
    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to MySQL server.");
        }
        catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error in connecting to MySQL server!");
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public User userExists(String uid, String pass) throws SQLException{
        ResultSet rs = null;
        User user = null;
        checkUserExistence = conn.createStatement();
        String query = "SELECT * FROM user WHERE uid ='" + uid + "' AND password ='" + pass + "'";
        
        try {
            rs = checkUserExistence.executeQuery(query);
            
            if (rs.next()) {
                user = new User(
                    uid,
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getInt("user_level"));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            rs.close();
        }
        
        return user;
    }
    
    public ResultSet getBookings(String uid) throws SQLException {
        ResultSet rs = null;
        getBookingList = conn.createStatement();
        String query = "select * from room, request where"
                + " room.room_id = request.room_id and uid ='" + uid + "'";
        
        try {
            rs = getBookingList.executeQuery(query);
        }
        catch (SQLException ex) {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public ResultSet getRooms() throws SQLException {
        ResultSet rs = null;
        getRoomList = conn.createStatement();
        String query = "select * FROM room";
        
        try {
            rs = getRoomList.executeQuery(query);
        }
        catch (SQLException ex) {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public ResultSet getBooking(int req_id) throws SQLException{
        ResultSet rs = null;
        getBookings = conn.createStatement();
        String query = "select * FROM request where request.req_id = '" + req_id + "'";
        
        try {
            rs = getBookings.executeQuery(query);
        }
        catch (SQLException ex) {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public ResultSet getAccessory() throws SQLException{
        ResultSet rs = null;
        getAccessories = conn.createStatement();
        String query = "select * FROM request, accessory where request.req_id = accessory.req_id and date > curdate()";
        
        try {
            rs = getAccessories.executeQuery(query);
        }
        catch (SQLException ex) {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }

    public void deleteBooking(int req_id) throws SQLException {
        deleteBookings = conn.createStatement();
        String query = "Delete from request where req_id = " + req_id;
        
        try {
            deleteBookings.executeUpdate(query);
        }
        catch (SQLException ex) {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public void deleteBooking(String room_id) throws SQLException {
        deleteBookings = conn.createStatement();
        deleteAccessories = conn.createStatement();
        String query2 = "Delete from accessory where req_id in (Select req_id from request where room_id = room_id)";
        String query = "Delete from request where room_id = " + room_id;
        
        try {
            deleteBookings.executeUpdate(query);
            deleteAccessories.executeUpdate(query2);
        }
        catch (SQLException ex) {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    
    public void deleteAccessory (int req_id) throws SQLException {
        deleteAccessories = conn.createStatement();
        String query = "Delete from accessory where req_id = " + req_id;
        
        try {
            deleteAccessories.executeUpdate(query);
        }
        catch (SQLException ex) {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteRoom(String room_id) throws SQLException {
        deleteRooms = conn.createStatement();
        String query = "Delete from room where room_id = " + room_id;
        
        try {
            deleteRooms.executeUpdate(query);
        }
        catch (SQLException ex) {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addRoom(Room room) throws SQLException {
        addRooms = conn.createStatement();
        String query = "Insert into room values('"
                + room.roomid + "','" + room.name + "'," + room.capacity + ",'" + room.type + "')";
        
        addRooms.executeUpdate(query);
    }
    
    public void addBooking(Booking booking) throws SQLException {
        addBookings = conn.createStatement();
        String query = "Insert into request values(" + 
                booking.req_id + ",'" + booking.room_id + "','" + booking.uid + "','" + 
                booking.date + "','" + booking.from_time + "','" + booking.to_time + "')";
        
        addBookings.executeUpdate(query);
    }
    
    public void addAccessory(Accessory a) throws SQLException {
        addAccessory = conn.createStatement();
        
        String query = "Insert into accessory values (" + a.req_id + "," + a.chairs + "," + a.pointers + "," + a.microphones + "," + a.speakers +")";
        
        addAccessory.executeUpdate(query);
    }

    public ResultSet searchRoom(Search s) throws SQLException {
        ResultSet rs = null;
        searchRooms = conn.createStatement();
        String query = "select * from room where (room_id not in ("
                + "select room.room_id from room, request where room.room_id = request.room_id and type = '" 
                + s.type + "' and capacity >= " + s.capacity + " and date = '" + s.date + "' and "
                + "(strcmp(cast(to_time as char), '" + s.from_time + "') >=  0 or strcmp(cast(from_time as char), '" + s.to_time + "') <= 0))"
                + " or room_id not in (select room_id from request))"
                + " and capacity >= " + s.capacity + " and type = '" + s.type + "'";
        
        try {
            rs = searchRooms.executeQuery(query);
        }
        catch (SQLException ex) {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public int lastRequest() throws SQLException {
        ResultSet rs = null;
        selectRequests = conn.createStatement();
        String query = "select * from request";
        int n = 0;
        
        try {
            rs = selectRequests.executeQuery(query);
            if(rs.next()) {
                rs.last();
                n = rs.getInt("req_id") + 1;
            }
            else {
                n = 1;
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(DbHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return n;
    }
}
