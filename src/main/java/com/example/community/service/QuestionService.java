package com.example.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.community.dto.QuestionDTO;
import com.example.community.dto.paginationDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;

@Service
public class QuestionService {

	@Autowired
	private QuestionMapper questionMapper;

	@Autowired
	private UserMapper userMapper;

	public paginationDTO list(Integer page, Integer size) {
		paginationDTO paginationDTO = new paginationDTO();
		Integer totalCount = questionMapper.count();
		paginationDTO.setPagination(totalCount, page, size);
		if (page < 1) {
			page = 1;
		}
		if (page > paginationDTO.getTotalPage()) {
			page = paginationDTO.getTotalPage();
		}

		Integer offSet = (page - 1) * size;
		List<Question> questions = questionMapper.list(offSet, size);
		List<QuestionDTO> questionDTOList = new ArrayList();
		// 接下来要将Question和User都放在QuestionDTO中，通过Question中的ID找到User

		for (Question question : questions) {
			User user = userMapper.findById(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}
		paginationDTO.setQuestions(questionDTOList);

		return paginationDTO;
	}

}