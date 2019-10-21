package com.example.community.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class paginationDTO {

	private List<QuestionDTO> questions;
	// 是否有向前按钮
	private boolean showPrevious;

	private boolean showFirstPage;

	private boolean showNext;

	private boolean showEndPage;

	private Integer page;
	// 用list装所有的页码
	private List<Integer> pages = new ArrayList();

	private Integer totalCount;
	
	private Integer totalPage;

	public void setPagination(Integer totalPage, Integer page) {
		this.page = page;
		this.totalPage = totalPage;
		pages.add(page);
		for(int i =1;i <=3;i++) {
			if(page - i >0) {
				pages.add(0,page - i);
			}
			if(page + i <= totalPage) {
				pages.add(page + i);
			}
		}
		
		// 是否展示上一页
		if (page == 1) {
			showPrevious = false;
		} else {
			showPrevious = true;
		}
		// 是否展示下上一页
		if (page == totalPage) {
			showNext = false;
		} else {
			showNext = true;
		}
		// 是否展示第一页
		if (pages.contains(1)) {
			showFirstPage = false;
		} else {
			showFirstPage = true;
		}
		// 是否展示下最后上一页
		if (pages.contains(totalPage)) {
			showEndPage = false;
		} else {
			showEndPage = true;
		}

	}
}
