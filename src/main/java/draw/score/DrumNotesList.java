package draw.score;

import java.util.List;

import GUI.MainViewController;
import converter.Score;
import converter.measure.TabMeasure;
import custom_exceptions.TXMLException;
import models.Part;

public class DrumNotesList {
	public String clef;
	public DrumNotesList() {
		super();
		// TODO Auto-generated constructor stub
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
				
				int duration=measureList.get(i).getSortedNoteList().get(j).duration;
				System.out.println("duration: "+duration);
				
				/*
				 * if(measureList.get(i).getSortedNoteList().get(j).getModel().getChord()==null)
				 * {
				 */ noteSize++; /* } */
				 
				
			}
			
		}
		return noteSize;
		
    }
	
	    
	    public int[][] notesList(MainViewController mvc) throws TXMLException  {

			String musicXml = mvc.converter.getMusicXML();
			
			Score score1 = mvc.converter.getScore();
			
			//System.out.println(musicXml);

			String instrumentName = getInstrumentName(score1);
			
			int scoreMeasureListSize = mvc.converter.getScore().getMeasureList().size();
			//int noteSize = score1.getMeasureList().get(1).getSortedNoteList().size();
			
			
			
			 List<TabMeasure> measureList = score1.getMeasureList();
			 int noteSize=0;

				 noteSize = getNoteListSizeDrum(score1);	


			
			
			int notePosition[][] = new int[noteSize][2] ;
			
			//Make default position 20 and 20 is ignored while drawing notes
			for (int i=0;i<noteSize;i++) {
				notePosition[i][1]=20;
			}
			int notePositionIndexD1 =0;
			int notePositionIndexD2 =0;
			
			// Create Note list for Drum
			if(instrumentName.contains("Drum")) {
				
				for(int i=0; i<measureList.size();i++) {
					for (int j=0; j<measureList.get(i).getSortedNoteList().size();j++)
					{	
						if(measureList.get(i).getSortedNoteList().get(j).getModel()==null ||measureList.get(i).getSortedNoteList().get(j).getModel().getUnpitched()==null ) {
				
						}
						else {
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
