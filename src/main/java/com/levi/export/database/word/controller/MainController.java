package com.levi.export.database.word.controller;

import com.levi.export.database.word.domain.DbConfig;
import com.levi.export.database.word.emums.DbType;
import com.levi.export.database.word.manager.DB2WordManager;
import com.levi.export.database.word.service.DB2WordService;
import com.levi.export.database.word.util.CommonUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static final String SUCCESS_CONNECTION = "connected to database success";
    private static final String FAIL_CONNECTION = "connecting to database failed";

    @FXML
    private Button testCon;

    @FXML
    private Button exportWord;

    @FXML
    private ChoiceBox<String> dbType;

    @FXML
    private TextField username;

    @FXML
    private TextField port;

    @FXML
    TextField host;

    @FXML
    private PasswordField password;

    @FXML
    private Label dirPath;

    @FXML
    private ChoiceBox<String> dbName;

    @FXML
    private TextField sshHost;
    @FXML
    private TextField sshPort;
    @FXML
    private TextField sshUsername;
    @FXML
    private TextField sshPassword;
    @FXML
    private RadioButton sshOff;
    @FXML
    private RadioButton sshOn;


    /**
     * 初始化数据
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbType.setItems(FXCollections.observableArrayList(DbType.getListDesc()));
        dbType.getSelectionModel().select(0);
    }

    /**
     * 选择数据库类型
     * 关联xml中点击事件
     *
     * @param event
     */
    public void dbTouch(MouseEvent event) {
        Connection dbConnection = getDbConnection();
        if (dbConnection == null) {
            showAlerts(FAIL_CONNECTION);
            return;
        }
        List<String> dbNameList = getDB2WordService().getDbNameList(dbConnection);
        dbName.setItems(FXCollections.observableArrayList(dbNameList));
        dbName.hide();
        dbName.show();
    }

    /**
     * 选择word存储路径
     * 关联xml中点击事件
     *
     * @param event
     */
    public void selectDirPath(ActionEvent event) {
        Stage mainStage = null;
        DirectoryChooser directory = new DirectoryChooser();
        directory.setTitle("选择路径");
        File file = directory.showDialog(mainStage);
        if (file != null) {
            String path = file.getPath();
            dirPath.setText(path);
        }
    }

    /**
     * 导出数据库为word
     * 关联xml中点击事件
     *
     * @param event
     */
    public void exportDoc(ActionEvent event) {
        if ("未选择".equals(dirPath.getText())) {
            showAlerts("请选择文件路径");
            return;
        }
        Connection dbConnection = getDbConnection();
        if (dbConnection == null) {
            showAlerts(FAIL_CONNECTION);
            return;
        }
        //导出word
        getDB2WordService().exportDatabase2Word(getDbConfig());
    }


    /**
     * 测试链接数据库
     * 关联xml中点击事件
     *
     * @param event
     */
    public void testCon(ActionEvent event) {
        Connection connection = getDbConnection();
        if (connection == null) {
            showAlerts(FAIL_CONNECTION);
            return;
        }
        showAlerts(SUCCESS_CONNECTION);
    }

    /**
     * 得到db的链接
     *
     * @return
     */
    private Connection getDbConnection() {
        DB2WordService db2WordService = getDB2WordService();
        if (db2WordService == null) {
            return null;
        }
        Connection connection = db2WordService.getConnection(getDbConfig());
        return connection;
    }

    /**
     * 获取DB2Word
     *
     * @return
     */
    private DB2WordService getDB2WordService() {
        DB2WordService db2WordService = DB2WordManager.getDB2WordService(dbType.getValue());
        return db2WordService;
    }

    /**
     * 获取配置信息
     *
     * @return
     */
    private DbConfig getDbConfig() {
        DbConfig dbConfig = new DbConfig();
        dbConfig.setHost(host.getText());
        dbConfig.setPort(CommonUtil.getIntegerByStr(port.getText()));
        dbConfig.setUsername(username.getText());
        dbConfig.setPassword(password.getText());
        dbConfig.setSshHost(sshHost.getText());
        dbConfig.setSshPort(CommonUtil.getIntegerByStr(sshPort.getText()));
        dbConfig.setSshUsername(sshUsername.getText());
        dbConfig.setSshPassword(sshPassword.getText());
        dbConfig.setSshEnable(sshOn.isSelected());
        dbConfig.setDbName(dbName.getValue());
        dbConfig.setWordSavePath(dirPath.getText());
        return dbConfig;
    }

    /**
     * 显示提示窗口
     *
     * @param content
     */
    private void showAlerts(String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Dialog");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
