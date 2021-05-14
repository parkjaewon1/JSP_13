package ch14.service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch14.dao.Board;
import ch14.dao.BoardDao;
public class UpdateForm implements CommandProcess {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum= request.getParameter("pageNum");
		BoardDao bd = BoardDao.getInstance();
		Board board = bd.select(num);

		request.setAttribute("board", board);
		request.setAttribute("pageNum", pageNum);
		return "updateForm.jsp";
	}

}
