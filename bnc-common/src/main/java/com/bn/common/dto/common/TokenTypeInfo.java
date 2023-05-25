package com.bn.common.dto.common;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.io.Serializable;

public final class TokenTypeInfo implements Serializable {

	private static final long serialVersionUID = 1;
	
	private int version = 1;
	private String id;
	private String name;
	private long duration;
	private String description;
	
	public TokenTypeInfo() { }
	
	public TokenTypeInfo(String id, String name,long duration, String description) {
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.description = description;
	}

	public String getId(){
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getDuration(){
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
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
		.append("name", name)
		.append("duration", duration)
		.append("description", description)
		.toString();
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeLong(serialVersionUID);
		out.writeInt(version);
		out.writeObject(id);
		out.writeObject(name);
		out.writeLong(duration);
		out.writeObject(description);
	}
	
	private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException
	{
		in.readLong();
		version = in.readInt();
		if (version>=1) {
			id = (String)in.readObject();
			name = (String)in.readObject();
			duration = in.readLong();
			description = (String)in.readObject();
		}
	}
}
