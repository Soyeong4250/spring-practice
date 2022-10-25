package com.sping.dao;

import com.sping.domain.User;

import java.sql.*;
import java.util.List;

public class UserDao {
    public List<User> selectAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> userList = null;

        try {
            conn = ConnectionMaker.getConnection();
            pstmt = conn.prepareStatement("select * from users");
            rs = pstmt.executeQuery();

            while(rs.next()) {
                User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionClose.close(conn, pstmt, rs);
        }

        return userList;
    }

    public User selectById(String sId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try{
            conn = ConnectionMaker.getConnection();
            pstmt = conn.prepareStatement("select id, name, password from users where id = ?");
            pstmt.setString(1, sId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionClose.close(conn, pstmt, rs);
        }
        return user;
    }

    public void add(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionMaker.getConnection();
            pstmt = conn.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
            pstmt.setString(1, "id");
            pstmt.setString(2, "name");
            pstmt.setString(3, "password");

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionClose.close(conn, pstmt);
        }
    }

    public void deleteAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConnectionMaker.getConnection();
            pstmt = conn.prepareStatement("delete from users");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionClose.close(conn, pstmt);
        }
    }
}
