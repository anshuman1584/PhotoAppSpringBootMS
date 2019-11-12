package com.appsdeveloperblog.photoapp.api.users;

import org.springframework.data.repository.CrudRepository;

import com.appsdeveloperblog.photoapp.api.users.data.UserEntity;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);
}
