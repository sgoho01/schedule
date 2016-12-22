package schedule;

import java.util.Scanner;

import util.cronCheck;

public class Test {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		cronCheck croncheck = new cronCheck();
		
		System.out.print("클래스 이름 : ");
		String clsNm = sc.nextLine();
		System.out.print("시간 : ");
		String time = sc.nextLine();
		
		ScheduleLauncher sch = new ScheduleLauncher(clsNm, time);
//		ScheduleLauncher sch = new ScheduleLauncher("job.JobA", 1000);
//		ScheduleLauncher sch2 = new ScheduleLauncher("job.JobB", 2000);
		
		Thread thread = new Thread(sch);
//		Thread thread2 = new Thread(sch2);
		
		if(croncheck.checkValid(time)){
			thread.start();
		}else{
			thread.interrupt();
		}
		
	}
	
	
}
