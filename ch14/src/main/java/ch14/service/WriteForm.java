package ch14.service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch14.dao.Board;
import ch14.dao.BoardDao;
public class WriteForm implements CommandProcess {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		int num = 0, ref = 0, re_level = 0, re_step = 0;
		String pageNum = request.getParameter("pageNum");
		num = Integer.parseInt(request.getParameter("num"));
		if (num != 0) { // 0이면 처음 글쓰기 0이 아니면 답변글
			BoardDao bd = BoardDao.getInstance();
			Board board = bd.select(num);
			ref = board.getRef();
			re_step  = board.getRe_step();
			re_level = board.getRe_level();
		}
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("num", num);
		request.setAttribute("ref", ref);
		request.setAttribute("re_level", re_level);
		request.setAttribute("re_step", re_step);
		return "writeForm.jsp";
	}
}