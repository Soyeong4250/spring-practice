package com.sping.dao;

import com.sping.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;
import java.util.List;

public class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public List<User> selectAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> userList = null;

        try {
            conn = connectionMaker.getConnection();
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
            conn = connectionMaker.getConnection();
            pstmt = conn.prepareStatement("select id, name, password from users where id = ?");
            pstmt.setString(1, sId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
            }
            if(user == null) {
                throw new EmptyResultDataAccessException(1);
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
            conn = connectionMaker.getConnection();
            pstmt = conn.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

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
            conn = connectionMaker.getConnection();
            pstmt = new DeleteAllStrategy().makePreparedStatement(conn);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionClose.close(conn, pstmt);
        }
    }

    public int getCount() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cnt = 0;

        try{
            conn = connectionMaker.getConnection();
            pstmt = conn.prepareStatement("select count(*) from users");

            rs = pstmt.executeQuery();

            if (rs.next()) {
                cnt = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionClose.close(conn, pstmt);
        }

        return cnt;
    }
}
