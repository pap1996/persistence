package persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
public class Message {
	
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String text;
	

}
