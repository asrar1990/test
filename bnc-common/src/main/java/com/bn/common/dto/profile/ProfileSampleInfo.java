package com.bn.common.dto.profile;

import com.bn.common.dto.util.SerializationUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This is unfortunately nothing more than a wrapper around EAN
 * User: kgankat
 * Date: 1/7/13
 */
public final class ProfileSampleInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private int version = 1;

    private String ean;

    public ProfileSampleInfo(String ean) {
        this.ean = ean;
    }

    public String getEan() {
        return ean;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeLong(serialVersionUID);
        out.writeInt(version);
        out.writeObject(SerializationUtil.serialize(ean));
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.readLong();
        version = in.readInt();
        if(version >= 1) {
            ean = SerializationUtil.deserialize((String)in.readObject());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ProfileSampleInfo that = (ProfileSampleInfo)obj;
        if(ean == null) {
            return that.ean == null;
        }
        return ean.equals(that.ean);
    }

    @Override
    public int hashCode() {
        if(ean == null) {
            return 0;
        }
        return ean.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("ean", ean)
            .toString();
    }
}
