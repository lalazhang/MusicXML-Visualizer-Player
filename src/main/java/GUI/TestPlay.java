package GUI;


import java.util.Arrays;
import java.util.List;




import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;
import org.jfugue.theory.Chord;
import org.jfugue.theory.ChordProgression;
import org.jfugue.theory.Note;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.xml.parsers.ParserConfigurationException;
import javax.sound.midi.MidiChannel;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import org.jfugue.integration.MusicXmlParser;
import org.jfugue.midi.MidiParserListener;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.midi.MidiParser;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.staccato.StaccatoParserListener;
import converter.Converter;
import nu.xom.ParsingException;
import nu.xom.ValidityException;
/**
 * A little example showing how to play a tune in Java.
 * 
 * Inputs are not sanitized or checked, this is just to show how simple it is.
 * 
 * @author Peter
 */
public class TestPlay {
	

	
	
	    public static void main(String[] args) throws ParserConfigurationException, ParsingException, ParsingException, IOException, URISyntaxException {
	        MusicXmlParser parser = new MusicXmlParser();
	        
	        MidiParserListener midiListener = new MidiParserListener();
	        MidiParser midParse = new MidiParser();
	        
	        parser.addParserListener(midiListener); 
	        StaccatoParserListener stLis = new StaccatoParserListener();
	        midParse.addParserListener(stLis);
	        parser.parse(new File(TestPlay.class.getClassLoader().getResource("midi/Capricho.xml").toURI()));
	        midParse.parse(midiListener.getSequence());
	        Pattern staccatoPattern = stLis.getPattern();
	       
//	            ChordProgression cp = new ChordProgression("D+A+D");
//
//	            Chord[] chords = cp.getChords();
//	            
//	            for (Chord chord : chords) {
//	              System.out.print("Chord "+chord+" has these notes: ");
//	              Note[] notes = chord.getNotes();
//	              for (Note note : notes) {
//	                System.out.print(note+" ");
//	              }
//	              System.out.println();
//	            }
	        
	        Rhythm rhythm = new Rhythm();
//	        rhythm.addLayer("O.OO...O.OO....O");
	        rhythm.addLayer("oo..o....oo...");
	        rhythm.addLayer("^.`.^.`.^.`.^.`.");
	       
	            Player player = new Player();
	            
	            player.play(rhythm.getPattern().repeat(4));
//	           stLis.getPattern().setTempo(230); 
//		        player.play(stLis.getPattern());
		      
	            
//	            System.out.println(listener.getSequence().getTickLength());
	        System.out.println(rhythm.getPattern());
//	        Player player = new Player();
//	        player.play(staccatoPattern);
	    }
	

}

