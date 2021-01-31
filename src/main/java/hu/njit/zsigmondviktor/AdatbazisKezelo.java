package hu.njit.zsigmondviktor;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.List;

public class AdatbazisKezelo {
    //Mezok
    private Connection connection;

    //Konstruktor
    public AdatbazisKezelo(String dbName, String user, String password){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName + "?characterEncoding=utf8", user, password);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //Metodusok
    public void dobogosok(){
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT name, score FROM ScoreTable ORDER BY score DESC LIMIT 3");

            ResultSet rs = stmt.executeQuery();

            System.out.println("Dobogósok:");
            while (rs.next()){
                System.out.println("\t" + rs.getString(1) + ": " + rs.getString(2) + " pont");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertWinnerIntoScore(Jatekos j){
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT id, score FROM ScoreTable WHERE name = ?");
            stmt.setString(1, j.getNev());

            ResultSet rs = stmt.executeQuery();
            int jId = 0, jIdPont = 0;

            if(rs.next()){
                jId = rs.getInt(1);
                jIdPont = rs.getInt(2);
            }

            if (jId == 0){
                //A jatekos meg nem jatszot korabban
                stmt = connection.prepareStatement("INSERT INTO ScoreTable (name, score) VALUES (?, ?)");
                stmt.setString(1, j.getNev());
                stmt.setInt(2, 1);
            }
            else{
                //A jatekos mar jatszott korabban
                jIdPont++;
                stmt = connection.prepareStatement("UPDATE ScoreTable SET score = ? WHERE id = ?");
                stmt.setInt(1, jIdPont);
                stmt.setInt(2, jId);
            }

            stmt.executeUpdate();
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
        }
    }
}
