package GUI;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//Copied ShowMXLController class as a placeholder for now so eventually we can display it
//Still need to figure out how to convert converter.getMusicXML() into actual XML rather than string
public class PreviewController extends Application {
	public File saveFile;
    private MainViewController mvc;
	public Highlighter highlighter;

	@FXML public CodeArea mxlText;
	@FXML TextField gotoMeasureField;
	@FXML Button goToline;

	public PreviewController() {}

	@FXML 
	public void initialize() {
		mxlText.setParagraphGraphicFactory(LineNumberFactory.get(mxlText));
	}

    public void setMainViewController(MainViewController mvcInput) {
    	mvc = mvcInput;
    }
    
    //This is the method we should be focusing on, also should create a preview.fxml doc later.
    public void update() {
		mxlText.replaceText(mvc.converter.getMusicXML());
		mxlText.moveTo(0);
		mxlText.requestFollowCaret();
        mxlText.requestFocus();
	}
    
	@Override
	public void start(Stage primaryStage) throws Exception {}
}
