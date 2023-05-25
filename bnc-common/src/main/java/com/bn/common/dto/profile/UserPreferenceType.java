package com.bn.common.dto.profile;

public enum UserPreferenceType {
    AUTO_ARCHIVE("EDS");
    private String database;

    UserPreferenceType(String database) {
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }

    public boolean isEDS() {
        return "EDS".equalsIgnoreCase(database);
    }
}
