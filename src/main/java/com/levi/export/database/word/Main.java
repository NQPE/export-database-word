package com.levi.export.database.word;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent main = FXMLLoader.load(getClass().getResource("/main.fxml"));
		primaryStage.setTitle("数据库导出WORD文档工具-Levi");
		primaryStage.setScene(new Scene(main));
		primaryStage.show();
	}
    
    public static void main(String[] args) {
        launch(args);
    }

}
