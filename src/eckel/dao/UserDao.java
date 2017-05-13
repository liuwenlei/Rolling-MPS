package eckel.dao;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eckel.model.User;

public class UserDao {
	public static List<User> getUsers(){
		List<User> users=new ArrayList<User>();
		Connection con=DBConnection.getConnection();
		String sql="select * from user_tb";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				users.add(new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
		return users;
	}
	public static void modifyUser(User user){
		Connection con=DBConnection.getConnection();
		String sql="update user_tb set username=?,account=?,password=?,status=? where userid=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,user.username);
			pstmt.setString(2,user.account);
			pstmt.setString(3,user.password);
			pstmt.setString(4,user.status);
			pstmt.setInt(5,user.userid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	public static void deleteUserById(int userid){
		Connection con=DBConnection.getConnection();
		String sql="delete from user_tb where userid=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,userid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	public static void addUser(User user){
		Connection con=DBConnection.getConnection();
		String sql="insert into user_tb values(?,?,?,?,?)";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,user.userid);
			pstmt.setString(2,user.username);
			pstmt.setString(3,user.account);
			pstmt.setString(4,user.password);
			pstmt.setString(5,user.status);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}
	public static User getUserByAccount(String account){
		User user=new User();
		String sql="select * from user_tb where account=?";
		Connection con=DBConnection.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, account);
			rs=pstmt.executeQuery();
			while(rs.next()){
				user.userid=rs.getInt(1);
				user.username=rs.getString(2);
				user.account=rs.getString(3);
				user.password=rs.getString(4);
				user.status=rs.getString(5);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(con);
			DBConnection.close(con);
		}
		return user;
		
	}
}
