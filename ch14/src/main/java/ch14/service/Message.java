package ch14.service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class Message implements CommandProcess {
	public String requestPro(HttpServletRequest request, HttpServletResponse response) {
		String msg = "대박 사건";
		request.setAttribute("msg", msg);
		return "/kk/message.jsp";
	}

}
