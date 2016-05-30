package org.enset;

import java.util.Date;
import java.util.List;

import org.apache.catalina.core.ApplicationContext;
import org.codehaus.groovy.runtime.metaclass.MethodMetaProperty.GetBeanMethodMetaProperty;
import org.enset.dao.EtudiantRepository;
import org.enset.entities.Etudiant;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx= SpringApplication.run(Application.class, args);
		EtudiantRepository etudiantRepository= ((BeanFactory) ctx).getBean(EtudiantRepository.class);
		etudiantRepository.save(new Etudiant("chance", "chane", new Date()));
		etudiantRepository.save(new Etudiant("chance1", "chane1", new Date()));
		etudiantRepository.save(new Etudiant("chance2", "chane2", new Date()));
		etudiantRepository.save(new Etudiant("chance2", "chane2", new Date()));
		etudiantRepository.save(new Etudiant("chance2", "chane2", new Date()));
		List<Etudiant> etds=etudiantRepository.findAll();
		etds.forEach(e->System.out.println(e.getNom()));//java 8
	}
}
