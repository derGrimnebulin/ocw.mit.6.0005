package piano;

import java.util.Date;

import javax.sound.midi.MidiUnavailableException;
import midi.Instrument;
import midi.Midi;
import music.Pitch;
import music.NoteEvent;

public class PianoMachine {
	
	private Midi midi;
	
	public boolean Recording;
	public int OCTAVE; //Must be in [-24,24].
	//For record keeping.
	public StringBuilder record;
	private long prevEventTime;
	
  public Instrument CURRENT_INSTRUMENT = Midi.DEFAULT_INSTRUMENT;
	/**
	 * constructor for PianoMachine.
	 * 
	 * initialize midi device and any other state that we're storing.
	 */
    public PianoMachine() {
    	Recording = false;
    	OCTAVE = 0;
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
    	
    	recordThings(note, CURRENT_INSTRUMENT, true);
    }
    
    /**
     * Stop play back of pitch rawPitch;
     * @param rawPitch pitch to be stopped;
     */
    public void endNote(Pitch rawPitch) {
    	int note = rawPitch.hashCode();
    	midi.endNote(new Pitch(note).toMidiFrequency() + OCTAVE, CURRENT_INSTRUMENT);
    	
    	recordThings(note, CURRENT_INSTRUMENT, false);
    }
    /**
     * Cycle the current instrument in the default ordering.
     * @modifies CURRENT_INSTRUMENT
     */
    public void changeInstrument() {
    	CURRENT_INSTRUMENT = CURRENT_INSTRUMENT.next();
    	System.out.println(CURRENT_INSTRUMENT);
    }
    
    /**
     * Shifts the keyboard up one Octave (12 semitones);
     * @modifies OCTAVE
     */    
    public void shiftUp() {

    	if (OCTAVE < 24) {
    		OCTAVE += 12;
    		
    	}else{
    	System.out.println("Limit Reached");}
    }
    
    /**
     * Shifts the keyboard up one Octave (12 semitones);
     * @modifies OCTAVE
     */
    public void shiftDown() {
    	if (OCTAVE > -24) {
    		OCTAVE -= 12;
    		
    	} else {
    	System.out.println("Limit Reached");}
    }
    
    /**
     * Updates Recording to the appropriate state.
     * When recording is toggled on, the previous record is overwritten.
     * @return updated recording state.
     */
    public boolean toggleRecording() {
    	
    	if (Recording == false) {
    		record = new StringBuilder();
    		prevEventTime = -1;
    		Recording = true;
    	} else {
    		Recording = false;
    	}
    	return Recording;
    }
    
    /**
     * Appends actions from keyboard to a record
     * @param int note
     * @param Instrument instr
     * @param boolean isNoteOn
     */
    public void recordThings(int note, midi.Instrument instr, boolean isNoteOn) {
    	if (Recording) {
    		//Must keep track of event Time separately from history.
    		long time = (new Date()).getTime();
    		if (prevEventTime > 0){
    			long timeDiff = Math.round((time-prevEventTime)/10.0);
    			record.append("wait("+timeDiff+") ");
    		}
    		prevEventTime = time;
    		if (isNoteOn) {
    			record.append("on("+note+","+instr+") ");
    		} else {
    			record.append("off("+note+","+instr+") ");
    		}
    	}
    }
    
    /**
     * Converts a record token into a NoteEvent object
     * @param string containing data about a note event
     * @return object containing note data
     */
    public NoteEvent toNoteEvent(String event) {
    	return new NoteEvent(null, OCTAVE, CURRENT_INSTRUMENT, null);
    	
    }
    /**
     * Plays back audio stored in record
     * @modifies nothing.
     */
    protected void playback() {    	
        //TODO: implement for question 4
    }

}
