package com.bn.common.dto.profile;

import com.bn.common.dto.util.SerializationUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.io.Serializable;

/**
 * User: ktran
 * Date: 5/12/12
 */
public class ProfileInterestInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private int version = 1;

    private String id;
    private String title;
    private String description;
    private String imageUrl;

    public ProfileInterestInfo() {}

    public ProfileInterestInfo(String id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ProfileInterestInfo that = (ProfileInterestInfo)obj;
        if(id == null) {
            return that.id == null;
        }
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        if(id == null) {
            return 0;
        }
        return id.hashCode();
    }
    
    private void writeObject(java.io.ObjectOutputStream out) throws IOException
    {
        out.writeLong(serialVersionUID);
        out.writeInt(version);
        out.writeObject(SerializationUtil.serialize(id));
        out.writeObject(SerializationUtil.serialize(title));
        out.writeObject(SerializationUtil.serialize(description));
        out.writeObject(SerializationUtil.serialize(imageUrl));
    }

    private void readObject(java.io.ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        in.readLong();
        version = in.readInt();
        if (version>=1) {
            id = SerializationUtil.deserialize((String)in.readObject());
            title = SerializationUtil.deserialize((String)in.readObject());
            description = SerializationUtil.deserialize((String)in.readObject());
            imageUrl = SerializationUtil.deserialize((String)in.readObject());
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .toString();
    }
}
