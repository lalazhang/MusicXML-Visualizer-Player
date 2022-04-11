package draw.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GUI.MainViewController;
import converter.Score;
import converter.measure.TabMeasure;
import custom_exceptions.TXMLException;
import models.Part;
import models.measure.note.Note;

public class GuitarNotesList {
	public Map<Integer, List<Note>> GuitarNotesList = new HashMap<>();
	private Map<Integer, Integer> measures = new HashMap<Integer, Integer>();
	private String clef;

	public GuitarNotesList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void createMeasuresList(MainViewController mvc) {

		Score score = mvc.converter.getScore();
		List<TabMeasure> measureList = score.getMeasureList();
		int measureListSize = measureList.size();
//		System.out.println("measure list size is  \n " + measureListSize);
	}

	public int getNoteListSizeGuitar(Score score) {
		List<TabMeasure> measureList = score.getMeasureList();

		int noteSize = 0;

		for (int i = 0; i < measureList.size(); i++) {
			for (int j = 0; j < measureList.get(i).getSortedNoteList().size(); j++) {

				if (measureList.get(i).getSortedNoteList().get(j).getModel().getChord() == null) {

					noteSize++;
				} else {
					noteSize++;
				}

			}
		}
		return noteSize;

	}

	public String getInstrumentName(Score score) throws TXMLException {

		String instrumentName = score.getModel().getPartList().getScoreParts().get(0).getPartName();
		return instrumentName;
	}
//	String musicXml = mvc.converter.getMusicXML();
//	
//	Score score1 = mvc.converter.getScore();
//int scoreMeasureListSize = mvc.converter.getScore().getMeasureList().size();
//	//int noteSize = score1.getMeasureList().get(1).getSortedNoteList().size();
//	
//	
//	
//	 List<TabMeasure> measureList = score1.getMeasureList();
//	 int noteSize= getNoteListSizeGuitar(score1);			
//	 int notePosition[][] = new int[noteSize][2] ;
//	
//	//Make default position 20 and 20 is ignored while drawing notes
//	for (int i=0;i<noteSize;i++) {
//		notePosition[i][1]=20;
//	}
//	int notePositionIndexD1 =0;
//	int notePositionIndexD2 =0;
//
//	//Create note list for Guitar
//
//	
//	for(int i=0; i<measureList.size();i++) {
//		for (int j=0; j<measureList.get(i).getSortedNoteList().size();j++)
//		{	
//				int fret = measureList.get(i).getSortedNoteList().get(j).getModel().getNotations().getTechnical().getFret();	
//				int stringPosition = measureList.get(i).getSortedNoteList().get(j).getModel().getNotations().getTechnical().getString();
//				String type = measureList.get(i).getSortedNoteList().get(j).getModel().getType();
//			
///*			  if(measureList.get(i).getSortedNoteList().get(j).getModel().getChord()==null)
//			  {
//			 */
//				notePositionIndexD2=0;
//				System.out.println("\n"+fret +stringPosition);
//		
//
//				notePosition[notePositionIndexD1][notePositionIndexD2]=fret;
//				notePosition[notePositionIndexD1][notePositionIndexD2+1]=stringPosition;
//				notePositionIndexD1++;
//		
//		}
//		
//	}
//
//	System.out.println("note size: "+ noteSize);
//	int printStr11 = score1.getMeasureList().size();
//	
//	List<Part> partList = score1.getModel().getParts();
//	
//	//System.out.println("score counts: "+score1.getModel().getScoreCount());		
//	clef = partList.get(0).getMeasures().get(0).getAttributes().clef.sign;
//	
//	
//	
//	return notePosition;
//	
//}
	
	public int[][] notesList(MainViewController mvc) throws TXMLException {

		int guitarNotesListIdx = 0;

		this.createMeasuresList(mvc);
		Score score1 = mvc.converter.getScore();

		// System.out.println(musicXml);

		String instrumentName = getInstrumentName(score1);

		int scoreMeasureListSize = mvc.converter.getScore().getMeasureList().size();
		// int noteSize = score1.getMeasureList().get(1).getSortedNoteList().size();

		List<TabMeasure> measureList = score1.getMeasureList();
		System.out.println("measure list size is  \n " + measureList.size());

		int noteSize = 0;

		noteSize = getNoteListSizeGuitar(score1);

		int notePosition[][] = new int[noteSize][2];
		//Make default position 20 and 20 is ignored while drawing notes
		for (int i=0;i<noteSize;i++) {
			notePosition[i][1]=20;
		}
		int notePositionIndexD1 =0;
		int notePositionIndexD2 =0;


		// Create Note list for Drum
		if (instrumentName.contains("Guitar")) {
			for (int i = 0; i < measureList.size(); i++) {
				for (int j = 0; j < measureList.get(i).getSortedNoteList().size(); j++) {
					if (measureList.get(i).getSortedNoteList().get(j).getModel() == null
							|| measureList.get(i).getSortedNoteList().get(j).getModel().getPitch() == null) {

					} else {
												
						int fret = measureList.get(i).getSortedNoteList().get(j).getModel().getNotations().getTechnical().getFret();	
						int stringPosition = measureList.get(i).getSortedNoteList().get(j).getModel().getNotations().getTechnical().getString();						
//						int octiveInt = measureList.get(i).getSortedNoteList().get(j).getModel().getPitch().getOctave();
//						String octive = String.valueOf(octiveInt);
//						String note = measureList.get(i).getSortedNoteList().get(j).getModel().getPitch().getStep();
								
						String type = measureList.get(i).getSortedNoteList().get(j).getModel().getType();

						Note guitarNote = measureList.get(i).getSortedNoteList().get(j).getModel();

//						System.out.printf("measure is %d \n", measureList.get(i).getModel().getNumber());

						int measure = measureList.get(i).getModel().getNumber();

						// String notehead=
						// measureList.get(i).getSortedNoteList().get(j).getModel().getNotehead().toString();
						if (measureList.get(i).getSortedNoteList().get(j).getModel().getChord() == null) {
							{
								
							
				/*			  if(measureList.get(i).getSortedNoteList().get(j).getModel().getChord()==null)
							  {
							 */
								notePositionIndexD2=0;
//								System.out.println("\n"+fret +stringPosition);
						

								notePosition[notePositionIndexD1][notePositionIndexD2]=fret;
								notePosition[notePositionIndexD1][notePositionIndexD2+1]=stringPosition;
								notePositionIndexD1++;
//								System.out.println("\n" + note + octive);
//								String noteWithOctive = note + octive;
//								// System.out.println(noteWithOctive);
//								int notePositionOnStaff = noteToNumber(noteWithOctive);
//								notePosition[notePositionIndexD1][notePositionIndexD2]=notePositionOnStaff;
//								System.out.println(notePositionOnStaff);

								// hashmap drumnotes list chord
								List<Note> chordDrumNotes = new ArrayList<Note>();
								// add this note to hashmap List<Note>
								chordDrumNotes.add(guitarNote);
								// add key and drumnotes list to hashmap
								GuitarNotesList.put(guitarNotesListIdx, chordDrumNotes);
								// drum notes index +1 only when it's not chord note
								this.measures.put(guitarNotesListIdx, measure);

								guitarNotesListIdx++;

							}
						} else {
							// go back to previous key
							guitarNotesListIdx--;
							GuitarNotesList.get(guitarNotesListIdx).add(guitarNote);
							guitarNotesListIdx++;

//							System.out.println(note + octive);
//							String noteWithOctive = note + octive;
////							int notePositionOnStaff = noteToNumber(noteWithOctive);
//							notePositionIndexD2 = 1;
//							notePositionIndexD1--;
//							notePosition[notePositionIndexD1][notePositionIndexD2] = notePositionOnStaff;
//							notePositionIndexD1++;

						}

					}

				}

			}
		}

//		for (int i = 0; i < notePosition.length; i++) {
//			for (int j = 0; j < notePosition[i].length; j++) {
//				// System.out.println(notePosition[i][j]);
//			}
//
//		}
//		System.out.println("note size: " + noteSize);
		int printStr11 = score1.getMeasureList().size();

		List<Part> partList = score1.getModel().getParts();

		// System.out.println("score counts: "+score1.getModel().getScoreCount());
		clef = partList.get(0).getMeasures().get(0).getAttributes().clef.sign;

//		System.out.printf("clef of this music sheet: %s \n", clef);
//		System.out.printf("instrument: %s \n", instrumentName);
//
//		System.out.println("hashmap size:" + GuitarNotesList.size());

		return notePosition;

	}

	public HashMap<Integer, List<Note>> getGuitarNotesMap() {
		return (HashMap<Integer, List<Note>>) this.GuitarNotesList;
	}
	 public HashMap<Integer,Integer> getMeasures() {
	    	return (HashMap<Integer, Integer>) this.measures;
	    }
}