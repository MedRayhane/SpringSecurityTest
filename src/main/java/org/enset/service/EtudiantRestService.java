package org.enset.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.enset.dao.EtudiantRepository;
import org.enset.entities.Etudiant;
import org.neo4j.cypher.internal.compiler.v2_1.executionplan.addEagernessIfNecessary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EtudiantRestService {
	@Autowired
	private EtudiantRepository etudiantRepository;

	@Secured(value = { "ROLE_ADMIN", "ROLE_PROF1" })
	@RequestMapping(value = "/etudiants", method = RequestMethod.POST)
	public Object saveEtudiant(@RequestBody @Valid Etudiant etudiant, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, Object> errors = new HashMap<>();
			errors.put("errors", true);
			for (FieldError fe : bindingResult.getFieldErrors()) {
				// on parcour par ce que jakson ne peut pas le serlaliser
				// directement en json le binding
				// donc on recuper les information et on le mettre dans un objet
				// serlizable
				errors.put(fe.getField(), fe.getDefaultMessage());
			}
			return errors;// il retourne un objet de type JSON

		}
		return etudiantRepository.save(etudiant);

	}

	// je coanis la page et le nombre des etudiants
	@Secured(value = { "ROLE_ADMIN", "ROLE_PROF1" })
	@RequestMapping(value = "/etudiants", method = RequestMethod.GET)
	public Page<Etudiant> listEtudiants(int page, int size) {
		return etudiantRepository.findAll(new PageRequest(page, size));

	}

	@RequestMapping(value = "/getlogedUser")
	public Map<String, Object> getlogedUser(HttpServletRequest httpServletRequest)// quon
																					// lui
																					// dit
																					// quon
																					// veu
																					// httpsesson
																					// automaticement
																					// il
																					// nous
																					// inject
																					// la
																					// session
																					// courante
	{
		HttpSession httpSession = httpServletRequest.getSession();
		SecurityContext context = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
		String username = context.getAuthentication().getName();
		List<String> role = new ArrayList<>();
		for (GrantedAuthority ga : context.getAuthentication().getAuthorities()) {

			role.add(ga.getAuthority());

		}
		Map<String, Object> parms = new HashMap<>();
		parms.put("username", username);
		parms.put("role", role);
		return parms;
	}
}
