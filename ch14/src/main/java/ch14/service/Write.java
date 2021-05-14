package ch14.service;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch14.dao.Board;
import ch14.dao.BoardDao;
public class Write implements CommandProcess {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		try {	request.setCharacterEncoding("utf-8");	} catch (UnsupportedEncodingException e) {	}
		Board board = new Board();
		int num = Integer.parseInt(request.getParameter("num"));
		int ref = Integer.parseInt(request.getParameter("ref"));
		int re_level = Integer.parseInt(request.getParameter("re_level"));
		int re_step = Integer.parseInt(request.getParameter("re_step"));
		
		String subject = request.getParameter("subject");
		String writer  = request.getParameter("writer");
		String content = request.getParameter("content");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String pageNum = request.getParameter("pageNum");
		String ip = request.getRemoteAddr(); // 글쓰는 사람이 사용하는 컴퓨터의 ip번호
		board.setIp(ip);				board.setNum(num);
		board.setContent(content);		board.setEmail(email);
		board.setPassword(password);	board.setRe_level(re_level);
		board.setRe_step(re_step);		board.setRef(ref);
		board.setSubject(subject);		board.setWriter(writer);
		BoardDao bd = BoardDao.getInstance();
		int result = bd.insert(board);		
		request.setAttribute("result", result);
		request.setAttribute("pageNum", pageNum);
		return "write.jsp";
	}
}