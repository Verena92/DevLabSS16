package de.hdm.wim.events.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ProductReceivedEvent implements Event {

	@Id
	@GeneratedValue
	private long id;

	@OneToOne(cascade = { CascadeType.ALL })
	private Product product;

	public ProductReceivedEvent() {
		
	}
	
	public ProductReceivedEvent(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getTimestamp() {
		return new Date(); // TODO: use meaningful date
	}

	@Override
	public String getId() {
		return "" + this.id;
	}

}