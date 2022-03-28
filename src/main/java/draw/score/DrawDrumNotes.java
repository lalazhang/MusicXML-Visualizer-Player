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

public class DrawDrumNotes {

	public DrawDrumNotes() {
		super();
		// TODO Auto-generated constructor stub

	}
	
	public void draw(MainViewController mvc,Group box, int[][] notesPositionList) throws TXMLException {
		int noteHeightDrum = 0;
		// get clef	
		Score score1 = mvc.converter.getScore();
		List<Part> partList = score1.getModel().getParts();	
		//System.out.println("score counts: "+score1.getModel().getScoreCount());		
		String clef = partList.get(0).getMeasures().get(0).getAttributes().clef.sign;

		Line line1 = new Line(105, 220, 105, 240);
		Line line2 = new Line(110, 220, 110, 240);
		line1.setStrokeWidth(4);
		line2.setStrokeWidth(4);
		box.getChildren().add(line2);
		box.getChildren().add(line1);

		System.out.printf("drum note span size" + String.valueOf(notesPositionList.length));
		for (int i = 0; i < notesPositionList.length; i++) {
//			for(int ii=0;i<i%30;ii++) {

			int dividend = i, divisor = 30;
			int height = dividend / divisor;
			int horizontalSpan = i % 30;
			System.out.println("i/30 " + height);
			System.out.println("i%30 " + horizontalSpan);
			DrumStaff drumStaff = new DrumStaff();
			drumStaff.draw(box, height);
			for (int j = 0; j < notesPositionList[i].length; j++) {
				noteHeightDrum = notesPositionList[i][j];
				if (noteHeightDrum < 20) {
					Line noteLine = new Line(135 + 25.0 * horizontalSpan, 200 - 5.0 * noteHeightDrum + 90 * height,
							135 + 25.0 * horizontalSpan, 250 - 5.0 * noteHeightDrum + 90 * height);
					Circle note = new Circle(130 + 25.0 * horizontalSpan,
							250.0 - 5.0 * noteHeightDrum + 90 * height, 5);
					note.setFill(Color.MIDNIGHTBLUE);

					box.getChildren().add(noteLine);
					box.getChildren().add(note);
				}

			}
//			}

		}

	

	}
	
}
