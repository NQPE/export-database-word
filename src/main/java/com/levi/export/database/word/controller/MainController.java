package com.levi.export.database.word.controller;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.levi.export.database.word.App;
import com.levi.export.database.word.OracleUtils;
import com.levi.export.database.word.SqlUtils;
import com.levi.export.database.word.util.CommonUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

public class MainController implements Initializable {

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
    private Button choisePath;

    @FXML
    private ImageView img;

    @FXML
    private ChoiceBox<String> dbName;

    @FXML
    private Label dbLabel;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbType.setItems(FXCollections.observableArrayList("mysql", "oracle"));
        dbType.getSelectionModel().select(0);
//		Image image = new Image("/head_2.jpg");
//		img.setImage(image);
        dbType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if ("mysql".equals(newValue)) {
                    dbName.setVisible(true);
                    dbLabel.setVisible(true);
                } else if ("oracle".equals(newValue)) {
                    dbName.setVisible(false);
                    dbLabel.setVisible(false);
                }
            }
        });
    }

    public void dbTouch(MouseEvent event) {
        String type = dbType.getValue();
        String user = username.getText();
        String pwd = password.getText();
        String value = dbName.getValue();
        String p = port.getText();
        String h = host.getText();
        if (value != null || "".equals(value)) {
            return;
        }
        if ("mysql".equals(type)) {
            Connection con = SqlUtils.getConnnection(String.format("jdbc:mysql://%s:%s", h, p), user, pwd);
            if (con == null) {
                Alerts(false, "connecting to database failed");
                return;
            }
            ResultSet set = SqlUtils.getResultSet(con, "show databases");
            try {
                List<String> list = new ArrayList<String>();
                while (set.next()) {
                    list.add(set.getString(1));
                }
                System.out.println(list.toString());
                dbName.setItems(FXCollections.observableArrayList(list));
                dbName.hide();
                dbName.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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

    public void typeTouch(MouseEvent event) {
        String value = dbType.getValue();
        System.out.println(value);
        if (value.equals("mysql")) {
            dbName.setVisible(true);
        }
        if (value.equals("oracle")) {
            dbName.setVisible(false);
        }
    }

    public void testCon(ActionEvent event) {
        isCon();
    }

    public boolean isCon() {
//        String type = dbType.getValue();
//        String user = username.getText();
//        String pwd = password.getText();
//        String p = port.getText();
//        String h = host.getText();
//        if ("mysql".equals(type)) {
//            Connection con = SqlUtils.getConnnection(String.format("jdbc:mysql://%s:%s", h, p), user, pwd);
//            if (con != null) {
//                Alerts(true, "connected to database success");
//                return true;
//            } else {
//                Alerts(false, "connecting to database failed");
//                return false;
//            }
//        }
//        if ("oracle".equals(type)) {
//            Connection con = OracleUtils.getConnnection(String.format("jdbc:oracle:thin:@%s:%s:ORCL", h, p), user, pwd);
//            if (con != null) {
//                Alerts(true, "connected to database success");
//                return true;
//            } else {
//                Alerts(false, "connecting to database failed");
//                return false;
//            }
//        }
        Connection connection = getConnection();
        if (connection!=null){
            Alerts(true, "connected to database success");
            return true;
        }
        return false;
    }

    public void exportDoc(ActionEvent event) {
        String type = dbType.getValue();
        String user = username.getText();
        String pwd = password.getText();
        String dir = dirPath.getText();
        String value = dbName.getValue();
        String p = port.getText();
        String h = host.getText();
        if (dir.equals("未选择")) {
            Alerts(false, "请选择文件路径");
            return;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("-t", type);
        map.put("-u", user);
        map.put("-p", pwd);
        map.put("-d", dir);
        map.put("-n", value);
        map.put("p", p);
        map.put("h", h);
        if ("mysql".equals(type)) {
            boolean b = check(map);
            if (!b) {
                return;
            }
            try {
                App.MySQL(map);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("oracle".equals(type)) {
            try {
                App.Oracle(map);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean check(Map<String, String> map) {
        if (!map.containsKey("-n") || map.get("-n") == null || map.get("-n").equals("")) {
            Alerts(false, "请输入数据库名称！");
            return false;
        }
        if (!map.containsKey("-u") || map.get("-u") == null || map.get("-u").equals("")) {
            Alerts(false, "请输入数据库用户名！");
            return false;
        }
        if (!map.containsKey("-p") || map.get("-p") == null || map.get("-p").equals("")) {
            Alerts(false, "请输入数据库密码！");
            return false;
        }
        if (!map.containsKey("-d") || map.get("-d") == null || map.get("-d").equals("")) {
            Alerts(false, "请输入保存文件的目录！");
            return false;
        }
        return true;
    }


    public static void Alerts(boolean is, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        if (is) {
            alert.setTitle("Dialog");
            alert.setHeaderText(null);
            alert.setContentText(content);
        } else {
            alert.setTitle("Dialog");
            alert.setHeaderText(null);
            alert.setContentText(content);
        }
        alert.showAndWait();
    }

    Connection getConnection() {
        boolean sshSelected = sshOn.isSelected();
        if (sshSelected){
            return getSshConnect();
        }
        Connection con = null;
        String type = dbType.getValue();
        String user = username.getText();
        String pwd = password.getText();
        String p = port.getText();
        String h = host.getText();
        if ("mysql".equals(type)) {
            con = SqlUtils.getConnnection(String.format("jdbc:mysql://%s:%s", h, p), user, pwd);
        }
        if ("oracle".equals(type)) {
            con = OracleUtils.getConnnection(String.format("jdbc:oracle:thin:@%s:%s:ORCL", h, p), user, pwd);
        }

        if (con == null) {
            Alerts(false, "connecting to database failed");
        }
        return con;
    }

    Connection getSshConnect(){
        Connection con = null;
        try {
            String type = dbType.getValue();
            String user = username.getText();
            String pwd = password.getText();
            Integer p = CommonUtil.getIntegerByStr(port.getText());
            String h = host.getText();

			String sHost =sshHost.getText();
			Integer sPort = CommonUtil.getIntegerByStr(sshPort.getText());
			String sUsername =sshUsername.getText();
			String sPassword =sshPassword.getText();
			if (sPort==null||CommonUtil.isEmpty(sHost)
                    || CommonUtil.isEmpty(sUsername)
                    ||CommonUtil.isEmpty(sPassword)){
                Alerts(false, "SSH信息填写错误！");
			    return null;
            }
			if (p==null||CommonUtil.isEmpty(h)){
                Alerts(false, "数据库信息填写错误！");
			    return null;
            }

			JSch jsch = new JSch();
			Session session = jsch.getSession(sUsername,sHost,sPort );
			session.setPassword(sPassword);
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
			session.connect();//连接
            //这里打印SSH服务器版本信息
			System.out.println("ssh服务打印："+session.getServerVersion());

			//  正向代理
			int assigned_port = session.setPortForwardingL(1117, h, p);//端口映射 转发

			System.out.println("assigned_port:" + assigned_port);

			//ssh -R 192.168.0.102:5555:192.168.0.101:3306 yunshouhu@192.168.0.102
			//session.setPortForwardingR("192.168.0.102",5555, "192.168.0.101", 3306);
			// System.out.println("localhost:  -> ");

            if (assigned_port == 0) {
                Alerts(false, "SSH Port forwarding failed !");
                System.out.println("Port forwarding failed !");
                return null;
            }

            h = "localhost:" + assigned_port + "/" + h;
            if ("mysql".equals(type)) {
                con = SqlUtils.getConnnection(String.format("jdbc:mysql://%s:%s", h, p), user, pwd);
            }
            if ("oracle".equals(type)) {
                con = OracleUtils.getConnnection(String.format("jdbc:oracle:thin:@%s:%s:ORCL", h, p), user, pwd);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return con;
	}
}
