package GUI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import converter.InstrumentSetting;
import custom_exceptions.TXMLException;
import draw.score.DrumNotesList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.Settings;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
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


@Disabled

@ExtendWith(ApplicationExtension.class)
public class SheetMusicTest extends ApplicationTest{
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
    public void testNoInput(FxRobot robot) {
    	//Make sure the button is disabled on launch without any input
        robot.clickOn("#previewButton");
        FxAssert.verifyThat("#previewButton", NodeMatchers.isDisabled());
    }
    

    @Test
    public void testWithInvalidInput(FxRobot robot) {
    	//Make sure the button is disabled when there is invalid text present
        robot.clickOn("#mainText");
        robot.write("Invalid Text");
        robot.clickOn("#previewButton");
        FxAssert.verifyThat("#previewButton", NodeMatchers.isDisabled());
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
        robot.clickOn("#previewButton");
        FxAssert.verifyThat("#previewButton", NodeMatchers.isEnabled());
    }
    

    
    @Test
    public void testNoteToNumber() {
    	//Tests sample input to make sure correct notes are being determined
    	PrevSheetController controller = new PrevSheetController();

    	DrumNotesList drumNotesList =new DrumNotesList();
    	String note = "C4";
    	int noteNum = drumNotesList.noteToNumber(note);
    	assertEquals(-2,noteNum);
    	note = "D5";
    	noteNum = drumNotesList.noteToNumber(note);

    	assertEquals(6,noteNum);
    	
    	
    }
    
}
