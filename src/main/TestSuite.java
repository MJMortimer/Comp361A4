package main;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSuite {

	private Scheduler s;
	private Scheduler optS;


	@Test
	public void worst_case_alg1() {
		for(int i = 0; i < 5; i++){
			String input  = "2,5,5,10";
			sortedInput(input);
			Given_A_Scheduler_With_Input(input);
			After_Computing_With_Alg1(s);
			The_Latest_Finish_Time_Should_Be(s,15);
			Pretty_Print_Should_Be(s,"[15,5]");
		}
	}

	@Test
	public void less_jobs_than_machines(){
		for(int i = 0; i < 5; i++){
			String input = "5,5,5,5";
			Given_A_Scheduler_With_Input(input);
			After_Computing_With_Alg1(s);
			The_Latest_Finish_Time_Should_Be(s,5);
			Pretty_Print_Should_Be(s,"[5,5,5,0,0]");
		}
	}

	//A Test For Checking Ratio is never worse than 1.5 or better than 1.0
	@Test
	public void large_test_alg1(){
		for(int i = 2; i < 11; i++){
			for(int j = 1; j < 1000; j++){
				StringBuilder sb = new StringBuilder();
				sb.append(String.format("%d,", i));
				String input = randomInput(sb, j);
				String sortedInput = sortedInput(input);
				Given_Schedulers_With_Inputs(input, sortedInput);
				After_Computing_Both_With_Alg1();
				assertTrue("Ratio was worse than 1.5", ((double)s.latestFinish()/(double)optS.latestFinish()) <= 1.5);
				assertTrue("Ratio was better than 1.0", ((double)s.latestFinish()/(double)optS.latestFinish()) >= 1.0);
			}
		}
	}

	//A test for checking if prob. is better if not as good as determ. most of the time
		@Test
		public void large_test_alg2(){
			Scheduler s2;
			int total = 0;
			int better = 0;
			int same = 0;

			for(int i = 2; i < 11; i++){
				for(int j = 1; j < 500; j++){
					StringBuilder sb = new StringBuilder();
					sb.append(String.format("%d,", i));
					String input = randomInput(sb, j);
					String sortedInput = sortedInput(input);
					Given_Schedulers_With_Inputs(input, sortedInput);
					s2 = new Scheduler(input);
					After_Computing_With_Alg1(optS);   //run deterministic for the optimal
					After_Computing_With_Alg1(s);   //run deterministic for the first scheduler
					After_Computing_With_Alg2(s2);   //run probabblistic for the second scheduler

					double determRatio = (double)s.latestFinish()/(double)optS.latestFinish();
					double probRatio = (double)s2.latestFinish()/(double)optS.latestFinish();

					if(probRatio < determRatio)
						better++;
					if(probRatio == determRatio)
						same++;
					total++;
					//assertTrue("Ratio was worse than 1.5", ((double)s2.latestFinish()/(double)optS.latestFinish()) <= 2.0);
					//assertTrue("Ratio was better than 1.0", ((double)s.latestFinish()/(double)optS.latestFinish()) >= 1.0);
					//System.out.println(String.format("%d, %4.2f, %4.2f", i, (double)s.latestFinish()/(double)j, (double)s2.latestFinish()/(double)j));
				}
			}

			System.out.println("Probabilistic better: "+(double)better/(double)total);
			System.out.println("Same: "+(double)same/(double)total);
			System.out.println("Deterministic Better: "+(1.0 - ((double)(better +same))/(double)total));
		}

	private void Given_A_Scheduler_With_Input(String input) {
		this.s = new Scheduler(input);
	}

	private void Given_Schedulers_With_Inputs(String input, String sortedInput) {
		this.s = new Scheduler(input);
		this.optS = new Scheduler(sortedInput);
	}

	private void After_Computing_With_Alg1(Scheduler sched) {
		sched.runAlg1();
	}

	private void After_Computing_With_Alg2(Scheduler sched) {
		sched.runAlg2();
	}

	private void After_Computing_Both_With_Alg1() {
		s.runAlg1();
		optS.runAlg1();
	}

	private void The_Latest_Finish_Time_Should_Be(Scheduler sched, int i) {
		assertEquals(i, sched.latestFinish());
	}

	private void Pretty_Print_Should_Be(Scheduler sched, String expected) {
		assertTrue(sched.prettyOutput().equals(expected));
	}

	private String sortedInput(String input){
		String[] a = input.split(",");
		int machineCount = Integer.parseInt(a[0]);
		int[] jobs = new int[a.length-1];
		for(int i = 1; i < a.length; i++){
			jobs[i-1] = Integer.parseInt(a[i]);
		}

		Arrays.sort(jobs);
		for(int i=0;i<jobs.length/2;i++) {
		     int t = jobs[i];
		     jobs[i] = jobs[jobs.length-(i+1)];
		     jobs[jobs.length-(i+1)] = t;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(machineCount+",");

		for(int i = 0; i < jobs.length; i++){
			sb.append(jobs[i]+",");
		}

		return sb.toString().substring(0, sb.length()-1);
	}

	private String randomInput(StringBuilder sb, int j) {
		Random rnd = new Random();
		int[] a = {5,10};
		for( int i = 0; i <= j; i++ )
			sb.append(String.format("%d,",a[rnd.nextInt(2)]));
		return sb.toString().substring(0, sb.length()-1);
	}

}
