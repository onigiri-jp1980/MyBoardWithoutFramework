package myBoardWithoutFrameworkDatabaseBacked;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class updatePost
 */
@WebServlet("/update")
public class updatePost extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public updatePost() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		MyBoardWithoutFrameworkDAO mbDAO = new MyBoardWithoutFrameworkDAO();
		// idが指定されているときに処理
		if (request.getParameter("id") != null) {

			MyBoardWithoutFramework Post = mbDAO.selectPost(Integer.parseInt(request.getParameter("id")));
			// 更新画面のJSPにフォワード
			request.setAttribute("post", Post);


			RequestDispatcher rDist = request.getRequestDispatcher("/WEB-INF/jsp/update.jsp");
			request.setCharacterEncoding("UTF-8");
			rDist.forward(request, response);
		} else {
			// idが指定されていなければtopにリダイレクト
			request.setCharacterEncoding("UTF-8");
			response.sendRedirect(request.getContextPath()+"/top");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println("doPost:update id=" + request.getParameter("name"));
		MyBoardWithoutFramework mb = new MyBoardWithoutFramework();
		mb.setId(Integer.parseInt(request.getParameter("id")));
		mb.setName(request.getParameter("name"));
		mb.setMsg(request.getParameter("msg"));
		mb.setEmail(request.getParameter("email"));
		MyBoardWithoutFrameworkDAO mbDAO = new MyBoardWithoutFrameworkDAO();
		mbDAO.updatePost(mb);
		System.out.println("doPost: msg=\n"+mb.getMsg());

		response.sendRedirect(request.getContextPath()+"/top");
	}

}
