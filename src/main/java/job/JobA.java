package job;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JobA implements Job{

	@Override
	public void execute() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println("job A execute Mehtod, Date : " + sdf.format(new Date()));
	}
	
	public static void main(String[] args) {
		System.out.println("Job A Main");
	}
}
