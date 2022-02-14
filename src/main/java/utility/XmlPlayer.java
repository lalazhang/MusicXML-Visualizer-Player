package utility;


import org.jfugue.integration.MusicXmlParser;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;
import org.staccato.StaccatoParserListener;


public class XmlPlayer {
	/**
	 *String to store xml string representation of score
	 */
	private String xmlString;
	/**
	 * MusicXmlParser object to parse xml string 
	 */
	private MusicXmlParser parser;
	/**
	 * Listener object to help with playing function
	 */
	private StaccatoParserListener listener;
	/**
	 * Pattern to help with playing function
	 */
	private Pattern staccatoPattern;
	/**
	 * Player object to play music
	 */
	private Player player;

	/**
	 * Constructor takes xml output as string, creates new instances of our
	 * fields and calls update method
	 * 
	 * @param str
	 * @throws Exception
	 */
	public XmlPlayer(String str) throws Exception {
		xmlString = str;
		parser = new MusicXmlParser();
		listener = new StaccatoParserListener();
		staccatoPattern = new Pattern();
		player = new Player();
		update();
	}

	/**
	 * Helper method to add listener to parser, parse given xml string and create
	 * pattern object from listener
	 * 
	 * @throws Exception
	 */
	private void update() throws Exception {
		// TODO Auto-generated method stub
		parser.addParserListener(listener);
		parser.parse(xmlString);
		staccatoPattern = listener.getPattern();
	}

	/**
	 * Play music method Plays the music from xml string
	 * starting from beginning if the playback hasn't started
	 * 
	 * @throws Exception
	 */
	public void play() throws Exception {
		// If playback has never been started, start playback
		if (!player.getManagedPlayer().isStarted()) {
			player.getManagedPlayer().start(player.getSequence(staccatoPattern));
		}
		// Otherwise resume playback
		else {
			player.getManagedPlayer().resume();
		}

	}

	/**
	 * pauses music playback
	 */
	public void pause() {
		player.getManagedPlayer().pause();
	}
	/**
	 * unimplemented set volume method to change the volume of the playback
	 * @param vol
	 */
	public void setVolume(int vol) {

	}
	/**
	 * Return instance of ManagedPlayer Object of player
	 * 
	 * @return
	 */
	public ManagedPlayer getManagedPlayer() {
		return this.player.getManagedPlayer();
	} 

}
