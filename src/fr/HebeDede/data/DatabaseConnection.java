package fr.HebeDede.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private static Connection connect;

	public static Connection getInstance() {
		if(connect == null){
			try {
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/HebeDede", "root", "root");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connect;
	}	
}