package com.my.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnection {

  public static void close(ResultSet rs, Statement stat, Connection con) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (stat != null) {
      try {
        stat.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    if (con != null) {
      try {
        con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public static void close(Statement stat, Connection con) {
    close(null, stat, con);
  }
}
