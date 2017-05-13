package eckel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eckel.model.MPS;

public class MPSDao {
	public static String addMPS(MPS mps){
		String key="1";
		Connection con=DBConnection.getConnection();
		String sql="insert into mps_tb values(?,?,?,?,?,?)";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,mps.productid);
			pstmt.setInt(2,mps.ph);
			pstmt.setInt(3,mps.lotsize);
			pstmt.setInt(4,mps.rf);
			pstmt.setInt(5,mps.sf);
			pstmt.setString(6,mps.planString);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			key="0";
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
		return key;
	}
	public static List<MPS> getMPSes(){
		List<MPS> mpses=new ArrayList<MPS>();
		Connection con=DBConnection.getConnection();
		String sql="select * from mps_tb";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				mpses.add(new MPS(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getString(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
		return mpses;
	}
	public static MPS getMPSById(int id){
		MPS mps=new MPS();
		Connection con=DBConnection.getConnection();
		String sql="select * from mps_tb where productid=?";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,id);
			rs=pstmt.executeQuery();
			while(rs.next()){
				mps=new MPS(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
		return mps;
	}
	public static MPS ModifyMPSPlans(MPS mps){
		Connection con=DBConnection.getConnection();
		String sql="update mps_tb set plans=? where productid=?";
		PreparedStatement pstmt=null;
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,mps.planString);
			pstmt.setInt(2,mps.productid);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
		return mps;
	}
	public static void deleteMPSById(int id){
		Connection con=DBConnection.getConnection();
		String sql="delete from mps_tb where productid=?";
		PreparedStatement pstmt=null;
		try{
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,id);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			DBConnection.close(pstmt);
			DBConnection.close(con);
		}
	}

}
