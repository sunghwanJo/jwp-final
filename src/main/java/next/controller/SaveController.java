package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;
import core.mvc.Controller;

public class SaveController implements Controller {
	private QuestionDao questionDao;

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String writer, title, contents;
		
		questionDao = new QuestionDao();
		
		writer = request.getParameter("writer");
		title = request.getParameter("title");
		contents = request.getParameter("contents");

		Question question = new Question(writer, title, contents);
		questionDao.insert(question);

		return "redirect:/list.next";
	}

}
