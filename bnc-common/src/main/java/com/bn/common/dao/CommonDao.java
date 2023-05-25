package com.bn.common.dao;

import com.bn.common.dto.common.ConfigInfo;

import java.util.List;

public interface CommonDao {

    List<ConfigInfo> getAllConfig();
}