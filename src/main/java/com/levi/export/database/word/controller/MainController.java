package com.levi.export.database.word.controller;

import com.levi.export.database.word.domain.DbConfig;
import com.levi.export.database.word.emums.DbType;
import com.levi.export.database.word.manager.DB2WordManager;
import com.levi.export.database.word.service.DB2WordService;
import com.levi.export.database.word.util.CommonUtil;
import com.levi.export.database.word.util.JsonUtil;
import com.levi.export.database.word.util.ThreadPoolsUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    private static final String FIRST_CONNECTION = "请先测试连接数据库";
    private static final String SAVE_CONFIG_PATH = "\\export-database-word\\config.txt";

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
    @FXML
    private Pane loadingParent;
    @FXML
    private ProgressIndicator loadingProgress;

    /**
     * Connection 链接只需要一次
     * 多次链接 ssh会出错
     */
    private Connection mConnection;


    /**
     * 初始化数据
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Background background = new Background(new BackgroundFill(Color.color(1, 1, 1), null, null));
        loadingParent.setBackground(background);
        loadingParent.setVisible(false);
        dbType.setItems(FXCollections.observableArrayList(DbType.getListDesc()));
        dbType.getSelectionModel().select(0);
        DbConfig dbConfig = getDbConfigBySave();
        if (dbConfig == null) {
            return;
        }
        if (!CommonUtil.isEmpty(dbConfig.getWordSavePath())) {
            dirPath.setText(dbConfig.getWordSavePath());
        }
        if (!CommonUtil.isEmpty(dbConfig.getHost())) {
            host.setText(dbConfig.getHost());
        }
        if (dbConfig.getPort() != null) {
            port.setText(dbConfig.getPort() + "");
        }
        if (!CommonUtil.isEmpty(dbConfig.getUsername())) {
            username.setText(dbConfig.getUsername());
        }
        if (!CommonUtil.isEmpty(dbConfig.getPassword())) {
            password.setText(dbConfig.getPassword());
        }
        if (dbConfig.getSshEnable()) {
            sshOn.setSelected(true);
        }
        if (!CommonUtil.isEmpty(dbConfig.getSshHost())) {
            sshHost.setText(dbConfig.getSshHost());
        }
        if (dbConfig.getSshPort() != null) {
            sshPort.setText(dbConfig.getSshPort() + "");
        }
        if (!CommonUtil.isEmpty(dbConfig.getSshUsername())) {
            sshUsername.setText(dbConfig.getSshUsername());
        }
        if (!CommonUtil.isEmpty(dbConfig.getSshPassword())) {
            sshPassword.setText(dbConfig.getSshPassword());
        }
    }

    /**
     * 选择数据库类型
     * 关联xml中点击事件
     *
     * @param event
     */
    public void dbTouch(MouseEvent event) {
        if (mConnection == null) {
            showAlerts(FIRST_CONNECTION);
            return;
        }
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
        if (CommonUtil.isEmpty(dbName.getValue())) {
            showAlerts("请选择数据库");
            return;
        }
        if (mConnection == null) {
            showAlerts(FIRST_CONNECTION);
            return;
        }
        loadingParent.setVisible(true);
        //存储记录
        saveDbConfig(getDbConfig());
        ThreadPoolsUtil.exec(new Runnable() {
            @Override
            public void run() {
                //导出word
                String res=getDB2WordService().exportDatabase2Word(getDbConnection(), getDbConfig());
                //切回主线程
                Platform.runLater(() -> {
                    loadingParent.setVisible(false);
                    showAlerts(res==null?"导出完成":res);
                });

            }
        });

    }


    /**
     * 测试链接数据库
     * 关联xml中点击事件
     *
     * @param event
     */
    public void testCon(ActionEvent event) {
        loadingParent.setVisible(true);
        ThreadPoolsUtil.exec(new Runnable() {
            @Override
            public void run() {
                saveDbConfig(getDbConfig());
                Connection connection = getDbConnection(true);
                //切回主线程
                Platform.runLater(() -> {
                    loadingParent.setVisible(false);
                    if (connection == null) {
                        showAlerts(FAIL_CONNECTION);
                        return;
                    }
                    showAlerts(SUCCESS_CONNECTION);
                });

            }
        });

    }

    /**
     * 得到db的链接
     * 强制刷新链接
     *
     * @return
     */
    private Connection getDbConnection(boolean isRefrsh) {
        if (isRefrsh) {
            mConnection = null;
        }
        return getDbConnection();
    }

    /**
     * 得到db的链接
     *
     * @return
     */
    private Connection getDbConnection() {
        if (mConnection == null) {
            DB2WordService db2WordService = getDB2WordService();
            if (db2WordService == null) {
                return null;
            }
            Connection connection = db2WordService.getConnection(getDbConfig());
            mConnection = connection;
        }

        return mConnection;
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

    /**
     * 存储dbConfig
     * 方便下次使用的时间记录
     *
     * @param dbConfig
     */
    private void saveDbConfig(DbConfig dbConfig) {
        if (dbConfig == null) {
            return;
        }
        String config = JsonUtil.getJsonString(dbConfig);
        String path = CommonUtil.getUserHomeDir() + SAVE_CONFIG_PATH;
        CommonUtil.writeStringToFile(path, config);
    }

    /**
     * 得到存储dbConfig
     * 方便下次使用的时间记录
     */
    private DbConfig getDbConfigBySave() {
        String path = CommonUtil.getUserHomeDir() + SAVE_CONFIG_PATH;
        String config = CommonUtil.readFileToString(path);
        DbConfig dbConfig = JsonUtil.getObject(config, DbConfig.class);
        return dbConfig;
    }
}
