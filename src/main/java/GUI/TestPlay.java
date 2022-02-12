package GUI;


import java.util.Arrays;
import java.util.List;




import org.jfugue.player.Player;
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
	        
	        StaccatoParserListener listener = new StaccatoParserListener();
	        parser.addParserListener(listener);     
	        parser.parse(new File(TestPlay.class.getClassLoader().getResource("midi/Capricho.xml").toURI()));
	        Pattern staccatoPattern = listener.getPattern();
	        System.out.println(staccatoPattern);
	        Player player = new Player();
	        player.play(staccatoPattern);
	    }
	

}

