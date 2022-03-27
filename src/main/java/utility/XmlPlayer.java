package utility;

import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import org.jfugue.integration.MusicXmlParser;
import org.jfugue.midi.MidiParser;
import org.jfugue.midi.MidiParserListener;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;
import org.staccato.StaccatoParserListener;
import java.util.concurrent.TimeUnit;
import GUI.MainViewController;
import javafx.util.Duration;
import models.Part;
import models.ScorePartwise;
import models.measure.Measure;
import models.measure.note.Note;

/*
 * FIXME inspect and add null checking in methods
 */
public class XmlPlayer {
	/**
	 * MainViewController object to store parent mvc instance
	 */
	private MainViewController mvc;
	/**
	 * String to store score instrument
	 */
	public String inst;
	/**
	 * String to store xml string representation of score
	 */
	private String xmlString;
	/**
	 * MusicXmlParser object to parse xml string
	 */
	private MusicXmlParser xmlParser;
	/**
	 * MidiParser object to parse midi string
	 */
	private MidiParser midiParser;
	/**
	 * Listener object to help with playing function
	 */
	private StaccatoParserListener stacListener;
	/**
	 * Listener object for drumset playback
	 */
	private StaccatoParserListener drumStacListener;
	/**
	 * MidiListener object to help with playing function
	 */
	private MidiParserListener midiListener;
	/**
	 * Pattern to help with playing guitar playback
	 */
	private Pattern staccatoPattern;
	/**
	 * Pattern to help with playing drumset
	 */
	private Pattern drumPattern;
	/**
	 * Pattern to help with tracking drumset progress
	 */
	private Pattern trackDrumPattern;
	/**
	 * Player object to play guitar music
	 */
	private Player guPlayer;
	/**
	 * Player object to play music
	 */
	private Player dsPlayer;
	/*
	 * Score partwise object to
	 */
	private ScorePartwise score;
	private boolean playing;

	/**
	 * Constructor takes mainview object and xml output as string, instantiates our
	 * fields and calls update method
	 * 
	 * @param str
	 * @throws Exception
	 */
	public XmlPlayer(MainViewController mvcInput, String str) throws Exception {
		mvc = mvcInput;
		score = mvc.converter.getScore().getModel();
		xmlString = str;
		xmlParser = new MusicXmlParser();
		stacListener = new StaccatoParserListener();
		staccatoPattern = new Pattern();
		drumPattern = new Pattern();
		playing = false;
		midiListener = new MidiParserListener();
		drumStacListener = new StaccatoParserListener();
		midiParser = new MidiParser();
		guPlayer = new Player();
		dsPlayer = new Player();
		inst = score.getPartList().getScoreParts().get(0).getPartName();
		xmlParser.addParserListener(midiListener);
		xmlParser.addParserListener(drumStacListener);
		xmlParser.parse(xmlString);
		midiParser.addParserListener(stacListener);
		midiParser.parse(midiListener.getSequence());
		update(120);
	}

	/**
	 * Helper method to add listener to parser, parse given xml string and create
	 * pattern object from listener based on instrument
	 * 
	 * @throws Exception
	 */
	private void update(int tempo) throws Exception {
		// TODO Auto-generated method stub

		if (this.inst.equals("Guitar")) {
			staccatoPattern = stacListener.getPattern().setTempo(tempo).setInstrument(24);
		} else {
			trackDrumPattern = drumStacListener.getPattern().setTempo(tempo);
			drumPattern = getDrumPattern(score).setTempo(tempo);

//			System.out.println(staccatoPattern.getPattern());
		}
	}

	/**
	 * Play music method Plays the music from a sequence starting from beginning if
	 * the playback hasn't started
	 * 
	 * @throws Exception
	 */
	public void play() throws Exception {

		if (this.inst.equals("Guitar")) {
			if (!guPlayer.getManagedPlayer().isStarted()) {

				guPlayer.getManagedPlayer().start(guPlayer.getSequence(staccatoPattern));
				playing = true;
			}

			else {
				if (guPlayer.getManagedPlayer().isFinished()) {
					guPlayer.getManagedPlayer().start(guPlayer.getSequence(staccatoPattern));
					playing = true;
				} else {
					guPlayer.getManagedPlayer().resume();
					playing = true;
				}
			}

		} else {
			if (!dsPlayer.getManagedPlayer().isStarted()) {

				dsPlayer.getManagedPlayer().start(dsPlayer.getSequence(drumPattern));
				playing = true;
			}

			else {
				if (dsPlayer.getManagedPlayer().isFinished()) {
					dsPlayer.getManagedPlayer().start(dsPlayer.getSequence(drumPattern));
					playing = true;
				} else {
					dsPlayer.getManagedPlayer().resume();
					playing = true;
				}
			}
		}
	}

	/**
	 * pauses music playback
	 */
	public void pause() {
		if (this.inst.equals("Guitar")) {
			guPlayer.getManagedPlayer().pause();
			playing = false;
		} else {
			dsPlayer.getManagedPlayer().pause();
			playing = false;
		}

	}

	/**
	 * Given a time from start to end (0.0-1.0) of the track, this method moves the
	 * audio playback to the specified time
	 * 
	 * @param time
	 */
	public void seek(float time) {
		if (this.inst.equals("Guitar")) {
			long tk = guPlayer.getManagedPlayer().getTickLength();
			guPlayer.getManagedPlayer().seek((long) (time * tk));
		} else {
			long tk = dsPlayer.getManagedPlayer().getTickLength();
			dsPlayer.getManagedPlayer().seek((long) (time * tk));
		}
	}

	/**
	 * set tempo method to change the tempo of the playback
	 * 
	 * @param temp
	 * @throws Exception
	 * 
	 */
	public void setTempo(float temp) throws Exception {
		update((int) (temp));
		if (this.inst.equals("Guitar")) {
			if (guPlayer.getManagedPlayer().isPlaying()) {
				long pos = guPlayer.getManagedPlayer().getTickPosition();
				long dur = guPlayer.getManagedPlayer().getTickLength();
				double ratio = (double) pos / (double) dur;
				guPlayer.getManagedPlayer().finish();
				guPlayer.getManagedPlayer().start(guPlayer.getSequence(staccatoPattern));
				guPlayer.getManagedPlayer().seek((long) (ratio * guPlayer.getManagedPlayer().getTickLength()));
			}
		} else {

			if (dsPlayer.getManagedPlayer().isPlaying()) {

				long pos = dsPlayer.getManagedPlayer().getTickPosition();
				long dur = dsPlayer.getManagedPlayer().getTickLength();
				double ratio = (double) pos / (double) dur;

				Player mp1 = new Player();
				mp1.getManagedPlayer().start(mp1.getSequence(drumPattern));

				mp1.getManagedPlayer().seek((long) (ratio * dsPlayer.getManagedPlayer().getTickLength()));
				dsPlayer = mp1;

				play();

			}
		}

	}

	/**
	 * get tempo method to get the tempo of the playback
	 * 
	 * @param vol
	 */
	public int getTempo() {
		int temp = 120;
		if (this.inst.equals("Guitar")) {
			temp = Integer.valueOf(staccatoPattern.getTokens().get(0).toString().substring(1));
//		System.out.println(staccatoPattern.getTokens());
		} else {
			temp = Integer.valueOf(drumPattern.getTokens().get(0).toString().substring(1));
		}
		return temp;

	}

	/**
	 * Method to get the sequences duration in the format HH:MM:SS
	 * 
	 * @return
	 */
	public String getDuration() {
		int hours = 0;
		int minutes = 0;
		int seconds = 0;

		if (this.inst.equals("Guitar")) {
			Duration time = new Duration(guPlayer.getSequence(staccatoPattern).getMicrosecondLength() / 1000);
			hours = (int) time.toHours();
			minutes = (int) time.toMinutes();
			seconds = (int) time.toSeconds();
		} else {

			Duration time = new Duration(dsPlayer.getSequence(trackDrumPattern).getMicrosecondLength() / 1000);
			hours = (int) time.toHours();
			minutes = (int) time.toMinutes();
			seconds = (int) time.toSeconds();
		}

//		int hours = (int) time.toHours();
//		int minutes = (int) time.toMinutes();
//		int seconds = (int) time.toSeconds();

		// Fix the issue with the timer going to 61 and above for seconds, minutes, and
		// hours
		if (seconds > 59)
			seconds = seconds % 60;
		if (minutes > 59)
			minutes = minutes % 60;
		if (hours > 59)
			hours = hours % 60;

		// Don't show the hours unless the song is for an hour or longer
		if (hours > 0)
			return String.format("%d:%02d:%02d", hours, minutes, seconds);
		else
			return String.format("%02d:%02d", minutes, seconds);

	}

	/**
	 * Method to get the sequences current in the format HH:MM:SS
	 * 
	 * @return
	 */
	public String getCurTime() {
		double totUS = 0.0;
		double cur = 0.0;
		if (this.inst.equals("Guitar")) {
			totUS = (double) (guPlayer.getSequence(staccatoPattern).getMicrosecondLength());
			cur = totUS * (double) guPlayer.getManagedPlayer().getTickPosition()
					/ guPlayer.getManagedPlayer().getTickLength();
		} else {
			totUS = (double) (dsPlayer.getSequence(trackDrumPattern).getMicrosecondLength());
			cur = totUS * (double) dsPlayer.getManagedPlayer().getTickPosition()
					/ dsPlayer.getManagedPlayer().getTickLength();
		}

		Duration time = new Duration(cur / 1000);

		int hours = (int) time.toHours();
		int minutes = (int) time.toMinutes();
		int seconds = (int) time.toSeconds();

		// Fix the issue with the timer going to 61 and above for seconds, minutes, and
		// hours
		if (seconds > 59)
			seconds = seconds % 60;
		if (minutes > 59)
			minutes = minutes % 60;
		if (hours > 59)
			hours = hours % 60;

		// Don't show the hours unless the song is for an hour or longer
		if (hours > 0)
			return String.format("%d:%02d:%02d", hours, minutes, seconds);
		else
			return String.format("%02d:%02d", minutes, seconds);

	}

	/**
	 * Method to get the drum pattern from the mainview input
	 * 
	 * @param mvcInput
	 * @return Pattern for the drumset tab given in mvcinput
	 */
	public Pattern getDrumPattern(ScorePartwise sc) {
		Pattern drumPattern = new Pattern();
		StringBuilder drumString = new StringBuilder();
//		int partCount = 0;
		for (Measure meas : sc.getParts().get(0).getMeasures()) {
			if (meas.getNotesBeforeBackup() != null) {
				for (Note note : meas.getNotesBeforeBackup()) {
//						if (drumString.length()==0) {
//							drumString.append(getNoteDetails(note));
//							drumString.append("+");
//							
//						}
					if (note.getChord() == null && drumString.length() > 0
							&& drumString.charAt(drumString.length() - 1) == '+') {
						drumString.deleteCharAt(drumString.length() - 1);
						drumString.append(" ");

					}
					if (note.getChord() != null && drumString.length() > 0
							&& drumString.charAt(drumString.length() - 1) == '+') {
						// System.out.println(musicString.toString());

						drumString.append("[" + getDrumName(note.getInstrument().getId()) + "] ");

					} else {
						drumString.append(getNoteDetails(note));
					}

					if (note.getRest() != null) {
						drumString.append("R");
						drumString.append(getNoteDuration(note));
					} else {
						drumString.deleteCharAt(drumString.length() - 1);
						drumString.append(getNoteDuration(note));
					}
					// musicString.append(getDots(note));
					// addTies(musicString, note);

					drumString.append(" ");

					if (note != meas.getNotesBeforeBackup().get(meas.getNotesBeforeBackup().size() - 1)) {
						// System.out.println(musicString.charAt(musicString.length() - 1));
						drumString.deleteCharAt(drumString.length() - 1);
						drumString.append("+");
					}
				}
			}
		}
		drumPattern.add(drumString.toString());

		return drumPattern;

	}

	public String getNoteDetails(Note note) {
		StringBuilder noteDetails = new StringBuilder();
		String instrument;
		if (note.getUnpitched() != null) {
			instrument = "[" + getDrumName(note.getInstrument().getId()) + "]";
			noteDetails.append("V9" + " " + instrument + " ");
		}
		return noteDetails.toString();
	}

	public String getDrumName(String InstrumentId) {
		if (InstrumentId.equals("P1-I47")) {
			return "OPEN_HI_HAT";
		} else if (InstrumentId.equals("P1-I52")) {
			return "RIDE_CYMBAL_1";
		} else if (InstrumentId.equals("P1-I53")) {
			return "CHINESE_CYMBAL";
		} else if (InstrumentId.equals("P1-I43")) {
			return "CLOSED_HI_HAT";
		} else if (InstrumentId.equals("P1-I46")) {
			return "LO_TOM";
		} else if (InstrumentId.equals("P1-I44")) {
			return "HIGH_FLOOR_TOM";
		} else if (InstrumentId.equals("P1-I54")) {
			return "RIDE_BELL";
		} else if (InstrumentId.equals("P1-I36")) {
			return "BASS_DRUM";
		} else if (InstrumentId.equals("P1-I50")) {
			return "CRASH_CYMBAL_1";
		} else if (InstrumentId.equals("P1-I39")) {
			return "ACOUSTIC_SNARE";
		} else if (InstrumentId.equals("P1-I42")) {
			return "LO_FLOOR_TOM";
		} else if (InstrumentId.equals("P1-I48")) {
			return "LO_MID_TOM";
		} else if (InstrumentId.equals("P1-I45")) {
			return "PEDAL_HI_HAT";
		}

		else {
			return "ACOUSTIC_SNARE";
		} // default
	}

	public char getNoteDuration(Note note) {
		if (note.getType() != null) {
			if (note.getType().equals("whole")) {
				return 'w';
			} else if (note.getType().equals("half")) {
				return 'h';
			} else if (note.getType().equals("quarter")) {
				return 'q';
			} else if (note.getType().equals("eighth")) {
				return 'i';
			} else if (note.getType().equals("16th")) {
				return 's';
			} else if (note.getType().equals("32nd")) {
				return 't';
			} else if (note.getType().equals("64th")) {
				return 'x';
			} else if (note.getType().equals("128th")) {
				return 'o';
			} else {
				return 'q';
			}
		} else {
			return 'q';
		}
	}

	/**
	 * Return instance of ManagedPlayer Object of player
	 * 
	 * @return
	 */
	public ManagedPlayer getManagedPlayer() {
		if (this.inst.equals("Guitar")) {
			return this.guPlayer.getManagedPlayer();
		} else {
			return this.dsPlayer.getManagedPlayer();
		}
	}

//	ScorePartwise sp = converter.getScore().getModel(); PartList pl = sp.getPartList(); pl.getScoreParts().get(0).getPartName();

}
