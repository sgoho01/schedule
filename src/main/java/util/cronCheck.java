package util;

import java.util.Date;

public class cronCheck {

	// 초 	분 	시 	일 	월 	?
	// 0/5 	* 	* 	* 	* 	?
	public boolean checkCron(String cron){
		
		Date date = new Date();
		boolean isCorrect = false;
		
		String[] crons = cron.split(" ");
		/*
		 * crons[0] = second
		 * crons[1] = minite
		 * crons[2] = hour
		 * crons[3] = day
		 * crons[4] = month
		 * crons[5] = ?
		 */

		if(checkSecond(crons[0], date)){
			if(checkMinute(crons[1], date)){
				if(checkHour(crons[2], date)){
					if(checkDay(crons[3], date)){
						if(checkMonth(crons[4], date)){
							isCorrect = true;
						}
					}
				}
			}
		}
		
		return isCorrect;
	}
	
	
	public boolean checkSecond(String second, Date date){
		boolean isTrue = false;
		
		if(second.equals("*")){
			isTrue = true;
		}else if(second.contains("/")){
			String[] seconds = second.split("/");
			int standard = Integer.parseInt(seconds[0]);
			int range = Integer.parseInt(seconds[1]);
			
			do{
				if(date.getSeconds() == standard)
					isTrue = true;
				standard += range;
			}while(standard < 60);
			
		}else{
			if(date.getSeconds() == Integer.parseInt(second))
				isTrue = true;
		}
		
		return isTrue;
	}
	
	public boolean checkMinute(String minute, Date date){
		boolean isTrue = false;
		
		if(minute.equals("*")){
			isTrue = true;
		}else if(minute.contains("/")){
			String[] minutes = minute.split("/");
			int standard = Integer.parseInt(minutes[0]);
			int range = Integer.parseInt(minutes[1]);
			
			do{
				if(date.getMinutes() == standard)
					isTrue = true;
				standard += range;
			}while(standard < 60);
			
		}else{
			if(date.getMinutes() == Integer.parseInt(minute))
				isTrue = true;
		}		
		
		return isTrue;
	}
	
	public boolean checkHour(String hour, Date date){
		boolean isTrue = false;
		
		if(hour.equals("*")){
			isTrue = true;
		}else if(hour.contains("/")){
			String[] hours = hour.split("/");
			int standard = Integer.parseInt(hours[0]);
			int range = Integer.parseInt(hours[1]);
			
			do{
				if(date.getHours() == standard)
					isTrue = true;
				standard += range;
			}while(standard < 24);
			
		}else{
			if(date.getHours() == Integer.parseInt(hour))
				isTrue = true;
		}	
				
		return isTrue;
	}
	
	public boolean checkDay(String day, Date date){
		boolean isTrue = false;
		
		if(day.equals("*")){
			isTrue = true;
		}else if(day.contains("/")){
			String[] days = day.split("/");
			int standard = Integer.parseInt(days[0]);
			int range = Integer.parseInt(days[1]);
			
			do{
				if(date.getDate() == standard)
					isTrue = true;
				standard += range;
			}while(standard < 32);
			
		}else{
			if(date.getDate() == Integer.parseInt(day))
				isTrue = true;
		}	
		
		return isTrue;
	}
	
	public boolean checkMonth(String month, Date date){
		boolean isTrue = false;
		
		if(month.equals("*")){
			isTrue = true;
		}else if(month.contains("/")){
			String[] months = month.split("/");
			int standard = Integer.parseInt(months[0]);
			int range = Integer.parseInt(months[1]);
			
			do{
				if(date.getMonth()+1 == standard)
					isTrue = true;
				standard += range;
			}while(standard < 13);
			
		}else{
			if(date.getMonth()+1 == Integer.parseInt(month))
				isTrue = true;
		}
		
		return isTrue;
	}
	
	public boolean checkValid(String cron){
		boolean isTrue = false;
		String[] crons = cron.split(" ");
		
		if(crons.length == 6){
			if(crons[5].equals("?")){
				isTrue = true;
			}
		}
		
		return isTrue;
	}
	
	
	
	public static void main(String[] args) {
		Date date = new Date();
		cronCheck croncheck = new cronCheck();
		
		int second= date.getSeconds();
		int minute = date.getMinutes();
		int hour = date.getHours();
		int day = date.getDate();
		int month = date.getMonth()+1;
		
		System.out.println("second : " + second + ", minute : " + minute+ ", hour : " + hour+ ", day : " + day+ ", month : " + month);
		
		long startTime = System.currentTimeMillis();
		System.out.println(croncheck.checkCron("0/5 * * * * ?"));
		long endTime = System.currentTimeMillis();
	   	long resutTime = endTime - startTime;
	   	
	   	System.out.println(" 소요시간  : " + resutTime);
		
	} 
	
}
