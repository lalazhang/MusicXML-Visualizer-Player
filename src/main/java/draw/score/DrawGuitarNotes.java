package draw.score;

import java.util.List;

import GUI.MainViewController;
import converter.Score;
import custom_exceptions.TXMLException;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Part;

public class DrawGuitarNotes {

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
		for (int i = 0; i < notesPositionList.length; i++) {
			int stringIndex = notesPositionList[i][1];
			String fret = Integer.toString(notesPositionList[i][0]);
			Text fretText = new Text(130 + 25 * i, 200 + stringIndex * 10, fret);
			fretText.setFill(Color.CRIMSON);
			box.getChildren().add(fretText);

		}

	

	}
	
}
