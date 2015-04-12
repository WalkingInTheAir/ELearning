package com.elearning.service.impl;

import com.elearning.dao.IUserDao;
import com.elearning.dao.impl.UserDaoImpl;
import com.elearning.domain.User;
import com.elearning.service.IUserService;

public class UserServiceImpl implements IUserService{
	private IUserDao userDao = new UserDaoImpl();
	public boolean isExist(User user) {
		try {
			return userDao.isExist(user);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
