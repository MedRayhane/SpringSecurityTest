package org.enset.dao;

import org.enset.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepositry  extends JpaRepository<Role, String>{

}
