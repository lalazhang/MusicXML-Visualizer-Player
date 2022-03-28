package draw.score;

import java.util.List;

import GUI.MainViewController;
import converter.Score;
import converter.measure.TabMeasure;
import custom_exceptions.TXMLException;
import models.Part;

public class GuitarNotesList {
	private String clef;
	public GuitarNotesList() {
		super();
		// TODO Auto-generated constructor stub
	}



	 public int getNoteListSizeGuitar(Score score) {
	  		List<TabMeasure> measureList = score.getMeasureList();
	  		
	  		int noteSize=0;
	  		
	  		for(int i=0; i<measureList.size();i++) {
	  			for (int j=0; j<measureList.get(i).getSortedNoteList().size();j++)
	  			{	
		
					  if(measureList.get(i).getSortedNoteList().get(j).getModel().getChord()==null)
					  {
					 
	  					noteSize++;
	  					}
					  else {
						  noteSize++;
					  }
	  				 				
	  			} 			
	  		}
	  		return noteSize;
	  		
	      }
	    
	    public int[][] notesList(MainViewController mvc) throws TXMLException  {

			String musicXml = mvc.converter.getMusicXML();
			
			Score score1 = mvc.converter.getScore();
		int scoreMeasureListSize = mvc.converter.getScore().getMeasureList().size();
			//int noteSize = score1.getMeasureList().get(1).getSortedNoteList().size();
			
			
			
			 List<TabMeasure> measureList = score1.getMeasureList();
			 int noteSize= getNoteListSizeGuitar(score1);			
			 int notePosition[][] = new int[noteSize][2] ;
			
			//Make default position 20 and 20 is ignored while drawing notes
			for (int i=0;i<noteSize;i++) {
				notePosition[i][1]=20;
			}
			int notePositionIndexD1 =0;
			int notePositionIndexD2 =0;
		
			//Create note list for Guitar

			
			for(int i=0; i<measureList.size();i++) {
				for (int j=0; j<measureList.get(i).getSortedNoteList().size();j++)
				{	
	  				int fret = measureList.get(i).getSortedNoteList().get(j).getModel().getNotations().getTechnical().getFret();	
	  				int stringPosition = measureList.get(i).getSortedNoteList().get(j).getModel().getNotations().getTechnical().getString();
					String type = measureList.get(i).getSortedNoteList().get(j).getModel().getType();
					
		/*			  if(measureList.get(i).getSortedNoteList().get(j).getModel().getChord()==null)
					  {
					 */
						notePositionIndexD2=0;
						System.out.println("\n"+fret +stringPosition);
				
	
						notePosition[notePositionIndexD1][notePositionIndexD2]=fret;
						notePosition[notePositionIndexD1][notePositionIndexD2+1]=stringPosition;
						notePositionIndexD1++;
				
				}
				
			}

			System.out.println("note size: "+ noteSize);
			int printStr11 = score1.getMeasureList().size();
			
			List<Part> partList = score1.getModel().getParts();
			
			//System.out.println("score counts: "+score1.getModel().getScoreCount());		
			clef = partList.get(0).getMeasures().get(0).getAttributes().clef.sign;
			
			
			
			return notePosition;
			
	    }
	    
	   
}
