package com.edts.concertsservice.common;

public enum StatusEnum {
	AVAILABLE("available"),
	NOT_AVAILABLE("not available"),
	ALREADY_USED("already used");
	
	private final String label;
	
	private StatusEnum(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
