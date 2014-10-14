package main;

import java.util.Arrays;

import org.junit.internal.runners.statements.RunAfters;

public class Scheduler {
	
	private int[] machines;
	private int[] jobs;
	
	public Scheduler(String input){
		String[] a = input.split(",");
		int machineCount = Integer.parseInt(a[0]);
		
		int[] jobs = new int[a.length-1];
		for(int i = 1; i < a.length; i++){
			jobs[i-1] = Integer.parseInt(a[i]);
		} 
		machines = new int[machineCount];
		this.jobs = jobs;
			
		
	}

	public void runAlg1() {
		for(int i = 0; i < machines.length; i++){
			machines[i] = 0;
		}
		
		
		for(int j= 0; j < jobs.length; j++){
			int index = -1;
			int finish = Integer.MAX_VALUE;
			for(int m = 0; m < machines.length; m++){
				if(machines[m] < finish){
					index = m;
					finish = machines[m];
				}
			}			
			machines[index] += jobs[j];
		}
	}
	
	public int latestFinish(){
		int finish = -1;
		for(int m = 0; m < machines.length; m++){
			if(machines[m] > finish){
				finish = machines[m];
			}
		}
		return finish;
	}
	
	public void printMachines(){
		for(int i =  0; i < machines.length; i++){
			System.out.println("Machine: "+(i+1)+". Finish Time: "+machines[i]);
		}
	}
	
	public String prettyOutput(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i =  0; i < machines.length; i++){
			sb.append(machines[i]);
			if(i != machines.length -1){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public static void main(String[] args){
		Scheduler s = new Scheduler(args[0]);
		s.runAlg1();
		s.printMachines();
		System.out.println(s.latestFinish());
	}
}
