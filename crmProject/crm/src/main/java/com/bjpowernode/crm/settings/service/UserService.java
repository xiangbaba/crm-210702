package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @author lzx
 * @create 2021/6/4 14:36
 */
public interface UserService {
	/**
	 * 根据用户名和密码查询用户
	 * @param map
	 * @return
	 */
	User selectUserByUserNameAndPwd(Map<String,Object> map);
	/**
	 * 查询全部用户
	 */
	List<User> selectAllUsers();

	int updateByPrimaryKeySelective(User record);
}
