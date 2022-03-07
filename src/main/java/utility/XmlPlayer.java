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
	 * Player object to play music
	 */
	private Player player;

	/**
	 * Constructor takes mainview object and xml output as string, instantiates our
	 * fields and calls update method
	 * 
	 * @param str
	 * @throws Exception
	 */
	public XmlPlayer(MainViewController mvcInput, String str) throws Exception {
		mvc = mvcInput;
		xmlString = str;
		xmlParser = new MusicXmlParser();
		stacListener = new StaccatoParserListener();
		staccatoPattern = new Pattern();
		drumPattern = new Pattern(getDrumPattern(mvc));
		trackDrumPattern = new Pattern();
		midiListener = new MidiParserListener();
		drumStacListener = new StaccatoParserListener();
		midiParser = new MidiParser();
		player = new Player();
		inst = mvc.converter.getScore().getModel().getPartList().getScoreParts().get(0).getPartName();
		update();
	}

	/**
	 * Helper method to add listener to parser, parse given xml string and create
	 * pattern object from listener based on instrument
	 * 
	 * @throws Exception
	 */
	private void update() throws Exception {
		// TODO Auto-generated method stub
		xmlParser.addParserListener(midiListener);
		xmlParser.addParserListener(drumStacListener);
		xmlParser.parse(xmlString);
		midiParser.addParserListener(stacListener);
		midiParser.parse(midiListener.getSequence());

		if (this.inst.equals("Guitar")) {
			staccatoPattern = stacListener.getPattern().setTempo(120).setInstrument(24);
		} else {
			trackDrumPattern = drumStacListener.getPattern().setTempo(120);
			staccatoPattern = drumPattern.setTempo(120);
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
		// If playback has never been started, start playback
		if (!player.getManagedPlayer().isStarted()) {
			player.getManagedPlayer().start(player.getSequence(staccatoPattern));
		}
		// Otherwise resume playback
		else {
			if (player.getManagedPlayer().isFinished()) {
				player.getManagedPlayer().start(player.getSequence(staccatoPattern));
			} else {
				player.getManagedPlayer().resume();
			}
		}

	}

	/**
	 * pauses music playback
	 */
	public void pause() {
		player.getManagedPlayer().pause();
	}

	/**
	 * Given a time from start to end (0.0-1.0) of the track, this method moves the
	 * audio playback to the specified time
	 * 
	 * @param time
	 */
	public void seek(float time) {
		long tk = player.getManagedPlayer().getTickLength();
		player.getManagedPlayer().seek((long) (time * tk));
	}

	/**
	 * set tempo method to change the tempo of the playback
	 * 
	 * @param temp
	 * @throws Exception
	 * 
	 */
	public void setTempo(float temp) throws Exception {
		staccatoPattern.setTempo((int) (temp));
		if (player.getManagedPlayer().isPlaying()) {
			long pos = player.getManagedPlayer().getTickPosition();
			long dur = player.getManagedPlayer().getTickLength();
			double ratio = (double) pos / (double) dur;
			player.getManagedPlayer().finish();
			player.getManagedPlayer().start(player.getSequence(staccatoPattern));
			player.getManagedPlayer().seek((long) (ratio * player.getManagedPlayer().getTickLength()));
		}
	}

	/**
	 * get tempo method to get the tempo of the playback
	 * 
	 * @param vol
	 */
	public int getTempo() {
		int temp = Integer.valueOf(staccatoPattern.getTokens().get(0).toString().substring(1));
//		System.out.println(staccatoPattern.getTokens());
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
			Duration time = new Duration(player.getSequence(staccatoPattern).getMicrosecondLength() / 1000);
			hours = (int) time.toHours();
			minutes = (int) time.toMinutes();
			seconds = (int) time.toSeconds();
		} else {

			Duration time = new Duration(player.getSequence(trackDrumPattern).getMicrosecondLength() / 1000);
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
		if (this.inst.equals("Guitar")) {
			totUS = (double) (player.getSequence(staccatoPattern).getMicrosecondLength());
		} else {
			totUS = (double) (player.getSequence(trackDrumPattern).getMicrosecondLength());
		}
		double cur = totUS * (double) player.getManagedPlayer().getTickPosition()
				/ player.getManagedPlayer().getTickLength();
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
	public Pattern getDrumPattern(MainViewController mvcInput) {
		// Custom rhythm kit
		Map<Character, String> newRhythmKit = new HashMap<Character, String>();
		newRhythmKit.put('.', "Ri");
		newRhythmKit.put('O', "[BASS_DRUM]i");
		newRhythmKit.put('o', "Rs [BASS_DRUM]s");
		newRhythmKit.put('S', "[ACOUSTIC_SNARE]i");
		newRhythmKit.put('s', "Rs [ACOUSTIC_SNARE]s");
		newRhythmKit.put('^', "[PEDAL_HI_HAT]i");
		newRhythmKit.put('`', "[PEDAL_HI_HAT]s Rs");
		newRhythmKit.put('*', "[CRASH_CYMBAL_1]i");
		newRhythmKit.put('+', "[CRASH_CYMBAL_1]s Rs");
		newRhythmKit.put('X', "[HAND_CLAP]i");
		newRhythmKit.put('x', "Rs [HAND_CLAP]s");
		newRhythmKit.put(' ', "Ri");
		newRhythmKit.put('>', "[CLOSED_HI_HAT]i");
		newRhythmKit.put('<', "[OPEN_HI_HAT]i");
		newRhythmKit.put('-', "[RIDE_CYMBAL_1]i");
		newRhythmKit.put(':', "[LO_MID_TOM]i");
		newRhythmKit.put(';', "[LO_TOM]i");
		newRhythmKit.put(',', "[HIGH_FLOOR_TOM]i");
		newRhythmKit.put('_', "[LO_FLOOR_TOM]i");
		// Object to store entire drum pattern
		Pattern drumPattern = new Pattern();
		// Iterate over the measures in the tab
		for (int i = 0; i < mvcInput.converter.getScore().getMeasureList().size(); i++) {

			// Create new rhythm per measure and set rhythm kit
			Rhythm rhythm = new Rhythm();
			rhythm.setRhythmKit(newRhythmKit);
			for (int j = 0; j < mvcInput.converter.getScore().getMeasureList().get(i).tabStringList.size(); j++) {
				// build rhythm
				String note = mvcInput.converter.getScore().getMeasureList().get(i).tabStringList.get(j).name;
				String line = mvcInput.converter.getScore().getMeasureList().get(i).tabStringList.get(j).line;
//				System.out.println(note + "l");
//				System.out.println(line + "l");
				/**
				 * State machine to iterate through lines of all measures in a drum tab and
				 * build drumset pattern from them TODO confirm types of instrument strikes
				 * match sounds such as g(grace), f and d
				 */

				switch (note) {
				case ("C "):// Crash cymbal
					// layer string to build layers for rhythm
					String cymLayer = "";
					/**
					 * Iterate though line array and build layer string based on characters in line
					 */
					for (char c : line.toCharArray()) {
						switch (c) {
						case ('-'):
							cymLayer = cymLayer + ".";
							break;
						case ('X'):
							cymLayer = cymLayer + "*";
							break;
						case ('r'):
							cymLayer = cymLayer + ".";
							break;
						case ('s'):
							break;
						case ('x'):
							cymLayer = cymLayer + "*";
							break;
						default:
							cymLayer = cymLayer + ".";
							break;
						}

					}
					rhythm.addLayer(cymLayer);
					break;

				case ("H "):// Closed hi-hat
					// layer string to build layers for rhythm
					String hhLayer = "";
					/**
					 * Iterate though line array and build layer string based on characters in line
					 */
					for (char c : line.toCharArray()) {
						switch (c) {
						case ('-'):
							hhLayer = hhLayer + ".";
							break;
						case ('x'):
							hhLayer = hhLayer + ">";
							break;
						case ('d'):
							hhLayer = hhLayer + ">";
							break;
						case ('o'):
							hhLayer = hhLayer + "<";
							break;
						case ('O'):
							hhLayer = hhLayer + "<";
							break;
						case ('X'):
							hhLayer = hhLayer + ">";
							break;
						default:
							hhLayer = hhLayer + ".";
							break;
						}
					}
					rhythm.addLayer(hhLayer);
					break;

				case ("R "):// Ride cymbal
					// layer string to build layers for rhythm
					String rcLayer = "";
					/**
					 * Iterate though line array and build layer string based on characters in line
					 */
					for (char c : line.toCharArray()) {
						switch (c) {
						case ('-'):
							rcLayer = rcLayer + ".";
							break;
						case ('x'):
							rcLayer = rcLayer + "-";
							break;
						case ('g'):
							rcLayer = rcLayer + "-";
							break;
						case ('X'):
							rcLayer = rcLayer + "-";
							break;
						default:
							rcLayer = rcLayer + ".";
							break;
						}
					}
					rhythm.addLayer(rcLayer);
					break;

				case ("S "):// Acoustic snare
					// layer string to build layers for rhythm
					String sLayer = "";
					/**
					 * Iterate though line array and build layer string based on characters in line
					 */
					for (char c : line.toCharArray()) {
						switch (c) {
						case ('-'):
							sLayer = sLayer + ".";
							break;
						case ('o'):
							sLayer = sLayer + "s";
							break;
						case ('f'):
							sLayer = sLayer + "s";
							break;
						case ('O'):
							sLayer = sLayer + "S";
							break;
						case ('g'):
							sLayer = sLayer + "s";
							break;
						case ('d'):
							sLayer = sLayer + "s";
							break;
						case ('r'):
							sLayer = sLayer + ".";
							break;
						default:
							sLayer = sLayer + ".";
							break;
						}
					}
					rhythm.addLayer(sLayer);
					break;

				case ("t "):// Low mid tom
					// layer string to build layers for rhythm
					String lmLayer = "";
					/**
					 * Iterate though line array and build layer string based on characters in line
					 */
					for (char c : line.toCharArray()) {
						switch (c) {
						case ('-'):
							lmLayer = lmLayer + ".";
							break;
						case ('o'):
							lmLayer = lmLayer + ":";
							break;
						case ('f'):
							lmLayer = lmLayer + ":";
							break;
						case ('O'):
							lmLayer = lmLayer + ":";
							break;
						case ('g'):
							lmLayer = lmLayer + ":";
							break;
						case ('d'):
							lmLayer = lmLayer + ":";
							break;
						case ('r'):
							lmLayer = lmLayer + ".";
							break;
						default:
							lmLayer = lmLayer + ".";
							break;
						}
					}
					rhythm.addLayer(lmLayer);
					break;

				case ("T "):// Low tom
					// layer string to build layers for rhythm
					String ltLayer = "";
					/**
					 * Iterate though line array and build layer string based on characters in line
					 */
					for (char c : line.toCharArray()) {
						switch (c) {
						case ('-'):
							ltLayer = ltLayer + ".";
							break;
						case ('o'):
							ltLayer = ltLayer + ";";
							break;
						case ('f'):
							ltLayer = ltLayer + ";";
							break;
						case ('O'):
							ltLayer = ltLayer + ";";
							break;
						case ('g'):
							ltLayer = ltLayer + ";";
							break;
						case ('d'):
							ltLayer = ltLayer + ";";
							break;
						case ('r'):
							ltLayer = ltLayer + ".";
							break;
						default:
							ltLayer = ltLayer + ".";
							break;
						}
					}
					rhythm.addLayer(ltLayer);
					break;
				case ("f "):// High floor tom
					// layer string to build layers for rhythm
					String fhLayer = "";
					/**
					 * Iterate though line array and build layer string based on characters in line
					 */
					for (char c : line.toCharArray()) {
						switch (c) {
						case ('-'):
							fhLayer = fhLayer + ".";
							break;
						case ('o'):
							fhLayer = fhLayer + ",";
							break;
						case ('f'):
							fhLayer = fhLayer + ",";
							break;
						case ('O'):
							fhLayer = fhLayer + ",";
							break;
						case ('g'):
							fhLayer = fhLayer + ",";
							break;
						case ('d'):
							fhLayer = fhLayer + ",";
							break;
						case ('r'):
							fhLayer = fhLayer + ".";
							break;
						default:
							fhLayer = fhLayer + ".";
							break;
						}
					}
					rhythm.addLayer(fhLayer);
					break;
				case ("F "):// Low floor tom
					// layer string to build layers for rhythm
					String flLayer = "";
					/**
					 * Iterate though line array and build layer string based on characters in line
					 */
					for (char c : line.toCharArray()) {
						switch (c) {
						case ('-'):
							flLayer = flLayer + ".";
							break;
						case ('o'):
							flLayer = flLayer + "_";
							break;
						case ('f'):
							flLayer = flLayer + "_";
							break;
						case ('O'):
							flLayer = flLayer + "_";
							break;
						case ('g'):
							flLayer = flLayer + "_";
							break;
						case ('d'):
							flLayer = flLayer + "_";
							break;
						case ('r'):
							flLayer = flLayer + ".";
							break;
						default:
							flLayer = flLayer + ".";
							break;
						}
					}
					rhythm.addLayer(flLayer);
					break;

				case ("B "):// Bass drum
					// layer string to build layers for rhythm
					String bdLayer = "";
					/**
					 * Iterate though line array and build layer string based on characters in line
					 */
					for (char c : line.toCharArray()) {
						switch (c) {
						case ('-'):
							bdLayer = bdLayer + ".";
							break;
						case ('o'):
							bdLayer = bdLayer + "o";
							break;
						case ('f'):
							bdLayer = bdLayer + "o";
							break;
						case ('O'):
							bdLayer = bdLayer + "O";
							break;
						case ('g'):
							bdLayer = bdLayer + "o";
							break;
						case ('d'):
							bdLayer = bdLayer + "o";
							break;
						case ('r'):
							bdLayer = bdLayer + ".";
							break;
						default:
							bdLayer = bdLayer + ".";
							break;
						}
					}
					rhythm.addLayer(bdLayer);
					break;
				case ("Hf"):// Pedal Hi-hat
					// layer string to build layers for rhythm
					String phLayer = "";
					/**
					 * Iterate though line array and build layer string based on characters in line
					 * 
					 */
					for (char c : line.toCharArray()) {
						switch (c) {
						case ('-'):
							phLayer = phLayer + ".";
							break;
						case ('x'):
							phLayer = phLayer + "^";
							break;
						case ('X'):
							phLayer = phLayer + "`";
							break;
						case ('r'):
							phLayer = phLayer + ".";
							break;
						default:
							phLayer = phLayer + ".";
							break;
						}
					}
					rhythm.addLayer(phLayer);
					break;
				default:
					if (note.equals("CC")) {
						// layer string to build layers for rhythm
						String cLayer = "";
						/**
						 * Iterate though line array and build layer string based on characters in line
						 */
						for (char c : line.toCharArray()) {
							switch (c) {
							case ('-'):
								cLayer = cLayer + ".";
								break;
							case ('X'):
								cLayer = cLayer + "*";
								break;
							case ('r'):
								cLayer = cLayer + ".";
								break;
							case ('s'):
								break;
							case ('x'):
								cLayer = cLayer + "*";
								break;
							default:
								cLayer = cLayer + ".";
								break;
							}

						}
						rhythm.addLayer(cLayer);

					} else if (note.equals("HH")) {
						// layer string to build layers for rhythm
						String hLayer = "";
						/**
						 * Iterate though line array and build layer string based on characters in line
						 */
						for (char c : line.toCharArray()) {
							switch (c) {
							case ('-'):
								hLayer = hLayer + ".";
								break;
							case ('x'):
								hLayer = hLayer + ">";
								break;
							case ('d'):
								hLayer = hLayer + ">";
								break;
							case ('o'):
								hLayer = hLayer + "<";
								break;
							case ('O'):
								hLayer = hLayer + "<";
								break;
							case ('X'):
								hLayer = hLayer + ">";
								break;
							default:
								hLayer = hLayer + ".";
								break;
							}
						}
						rhythm.addLayer(hLayer);

					} else if (note.equals("SD")) {
						// layer string to build layers for rhythm
						String sDLayer = "";
						/**
						 * Iterate though line array and build layer string based on characters in line
						 */
						for (char c : line.toCharArray()) {
							switch (c) {
							case ('-'):
								sDLayer = sDLayer + ".";
								break;
							case ('o'):
								sDLayer = sDLayer + "s";
								break;
							case ('f'):
								sDLayer = sDLayer + "s";
								break;
							case ('O'):
								sDLayer = sDLayer + "S";
								break;
							case ('g'):
								sDLayer = sDLayer + "s";
								break;
							case ('d'):
								sDLayer = sDLayer + "s";
								break;
							case ('r'):
								sDLayer = sDLayer + ".";
								break;
							default:
								sDLayer = sDLayer + ".";
								break;
							}
						}
						rhythm.addLayer(sDLayer);

					}else if (note.equals("HT")) {
						// layer string to build layers for rhythm
						String fhTLayer = "";
						/**
						 * Iterate though line array and build layer string based on characters in line
						 */
						for (char c : line.toCharArray()) {
							switch (c) {
							case ('-'):
								fhTLayer = fhTLayer + ".";
								break;
							case ('o'):
								fhTLayer = fhTLayer + ",";
								break;
							case ('f'):
								fhTLayer = fhTLayer + ",";
								break;
							case ('O'):
								fhTLayer = fhTLayer + ",";
								break;
							case ('g'):
								fhTLayer = fhTLayer + ",";
								break;
							case ('d'):
								fhTLayer = fhTLayer + ",";
								break;
							case ('r'):
								fhTLayer = fhTLayer + ".";
								break;
							default:
								fhTLayer = fhTLayer + ".";
								break;
							}
						}
						rhythm.addLayer(fhTLayer);
					}
					else if (note.equals("MT")) {
						// layer string to build layers for rhythm
						String fMTLayer = "";
						/**
						 * Iterate though line array and build layer string based on characters in line
						 */
						for (char c : line.toCharArray()) {
							switch (c) {
							case ('-'):
								fMTLayer = fMTLayer + ".";
								break;
							case ('o'):
								fMTLayer = fMTLayer + "_";
								break;
							case ('f'):
								fMTLayer = fMTLayer + "_";
								break;
							case ('O'):
								fMTLayer = fMTLayer + "_";
								break;
							case ('g'):
								fMTLayer = fMTLayer + "_";
								break;
							case ('d'):
								fMTLayer = fMTLayer + "_";
								break;
							case ('r'):
								fMTLayer = fMTLayer + ".";
								break;
							default:
								fMTLayer = fMTLayer + ".";
								break;
							}
						}
						rhythm.addLayer(fMTLayer);
					}
					else if (note.equals("BD")) {
						// layer string to build layers for rhythm
						String bLayer = "";
						/**
						 * Iterate though line array and build layer string based on characters in line
						 */
						for (char c : line.toCharArray()) {
							switch (c) {
							case ('-'):
								bLayer = bLayer + ".";
								break;
							case ('o'):
								bLayer = bLayer + "o";
								break;
							case ('f'):
								bLayer = bLayer + "o";
								break;
							case ('O'):
								bLayer = bLayer + "O";
								break;
							case ('g'):
								bLayer = bLayer + "o";
								break;
							case ('d'):
								bLayer = bLayer + "o";
								break;
							case ('r'):
								bLayer = bLayer + ".";
								break;
							default:
								bLayer = bLayer + ".";
								break;
							}
						}
						rhythm.addLayer(bLayer);

					}
					else if (note.equals("FT")) {
						// layer string to build layers for rhythm
						String lFTLayer = "";
						/**
						 * Iterate though line array and build layer string based on characters in line
						 */
						for (char c : line.toCharArray()) {
							switch (c) {
							case ('-'):
								lFTLayer = lFTLayer + ".";
								break;
							case ('o'):
								lFTLayer = lFTLayer + ":";
								break;
							case ('f'):
								lFTLayer = lFTLayer + ":";
								break;
							case ('O'):
								lFTLayer = lFTLayer + ":";
								break;
							case ('g'):
								lFTLayer = lFTLayer + ":";
								break;
							case ('d'):
								lFTLayer = lFTLayer + ":";
								break;
							case ('r'):
								lFTLayer = lFTLayer + ".";
								break;
							default:
								lFTLayer = lFTLayer + ".";
								break;
							}
						}
						rhythm.addLayer(lFTLayer);
					}
					break;

				}

			}
			// build drum pattern
			if (i > 0) {

				drumPattern = drumPattern.add("|" + rhythm.getPattern().toString().substring(2));

			} else {
				drumPattern = rhythm.getPattern();
			}

		}
		// FIXME fix drum pattern timing

		return drumPattern;

	}

	/**
	 * Return instance of ManagedPlayer Object of player
	 * 
	 * @return
	 */
	public ManagedPlayer getManagedPlayer() {
		return this.player.getManagedPlayer();
	}

//	ScorePartwise sp = converter.getScore().getModel(); PartList pl = sp.getPartList(); pl.getScoreParts().get(0).getPartName();

}
