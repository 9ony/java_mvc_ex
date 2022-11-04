package app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemoDAO {
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	public void close() {
		try {
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
			if(con!=null) con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	//메모테이블에 데이터 추가
	public int insertMemo(MemoVO memo) throws SQLException{
		try {
			con =DBUtil.getCon();
//			String : inmmutable (불변성) 원본은 변하지 않는다. + (문자열결합시 새로 객체를 만듬)
//			ex) String ("원본") 
			//          +=("두번째스트링"); 할시 new String()으로 새로운 객체를만들어서 heap영역에 저장한다
//			StringBuilder,StringBuffer : 문자열을 메모리 버퍼에 넣고 수정 삭제 삽입등의 편집작업을 할수 있다

//			String sql = "insert into memo(idx,name,msg,wdate)"
//					+ " values(memo_seq.nextval,?,?,sysdate)";
			StringBuilder buf = new StringBuilder("insert into memo(idx,name,msg,wdate)")
											.append(" values(memo_seq.nextval,?,?,sysdate)");
			ps = con.prepareStatement(buf.toString());
			
			ps.setString(1, memo.getName());
			ps.setString(2, memo.getMsg());
			
			int n = ps.executeUpdate();
			
			return n;
		} finally{
			close();
		}
	}
	//메모테이블에 데이터를 모두 select해옴
	public List<MemoVO> selectMemoAll() throws SQLException{
		try {
			con=DBUtil.getCon();
			StringBuilder buf = new StringBuilder("select idx,rpad(name,12,' ') name, rpad(msg,100,' ') msg,")
					.append("wdate from memo order by idx desc");
			String sql=buf.toString();
			ps=con.prepareStatement(sql);
			System.out.println("ps: "+ps);
			rs=ps.executeQuery();
			System.out.println("rs: "+rs);
			List<MemoVO> arr = makeList(rs);
			return arr;
		}finally {
			close();
		}
	}
	//리스트로 바꿔주는 메서드
	private List<MemoVO> makeList(ResultSet rs) throws SQLException{
		List<MemoVO> arr = new ArrayList<>();
		while(rs.next()) {
			int idx=rs.getInt("idx");
			String name=rs.getString("name");
			String msg=rs.getString("msg");
			java.sql.Date wdate=rs.getDate("wdate");
			MemoVO vo=new MemoVO(idx,name,msg,wdate);
			arr.add(vo);
		}
		return arr;
	}

	//db에 idx값을 통해 일치하는 메모 삭제
	public int deleteMemo(int idx)throws SQLException {
		try {
		con=DBUtil.getCon();
		StringBuilder buf = new StringBuilder("delete from memo where idx=?");
		ps=con.prepareStatement(buf.toString());
		ps.setInt(1, idx);
		int n = ps.executeUpdate();
		return n;
		}finally {
			close();
		}
	}

	//pk키로 메모 select
	public MemoVO selectMemo(int idx) throws SQLException{
		try {
			con=DBUtil.getCon();
			String sql = "select idx,name,msg,wdate from memo where idx=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, idx);
			rs=ps.executeQuery();
			List<MemoVO> arr=makeList(rs);
			if(arr!=null && arr.size()==1) {
				return arr.get(0);
			}
			return null;
		}finally {
			close();
		}
	}
	//해당 idx에 메모테이블 데이터를 사용자와 메세지값을 수정
	public int updateMemo(MemoVO vo) throws SQLException{
		try {
			con=DBUtil.getCon();
			StringBuilder buf=new StringBuilder("update memo set name=?,msg=?")
					.append("where idx=?");
			String sql=buf.toString();
			ps=con.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getMsg());
			ps.setInt(3, vo.getIdx());
			int n = ps.executeUpdate();
			return n;
		}finally {
			close();
		}
	}
	//memo테이블에 해당 문자열이 포함된 데이터를 찾아서 출력
	public List<MemoVO> findMemo(int type, String keyword) throws SQLException {
		try {
			con=DBUtil.getCon();
			String colName=(type==0)?"name":"msg";
			//String sql = "SELECT * FROM memo WHERE msg like '%"+keyword+"%' order by idx desc";
			StringBuilder buf=new StringBuilder("select idx,name,msg,wdate from memo")
					.append(" where "+colName+" like ?");
			String sql=buf.toString();
			ps=con.prepareStatement(sql);
			ps.setString(1, "%"+keyword+"%");
			rs=ps.executeQuery();
			List<MemoVO> arr=makeList(rs);
			return arr;
		}finally {
			close();
		}
	}
}
