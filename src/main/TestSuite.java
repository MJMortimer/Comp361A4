package main;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSuite {

	private Scheduler s;


	@Test
	public void worst_case_alg1() {
		for(int i = 0; i < 5; i++){
			Given_A_Scheduler_With_Input("2,5,5,10");
			After_Computing_With_Alg1();
			The_Latest_Finish_Time_Should_Be(15);	
			Pretty_Print_Should_Be("[15,5]");
		}
	}

	@Test
	public void less_jobs_than_machines(){
		for(int i = 0; i < 5; i++){
			Given_A_Scheduler_With_Input("5,5,5,5");
			After_Computing_With_Alg1();
			The_Latest_Finish_Time_Should_Be(5);
			Pretty_Print_Should_Be("[5,5,5,0,0]");
		}
	}

	private void Given_A_Scheduler_With_Input(String input) {
		this.s = new Scheduler(input);		
	}

	private void After_Computing_With_Alg1() {
		s.runAlg1();		
	}

	private void The_Latest_Finish_Time_Should_Be(int i) {
		assertEquals(i, s.latestFinish());		
	}

	private void Pretty_Print_Should_Be(String expected) {
		assertTrue(s.prettyOutput().equals(expected));		
	}

}
