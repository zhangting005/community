package com.example.community.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.community.model.Question;

@Mapper
public interface QuestionMapper {
	
	@Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
	void create(Question question);

	@Select("select * from question limit #{offSet},#{size}")
	List<Question> list(@Param("offSet")Integer offSet, @Param("size")Integer size);
	
	@Select("select count(1) from question")
	Integer count();

	@Select("select * from question where creator = #{userId} limit #{offSet},#{size}")
	List<Question> listByUserId(@Param("userId")Integer userId, @Param("offSet")Integer offSet, @Param("size")Integer size);

	@Select("select count(1) from question where creator = #{userId}")
	Integer countByUserId(@Param("userId")Integer userId);

	@Select("select * from question where id = #{id}")
	Question getById(@Param("id")Integer id);

}
