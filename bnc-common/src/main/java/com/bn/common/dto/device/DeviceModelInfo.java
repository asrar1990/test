package com.bn.common.dto.device;

import com.bn.common.dto.util.SerializationUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.io.Serializable;

public class DeviceModelInfo implements Serializable
{
	
	private static final long	serialVersionUID	= 1L;
	
	private int version = 3;
	private String modelID;
	private String name;
	private String description;
	private String shortDescription;
	private String sourceID;
	private int encoding;
	private int bnModel;
	private int familyid;
	private String digest;
	private int iteration;
	private long tokenLimit = 0;
	private int maxAllowable = 0;
	private int video = 0;
	private int profile = 0;
	private int enabled = 1;
	private String ean;

	private boolean setVideo =false;
	private boolean setProfile =false;
	private boolean setEnabled =false;
	private boolean setTokenLiimit =false;
	private boolean setMaxAllowable =false;
	
	public DeviceModelInfo() {
		
	}
	
	public DeviceModelInfo(String modelId, String name, String description, String shortDescription
			, String sourceID,int encoding,int bnModel,int familyid)
	{
		this.modelID = modelId;
		this.name = name;
		this.description = description;
		this.shortDescription = shortDescription;
		this.sourceID = sourceID;
		this.encoding = encoding;
		this.bnModel = bnModel;
		this.familyid = familyid;
	}
	
	public String getModelID()
	{
		return modelID;
	}

	public void setModelID(String modelID)
	{
		this.modelID = modelID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getShortDescription()
	{
		return shortDescription;
	}

	public void setShortDescription(String shortDescription)
	{
		this.shortDescription = shortDescription;
	}

	public String getSourceID()
	{
		return sourceID;
	}

	public void setSourceID(String sourceID)
	{
		this.sourceID = sourceID;
	}

	public int getEncoding()
	{
		return encoding;
	}

	public void setEncoding(int encoding)
	{
		this.encoding = encoding;
	}

	public int getBnModel()
	{
		return bnModel;
	}

	public void setBnModel(int bnModel)
	{
		this.bnModel = bnModel;
	}

	public int getFamilyid()
	{
		return familyid;
	}

	public void setFamilyid(int familyid)
	{
		this.familyid = familyid;
	}

	public String getDigest()
	{
		return digest;
	}

	public void setDigest(String digest)
	{
		this.digest = digest;
	}

	public int getIteration()
	{
		return iteration;
	}

	public void setIteration(int iteration)
	{
		this.iteration = iteration;
	}

	public long getTokenLimit() {
		return tokenLimit;
	}

	public void setTokenLimit(long tokenLimit) {
		setTokenLiimit = true;
		this.tokenLimit = tokenLimit;
	}

	public int getMaxAllowable()
	{
		return maxAllowable;
	}

	public void setMaxAllowable(int maxAllowable)
	{
		setMaxAllowable = true;
		this.maxAllowable = maxAllowable;
	}

	public int getVideo() {
		return video;
	}

	public void setVideo(int video) {
		if (video==1)
			setVideo = true;
		this.video = video;
	}

	public int getProfile() {
		return profile;
	}

	public void setProfile(int profile) {
		if (profile==1)
			setProfile = true;
		this.profile = profile;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		setEnabled = true;
		this.enabled = enabled;
	}

	public boolean isSetVideo() {
		return setVideo;
	}

	public boolean isSetProfile() {
		return setProfile;
	}

	public boolean isSetEnabled() {
		return setEnabled;
	}

	public boolean isSetTokenLiimit() {
		return setTokenLiimit;
	}

	public boolean isSetMaxAllowable() {
		return setMaxAllowable;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeLong(serialVersionUID);
		out.writeInt(version);
		out.writeObject(SerializationUtil.serialize(modelID));
		out.writeObject(SerializationUtil.serialize(name));
		out.writeObject(SerializationUtil.serialize(description));
		out.writeObject(SerializationUtil.serialize(shortDescription));
		out.writeObject(SerializationUtil.serialize(sourceID));
		out.writeInt(encoding);
		out.writeInt(bnModel);
		out.writeInt(familyid);
		out.writeObject(SerializationUtil.serialize(digest));
		out.writeInt(iteration);
		out.writeLong(tokenLimit);
		out.writeInt(maxAllowable);
		out.writeInt(video);
		out.writeInt(profile);
		out.writeInt(enabled);
		out.writeObject(SerializationUtil.serialize(ean));
	}
	
	private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException
	{
		in.readLong();
		version = in.readInt();
		if (version>=1) {
			modelID = SerializationUtil.deserialize((String)in.readObject());
			name = SerializationUtil.deserialize((String)in.readObject());
			description = SerializationUtil.deserialize((String)in.readObject());
			shortDescription = SerializationUtil.deserialize((String)in.readObject());
			sourceID = SerializationUtil.deserialize((String)in.readObject());
			encoding = in.readInt();
			bnModel  = in.readInt();
			familyid = in.readInt();
			digest = SerializationUtil.deserialize((String)in.readObject());
			iteration = in.readInt();
			tokenLimit = in.readLong();
			maxAllowable = in.readInt();
		}
		if (version>=2) {
			video = in.readInt();
			profile = in.readInt();
			enabled = in.readInt();
		}
		if (version>=3) {
			ean = SerializationUtil.deserialize((String)in.readObject());
		}
	}
	
	public String toString()
	{
		return new ToStringBuilder(this)
			.append("modelId", modelID)
			.append("name", name)
			.append("description", description)
			.append("sourceID", sourceID)
			.append("encoding", encoding)
			.append("bnmodel",bnModel)
			.append("Familyid",familyid)
			.append("Digest", digest)
			.toString();
	}
}
