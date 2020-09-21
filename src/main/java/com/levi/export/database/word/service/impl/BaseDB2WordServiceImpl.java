package com.levi.export.database.word.service.impl;

import com.levi.export.database.word.domain.DbConfig;
import com.levi.export.database.word.service.DB2WordService;
import com.levi.export.database.word.util.CommonUtil;

public abstract class BaseDB2WordServiceImpl implements DB2WordService {
    /**
     * 检查DbConnection参数
     *
     * @param dbConfig
     * @return true 通过验证 false未通过验证
     */
    public boolean checkDbConnectionParam(DbConfig dbConfig) {
        if (dbConfig == null
                || CommonUtil.isEmpty(dbConfig.getHost())
                || CommonUtil.isEmpty(dbConfig.getUsername())
                || CommonUtil.isEmpty(dbConfig.getPassword())
                || dbConfig.getPort() == null) {
            return false;
        }
        //ssh链接验证
        if (dbConfig.getSshEnable() && (
                CommonUtil.isEmpty(dbConfig.getSshHost())
                        || CommonUtil.isEmpty(dbConfig.getSshUsername())
                        || CommonUtil.isEmpty(dbConfig.getSshPassword())
                        || dbConfig.getSshPort() == null
        )) {
            return false;
        }
        return true;
    }
}
