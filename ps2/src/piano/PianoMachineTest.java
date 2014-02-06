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
    	 
    	 String expected_0 = "on(60,PIANO) wait(50) off(60,PIANO) wait(100) " + 
    	                     "on(68,BRIGHT_PIANO) wait(50) off(68,BRIGHT_PIANO)";
    	 
    	 pm.recordThings(60, Instrument.PIANO, true);
    	 Midi.wait(100);
    	 pm.recordThings(60, Instrument.PIANO, false);
    	 
    	 Midi.wait(10);
    	 pm.Recording = true; //'R' event
    	 pm.record = new StringBuilder();
    	 
    	 pm.recordThings(60, Instrument.PIANO, true);
    	 Midi.wait(50);
    	 pm.recordThings(60, Instrument.PIANO, false);
    	 
    	 Midi.wait(100);
    	 pm.recordThings(68, Instrument.BRIGHT_PIANO, true);
    	 Midi.wait(50);
    	 pm.recordThings(68, Instrument.BRIGHT_PIANO, false);
    	 

    	 pm.Recording = false; //'R' event
    	 
    	 Midi.wait(10);
    	 pm.recordThings(68, Instrument.BRIGHT_PIANO, true);
    	 Midi.wait(50);
    	 pm.recordThings(68, Instrument.BRIGHT_PIANO, false);

    	 
    	 assertEquals(expected_0, pm.record.toString().trim());
    	
     }
     
     @Test
     public void toNoteEventTest() {
    	 long time_0 = 0L;
    	 String token_0 = "on(60,PIANO)";
    	 NoteEvent test_0 = pm.toNoteEvent(token_0, time_0);
    	 NoteEvent expected_0 = new NoteEvent(new Pitch(0), time_0, Instrument.PIANO, NoteEvent.Kind.start);
    	 assertEquals(test_0.getClass(), expected_0.getClass());
    	 
    	 long time_1 = 30L;
    	 String token_1 = "off(60,PIANO)";
    	 NoteEvent test_1 = pm.toNoteEvent(token_1, time_1);
    	 NoteEvent expected_1 = new NoteEvent(new Pitch(0), time_1,Instrument.PIANO, NoteEvent.Kind.stop);
    	 assertEquals(test_1.getClass(), expected_1.getClass());
     }
     
     @Test
     public void playbackTest() throws MidiUnavailableException {
    	 pm.OCTAVE = 0;
    	 Midi midi = Midi.getInstance();
    	 midi.clearHistory();
    	 pm.record = new StringBuilder();
    	 String expected = "";
    	 
    	 pm.playback();
    	 assertEquals(expected, midi.history());
    	 
    	 // No changes
    	 midi.clearHistory();
    	 pm.record = new StringBuilder();
    	 String expected_0 = "on(60,PIANO) wait(100) off(60,PIANO)";
    	 pm.record.append("on(60,PIANO) wait(100) off(60,PIANO)");
    	 pm.playback();
    	 assertEquals(expected_0, midi.history());

    	 // Instrument Change
    	 midi.clearHistory();
    	 pm.record = new StringBuilder();
    	 String expected_1 = "on(60,PIANO) wait(100) off(60,PIANO) " + 
                             "wait(10) on(68,BRIGHT_PIANO) wait(50) off(68,BRIGHT_PIANO)";
    	 pm.record.append("on(60,PIANO) wait(100) off(60,PIANO) " + 
                          "wait(10) on(68,BRIGHT_PIANO) wait(50) off(68,BRIGHT_PIANO)");
    	 pm.playback();
    	 assertEquals(expected_1, midi.history());
     }
     
}
