package GUI;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.stage.Window;

import utility.XmlPlayer;

public class PlayMusicController extends Application {
	/**
	 * MainViewController object to store parent mvc instance
	 */
	private MainViewController mvc;
	/**
	 * XmlPlayer object to use xmlplayer functionality 
	 */
	private XmlPlayer mp;
	/**
	 * String to store xml output as string from main view
	 */
	private String xmlstr;
	private static Window convertWindow = new Stage();

	@FXML
	Button playButton;
	@FXML
	Button pauseButton;
	@FXML
	Slider songSlider;
	@FXML
	Slider volSlider;
	@FXML
	Label volLabel;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * Creates mvc instance using main view mvc and takes the converted 
	 * xml string from main view to play score
	 * 
	 * @param mvcInput
	 * @param str
	 * @throws Exception
	 */
	public void setMainViewController(MainViewController mvcInput, String str) throws Exception {
		mvc = mvcInput;
		xmlstr = str;
		mp = new XmlPlayer(xmlstr);
	}

	
	@FXML
	public void initialize() throws Exception {
		Image vol1 = new Image(getClass().getClassLoader().getResource("image_assets/Low-Volume-icon.png").toString());
		ImageView vol1v = new ImageView(vol1);
		volLabel.setGraphic(vol1v);

		volSlider.valueProperty().addListener((observable, oldValue, newValue) -> {

		});

	}

	@FXML
	private void playMusicHandle() throws Exception {

		mvc.converter.update();
		mp.play();

	}

	@FXML
	private void pauseMusicHandle() {
		mp.pause();
	}

	@FXML
	private void exit() {
		mp.getManagedPlayer().finish();
		mvc.convertWindow.hide();
	}
}
