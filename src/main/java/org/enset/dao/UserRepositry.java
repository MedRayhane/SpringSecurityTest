package org.enset.dao;

import org.enset.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositry extends JpaRepository<User, String> {

}
