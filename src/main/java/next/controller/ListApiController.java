package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;

import com.google.gson.Gson;

import core.mvc.Controller;

public class ListApiController implements Controller {
	private QuestionDao questionDao;;
	private List<Question> questions;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Gson gson = new Gson();
		
		questionDao  = new QuestionDao();
		questions = questionDao.findAll();
		
		String json = gson.toJson(questions);
		
		response.setContentType("application/json");
		response.getWriter().write(json.toString());
		
		return "api";
	}
	
}
