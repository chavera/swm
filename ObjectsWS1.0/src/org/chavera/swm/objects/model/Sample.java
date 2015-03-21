package org.chavera.swm.objects.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sample", catalog="sample")
public class Sample{
	private String name;
	
	@Id
	@Column(name="test")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
