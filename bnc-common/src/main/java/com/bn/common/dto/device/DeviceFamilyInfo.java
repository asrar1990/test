package com.bn.common.dto.device;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.io.Serializable;

public final class DeviceFamilyInfo implements Serializable {

	private static final long serialVersionUID = 1;
	
	private int version = 1;
	private String id;
	private String name;
	
	public DeviceFamilyInfo() { }
	
	public DeviceFamilyInfo(String id, String name) {
		this.id = id;
		this.name = name;
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
	
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("name", name)
		.toString();
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeLong(serialVersionUID);
		out.writeInt(version);
		out.writeObject(id);
		out.writeObject(name);
	}
	
	private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException
	{
		in.readLong();
		version = in.readInt();
		if (version>=1) {
			id = (String)in.readObject();
			name = (String)in.readObject();
		}
	}
}
