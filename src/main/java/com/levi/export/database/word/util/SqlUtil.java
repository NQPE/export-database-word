package com.levi.export.database.word.util;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.sql.*;

public class SqlUtil {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * mysql链接
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @return
     */
    public static Connection getConnectionByMysql(String host, Integer port, String username, String password) {
        String url = String.format("jdbc:mysql://%s:%d", host, port);
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ssh mysql链接
     *
     * @param host
     * @param port
     * @param username
     * @param password
     * @param sshHost
     * @param sshPort
     * @param sshUsername
     * @param sshPassword
     * @return
     */
    public static Connection getConnectionToSSHByMysql(String host, Integer port, String username, String password,
                                                       String sshHost, Integer sshPort, String sshUsername, String sshPassword) {
        Integer assignedPort = getSshAssignedPort(host, port, sshHost, sshPort, sshUsername, sshPassword);
        System.out.println("assigned_port:" + assignedPort);
        if (assignedPort == null || assignedPort == 0) {
            return null;
        }
//        host = "localhost:" + assignedPort + "/" + host;
        host = "localhost";
        port = assignedPort;
        return getConnectionByMysql(host, port, username, password);
    }

    /**
     * 获取ssh端口
     *
     * @param sshHost
     * @param sshPort
     * @param sshUsername
     * @param sshPassword
     * @return
     */
    public static Integer getSshAssignedPort(String host, Integer port, String sshHost, Integer sshPort, String sshUsername, String sshPassword) {
        try {
            JSch jsch = new JSch();
            Session session = null;
            session = jsch.getSession(sshUsername, sshHost, sshPort);
            session.setPassword(sshPassword);
            /*
             *默认情况下，StrictHostKeyChecking=ask。简单所下它的三种配置值：
             *1.StrictHostKeyChecking=no #最不安全的级别，当然也没有那么多烦人的提示了，相对安
             *全的内网测试时*建议使用。如果连接server的key在本地不存在，那么就自动添加到文件中
             *（默认是known_hosts），并且给*出一个警告。
             *2.StrictHostKeyChecking=ask #默认的级别，就是出现刚才的提示了。如果连接和key不匹
             *配，给出提示，并拒绝登录。
             *3.StrictHostKeyChecking=yes #最安全的级别，如果连接与key不匹配，就拒绝连接，不会
             *提示详细信
             *息。
             */

            session.setConfig("StrictHostKeyChecking", "no");
            //连接
            session.connect();
            //这里打印SSH服务器版本信息
            System.out.println("ssh服务打印：" + session.getServerVersion());

            //  正向代理 端口映射 转发
//            int lport = 1117;
            int lport = CommonUtil.getRandomInt(1000,1300);
            int assignedPort = session.setPortForwardingL(lport, host, port);
            return assignedPort;
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getResultSet(Connection conn, String sql) {
        try {
            Statement stat = conn.createStatement();
            return stat.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断查询结果集中是否存在某列
     *
     * @param rs         查询结果集
     * @param columnName 列名
     * @return true 存在; false 不存咋
     */
    public static boolean isExistColumn(ResultSet rs, String columnName) {
        try {
            if (rs.findColumn(columnName) > 0) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }
}
