package draw.score;


import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import GUI.MainViewController;
import converter.Score;
import custom_exceptions.TXMLException;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Part;

import models.measure.note.Note;
import utility.Settings;

public class DrawDrumNotes {
	private Group group = new Group();
	//private HashMap<Integer,Integer> measuresList = new HashMap<Integer,Integer>();
	//public DrumNotesList drumNotesList= new DrumNotesList();

	private double distanceBetweenNotes=40.0;
	private double measureBelowStaff=10.0;


	public DrawDrumNotes() {
		super();
		// TODO Auto-generated constructor stub

	}
	

	//draw based on 2D array, to be deleted

	/*
	 * public void draw(MainViewController mvc,Group box, int[][] notesPositionList)
	 * throws TXMLException { int noteHeightDrum = 0; // get clef Score score1 =
	 * mvc.converter.getScore(); List<Part> partList = score1.getModel().getParts();
	 * //System.out.println("score counts: "+score1.getModel().getScoreCount());
	 * String clef = partList.get(0).getMeasures().get(0).getAttributes().clef.sign;
	 * 
	 * Line line1 = new Line(105, 220, 105, 240); Line line2 = new Line(110, 220,
	 * 110, 240); line1.setStrokeWidth(4); line2.setStrokeWidth(4);
	 * box.getChildren().add(line2); box.getChildren().add(line1);
	 * 
	 * System.out.printf("drum note span size" +
	 * String.valueOf(notesPositionList.length)); for (int i = 0; i <
	 * notesPositionList.length; i++) { // for(int ii=0;i<i%30;ii++) {
	 * 
	 * int dividend = i, divisor = 30; int height = dividend / divisor; int
	 * horizontalSpan = i % 30; System.out.println("i/30 " + height);
	 * System.out.println("i%30 " + horizontalSpan); DrumStaff drumStaff = new
	 * DrumStaff(); drumStaff.draw(box, height); for (int j = 0; j <
	 * notesPositionList[i].length; j++) { noteHeightDrum = notesPositionList[i][j];
	 * if (noteHeightDrum < 20) { Line noteLine = new Line(135 + distanceBetweenNotes *
	 * horizontalSpan, 200 - 5.0 * noteHeightDrum + 90 * height, 135 + distanceBetweenNotes *
	 * horizontalSpan, 250 - 5.0 * noteHeightDrum + 90 * height); Circle note = new
	 * Circle(130 + distanceBetweenNotes * horizontalSpan, 250.0 - 5.0 * noteHeightDrum + 90 *
	 * height, 5); note.setFill(Color.MIDNIGHTBLUE);
	 * 
	 * box.getChildren().add(noteLine); box.getChildren().add(note); }
	 * 
	 * } // }
	 * 
	 * }
	 * 
	 * 
	 * 
	 * }
	 */
	


	public void drawSolidNote(int horizontalPosition, int stepOctave, int rowIndex) {
		//distance between 2 notes is 25
		int noteRadius=4;
		Line noteLine = new Line(135 + distanceBetweenNotes * horizontalPosition, 
				225 - 5.0 * stepOctave + 90 * rowIndex,
				135 + distanceBetweenNotes * horizontalPosition, 

				250 - 5.0 * stepOctave + 90 * rowIndex);
		Circle note = new Circle(130 + distanceBetweenNotes * horizontalPosition,
				250.0 - 5.0 * stepOctave + 90 * rowIndex, 5);
		note.setFill(Color.MIDNIGHTBLUE);
		group.getChildren().add(noteLine);
		group.getChildren().add(note);
		
	}
	
	public void drawSolidGraceNote(int horizontalPosition, int stepOctave, int rowIndex) {
		//distance between 2 notes is 25
		int moveToRight=2;
		Line noteLine = new Line(135 + distanceBetweenNotes * horizontalPosition, 
				225- 5.0 * stepOctave + 90 * rowIndex,
				135 + distanceBetweenNotes * horizontalPosition, 
				250 - 5.0 * stepOctave + 90 * rowIndex);
		Circle note = new Circle(130 + distanceBetweenNotes * horizontalPosition+moveToRight,
				250.0 - 5.0 * stepOctave + 90 * rowIndex, 3);
		note.setFill(Color.MIDNIGHTBLUE);
		group.getChildren().add(noteLine);
		group.getChildren().add(note);
		
	}
	
	public void drawHalfNote(int horizontalPosition, int stepOctave, int rowIndex) {
		int noteRadius=4;
		Line noteLine = new Line(135 + distanceBetweenNotes * horizontalPosition, 
				225 - 5.0 * stepOctave + 90 * rowIndex,
				135 + distanceBetweenNotes * horizontalPosition, 
				250 - 5.0 * stepOctave + 90 * rowIndex);
		Circle note = new Circle(130 + distanceBetweenNotes * horizontalPosition,
				250.0 - 5.0 * stepOctave + 90 * rowIndex, 5);
		note.setFill(Color.TRANSPARENT);
		note.setStroke(Color.MIDNIGHTBLUE);
		note.setStrokeWidth(2);
		group.getChildren().add(note);
		group.getChildren().add(noteLine);
	}
	
	public void drawWholeNote(int horizontalPosition, int stepOctave, int rowIndex) {
		int noteRadius=4;

		Circle note = new Circle(130 + distanceBetweenNotes * horizontalPosition,
				250.0 - 5.0 * stepOctave + 90 * rowIndex, 5);
		note.setFill(Color.ANTIQUEWHITE);
		note.setStroke(Color.MIDNIGHTBLUE);
		note.setStrokeWidth(3);
		group.getChildren().add(note);
	
	}
	
	
	
	public void drawNoteHead(int horizontalPosition, int stepOctave, int rowIndex) {
		Line cross1= new Line(130 + distanceBetweenNotes * horizontalPosition-5,
				250.0 - 5.0 * stepOctave + 90 * rowIndex-2.5,
				130 + distanceBetweenNotes * horizontalPosition+5,
				250.0 - 5.0 * stepOctave + 90 * rowIndex+2.5);
		Line cross2 = new Line(130 + distanceBetweenNotes * horizontalPosition-5,
				250.0 - 5.0 * stepOctave + 90 * rowIndex+2.5,
				130 + distanceBetweenNotes * horizontalPosition+5,
				250.0 - 5.0 * stepOctave + 90 * rowIndex-2.5);
		Line line = new Line(135 + distanceBetweenNotes * horizontalPosition, 
							225 - 5.0 * stepOctave + 90 * rowIndex,
							135 + distanceBetweenNotes * horizontalPosition, 
							250 - 5.0 * stepOctave + 90 * rowIndex);
		group.getChildren().add(cross1);
		group.getChildren().add(cross2);
		group.getChildren().add(line);
		
	}
	
	public void drawDuration4(int horizontalPosition, int stepOctave, int rowIndex) {
		int durationLength=18;
		int controlX=14;
		int controlY=11;
		int distanceBetweenTwoCurve=7;
		QuadCurve quadcurve1 = new QuadCurve(
				135 + distanceBetweenNotes * horizontalPosition, 
				225 - 5.0 * stepOctave + 90 * rowIndex, 
				135 + distanceBetweenNotes * horizontalPosition+controlX, 
				225 - 5.0 * stepOctave + 90 * rowIndex+controlY, 
				135 + distanceBetweenNotes * horizontalPosition+10, 
				225 - 5.0 * stepOctave + 90 * rowIndex+durationLength);
		quadcurve1.setStroke(Color.CRIMSON);
		quadcurve1.setFill(Color.TRANSPARENT);
		
		QuadCurve quadcurve2 = new QuadCurve(
				135 + distanceBetweenNotes * horizontalPosition, 
				225 - 5.0 * stepOctave + 90 * rowIndex+distanceBetweenTwoCurve,
				135 + distanceBetweenNotes * horizontalPosition+controlX, 
				225 - 5.0 * stepOctave + 90 * rowIndex+controlY, 
				135 + distanceBetweenNotes * horizontalPosition+10, 
				225 - 5.0 * stepOctave + 90 * rowIndex+distanceBetweenTwoCurve+durationLength);
		quadcurve2.setStroke(Color.CRIMSON);
		quadcurve2.setFill(Color.TRANSPARENT);
		group.getChildren().add(quadcurve1);
		group.getChildren().add(quadcurve2);
		
		

	}
	
	public void drawDuration8(int horizontalPosition, int stepOctave, int rowIndex) {
		int durationLength=18;
		int controlX=14;
		int controlY=11;
		int distanceBetweenTwoCurve=7;
		QuadCurve quadcurve1 = new QuadCurve(
				135 + distanceBetweenNotes * horizontalPosition, 
				225 - 5.0 * stepOctave + 90 * rowIndex, 
				135 + distanceBetweenNotes * horizontalPosition+controlX, 
				225 - 5.0 * stepOctave + 90 * rowIndex+controlY, 
				135 + distanceBetweenNotes * horizontalPosition+10, 
				225 - 5.0 * stepOctave + 90 * rowIndex+durationLength);
		quadcurve1.setStroke(Color.CRIMSON);
		quadcurve1.setFill(Color.TRANSPARENT);
	
		group.getChildren().add(quadcurve1);

		
	}
	
	public void drawSlur(int horizontalPosition, int stepOctave, int rowIndex) {
		int durationLength=18;
		int controlX=10;
		int controlY=8;
	
		QuadCurve quadcurve1 = new QuadCurve(
				135 + distanceBetweenNotes * horizontalPosition, 
				270 - 5.0 * stepOctave + 90 * rowIndex, 
				135 + distanceBetweenNotes * horizontalPosition+controlX, 
				270 - 5.0 * stepOctave + 90 * rowIndex+controlY, 
				160 + distanceBetweenNotes * horizontalPosition+10, 
				270 - 5.0 * stepOctave + 90 * rowIndex);
		quadcurve1.setStroke(Color.CRIMSON);
		quadcurve1.setFill(Color.TRANSPARENT);
	
		group.getChildren().add(quadcurve1);

		
	}
	public void drawMeasures(int horizontalPosition,int rowIndex, int measureNumber) {

		int adjustment=5;
		int distanceBetweenClelfMeasure=3;
		int distanceBetweenClelfs=2;
		int moveBitToLeft=7;
		Line line = new Line(
				160+distanceBetweenNotes * horizontalPosition-moveBitToLeft,
				210+90*rowIndex,
				160+distanceBetweenNotes * horizontalPosition-moveBitToLeft,

				250+90*rowIndex);
		line.setStrokeWidth(3);
		String measureNumberStr = String.valueOf(measureNumber);
		//draw measure number

		Text measureNumberText = new Text(160+distanceBetweenNotes * horizontalPosition-adjustment-moveBitToLeft, 250+90*rowIndex+measureBelowStaff+adjustment, measureNumberStr);
		measureNumberText.setFont(Font.font("Verdana", 16));
		measureNumberText.setFill(Color.CRIMSON);
		
		//draw clef
		Line clef1 = new Line(
				160+distanceBetweenNotes * horizontalPosition+distanceBetweenClelfMeasure-moveBitToLeft,
				220+90*rowIndex,
				160+distanceBetweenNotes * horizontalPosition+distanceBetweenClelfMeasure-moveBitToLeft,
				240+90*rowIndex);
		clef1.setStroke(Color.BLUE);

		Line clef2 = new Line(
				160+distanceBetweenNotes * horizontalPosition+distanceBetweenClelfMeasure+distanceBetweenClelfs-moveBitToLeft,
				220+90*rowIndex,
				160+distanceBetweenNotes * horizontalPosition+distanceBetweenClelfMeasure+distanceBetweenClelfs-moveBitToLeft,
				240+90*rowIndex);
		clef2.setStroke(Color.GRAY);
	
		//check if clef should be drawn
		if(measureNumber%2==1) {
			group.getChildren().add(clef1);
			group.getChildren().add(clef2);

		}
		
		
		group.getChildren().add(line);
		group.getChildren().add(measureNumberText);
	}


	public void drawEverything(HashMap <Integer, List<Note>>drumNotesMap,HashMap<Integer,Integer>measuresList,MainViewController mvc ) throws TXMLException {
		//Loop through drum notes hashmap
		
		Score score1 = mvc.converter.getScore();
		List<Part> partList = score1.getModel().getParts();	
		//measure is drawn only when measureNumber increases

		int measureNum=0;	
		String clef = partList.get(0).getMeasures().get(0).getAttributes().clef.sign;

	

		//time signature
		int nume = Settings.getInstance().tsNum;
		int denom = Settings.getInstance().tsDen;
		Text timeSigNume = new Text(125, 230, ""+nume);
		timeSigNume.setFont(Font.font("Verdana", 20));
		Text timeSigDenom = new Text(125, 250, ""+denom);
		timeSigDenom.setFont(Font.font("Verdana", 20));
		group.getChildren().add(timeSigNume);
		group.getChildren().add(timeSigDenom);
		
		System.out.println(measuresList);
		
		//draw measures
		for (HashMap.Entry<Integer,Integer> entry: measuresList.entrySet()) {

			int keyValue=entry.getKey();
			System.out.printf("measure key: %d",keyValue);

			//20 notes each row
			int dividend = keyValue, divisor = 20;
			int rowIndex = dividend / divisor;
			int horizontalPosition = keyValue % 20;
			//measure is drawn only when entry.getValue changes
			if(measureNum<entry.getValue()) {
				measureNum=entry.getValue();
				drawMeasures(horizontalPosition, rowIndex,measureNum);
			}
			
		}

		//draw notes
		for(HashMap.Entry<Integer,List<Note>> entry: drumNotesMap.entrySet()) {
			int keyValue=entry.getKey();
			int duration=0;
			System.out.printf("hashmap key: %d",keyValue);
			

			//20 notes each row
			int dividend = keyValue, divisor = 20;
			int rowIndex = dividend / divisor;
			int horizontalPosition = keyValue % 20 + 1;
			//20 notes each row
//			int dividend = keyValue, divisor = 20;
//			System.out.println("Kayvalue: "+keyValue);

//			System.out.println("\ndividend: "+dividend);
//			int rowIndex = dividend / divisor;		
//			System.out.println("rowIndex: "+rowIndex);
//			int horizontalPosition = keyValue % 20;
//			System.out.println("horizontalPosition: "+horizontalPosition);
			DrumStaff drumStaff = new DrumStaff();

	
			drumStaff.draw(group, rowIndex);
			


			//get display-step
			String step =entry.getValue().get(0).getUnpitched().getDisplayStep();
			//get display-octave
			int octaveInt = entry.getValue().get(0).getUnpitched().getDisplayOctave();
			String octave = String.valueOf(octaveInt);
			//combine step and octave to use noteToNumber to get position on staff
			String stepWithOctave = step+octave;
			int stepOctave = noteToNumber(stepWithOctave);
			
			if (entry.getValue().get(0).getDuration()==null) {}
			else { duration=entry.getValue().get(0).getDuration();
	
			if(duration==4 ||entry.getValue().get(0).getType()=="16th" ) 
			{  drawDuration4( horizontalPosition, stepOctave, rowIndex);}
			else if(duration==8||entry.getValue().get(0).getType()=="eighth") {
				drawDuration8( horizontalPosition, stepOctave, rowIndex);
			}}
			//draw slur
			if(entry.getValue().get(0).getNotations()==null) {
				
			}
			else if(entry.getValue().get(0).getNotations().getSlurs()==null){
				
			}
			else {
				if(entry.getValue().get(0).getNotations().getSlurs().get(0).getType()=="start") {
					drawSlur( horizontalPosition, stepOctave, rowIndex);
				}
				
			}
			List<Note> notesIncludeChord = entry.getValue();
			for (Note note: notesIncludeChord) {
				/*
				 * if(note.getNotehead()==null) {
				 * System.out.printf("note head is null %d",keyValue); }else {
				 * System.out.printf("note head is not null %d",keyValue); }
				 */
				
				//get display-step
				step =note.getUnpitched().getDisplayStep();
				//get display-octave
				octaveInt = note.getUnpitched().getDisplayOctave();
				octave = String.valueOf(octaveInt);
				//combine step and octave to use noteToNumber to get position on staff
				stepWithOctave = step+octave;
				stepOctave = noteToNumber(stepWithOctave);
//				System.out.printf("hashmap %s is %d",stepWithOctave,stepOctave);
//				System.out.println("Step with Octave: "+stepWithOctave);

//				System.out.println("StepOctave: "+stepOctave);

				String type = note.getType();
				  if(note.getNotehead()==null) { 
					  if(type=="half") {
						  drawHalfNote(horizontalPosition,stepOctave,rowIndex);
					  }
					  else if(type=="whole") {
						  drawWholeNote(horizontalPosition,stepOctave,rowIndex);

					  }
					  
					  else {
						  if(note.getGrace()==null) {
							  drawSolidNote(horizontalPosition,stepOctave,rowIndex);
						  }
						  else {
							  drawSolidGraceNote(horizontalPosition,stepOctave,rowIndex);
						  }
					  }
					
					  } 
				  else {
					  drawNoteHead(horizontalPosition,stepOctave,rowIndex); }
				  
				
				 
			}
			
		}
	}
	

	public int noteToNumber (String noteWithOctive) {
    	// noteNumber indicates the location of the note on staff
//    	System.out.print(noteWithOctive);

    	int noteNumber=0;
    	//noteNumber set as 0 means E4 so it sits on the bottom line of 5 staff lines
    	if (noteWithOctive.matches("C4")){   	
    		
    		noteNumber=-2;	  		
    	}
    	if (noteWithOctive.matches("D4")){
    		noteNumber=-1;	
    	}
    	if (noteWithOctive.matches("E4")){
    		noteNumber=0;	
    	}
    	if (noteWithOctive.matches("F4")){
    		noteNumber=1;	
    	}
    	if (noteWithOctive.matches("G4")){
    		noteNumber=2;	
    	}
    	if (noteWithOctive.matches("A4")){
    		noteNumber=3;	
    	}
    	if (noteWithOctive.matches("B4")){
    		noteNumber=4;	
    	}
    	if (noteWithOctive.matches("C5")){
    		noteNumber=5;	
    	}
    	if (noteWithOctive.matches("D5")){
    		noteNumber=6;	
    	}
    	if (noteWithOctive.matches("E5")){
    		noteNumber=7;	
    	}
    	if (noteWithOctive.matches("F5")){
    		noteNumber=8;	
    	}
    	if (noteWithOctive.matches("G5")){
    		noteNumber=9;	
    	}
    	if (noteWithOctive.matches("A5")){
    		noteNumber=10;	
    	}
    	return noteNumber;
    }

	public Group getDrawing() {
		return this.group;
	}
	

}
