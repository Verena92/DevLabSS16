package de.hdm.wim.events.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ProductReceivedEvent implements Event {

	@Id
	private String id;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Product product;

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
		return this.id; // TODO: use meaningful id
	}

	public void setId(String id) {
		this.id = id;
	}

}