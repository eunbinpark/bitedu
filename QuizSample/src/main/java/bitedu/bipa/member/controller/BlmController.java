package bitedu.bipa.member.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitedu.bipa.member.service.QuizService;
import bitedu.bipa.member.vo.BookCopy;

/**
 * Servlet implementation class BlmController
 */
@WebServlet("/action.do")
public class BlmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private QuizService quizService;
    
    
    @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		quizService = new QuizService();
	}

	/**
     * @see HttpServlet#HttpServlet()
     */
    public BlmController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		String cmd = request.getParameter("cmd");
		cmd = cmd == null?"list":cmd;
		String url = "./manager/book_list.jsp";
		boolean isRedirect = false;
		if(cmd.equals("list")) {
			ArrayList<BookCopy> list = quizService.searchBookAll();

			request.setAttribute("list", list);

		} else if(cmd.equals("regist")) {
			BookCopy copy = new BookCopy();
			copy.setIsbn(request.getParameter("isbn"));
			copy.setTitle(request.getParameter("book_title"));
			copy.setAuthor(request.getParameter("author"));
			copy.setPublisher(request.getParameter("publisher"));
			String date = request.getParameter("publish_date");
			System.out.println(date);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date now = df.parse(date);
				copy.setPublishDate(new Timestamp(now.getTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			quizService.registBook(copy);
			url = "./BlmController?cmd=list";
		} else if(cmd.equals("remove")) {
			String bookSeq = request.getParameter("bookSeq");
			boolean flag = quizService.removeBook(bookSeq);
			System.out.println(flag);
			url = "./BlmController?cmd=list&flag=true";
			isRedirect = true;
		} else if(cmd.equals("view_regist")) {
			url = "./manager/book_regist.jsp";
		} else if(cmd.equals("view_detail")) {
			String bookSeq = request.getParameter("bookSeq");
			BookCopy copy = quizService.findBook(bookSeq);
			request.setAttribute("copy", copy);
			url = "./manager/book_detail.jsp";
		} else if(cmd.equals("view_update")) {
			String bookSeq = request.getParameter("bookSeq");
			BookCopy copy = quizService.findBook(bookSeq);
			request.setAttribute("copy", copy);
			url = "./manager/book_update.jsp";
		} else if(cmd.equals("update")) {
			BookCopy copy = new BookCopy();
			copy.setBookSeq(Integer.parseInt(request.getParameter("book_seq")));
			copy.setIsbn(request.getParameter("isbn"));
			copy.setTitle(request.getParameter("book_title"));
			copy.setAuthor(request.getParameter("author"));
			String date = request.getParameter("publish_date");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date now = df.parse(date);
				copy.setPublishDate(new Timestamp(now.getTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean flag = quizService.modifyBook(copy);
			url = "./BlmController?cmd=list";
		} else if (cmd.equals("path")){
			String context = getServletContext().getContextPath();
			String urls = request.getRequestURL().toString();
			String uri = request.getRequestURI();
			System.out.printf("%s \n %s \n %s", context, urls, uri);
			
			url = "./path/path.jsp";
			
		}
		
		if(!isRedirect) {
			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request, response);
		} else {
			response.sendRedirect(url);
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
