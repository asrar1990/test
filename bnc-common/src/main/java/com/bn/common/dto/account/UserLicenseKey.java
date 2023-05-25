package com.bn.common.dto.account;

import com.bn.common.dto.common.LicenseEncryptionType;
import com.bn.common.dto.util.SerializationUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * DTO for User License Keys.
 * DRM licenses depend on a user-level encryption around the book key.  This class holds data for a user key for a given account and encryption type.
 * User: ataylor
 * Date: 4/10/12
 * Time: 7:46 PM
 */
public class UserLicenseKey implements Serializable {

    private static final long serialVersionUID = 1;

    private int version = 1;


    private long accountId;
    public long getAccountId() {      return accountId;   }
    public void setAccountId(long accountId) {  this.accountId = accountId;  }
    
    private int keyVersion;
    public int getKeyVersion(){return keyVersion;}
    public void setKeyVersion(int keyVersion){this.keyVersion = keyVersion;}
    
    private LicenseEncryptionType licenseEncryptionType;
    public LicenseEncryptionType getLicenseEncryptionType (){return this.licenseEncryptionType;}
    public void setLicenseEncryptionType( LicenseEncryptionType licenseEncryptionType){this.licenseEncryptionType = licenseEncryptionType;}
    
    private String userPrivateKey;
    public String getUserPrivateKey(){return userPrivateKey;    }
    public void setUserPrivateKey (String userPrivateKey){this.userPrivateKey = userPrivateKey;}
    
    private String userPublicKey;
    public String getUserPublicKey(){return userPublicKey;}
    public void setUserPublicKey(String userPublicKey){this.userPublicKey = userPublicKey;}
    
    private String userSymmetricKey;
    public String getUserSymmetricKey(){ return this.userSymmetricKey;}
    public void setUserSymmetricKey(String userSymmetricKey ){this.userSymmetricKey = userSymmetricKey;}

    private boolean isActive;
    public boolean getIsActive() {return isActive;}
    public void setIsActive(boolean isActive){this.isActive = isActive;}

    private void writeObject (ObjectOutputStream out) throws IOException{
        out.writeLong(serialVersionUID);
        out.writeInt(version);
        out.writeLong(accountId);
        out.writeInt(keyVersion);
        out.writeInt(licenseEncryptionType.ordinal());
        out.writeObject(SerializationUtil.serialize(userPrivateKey));
        out.writeObject(SerializationUtil.serialize(userPublicKey));
        out.writeObject(SerializationUtil.serialize(userSymmetricKey));
        out.writeBoolean(isActive);
    }

    /*
     * UserLicenseKey is internal only and not device-facing thus not versioning the deserialization
     */
    private void readObject(ObjectInputStream in) throws IOException,ClassNotFoundException{
        in.readLong();
        version = in.readInt();
        if(version >= 1){
            accountId = in.readLong();
            keyVersion = in.readInt();
            licenseEncryptionType   = LicenseEncryptionType.getType(in.readInt());
            userPrivateKey = SerializationUtil.deserialize((String) in.readObject());
            userPublicKey = SerializationUtil.deserialize((String) in.readObject());
            userSymmetricKey = SerializationUtil.deserialize((String) in.readObject());
            isActive = in.readBoolean();
        }
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this)
                .append("accountId",accountId)
                .append("keyVersion",keyVersion)
                .append("licenseEncType", licenseEncryptionType.name())
                .append("isActive",isActive).toString();
    }
}
