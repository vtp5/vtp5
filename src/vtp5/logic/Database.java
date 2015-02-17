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
	private String INSERT_LINE = "insert into leaderboard values("
			+ "?, ?, ?, ?, ?)";

	public Database(String path) {
		this.path = path;
	}

	public void createTable() {
		try {
			setupConnection();
			stmt.executeUpdate("create table leaderboard (id numeric, file varChar(255), "
					+ "time varChar(255), questions int, successRate real)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void query(String query) {

	}

	public void insert(int id, String file, String time, int noQuestion,
			double successRate) {
		try {
			/*
			 * stmt.executeUpdate("insert into leaderboard VALUES('" + file +
			 * "'," + time + "," + noQuestion + "," + successRate);
			 */
			// stmt.executeUpdate("insert into leaderboard VALUES('cares',100, 100, 100, 90.5 ");
			setupConnection();
			pstmt = con.prepareStatement(INSERT_LINE);
			pstmt.setLong(1, id);
			pstmt.setString(2, file);
			pstmt.setString(3, time);
			pstmt.setLong(4, noQuestion);
			pstmt.setLong(5, (int) successRate);
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
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					System.out.println(rs.getString(i));
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet select(String column, String column2) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("select " + column + "," + column2
					+ " from leaderboard");
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

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
			return true;
		} catch (SQLException e) {
			System.out.println("Exception when trying to find database file");
			e.printStackTrace();
			return false;
		}

	}

	public void setupConnection() {
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

	public String getPath() {
		return path;
	}
}
