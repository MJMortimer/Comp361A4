package main;

public class Scheduler {
	
	private int[] machines;
	
	public Scheduler(int machineCount, int[] jobs){
		machines = new int[machineCount];
		for(int i = 0; i < machineCount; i++){
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
	
	public void printMachines(){
		for(int i =  0; i < machines.length; i++){
			System.out.println("Machine: "+(i+1)+". Finish Time: "+machines[i]);
		}
	}

	public static void main(String[] args){
		String[] a = args[0].split(",");
		int machines = Integer.parseInt(a[0]);
		
		int[] jobs = new int[a.length-1];
		for(int i = 1; i < a.length; i++){
			jobs[i-1] = Integer.parseInt(a[i]);
		} 
		
		Scheduler s = new Scheduler(machines, jobs);
		s.printMachines();
	}
}
