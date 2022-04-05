package draw.score;

import java.util.HashMap;
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
import models.measure.note.Note;

public class DrawDrumNotes {
	private Group group = new Group();
	public DrawDrumNotes() {
		super();
		// TODO Auto-generated constructor stub

	}
	
	//draw based on 2D array, to be deleted
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
	
	public void drawNote(int mapKey, int noteHeight, int rowIndex) {
		Line noteLine = new Line(135 + 25.0 * mapKey, 200 - 5.0 * noteHeight + 90 * rowIndex,
				135 + 25.0 * mapKey, 250 - 5.0 * noteHeight + 90 * rowIndex);
		Circle note = new Circle(130 + 25.0 * mapKey,
				250.0 - 5.0 * noteHeight + 90 * rowIndex, 5);
		note.setFill(Color.MIDNIGHTBLUE);
		group.getChildren().add(noteLine);
		group.getChildren().add(note);
		
	}
	
	
	public void drawNoteHead(int mapKey, int noteHeight, int rowIndex) {
		Line line1= new Line(130 + 25.0 * mapKey-5,
				250.0 - 5.0 * noteHeight + 90 * rowIndex-2.5,
				130 + 25.0 * mapKey+5,
				250.0 - 5.0 * noteHeight + 90 * rowIndex+2.5);
		Line line2 = new Line(130 + 25.0 * mapKey-5,
				250.0 - 5.0 * noteHeight + 90 * rowIndex+2.5,
				130 + 25.0 * mapKey+5,
				250.0 - 5.0 * noteHeight + 90 * rowIndex-2.5);
		group.getChildren().add(line1);
		group.getChildren().add(line2);
		
	}
	
	public void drawEverything(HashMap <Integer, List<Note>>drumNotesMap ) {
		//Loop through drum notes hashmap
		for(HashMap.Entry<Integer,List<Note>> entry: drumNotesMap.entrySet()) {
			int keyValue=entry.getKey();
			System.out.printf("hashmap key: %d",keyValue);
			List<Note> notesIncludeChord = entry.getValue();
			for (Note note: notesIncludeChord) {
				/*
				 * if(note.getNotehead()==null) {
				 * System.out.printf("note head is null %d",keyValue); }else {
				 * System.out.printf("note head is not null %d",keyValue); }
				 */
	
				
				  if(note.getNotehead()==null) { 
					  drawNote(keyValue,1,1); } 
				  else {
					  drawNoteHead(keyValue,3,1); }
				 
			}
			
		}
	}
	public Group getDrawing() {
		return this.group;
	}
	
}
