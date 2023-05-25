package com.bn.common.dto.common;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.Serializable;

@XmlRootElement(name = "ParameterInfo")
public class ParameterInfo implements Serializable
{
	private static final long serialVersionUID = 1;

	private String category;
	private String key;
	private String value;
	
	public ParameterInfo() {}
	
	public ParameterInfo(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public ParameterInfo(String category, String key, String value) {
		this.category = category;
		this.key = key;
		this.value = value;
	}
	
	public String toString() {
		return "ParameterInfo: " +  
			new ToStringBuilder(this)
					.append("category", category)
					.append("key", key)
					.append("value", value)
					.toString();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.write((int)serialVersionUID);
		out.writeObject(category);
		out.writeObject(key);
		out.writeObject(value);
	}
	
	private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException
	{
		int s = (int)in.read();
		if (s>=1) {
			category = (String)in.readObject();
			key = (String)in.readObject();
			value = (String)in.readObject();
		}
	}
}

