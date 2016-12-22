package schedule;

import util.cronCheck;
import job.Job;


public class ScheduleLauncher implements Runnable{

	String className;
	String time;
	
	public ScheduleLauncher(String className, String time) {
		this.className = className;
		this.time = time;
	}
	
	@Override
	public void run() {
		
		try {
			while(!Thread.currentThread().isInterrupted()){
				// 클래스 로드
				Job job = (Job) Class.forName(className).newInstance();
				
				cronCheck cronCheck = new cronCheck();
				
				if(cronCheck.checkCron(time)){
					// 클래스의 메소드 실행
					job.execute();
				}
				
				Thread.sleep(1000);
			}
						
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (InterruptedException e) {
		} finally {
			System.out.println("Thread 정지");
		}
		
	}
	
}
