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
public class User {
    String uid;
    private String name;
    private String email;
    private String phone;
    private int level;

    public User() {
    }
    
    public User(String uid, String name, String email, String phone, int level) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.level = level;
    }

    public String getUid() {
        return uid;
    }

    public int getLevel() {
        return level;
    }
}
