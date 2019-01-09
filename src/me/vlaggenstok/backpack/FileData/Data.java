package me.vlaggenstok.backpack.FileData;

import me.vlaggenstok.backpack.main.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Data {
    int Id;
    int code;
    String content;
    String owner;
    String status;
    int size;
    public Data( int id){
        Id = id;
        try{
        java.sql.PreparedStatement sta = Main.getInstance().getConnection().prepareStatement("SELECT * FROM Backpacks WHERE id = ?");
        sta.setInt(1, Id);
        ResultSet rs = sta.executeQuery();
        rs.next();

        code = rs.getInt("Code");
        content = rs.getString("Content");
        owner = rs.getString("Owner");
        size = rs.getInt("Size");
        status = rs.getString("Status");
        rs.close();
        sta.close();
    }catch (SQLException e){

        }

    }
    public static boolean isbackpack(int id){
        boolean  d = false;
        try{
            java.sql.PreparedStatement sta = Main.getInstance().getConnection().prepareStatement("SELECT * FROM Backpacks WHERE ID = ?");
            sta.setInt(1	, id);
            ResultSet rsset = sta.executeQuery();
            d = rsset.next();
            rsset.close();
            sta.close();
        }catch(Exception e){
        }
        return d;
    }

    public static void newdata(int code, String owner, String content, int size, int id,String status){
try{
    java.sql.PreparedStatement fh = Main.getInstance().getConnection().prepareStatement("INSERT INTO Backpacks (ID,size,Content, Code , Owner , Status) VALUES (? ,?,?, ?, ?,?)");
    fh.setInt(1, id);
    fh.setInt(2, size);
    fh.setString(3, content);
    fh.setInt(4, code);
    fh.setString(5, owner);
    fh.setString(6, status);


    fh.executeUpdate();
    fh.close();
}catch (SQLException e){

}
    }
    public static boolean iscode(int code){
        boolean  d = false;
        try{
            java.sql.PreparedStatement sta = Main.getInstance().getConnection().prepareStatement("SELECT * FROM Backpacks WHERE code = ?");
            sta.setInt(1	, code);
            ResultSet rsset = sta.executeQuery();
            d = rsset.next();
            rsset.close();
            sta.close();
        }catch(Exception e){
        }
        return d;
    }
    public int getcode(){
        return code;
    }
    public String getContent(){
        return content;

    }
    public int getSize(){
        return size;
    }

    public String getStatus(){
        return status;

    }
    public int getId(){
    return Id;

    }
    public String getOwner(){
        return owner;
    }
    public void setId(int id){
        Id = id;
    }
    public void setOwner(String s){
        this.owner = s;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setContent(String content){
        this.content = content;
    }
    public void setSize(int size){
        this.size = size;
    }
    public void setCode(int code){
        this.status = status;
    }
    public void save(){
try {
    PreparedStatement fh  = Main.getInstance().getConnection().prepareStatement("UPDATE `Backpacks` SET `Size`=?, `Content`=? ,`Code`=?, `Owner`=? , `Status` =?  WHERE `Id`=?  ");
    fh.setInt(1, size);
    fh.setString(2, content);
    fh.setInt(3, code);
    fh.setString(4, owner);
    fh.setString(5, status);
    fh.setInt(6, Id);
    fh.executeUpdate();
    fh.close();
}catch (SQLException e){

}
    }




}
