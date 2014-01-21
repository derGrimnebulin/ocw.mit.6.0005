package piano;

import javax.sound.midi.MidiUnavailableException;

import midi.Instrument;
import midi.Midi;
import music.Pitch;

public class PianoMachine {
	
	private Midi midi;
	public boolean Recording = false; //default
	public int OCTAVE = 0; //Must be in [-24,24].
  public Instrument CURRENT_INSTRUMENT = Midi.DEFAULT_INSTRUMENT;
	/**
	 * constructor for PianoMachine.
	 * 
	 * initialize midi device and any other state that we're storing.
	 */
    public PianoMachine() {
    	try {
            midi = Midi.getInstance();
        } catch (MidiUnavailableException e1) {
            System.err.println("Could not initialize midi device");
            e1.printStackTrace();
            return;
        }
    }
    
    /**
     * Plays a pitch furnished by rawPitch;
     * @param rawPitch Pitch to be played; Requires pitch to be in range A-G.
     * @requires 0 <= rawPitch.hashCode() <= 12
     */
    public void beginNote(Pitch rawPitch) {
    	int note = rawPitch.hashCode();
    	midi.beginNote(new Pitch(note).toMidiFrequency() + OCTAVE, CURRENT_INSTRUMENT);
    }
    
    /**
     * Stop play back of pitch rawPitch;
     * @param rawPitch pitch to be stopped;
     */
    public void endNote(Pitch rawPitch) {
    	int note = rawPitch.hashCode();
    	midi.endNote(new Pitch(note).toMidiFrequency() + OCTAVE, CURRENT_INSTRUMENT);
    }
    /**
     * Cycle the current instrument in the default ordering.
     * @modifies instrument state
     */
    public void changeInstrument() {
    	CURRENT_INSTRUMENT = CURRENT_INSTRUMENT.next();
    	System.out.println(CURRENT_INSTRUMENT);
    }
    
    /**
     * Shifts the keyboard up one Octave (12 semitones);
     * @modifies Octave state.
     */    
    public void shiftUp() {

    	if (OCTAVE < 24) {
    		OCTAVE += 12;
    		System.out.println("Octave UP");
    	}else{
    	System.out.println("Limit Reached");}
    }
    
    /**
     * Shifts the keyboard up one Octave (12 semitones);
     * @modifies Octave state.
     */
    public void shiftDown() {
    	if (OCTAVE > -24) {
    		OCTAVE -= 12;
    		System.out.println("Octave DOWN");
    	} else {
    	System.out.println("Limit Reached");}
    }
    
    /**
     * Updates Recording to the appropriate state.
     */
    public boolean toggleRecording() {
    	
    	if (Recording == false) {
    		Recording = true;
    	} else {
    	Recording = false;
    	}
    	return Recording;
    }
    
    /**
     * Creates a log of note events while pianoMachine is
     * in a recording state.
     */
    public void recordThings() {
    	while (Recording == true) {
    		//record stuff.
    	}
    }
    /**
     * Plays back audio from recorded sequence of notes 
     * @modifies nothing.
     */
    protected void playback() {    	
        //TODO: implement for question 4
    }

}
