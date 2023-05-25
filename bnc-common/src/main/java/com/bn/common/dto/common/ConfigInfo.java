package com.bn.common.dto.common;

import com.bn.common.dto.util.SerializationUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.io.Serializable;

public class ConfigInfo implements Serializable {

	private static final long serialVersionUID = 1;
	private int version = 1;

	private String service;
	private String key;
	private String value;
	private String site;

	public ConfigInfo() {
	}

	public ConfigInfo(String service, String key, String value) {
		this.service = service;
		this.key = key;
		this.value = value;
	}
	
	public ConfigInfo(String service, String key, String value, String site) {
		this.service = service;
		this.key = key;
		this.value = value;
		this.site = site;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
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
	
	public String getSite()
	{
		return site;
	}

	public void setSite(String site)
	{
		this.site = site;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.writeLong(serialVersionUID);
		out.writeInt(version);
		out.writeObject(SerializationUtil.serialize(service));
		out.writeObject(SerializationUtil.serialize(key));
		out.writeObject(SerializationUtil.serialize(value));
		out.writeObject(SerializationUtil.serialize(site));
	}

	private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException
	{
		in.readLong();
		version = in.readInt();
		if (version>=1) {
			service = SerializationUtil.deserialize((String) in.readObject());
			key = SerializationUtil.deserialize((String) in.readObject());
			value = SerializationUtil.deserialize((String) in.readObject());
			site = SerializationUtil.deserialize((String) in.readObject());
		}
	}

	public String toString() {
		return new ToStringBuilder(this)
			.append("service", service)
			.append("key", key)
			.append("value", value)
			.append("site", site)
			.toString();
	}
}
