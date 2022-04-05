package GUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import converter.Score;
import converter.measure.TabMeasure;
import custom_exceptions.TXMLException;
import draw.score.DrawDrumNotes;
import draw.score.DrawGuitarNotes;
import draw.score.DrumNotesList;
import draw.score.DrumStaff;
import draw.score.GuitarNotesList;
import draw.score.GuitarStaff;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;
import models.Part;
import models.measure.note.Note;
import models.measure.note.Notehead;
import models.part_list.PartList;
import models.part_list.ScorePart;

public class PrevSheetController extends Application {
	private MainViewController mvc;
	@FXML
	public CodeArea mxlTextPre;
	@FXML
	// public VBox myVBox;

	public String clef;

	private String instrumentName;
	DrumNotesList drumNotesList = new DrumNotesList();
	GuitarNotesList guitarNotesList = new GuitarNotesList();
	DrawDrumNotes drawDrumNotes = new DrawDrumNotes();
	DrawGuitarNotes drawGuitarNotes = new DrawGuitarNotes();
	@FXML
	public void initialize() {
		// mxlTextPre.setParagraphGraphicFactory(LineNumberFactory.get(mxlTextPre));
		Button button1 = new Button("button1");

		/*
		 * Dot01 test = new Dot01(); test.view();
		 */

	}

	@Override

	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		ScrollPane scrollPane = new ScrollPane();
		Score score = mvc.converter.getScore();
		String instrumentName = getInstrumentName(score);

		Text instrumentNameTitle = new Text(450, 100, instrumentName);
		instrumentNameTitle.setFont(Font.font("Verdana", 50));
		Group box = new Group();
		//drawDrumNote.getGroup
		Group drawing=new Group();
		box.getChildren().add(instrumentNameTitle);

		// Draw Staff
		if (instrumentName.contains("Drum")) {
			DrumStaff drumStaff = new DrumStaff();
			drumStaff.draw(box, 0);
			int[][] notesPositionList = drumNotesList.notesList(mvc);
			//hashmap of drum notes map
			HashMap <Integer, List<Note>>drumNotesMap = drumNotesList.getDrumNotesMap();
			//draw with 2D array
			//drawDrumNotes.draw(mvc,box, notesPositionList);
			drawDrumNotes.drawEverything(drumNotesMap);
			drawing=drawDrumNotes.getDrawing();
			
		} else if (instrumentName.contains("Guitar")) {
			GuitarStaff guitarStaff = new GuitarStaff();
			guitarStaff.draw(box, 0);
			int[][] notesPositionList = guitarNotesList.notesList(mvc);
			drawGuitarNotes.draw(mvc,box, notesPositionList);
		}


		scrollPane.setContent(drawing);
		scrollPane.setPannable(true);
		Scene scene = new Scene(scrollPane, 800, 600, Color.AZURE);

		primaryStage.setTitle("music sheet");

		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public void setMainViewController(MainViewController mvcInput) {

		mvc = mvcInput;

	}



	public String getInstrumentName(Score score) throws TXMLException {

		String instrumentName = score.getModel().getPartList().getScoreParts().get(0).getPartName();
		return instrumentName;
	}

	
}
