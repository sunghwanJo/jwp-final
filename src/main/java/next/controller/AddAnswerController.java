package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import core.mvc.Controller;

public class AddAnswerController implements Controller {
	private AnswerDao answerDao = new AnswerDao();
	private QuestionDao questionDao = new QuestionDao();
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long questionId;
		String writer, contents;
		
		questionId = Long.parseLong(request.getParameter("questionId"));
		writer = request.getParameter("writer");
		contents = request.getParameter("contents");
		
		//Answer insert
		Answer answer = new Answer(writer, contents, questionId);
		answerDao.insert(answer);
		
		//question Count++
		questionDao.addCountOfComment(questionId);
		
		return "api";
	}
}
