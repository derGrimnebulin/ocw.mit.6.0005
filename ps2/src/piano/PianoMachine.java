package piano;

import java.util.Date;

import javax.sound.midi.MidiUnavailableException;
import midi.Instrument;
import midi.Midi;
import music.NoteEvent.Kind;
import music.Pitch;
import music.NoteEvent;

public class PianoMachine {
	
	private Midi midi;
	public boolean Recording;
	public int OCTAVE; //Must be in [-24,24].
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
    	recordThings(new Pitch(note).toMidiFrequency() + OCTAVE, CURRENT_INSTRUMENT, true);
    }
    
    /**
     * Stop play back of pitch rawPitch;
     * @param rawPitch pitch to be stopped;
     */
    public void endNote(Pitch rawPitch) {
    	int note = rawPitch.hashCode();
    	midi.endNote(new Pitch(note).toMidiFrequency() + OCTAVE, CURRENT_INSTRUMENT);	
    	recordThings(new Pitch(note).toMidiFrequency() + OCTAVE, CURRENT_INSTRUMENT, false);
    }
    /**
     * Cycle the current instrument in the default ordering.
     * @modifies CURRENT_INSTRUMENT
     */
    public void changeInstrument() {
    	CURRENT_INSTRUMENT = CURRENT_INSTRUMENT.next();
    }
    
    /**
     * Shifts the keyboard up one Octave (12 semitones);
     * @modifies OCTAVE
     */    
    public void shiftUp() {
    	if (OCTAVE < 24) {
    		OCTAVE += 12;
    	}
    }
    
    /**
     * Shifts the keyboard up one Octave (12 semitones);
     * @modifies OCTAVE
     */
    public void shiftDown() {
    	if (OCTAVE > -24) {
    		OCTAVE -= 12;
    	}
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
     * @param time in milliseconds that event occurred.
     * @return object containing note data
     */
    public NoteEvent toNoteEvent(String event, long time) {
    	NoteEvent note = null;
    	if ( event.substring(0,2).equals("on") ) {
    		String[] data = event.substring( 3, event.length()-1 ).split(",");
    		Kind kind = Kind.start;
    		Pitch pitch = new Pitch( Integer.parseInt(data[0]) );
    		Instrument instr = Instrument.valueOf( data[1] );
    		note = new NoteEvent(pitch, time, instr, kind);
    		
    	} else 
    	if ( event.substring(0,3).equals("off") ) {
    		String[] data = event.substring( 4, event.length()-1 ).split(",");
    		Kind kind = Kind.stop;
    		Pitch pitch = new Pitch( Integer.parseInt( data[0]) );
    		Instrument instr = Instrument.valueOf(data[1]);
    		note = new NoteEvent(pitch, time, instr, kind);
    		
    	} 
    	return note;
    }
    /**
     * Plays back audio stored in record
     * @modifies nothing.
     */
    protected void playback() throws NullPointerException {
    		long time = 0;
    		
    		if (record.length() > 0){
    			String[] events = record.toString().split(" ");
    			for (String event : events) {
    				
    				if (event.substring(0,4).equals("wait")) {
    					int waitTime = Integer.parseInt( event.substring(5,event.length()-1) );
    					Midi.wait(waitTime);
    					time += waitTime;
    				} else {
    					NoteEvent noteEvent = toNoteEvent(event,time);
    					Kind kind = noteEvent.getKind();
    					int note = noteEvent.getPitch().hashCode() - 60;
    					Instrument instr = noteEvent.getInstr();

    					if (kind == Kind.start) {
    						midi.beginNote(new Pitch(note).toMidiFrequency(), instr);
    					} else {
    						midi.endNote(new Pitch(note).toMidiFrequency(), instr);
    					}
    				}
    			}
    		} 
    	}

	}
