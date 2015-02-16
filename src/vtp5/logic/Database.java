package vtp5.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/*VTP5 Copyright (C) 2015  Abdel-Rahim Abdalla, Minghua Yin, Yousuf Mohamed-Ahmed and Nikunj Paliwal

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Database {
	private Connection con;
	private Statement stmt;
	private PreparedStatement pstmt;
	private String path;
	private final String INSERT = "insert into leaderboard values("
			+ "?, ?, ?, ?, ?)";

	public Database(String path) {
		this.path = path;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:" + path
					+ "/leaderboard.db");
			stmt = con.createStatement();
			stmt.setQueryTimeout(30);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createTable() {
		try {
			stmt.executeUpdate("create table leaderboard (id numeric, file varChar(255), "
					+ "time int, questions int, successRate real)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void query(String query) {

	}

	public void insert(int id, String file, int time, int noQuestion,
			double successRate) {
		try {
			/*stmt.executeUpdate("insert into leaderboard VALUES('" + file + "',"
					+ time + "," + noQuestion + "," + successRate);*/
		//	stmt.executeUpdate("insert into leaderboard VALUES('cares',100, 100, 100, 90.5 ");
			pstmt = con.prepareStatement(INSERT);
			pstmt.setLong(1, id);
			pstmt.setString(2, file);
			pstmt.setLong(3, time);
			pstmt.setLong(4, noQuestion);
			pstmt.setString(5, String.valueOf(successRate));
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void retrieve() {
		try {
			ResultSet rs = stmt.executeQuery("select * from leaderboard");
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++){
					System.out.println(rs.getString(i));
			}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean exists() {
		try {
			con = DriverManager.getConnection("jdbc:sqlite:" + path
					+ "/leaderboard.db");
			System.out.println("DB exists");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
}
