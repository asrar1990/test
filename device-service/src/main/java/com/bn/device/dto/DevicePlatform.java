package com.bn.device.dto;

import com.bn.common.dto.util.SerializationUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.io.Serializable;

public class DevicePlatform implements Serializable {
	private static final long serialVersionUID  = 1L;
	
	private int sVersion = 1;
	public static final int ENCORE1_0 = 6;
	public static final int ENCORE1_2 = 36;
	public static final int GOSSAMER = 37;
	public static final int OWL = 64; //need a better way to identify devices... for now using the "established" approach
	
	
	private int id = 0;
	private String model = null;
	private String version = null;
	private String name = null;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		if (model != null) {
			model = model.trim();
		}
		this.model = model;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		if (version != null) {
			version = version.trim();
		}
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeLong(serialVersionUID);
		out.writeInt((int)sVersion);
		out.writeInt(id);
		out.writeObject(SerializationUtil.serialize(model));
		out.writeObject(SerializationUtil.serialize(version));
		out.writeObject(SerializationUtil.serialize(name));
	}
	
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.readLong();
		sVersion = in.readInt();
		if (sVersion>=1) {
			id = in.readInt();
			model = SerializationUtil.deserialize((String)in.readObject());
			version = SerializationUtil.deserialize((String)in.readObject());
			name = SerializationUtil.deserialize((String)in.readObject());
		}
	}
	
	public String toDisplayString() {
		return name + " " + version;
	}
	
	public boolean matches(String deviceModel, String deviceVersion) {
				//both the models are null, or
		return ((this.model == null && deviceModel == null) || 
				//device models match
				(deviceModel == null ? false : deviceModel.equalsIgnoreCase(this.model))) &&
				//AND
				//the version is empty in db
				(((this.version == null || this.version.isEmpty()) && 
				//and the input version is "empty", or 1.0
				(deviceVersion == null || deviceVersion.isEmpty() || "0.0".equals(deviceVersion) || "1.0".equals(deviceVersion))) || 
				//or the device models match
				(deviceVersion == null ? false : deviceVersion.equalsIgnoreCase(this.version)));
	}
	
	public static boolean isGossamer(Integer deviceId) {
		return deviceId != null && deviceId.intValue() == GOSSAMER;
	}
	public static boolean isOwl(Integer deviceId) {
		return deviceId != null && deviceId.intValue() == OWL;
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this).
            append("id", id).
            append("model", model).
            append("version", version).
            append("name", name).
            toString();
    }
}
