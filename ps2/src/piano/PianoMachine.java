package piano;

import javax.sound.midi.MidiUnavailableException;

import midi.Instrument;
import midi.Midi;
import music.Pitch;

public class PianoMachine {
	
	private Midi midi;
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
    	midi.beginNote(new Pitch(note).toMidiFrequency(), CURRENT_INSTRUMENT);
    }
    
    /**
     * Stop play back of pitch rawPitch;
     * @param rawPitch pitch to be stopped;
     */
    public void endNote(Pitch rawPitch) {
    	int note = rawPitch.hashCode();
    	midi.endNote(new Pitch(note).toMidiFrequency(),CURRENT_INSTRUMENT);
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
     * @modifies Pitch 
     */
    public void shiftUp() {
    	//TODO: implement for question 3
    	//Pitch.transpose(Octave);
    }
    
    /**
     * Shifts the keyboard up one Octave (12 semitones);
     * @modifies Pitch.
     */
    public void shiftDown() {
    	//TODO: implement for question 3
    }
    
    /**
     * records notes to be played back later		
     * @return boolean true if recording, false is not;
     */
    public boolean toggleRecording() {
    	return false;
    	//TODO: implement for question 4
    }
    
    /**
     * Plays back audio from recorded sequence of notes 
     */
    protected void playback() {    	
        //TODO: implement for question 4
    }

}
