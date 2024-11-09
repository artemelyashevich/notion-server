package com.aelyashevich.notion.service;

import com.aelyashevich.notion.entity.User;
import com.aelyashevich.notion.service.contract.CrudService;

import java.util.Optional;

public interface UserService extends CrudService<User> {

    User findByEmail(final String email);
}
