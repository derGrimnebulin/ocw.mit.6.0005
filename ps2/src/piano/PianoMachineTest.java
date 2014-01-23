package piano;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import javax.sound.midi.MidiUnavailableException;
import midi.Instrument;
import midi.Midi;
import music.Pitch;
import music.NoteEvent;
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
    	 
    	 // Shift-up/down from boundaries.
    	 pm.OCTAVE = 24;
    	 pm.shiftDown();
    	 assertEquals(12, pm.OCTAVE);
    	 pm.OCTAVE = -24;
    	 pm.shiftUp();
    	 assertEquals(-12, pm.OCTAVE);
     }
     
     @Test
     public void recordStateTest() {
    	 pm.Recording = false;
    	 pm.toggleRecording();
    	 assertTrue(pm.Recording);
    	 
    	 pm.Recording = true;
    	 pm.toggleRecording();
    	 assertTrue(pm.Recording == false);
     }
     
     @Test 
     public void recordEventsTest() throws MidiUnavailableException {
    	 Midi midi= Midi.getInstance();
    	 midi.clearHistory();
    	 
    	 String expected_0 = "on(60,PIANO) wait(100) off(60,PIANO) " + 
    	                     "on(68,BRIGHT_PIANO) wait(50) off(68,BRIGHT_PIANO)";
    	 
    	 midi.beginNote(60, Instrument.PIANO);
    	 Midi.wait(100);
    	 
    	 pm.Recording = true; //'R' event
    	 pm.recordThings();
    	 
    	 midi.beginNote(60, Instrument.PIANO);
    	 Midi.wait(100);
    	 midi.endNote(60, Instrument.PIANO);
    	 
    	 midi.beginNote(68, Instrument.BRIGHT_PIANO);
    	 Midi.wait(50);
    	 midi.endNote(68, Instrument.BRIGHT_PIANO);
    	 
    	 pm.Recording = false; //'R' event
    	 
    	 midi.beginNote(68, Instrument.BRIGHT_PIANO);
    	 Midi.wait(50);
    	 
    	 assertEquals(expected_0, pm.record);
    	
     }
     
     @Test
     public void toNoteEventTest() {
    	 long eventTime_0 = 0L;
    	 String token_0 = "on(60,PIANO)";
    	 NoteEvent expected_0 = new NoteEvent(new Pitch(0), eventTime_0, Instrument.PIANO, NoteEvent.Kind.start);
    	 assertEquals(token_0, expected_0);
    	 
    	 long eventTime_1 = 30L;
    	 String token_1 = "on(60,PIANO)";
    	 NoteEvent expected_1 = new NoteEvent(new Pitch(0),eventTime_1,Instrument.PIANO, NoteEvent.Kind.start);
    	 assertEquals(token_1, expected_1);
     }
     
     @Test
     public void playbackTest() throws MidiUnavailableException {
    	 Midi midi = Midi.getInstance();
    	 midi.clearHistory();
    	 pm.record = new StringBuilder();
    	 
    	 // No changes
    	 String expected_0 = "on(60,PIANO) wait(100) off(60,PIANO)";
    	 pm.record = new StringBuilder(); //clear record
    	 pm.record.append("on(60,PIANO) wait(100) off(60,PIANO)");
    	 pm.playback();
    	 assertEquals(expected_0, midi.history());
    	 
    	 // Instrument Change
    	 String expected_1 = "on(60,PIANO) wait(100) off(60,PIANO) " + 
                             "on(68,BRIGHT_PIANO) wait(50) off(68,BRIGHT_PIANO)";
    	 pm.record = new StringBuilder();
    	 pm.record.append("on(60,PIANO) wait(100) off(60,PIANO) " + 
                          "on(68,BRIGHT_PIANO) wait(50) off(68,BRIGHT_PIANO)");
    	 pm.playback();
    	 assertEquals(expected_1, midi.history());
     }
     
}
