package org.enset.service;

import java.util.List;

import org.enset.dao.RoleRepositry;
import org.enset.dao.UserRepositry;
import org.enset.entities.Role;
import org.enset.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Secured(value={"ROLE_ADMIN"})
public class UserRestService {
	@Autowired
	private UserRepositry userRepositry;
	@Autowired
	private RoleRepositry roleRepositry;

	@RequestMapping(value = "/adduser")
	public User save(User user) {
		return userRepositry.save(user);

	}

	@RequestMapping(value = "/listuser")
	public List<User> findAll() {
		return userRepositry.findAll();

	}
	@RequestMapping(value = "/addrole")
	public Role saveRole(Role r) {
		return roleRepositry.save(r);

	}
	@RequestMapping(value = "/listrole")
	public List<Role> findAllRole() {
		return roleRepositry.findAll();

	}

	@RequestMapping(value = "/addroletouser")
	public User addRoleTUser(String username,String role){
		User user=userRepositry.findOne(username);
		Role role2=roleRepositry.findOne(role);
		user.getRoles().add(role2);
		userRepositry.save(user);
		return user;
		
		
		
	}

}
