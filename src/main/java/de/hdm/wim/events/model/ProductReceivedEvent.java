package de.hdm.wim.events.model;

import java.util.Date;

public class ProductReceivedEvent implements Event {

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
		return ""; // TODO: use meaningful id
	}

}