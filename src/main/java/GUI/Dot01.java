package GUI;

import jm.music.data.*;
import jm.JMC;
import jm.gui.show.ShowScore;
import jm.util.*;

public class Dot01 implements JMC {
	private Phrase melody = new Phrase("Original"); 
	private Score melScore = new Score();
	private Part melPart = new Part();
	public Dot01() {
		super();

		for(int i=0;i<12;i++){
			Note n = new Note(C4, QUARTER_NOTE);
			melody.addNote(n);
		}
 

		this.melPart.addPhrase(melody);
		this.melScore.addPart(melPart);
	}


    
    public void view() {
   
    	//View.show(melScore);
    }


}
