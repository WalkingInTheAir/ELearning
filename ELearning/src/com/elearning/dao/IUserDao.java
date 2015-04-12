package com.elearning.dao;

import com.elearning.domain.User;

public interface IUserDao {
	public boolean isExist(User user) throws Exception;
}
