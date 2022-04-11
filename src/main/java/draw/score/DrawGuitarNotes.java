package draw.score;

import java.util.HashMap;
import java.util.List;

import GUI.MainViewController;
import converter.Score;
import converter.measure.TabMeasure;
import custom_exceptions.TXMLException;
import javafx.scene.Group;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Part;
import models.measure.note.Note;
import utility.Settings;

public class DrawGuitarNotes {
	private Group group = new Group();
	private int temp = 0;

	public DrawGuitarNotes() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Boolean hasSlur(Note note) {
		if (note.getNotations().getSlurs() == null) {
			return false;
		} else {

			return true;
		}
	}
	private void drawGrace(int horizontalPosition, int stringPosition, int rowIndex, int fret) {
		// TODO Auto-generated method stub
		// distance between 2 notes here is 37
				Text fretText = new Text(152 + 33.0 * horizontalPosition, (326 + stringPosition * 13.0) + 150 * rowIndex,
						Integer.toString(fret));
				fretText.toFront();
				fretText.setFont(Font.font(9));
//				For fret background
				Blend blend = new Blend();
				Blend blend2 = new Blend();

				ColorInput topInput = new ColorInput((154 + 33.0 * horizontalPosition) - 3,
						((326 + stringPosition * 13.0) + 150 * rowIndex) - 10, 10, 12, Color.WHITE);
				ColorInput topInput2 = new ColorInput((155 + 33.0 * horizontalPosition) - 3,
						((326 + stringPosition * 13.0) + 150 * rowIndex) - 10, 14, 12, Color.WHITE);
				blend.setTopInput(topInput);
				blend2.setTopInput(topInput2);
				blend.setMode(BlendMode.OVERLAY);
				blend2.setMode(BlendMode.OVERLAY);

//				Check if fret is double digit and set effect
				if (fret < 10) {
					fretText.setEffect(blend);

				} else {
					fretText.setEffect(blend2);

				}
				group.getChildren().add(fretText);
	}

	private void topSlur(int horizontalPosition, int stringPosition, int rowIndex, int fret) {
		Arc arc = new Arc((172 + 33.0 * horizontalPosition), (328 + stringPosition * 13.0) + 150 * rowIndex, 16.0, 18.0,
				17.0, 160.0);
		arc.setFill(Color.TRANSPARENT);
		arc.setStroke(Color.BLACK);
		arc.toFront();
		arc.setType(ArcType.OPEN);

		Arc arc2 = new Arc((175 + 33.0 * horizontalPosition), (328 + stringPosition * 13.0) + 150 * rowIndex, 17.0,
				18.0, 17.0, 160.0);
		arc2.setFill(Color.TRANSPARENT);
		arc2.setStroke(Color.BLACK);
		arc2.toFront();
		arc2.setType(ArcType.OPEN);

		if (fret < 10) {
			group.getChildren().add(arc);

		} else {
			group.getChildren().add(arc2);
		}
	}
	private void bottomSlur(int horizontalPosition, int stringPosition, int rowIndex, int fret) {
		Arc arc = new Arc((172 + 33.0 * horizontalPosition), (320 + stringPosition * 13.0) + 150 * rowIndex, 16.0, 18.0,
				189.0, 160.0);
		arc.setFill(Color.TRANSPARENT);
		arc.setStroke(Color.BLACK);
		arc.toFront();
		arc.setType(ArcType.OPEN);

		Arc arc2 = new Arc((175 + 33.0 * horizontalPosition), (320 + stringPosition * 13.0) + 150 * rowIndex, 17.0,
				18.0, 189.0, 160.0);
		arc2.setFill(Color.TRANSPARENT);
		arc2.setStroke(Color.BLACK);
		arc2.toFront();
		arc2.setType(ArcType.OPEN);

		if (fret < 10) {
			group.getChildren().add(arc);

		} else {
			group.getChildren().add(arc2);
		}
	}
	 private void drawSlur(int horizontalPosition, int stringPosition, int rowIndex, int fret, Note note) {
		if(note.getNotations().getSlurs().get(0).getType().equals("start")&&note.getNotations().getSlurs().get(0).getPlacement()!=null) {
			topSlur(horizontalPosition,stringPosition,rowIndex,fret );
		}
		else if(note.getNotations().getSlurs().get(0).getType().equals("stop")&&note.getNotations().getSlurs().get(0).getPlacement()==null) {
			topSlur(horizontalPosition,  stringPosition,  rowIndex, fret);
		}
		else {
			bottomSlur(horizontalPosition,  stringPosition,  rowIndex, fret);
		}
	
	}

	public void drawNote(int horizontalPosition, int stringPosition, int rowIndex, int fret) {
		// distance between 2 notes here is 37
		Text fretText = new Text(152 + 33.0 * horizontalPosition, (326 + stringPosition * 13.0) + 150 * rowIndex,
				Integer.toString(fret));
		fretText.toFront();
//		For fret background
		Blend blend = new Blend();
		Blend blend2 = new Blend();

		ColorInput topInput = new ColorInput((154 + 33.0 * horizontalPosition) - 3,
				((326 + stringPosition * 13.0) + 150 * rowIndex) - 10, 10, 12, Color.WHITE);
		ColorInput topInput2 = new ColorInput((155 + 33.0 * horizontalPosition) - 3,
				((326 + stringPosition * 13.0) + 150 * rowIndex) - 10, 14, 12, Color.WHITE);
		blend.setTopInput(topInput);
		blend2.setTopInput(topInput2);
		blend.setMode(BlendMode.OVERLAY);
		blend2.setMode(BlendMode.OVERLAY);

//		Check if fret is double digit and set effect
		if (fret < 10) {
			fretText.setEffect(blend);

		} else {
			fretText.setEffect(blend2);

		}
		group.getChildren().add(fretText);

	}

	public void drawMeasures(int horizontalPosition, int rowIndex, int measureNumber) {
		Line line = new Line(144 + horizontalPosition * 33.0, 335 + 150 * rowIndex, 144 + horizontalPosition * 33.0,
				400 + 150 * rowIndex);
		line.setStrokeWidth(2);
		String measureNumberStr = String.valueOf(measureNumber);
		// draw measure number
		Text measureNumberText = new Text(144 + horizontalPosition * 33.0, 418 + 150 * rowIndex, measureNumberStr);
		measureNumberText.setFont(Font.font("Verdana", 13));
		measureNumberText.setFill(Color.CRIMSON);

		group.getChildren().add(line);
		group.getChildren().add(measureNumberText);
	}

	public void drawEverything(HashMap<Integer, List<Note>> guitarNotesMap, HashMap<Integer, Integer> measuresList,
			MainViewController mvc) throws TXMLException {

		Score score1 = mvc.converter.getScore();
		List<Part> partList = score1.getModel().getParts();
		// measure is drawn only when measureNumber increases
		int measureNum = 0;
		GuitarStaff guitarStaff = new GuitarStaff();
		int temp = 0;
		String clef = partList.get(0).getMeasures().get(0).getAttributes().clef.sign;
		
		int nume = Settings.getInstance().tsNum;
		int denom = Settings.getInstance().tsDen;
		Text timeSigNume = new Text(125, 365, ""+nume);
		timeSigNume.setFont(Font.font("Verdana", 20));
		Text timeSigDenom = new Text(125, 385, ""+denom);
		timeSigDenom.setFont(Font.font("Verdana", 20));
		group.getChildren().add(timeSigNume);
		group.getChildren().add(timeSigDenom);
		
		for (HashMap.Entry<Integer, Integer> entry : measuresList.entrySet()) {
			int keyValue = entry.getKey();
//			System.out.printf("measure key: %d",keyValue);
			// 20 notes each row
			int dividend = keyValue, divisor = 22;
			int rowIndex = dividend / divisor;
			int horizontalPosition = keyValue % 22;
			// measure is drawn only when entry.getValue changes
			if (measureNum < entry.getValue()) {
				measureNum = entry.getValue();
				drawMeasures(horizontalPosition, rowIndex, measureNum);
			}
//			
		}

		for (HashMap.Entry<Integer, List<Note>> entry : guitarNotesMap.entrySet()) {

			int keyValue = entry.getKey();
//			System.out.printf("hashmap key: %d",keyValue);
			// 20 notes each row
			int dividend = keyValue, divisor = 22;
//			System.out.println("dividend: " + dividend);
			int rowIndex = dividend / divisor;
//			System.out.println("rowIndex: " + rowIndex);
			int horizontalPosition = keyValue % 22;
//			System.out.println("horizontalPosition: "+horizontalPosition);
			int stringPosition = entry.getValue().get(0).getNotations().getTechnical().getString();
//			Draw Staffs
			if (temp == rowIndex) {
//				System.out.println("Drawing staff: " + temp);
				Text hold = new Text(105, 315, "");
				hold.setFont(Font.font("Verdana", 100));
				group.getChildren().add(hold);
				guitarStaff.draw(group, temp);
				temp++;
			}
		
			int fret = entry.getValue().get(0).getNotations().getTechnical().getFret();

//			Draw Slurs

			if (hasSlur(entry.getValue().get(0)) && guitarNotesMap.get(keyValue + 1) != null
					&& guitarNotesMap.get(keyValue + 1).get(0).getNotations().getSlurs() != null && guitarNotesMap
							.get(keyValue + 1).get(0).getNotations().getSlurs().get(0).getType().equals("stop")) {

				String place = entry.getValue().get(0).getNotations().getSlurs().get(0).getPlacement();
				int num = entry.getValue().get(0).getNotations().getSlurs().get(0).getNumber();
//				System.out.println("Slur place: " + place);
//				System.out.println("Slur num: " + num);
				drawSlur(horizontalPosition, stringPosition, rowIndex, fret, entry.getValue().get(0));

			}
			if(entry.getValue().get(0).getGrace()!=null) {
//				draw grace notes
				drawGrace(horizontalPosition, stringPosition, rowIndex, fret);
				System.out.println("Grace note:"+fret);
			}
			else{
//				Draw Notes
				drawNote(horizontalPosition, stringPosition, rowIndex, fret);
			}

//			Get chords if any
			List<Note> notesIncludeChord = entry.getValue();
			notesIncludeChord.remove(0);
//			Draw Chords 
			for (Note note : notesIncludeChord) {
				keyValue = entry.getKey();
				dividend = keyValue;
				rowIndex = dividend / divisor;
				horizontalPosition = keyValue % 22;
				fret = note.getNotations().getTechnical().getFret();
				stringPosition = note.getNotations().getTechnical().getString();
				if(note.getGrace()!=null) {
//					draw grace notes
					drawGrace(horizontalPosition, stringPosition, rowIndex, fret);
					System.out.println("Grace note:"+fret);
				}
				else{
//					Draw Notes
					drawNote(horizontalPosition, stringPosition, rowIndex, fret);
				}
//				drawNote(horizontalPosition, stringPosition, rowIndex, fret);

			}

		}

	}


	public Group getDrawing() {
		return this.group;
	}

}
