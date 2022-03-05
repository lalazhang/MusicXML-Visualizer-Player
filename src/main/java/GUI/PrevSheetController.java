package GUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import converter.Score;
import converter.measure.TabMeasure;
import custom_exceptions.TXMLException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;
import models.Part;
import models.part_list.PartList;
import models.part_list.ScorePart;


public class PrevSheetController extends Application {
	private MainViewController mvc;
	@FXML 
	public CodeArea mxlTextPre;  
	@FXML
	//public VBox myVBox;
	
	public String clef;
	private String instrumentName;
	@FXML 
	public void initialize() {
		//mxlTextPre.setParagraphGraphicFactory(LineNumberFactory.get(mxlTextPre));
		Button button1 = new Button("button1");
		//myVBox.setMargin(button1,new Insets(10, 10, 10, 10));
		//myVBox.setStyle("-fx-border-color: red");
		//String musicXml = mvc.converter.getMusicXML();
		//System.out.println(musicXml);
		
		Dot01 test = new Dot01();
		test.view();
	}
	@Override
	

	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
	
		int[][] notePosition = printMusicXml();
		Text text = new Text(500,100,"Drumset");
		Group box = new Group();
		Line xAxis = new Line(100,300,900,300);
		xAxis.setStroke(Color.WHITE);
		box.getChildren().add(xAxis);
		
		Line testLine = new Line(100,200,900,200);
		testLine.setStroke(Color.WHITE);
		box.getChildren().add(testLine);


		Line yAxis = new Line(500,100,500,500);
		yAxis.setStroke(Color.WHITE);
		box.getChildren().add(yAxis);
		
		box.getChildren().add(text);
		
		//Draw Staff
		for (int i=0;i<5;i++) {
			Line staffLine = new Line(100.0,250.0-10.0*i,900.0,250.0-10.0*i);

			box.getChildren().add(staffLine);

			}
		
		//Draw notes
		int noteHeight=0;
		
		for (int i=0; i<notePosition.length;i++) {
			for (int j=0; j<notePosition[i].length;j++) {
				noteHeight=notePosition[i][j];
				if(noteHeight<20) {
					Line noteLine = new Line(135+25.0*i,200-5.0*noteHeight,135+25.0*i,250-5.0*noteHeight);
					Circle note = new Circle(130+25.0*i,250.0-5.0*noteHeight,5 );
					note.setFill(Color.MIDNIGHTBLUE);


					box.getChildren().add(noteLine);
					box.getChildren().add(note);
				}

			}
			
		}
		
		//draw clef
		System.out.println(clef);
		if(clef.matches("percussion")) {
			Line line1 = new Line(105,220,105,240);
			Line line2 = new Line(110,220,110,240);
			line1.setStrokeWidth(4);
			line2.setStrokeWidth(4);
			box.getChildren().add(line2);
			box.getChildren().add(line1);


		}
		
		Scene scene = new Scene(box,1000,600,Color.AZURE);
		
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
    
    
    public int getNoteListSizeDrum(Score score) {
		List<TabMeasure> measureList = score.getMeasureList();
		
		int noteSize=0;
		
		for(int i=0; i<measureList.size();i++) {
			for (int j=0; j<measureList.get(i).getSortedNoteList().size();j++)
			{	
				//int octiveInt = measureList.get(i).getSortedNoteList().get(j).getModel().getUnpitched().getDisplayOctave();
				if(measureList.get(i).getSortedNoteList().get(j).getModel().getChord()==null) 
					{
					noteSize++;
					}

				
				
			}
			
		}
		return noteSize;
		
    }
    
    public int getNoteListSizeGuitar(Score score) {
  		List<TabMeasure> measureList = score.getMeasureList();
  		
  		int noteSize=0;
  		
  		for(int i=0; i<measureList.size();i++) {
  			for (int j=0; j<measureList.get(i).getSortedNoteList().size();j++)
  			{	
  				int octiveInt = measureList.get(i).getSortedNoteList().get(j).getModel().getPitch().getOctave();
  				String octive = String.valueOf(octiveInt);
  				String note = measureList.get(i).getSortedNoteList().get(j).getModel().getPitch().getStep();
  				String type = measureList.get(i).getSortedNoteList().get(j).getModel().getType();
  				if(measureList.get(i).getSortedNoteList().get(j).getModel().getChord()==null) 
  					{
  					noteSize++;
  					}

  				
  				
  			}
  			
  		}
  		return noteSize;
  		
      }
    
    public int[][] printMusicXml() throws TXMLException  {

		String musicXml = mvc.converter.getMusicXML();
		
		Score score1 = mvc.converter.getScore();
		
		//System.out.println(musicXml);

		String instrumentName = getInstrumentName(score1);
		
		int scoreMeasureListSize = mvc.converter.getScore().getMeasureList().size();
		//int noteSize = score1.getMeasureList().get(1).getSortedNoteList().size();
		
		
		
		  List<TabMeasure> measureList = score1.getMeasureList();
		  

		
		int noteSize = getNoteListSizeDrum(score1);	
		int notePosition[][] = new int[noteSize][2] ;
		
		//Make default position 20 and 20 is ignored while drawing notes
		for (int i=0;i<noteSize;i++) {
			notePosition[i][1]=20;
		}
		int notePositionIndexD1 =0;
		int notePositionIndexD2 =0;
		
		
		for(int i=0; i<measureList.size();i++) {
			for (int j=0; j<measureList.get(i).getSortedNoteList().size();j++)
			{	
				int octiveInt = measureList.get(i).getSortedNoteList().get(j).getModel().getUnpitched().getDisplayOctave();
				String octive = String.valueOf(octiveInt);
				String note = measureList.get(i).getSortedNoteList().get(j).getModel().getUnpitched().getDisplayStep();
				String type = measureList.get(i).getSortedNoteList().get(j).getModel().getType();
				//String notehead= measureList.get(i).getSortedNoteList().get(j).getModel().getNotehead().toString();
				if(measureList.get(i).getSortedNoteList().get(j).getModel().getChord()==null) 
					{
					notePositionIndexD2=0;
					System.out.println("\n"+note +octive);
					String noteWithOctive = note+octive;
					//System.out.println(noteWithOctive);
					int notePositionOnStaff = noteToNumber(noteWithOctive);
					notePosition[notePositionIndexD1][notePositionIndexD2]=notePositionOnStaff;
					notePositionIndexD1++;
					System.out.println(notePositionOnStaff);
					}
				else {
					System.out.println(note+octive );
					String noteWithOctive = note+octive;
					int notePositionOnStaff = noteToNumber(noteWithOctive);
					notePositionIndexD2=1;
					notePositionIndexD1--;
					notePosition[notePositionIndexD1][notePositionIndexD2]=notePositionOnStaff;
					notePositionIndexD1++;
					
				}
				
				
				
				
			}
			
		}
		
		
		for (int i=0; i<notePosition.length;i++) {
			for (int j=0; j<notePosition[i].length;j++) {
				//System.out.println(notePosition[i][j]);
			}
			
		}
		System.out.println("note size: "+ noteSize);
		int printStr11 = score1.getMeasureList().size();
		
		List<Part> partList = score1.getModel().getParts();
		
		//System.out.println("score counts: "+score1.getModel().getScoreCount());
		
		
		
		clef = partList.get(0).getMeasures().get(0).getAttributes().clef.sign;
		
		
		
		System.out.printf("clef of this music sheet: %s \n", clef);
		System.out.printf("instrument: %s \n", instrumentName);
		
		
		return notePosition;
		
    }
    
    public int noteToNumber (String noteWithOctive) {
    	// noteNumber indicates the location of the note on staff
    	System.out.print(noteWithOctive);
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
}
