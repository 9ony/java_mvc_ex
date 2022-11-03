package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemoDAO {
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	public int insertMemo(MemoVO memo) throws SQLException{
		try {
			con =DBUtil.getCon();
			String sql = "insert into memo(idx,name,msg,wdate)"
					+ " values(memo_seq.nextval,?,?,sysdate)";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, memo.getName());
			ps.setString(2, memo.getMsg());
			
			int n = ps.executeUpdate();
			
			return n;
		} finally{
			close();
		}
	}
	
	
	public void close() {
		try {
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
			if(con!=null) con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
