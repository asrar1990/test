package com.bn.common.dto.common;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.io.Serializable;

public final class TokenStateInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int version = 1;
	private int id;
	private String description;
	
	public TokenStateInfo() { }
	public TokenStateInfo(int id, String description){
		this.id = id;
		this.description = description;
	}

	public int getId(){
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("description", description).toString();
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.writeLong(serialVersionUID);
		out.writeInt(version);
		out.writeInt(id);
		out.writeObject(description);
	}
	
	private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException
	{
		in.readLong();
		version = in.readInt();
		if (version>=1) {
			id = in.readInt();
			description = (String)in.readObject();
		}
	}
}
