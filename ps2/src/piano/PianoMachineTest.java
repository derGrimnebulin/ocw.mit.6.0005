package piano;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.sound.midi.MidiUnavailableException;

import midi.Instrument;
import midi.Midi;
import music.Pitch;

import org.junit.Test;

public class PianoMachineTest {
	
	PianoMachine pm = new PianoMachine();
	
    @Test
    public void singleNoteTest() throws MidiUnavailableException {
        String expected0 = "on(60,PIANO) wait(100) off(60,PIANO)";
        
        Midi midi = Midi.getInstance();

    		midi.clearHistory();
    		
    		//represents two transition events:
    		//(UP,pr,DOWN), (DOWN,rel,UP)
      	pm.beginNote(new Pitch(0));
      	Midi.wait(100);
      	pm.endNote(new Pitch(0));

        System.out.println(midi.history());
        assertEquals(expected0,midi.history());
    }
    
     @Test
     public void instrumentTest() throws MidiUnavailableException {
    	 	
    	 	// Middle case test.
    	 	Instrument expected0 = Instrument.BRIGHT_PIANO;
    	 	pm.changeInstrument();
    	 	assertEquals(expected0, pm.CURRENT_INSTRUMENT);
    	 	
    	 	// Boundary case test.
    	 	Instrument expected1 = Instrument.PIANO;
    	 	pm.CURRENT_INSTRUMENT = Instrument.GUNSHOT;
    	 	pm.changeInstrument();
    	 	assertEquals(expected1, pm.CURRENT_INSTRUMENT);
     }
     
     @Test
     public void octaveTest () throws MidiUnavailableException {

    	 // Shift-up test case.
    	 pm.OCTAVE = 0;
    	 pm.shiftUp();
    	 assertEquals(12, pm.OCTAVE);
    	 
    	 // Shift-down test case.
    	 pm.OCTAVE = 0;
    	 pm.shiftDown();
    	 assertEquals(-12, pm.OCTAVE);
    	 
    	 //Idempotency test cases.
    	 pm.OCTAVE = 24;
    	 pm.shiftUp();
    	 assertEquals(24, pm.OCTAVE);
    	 pm.OCTAVE = -24;
    	 pm.shiftDown();
    	 assertEquals(-24, pm.OCTAVE);
    	 
     }
}
