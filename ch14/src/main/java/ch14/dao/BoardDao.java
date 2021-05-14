package ch14.dao;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import javax.naming.*;
import javax.sql.DataSource;
public class BoardDao {
	// singleton
	private static BoardDao instance = new BoardDao();
	private BoardDao() {}
	public static BoardDao getInstance() {
		return instance;
	}
	// DataBase Connection Pool
	private Connection getConnection() {
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
		}catch (Exception e) {
			System.out.println("연결 에러 : "+e.getMessage());
		}
		return conn;
	} 
//	public List<Board> list() {
	public List<Board> list(int startRow, int endRow) {
		List<Board> list = new ArrayList<Board>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
//		String sql = "select * from board order by num desc";
//      mysql
//      String sql = "select * from board order by num desc limit startRow, 10'		
//		String sql = "select * from (select rowNum rn, a.* from "
//				+ "(select * from board order by num desc) a)"
//				+ "    where rn between ? and ?";
		String sql = "select * from (select rowNum rn, a.* from "
				+ "(select * from board order by ref desc, re_step) a)"
				+ "    where rn between ? and ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Board board = new Board();
				board.setNum(rs.getInt("num"));
				board.setWriter(rs.getString("writer"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setEmail(rs.getString("email"));
				board.setReadcount(rs.getInt("readcount"));
				board.setPassword(rs.getString("password"));
				board.setRef(rs.getInt("ref"));
				board.setRe_step(rs.getInt("re_step"));
				board.setRe_level(rs.getInt("re_level"));
				board.setIp(rs.getString("ip"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setDel(rs.getString("del"));
				
				list.add(board);
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
		return list;
	}
	public int insert(Board board) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql="insert into board values(?,?,?,?,?,0,?,?,?,?,?,sysdate,'n')";
		// 가장 큰 num을 가지고 와서 1을 더하면 null이면 1
		String sql2 = "select nvl(max(num),0) + 1 from board";
		String sqlUp = "update board set re_step = re_step+1 where ref=? and re_step > ?";
		try {
			pstmt = conn.prepareStatement(sql2);
			rs = pstmt.executeQuery();
			rs.next();
			int number = rs.getInt(1); // 가장 큰 num에 1을 더한 값
			pstmt.close();
			if (board.getNum() != 0) { // 답변글
				pstmt = conn.prepareStatement(sqlUp);
				pstmt.setInt(1, board.getRef());
				pstmt.setInt(2, board.getRe_step());
				pstmt.executeUpdate();
				pstmt.close();
				board.setRe_step(board.getRe_step()+1);
				board.setRe_level(board.getRe_level()+1);
			} else board.setRef(number); // 답변글 아니면 ref와 num은 같다
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getSubject());
			pstmt.setString(4, board.getContent());
			pstmt.setString(5, board.getEmail());
			pstmt.setString(6, board.getPassword());
			// 답변글
			pstmt.setInt(7, board.getRef());
			pstmt.setInt(8, board.getRe_step());
			pstmt.setInt(9, board.getRe_level());
			
			pstmt.setString(10, board.getIp());
			
			result = pstmt.executeUpdate();			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
		return result;
	}
	public void readcoutUpdate(int num) {
		PreparedStatement pstmt = null;
		Connection conn = getConnection();
		// readcount 1증가
		String sql="update board set readcount = readcount + 1 where num=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
	
			pstmt.executeUpdate();			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
	}
	public Board select(int num) {
		Board board = new Board();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql = "select * from board where num=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				board.setNum(rs.getInt("num"));
				board.setWriter(rs.getString("writer"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setEmail(rs.getString("email"));
				board.setReadcount(rs.getInt("readcount"));
				board.setPassword(rs.getString("password"));
				board.setRef(rs.getInt("ref"));
				board.setRe_step(rs.getInt("re_step"));
				board.setRe_level(rs.getInt("re_level"));
				board.setIp(rs.getString("ip"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setDel(rs.getString("del"));
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
		return board;
	}
	public int update(Board board) { // board 화면에서 입력한 게시글
		int result = 0;
		PreparedStatement pstmt = null;
		Connection conn = getConnection();
		String sql="update board set content=?,subject=? where num=?";
		Board bd = select(board.getNum()); // bd은 num으로 읽은 게시글
		if (bd.getPassword().equals(board.getPassword())) { // 화면에서 읽은 암호와 읽은 게시글의 암호와 비교
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, board.getContent());
				pstmt.setString(2, board.getSubject());				
				pstmt.setInt(3, board.getNum());				
				result = pstmt.executeUpdate();			
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}finally {
				try {
					if (pstmt != null) pstmt.close();
					if (conn != null)  conn.close();
				}catch (Exception e) {		}
			}
		} else result = -1;
		return result;
	}
	public int delete(int num, String password) { // board 화면에서 입력한 게시글
		int result = 0;
		PreparedStatement pstmt = null;
		Connection conn = getConnection();
		String sql="update board set del='y' where num=?";
		Board bd = select(num); // bd은 num으로 읽은 게시글
		if (password.equals(bd.getPassword())) { // 화면에서 읽은 암호와 읽은 게시글의 암호와 비교
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,num);				
				result = pstmt.executeUpdate();			
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}finally {
				try {
					if (pstmt != null) pstmt.close();
					if (conn != null)  conn.close();
				}catch (Exception e) {		}
			}
		} else result = -1;
		return result;
	}
	public int getTotal() {
		int total = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		String sql = "select count(*) from board";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null)  conn.close();
			}catch (Exception e) {		}
		}
		return total;
	}
}