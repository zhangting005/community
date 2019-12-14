package com.example.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.community.model.User;

@Mapper
public interface UserMapper {
	
	@Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
	void insert(User user);

	@Select("select * from user where token = #{token}")
	User findByToken(@Param("token")String token);

	@Select("select * from user where id = #{id}")
	User findById(@Param("id")Integer id);

	@Select("select * from user where account_id = #{accountId}")
	User findAccountId(@Param("accountId")String accountId);

	@Update("update user set name = #{name},token = #{token},gmt_modified = #{gmtModified},avatar_url = #{avatarUrl} where account_id = #{accountId}")
	void update(User dbUser);

}
