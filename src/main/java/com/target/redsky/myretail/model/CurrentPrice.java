package com.target.redsky.myretail.model;

public class CurrentPrice {

	private Double value;

	private String currency_code;

	public CurrentPrice() {
	}

	public CurrentPrice(Double value, String currency_code) {
		this.value = value;
		this.currency_code = currency_code;
	}
	
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getCurrency_code() {
		return currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	@Override
	public String toString() {
		return "Price [value=" + value + ", currency_code=" + currency_code + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency_code == null) ? 0 : currency_code.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CurrentPrice other = (CurrentPrice) obj;
		if (currency_code == null) {
			if (other.currency_code != null)
				return false;
		} else if (!currency_code.equals(other.currency_code))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
