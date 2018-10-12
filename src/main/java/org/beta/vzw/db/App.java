package org.beta.vzw.db;


import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String CONN_STRING="jdbc:mysql://localhost/testdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Brussels";
    private static final String SELECT="SELECT * FROM persoon";
    private static final  String DELETE="DELETE FROM persoon where ID = ?";
    public static void main( String[] args ) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
        Scanner invoer = new Scanner(System.in);
        int keuze = 0;
        try(Connection con = DriverManager.getConnection(CONN_STRING, "root", "Vdab")){
            try(Statement stat = con.createStatement()){
                try(ResultSet rs = stat.executeQuery(SELECT)){
                    while (rs.next()){
                        int id = rs.getInt("id");
                        String voornaam = rs.getString("voornaam");
                        String achternaam = rs.getString("achternaam");
                        LocalDate geboortedatum = rs.getDate("geboortedatum").toLocalDate();
                        System.out.printf("%d) %s %s (%s)%n", id, voornaam, achternaam, geboortedatum.format(formatter));
                    }
                    System.out.printf("Welke persoon wilt u verwijderen?");
                    keuze = Integer.parseInt(invoer.nextLine());
                }
            }
            try(PreparedStatement prepstat = con.prepareStatement(DELETE)){
                prepstat.setInt(1, keuze);
                prepstat.executeUpdate();
                int aantal = prepstat.executeUpdate();
                if (aantal > 0) {
                    System.out.println("Record is verwijderd");
                }else{
                    System.out.println("Record niet gevonden");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();




            }


        }

    }

