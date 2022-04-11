package draw.score;

import java.util.List;

import GUI.MainViewController;
import converter.Score;
import custom_exceptions.TXMLException;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import models.Part;
import utility.Settings;

public class DrawGuitarNotes {

	private FlowPane flow;
	
	public DrawGuitarNotes() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public void draw(MainViewController mvc,Group box, int[][] notesPositionList) throws TXMLException {

		// get clef	
		Score score1 = mvc.converter.getScore();
		List<Part> partList = score1.getModel().getParts();	
		//System.out.println("score counts: "+score1.getModel().getScoreCount());		
		String clef = partList.get(0).getMeasures().get(0).getAttributes().clef.sign;


		Text t = new Text(105, 215, "T");
		t.setFont(Font.font("Verdana", 20));
		t.setFill(Color.CRIMSON);
		Text a = new Text(105, 230, "A");
		a.setFont(Font.font("Verdana", 20));
		a.setFill(Color.CRIMSON);
		Text b = new Text(105, 245, "B");
		b.setFont(Font.font("Verdana", 20));
		b.setFill(Color.CRIMSON);
		box.getChildren().add(t);
		box.getChildren().add(a);
		box.getChildren().add(b);
		int nume = Settings.getInstance().tsNum;
		int denom = Settings.getInstance().tsDen;
		Text timeSigNume = new Text(125, 225, ""+nume);
		timeSigNume.setFont(Font.font("Verdana", 30));
		Text timeSigDenom = new Text(125, 245, ""+denom);
		timeSigDenom.setFont(Font.font("Verdana", 30));
		box.getChildren().add(timeSigNume);
		box.getChildren().add(timeSigDenom);
		for (int i = 0; i < notesPositionList.length; i++) {
			int stringIndex = notesPositionList[i][1];
			String fret = Integer.toString(notesPositionList[i][0]);
			Text fretText = new Text(155 + 25 * i, 195 + stringIndex * 10, fret);
//			TextFlow newText = new TextFlow(fretText);
//			newText.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, null, null)));
//			newText.setStyle("-fx-background-color: red");
//			Label lbl = new Label(""+stringIndex);
			fretText.setFill(Color.CRIMSON);
//			flow = new FlowPane(fretText);
//			flow.setStyle("-fx-background-color: white");
			box.getChildren().add(fretText);

		}

	

	}
	
}
