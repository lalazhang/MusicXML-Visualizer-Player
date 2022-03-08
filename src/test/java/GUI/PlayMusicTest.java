package GUI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import converter.InstrumentSetting;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.robot.Robot;
import utility.Settings;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.application.Platform;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobot.*;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import converter.Converter;

@ExtendWith(ApplicationExtension.class)
public class PlayMusicTest extends ApplicationTest {

	public Converter converter;
	private Robot bot;
		@Override
	    public void start(Stage stage) throws IOException {
	        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI/mainView.fxml"));
	        Scene scene = new Scene(root);
	
	
	        scene.getStylesheets().add(getClass().getClassLoader().getResource("GUI/styles.css").toExternalForm());
	
	        stage.setTitle("TAB 2 XML");
	        stage.setMinWidth(700);
	        stage.setMinHeight(500);
	        stage.setScene(scene);
	        stage.show();
	    }


	@Test
	void testAssertions() {
		//A test case to make sure assertions are working as intended
		assertTrue(true);
		assertFalse(false);
	}
	

    @Test
    public void testNoInput(FxRobot robot) {
    	//Make sure the button is disabled on launch without any input
        robot.clickOn("#playButton");
        FxAssert.verifyThat("#playButton", NodeMatchers.isDisabled());
    }


    @Test
    public void testWithInvalidInput(FxRobot robot) {
    	//Make sure the button is disabled when there is invalid text present
        robot.clickOn("#mainText");
        robot.write("Invalid Text");
        robot.clickOn("#playButton");
        FxAssert.verifyThat("#playButton", NodeMatchers.isDisabled());
    }
    

    @Test
    public void testWithValidInput(FxRobot robot) {
    	//Make sure the button is enabled when there is valid text present
        robot.clickOn("#mainText");
        robot.write("CC|x---------------|--------x-------|\r\n"
        		+ "HH|--x-x-x-x-x-x-x-|----------------|\r\n"
        		+ "SD|----o-------o---|oooo------------|\r\n"
        		+ "HT|----------------|----oo----------|\r\n"
        		+ "MT|----------------|------oo--------|\r\n"
        		+ "BD|o-------o-------|o-------o-------|", 0);
        robot.clickOn("#playButton");
        FxAssert.verifyThat("#playButton", NodeMatchers.isEnabled());
        FxAssert.verifyThat("#playButton", NodeMatchers.isEnabled());
        
    }
    

    @Test
    public void testPlayPause(FxRobot robot) {
    	//Make sure the button is enabled when there is valid text present
        robot.clickOn("#mainText");
        robot.write("CC|x---------------|--------x-------|\r\n"
        		+ "HH|--x-x-x-x-x-x-x-|----------------|\r\n"
        		+ "SD|----o-------o---|oooo------------|\r\n"
        		+ "HT|----------------|----oo----------|\r\n"
        		+ "MT|----------------|------oo--------|\r\n"
        		+ "BD|o-------o-------|o-------o-------|", 0);
        robot.clickOn("#playButton");
        //Verify that the play and pause buttons are clickable
        FxAssert.verifyThat("#pauseButton", NodeMatchers.isEnabled());
        FxAssert.verifyThat("#playMusic", NodeMatchers.isEnabled());
        
        //Check if the current time on the player is 00:00 since the window just launched
        FxAssert.verifyThat("#labelTimeCur", (Label current) -> {
        	String lbl = current.getText();
        	return lbl.equals("00:00");
        });
        
        //Have the player play for a second
        robot.clickOn("#playMusic");
        robot.sleep(1000);
        robot.clickOn("#pauseButton");
        
        //Check if the current time on the player is not 00:00 since the player has played for a minute
        FxAssert.verifyThat("#labelTimeCur", (Label current) -> {
        	String lbl = current.getText();
        	return !lbl.equals("00:00");
        });
        
    }
	
	@Test
	public void testBPM(FxRobot robot) throws Exception {
		//Test case to make sure the BPM slider actually changes the BPM
        robot.clickOn("#mainText");
        robot.write("|-----------0-----|-0---------------|\r\n"
        		+ "|---------0---0---|-0---------------|\r\n"
        		+ "|-------1-------1-|-1---------------|\r\n"
        		+ "|-----2-----------|-2---------------|\r\n"
        		+ "|---2-------------|-2---------------|\r\n"
        		+ "|-0---------------|-0---------------|", 0);
        robot.clickOn("#playButton");
        
        //Confirm that the default slider value of 120.0 is correct
        FxAssert.verifyThat("#tempSlider", (Slider current) -> {
        	double lbl = current.getValue();
        	return lbl==120.0;
        });
        
        //Have the player play for a second
        robot.clickOn("#playMusic");
        robot.sleep(1000);
        robot.clickOn("#pauseButton");
        
        //Confirm that the end time is 00:16 which is the end time at 120bpm(default) for the given tablature
        FxAssert.verifyThat("#labelTimeEnd", (Label current) -> {
        	String lbl = current.getText();
        	return lbl.equals("00:16");
        });
        
        //The following code is to manipulate the mouse to move the BPM slider to something different from the default
        robot.clickOn("#tempSlider");
        
        Platform.runLater(() -> {
        	bot = new Robot();
        	double x = bot.getMouseX();
            double y = bot.getMouseY();
            
            bot.mouseMove(x, y-17.5);
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            y = bot.getMouseY();
            
            bot.mousePress(MouseButton.PRIMARY);
            bot.mouseMove(x+35,y);            
            bot.mouseRelease(MouseButton.PRIMARY);
        });
        
        //Have the player play for a second
        robot.clickOn("#playMusic");
        robot.sleep(1000);
        robot.clickOn("#pauseButton");
        
        //Confirm that the end time is not 00:16 which means the bpm has changed
        FxAssert.verifyThat("#labelTimeEnd", (Label current) -> {
        	String lbl = current.getText();
        	return !lbl.equals("00:16");
        });       
        //Essentially, since the end time has changed, it means that the bpm must have changed.
    }
    

}
