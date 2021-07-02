package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * @author lzx
 * @create 2021/6/4 14:36
 */
@Controller
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Override
	public User selectUserByUserNameAndPwd(Map<String, Object> map) {
		return userMapper.selectUserByUserNameAndPwd(map);
	}

	@Override
	public List<User> selectAllUsers() {
		return userMapper.selectAllUsers();
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}
}
