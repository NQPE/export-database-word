package com.levi.export.database.word.domain;

import java.io.Serializable;

public class DbConfig implements Serializable {
    /**
     * 主机
     */
    private String host;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 是否使用ssh
     */
    private Boolean sshEnable;

    /**
     * ssh主机
     */
    private String sshHost;
    /**
     * ssh端口
     */
    private Integer sshPort;
    /**
     * ssh用户名
     */
    private String sshUsername;
    /**
     * ssh密码
     */
    private String sshPassword;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 文档存储路径
     */
    private String wordSavePath;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSshEnable() {
        return sshEnable;
    }

    public void setSshEnable(Boolean sshEnable) {
        this.sshEnable = sshEnable;
    }

    public String getSshHost() {
        return sshHost;
    }

    public void setSshHost(String sshHost) {
        this.sshHost = sshHost;
    }

    public Integer getSshPort() {
        return sshPort;
    }

    public void setSshPort(Integer sshPort) {
        this.sshPort = sshPort;
    }

    public String getSshUsername() {
        return sshUsername;
    }

    public void setSshUsername(String sshUsername) {
        this.sshUsername = sshUsername;
    }

    public String getSshPassword() {
        return sshPassword;
    }

    public void setSshPassword(String sshPassword) {
        this.sshPassword = sshPassword;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getWordSavePath() {
        return wordSavePath;
    }

    public void setWordSavePath(String wordSavePath) {
        this.wordSavePath = wordSavePath;
    }

}
