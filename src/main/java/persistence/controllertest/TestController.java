package persistence.controllertest;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import persistence.model.Message;

@Controller
@RequestMapping("/writeMessages")
public class TestController {
	
	@Autowired
	private LocalContainerEntityManagerFactoryBean entityManager;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@GetMapping(value = "/write")
	public void writeMessages() {
		
		
		EntityManagerFactory emf = entityManager.getObject();
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		
		Message message = new Message();
		message.setText("test hibernate");
		
		em.persist(message);
		
		tx.commit();
		em.close();
		
	}
	
	@GetMapping(value = "/change")
	public void changeMessages() {
		
		EntityManagerFactory emf = entityManager.getObject();
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		
		List<Message> listMessages = em.createQuery("select m from Message m").getResultList();
		
		Iterator<Message> it = listMessages.iterator();
		while(it.hasNext()) {
			Message message = it.next();
			message.setText(message.getText() + " change ");
		}
		
		tx.commit();
		em.close();
	}
	
	
	

}
