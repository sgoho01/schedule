package socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.springframework.dao.CleanupFailureDataAccessException;

public class clientSocket {
	public static void main(String[] args) {
		
		Socket socket = null;
		
		InputStream in = null;
		DataInputStream dis = null;
		OutputStream out = null;
		DataOutputStream dos = null;
		
		
		BufferedReader input = new BufferedReader( new InputStreamReader( System.in ) );
		
		String Nm = "[CLIENT] ";
		String data = "";
		
		try {
			
			String serverIP = "127.0.0.1"; // 127.0.0.1 & localhost 본인
			System.out.println(Nm + "서버에 연결중입니다. 서버 IP : " + serverIP);
			 
			// 소켓을 생성하여 연결을 요청한다.
			socket = new Socket(serverIP, 5000);
			 
			// 소켓의 입력스트림을 얻는다.
			in = socket.getInputStream();
			dis = new DataInputStream(in);  // 기본형 단위로 처리하는 보조스트림

			out = socket.getOutputStream(); 
            dos = new DataOutputStream(out);
            
            // 소켓으로 부터 받은 데이터를 출력한다.
 			System.out.println(dis.readUTF());
            
            dos.writeUTF(Nm + "클라이언트 접속!");
            System.out.println(Nm + "데이터를 전송했습니다.");
            
            
            while(true){
            	
            	// 선택 메뉴
            	System.out.print(Nm + "스케줄 입력 : 1, 스케줄 종료 : 2 : ");
            	String startOrQuit = input.readLine();
            	
            	// 스케줄 입력 
            	if(startOrQuit.equals("1")){
		    		System.out.print(Nm + "클래스 이름을 입력하세요 : ");
		    		String clsNm = input.readLine();
		    		System.out.print(Nm + "시간을 입력하세요 (초 분 시 일 월 ?) : ");
		    		String time = input.readLine();
	    		
		    		// (클래스명:크론) 형태로 데이터를 전송한다. 
		    		dos.writeUTF(clsNm + ":" + time);
            	}else if(startOrQuit.equals("2")){
            		// 스케줄 종료 목록
            		dos.writeUTF(startOrQuit);
            	}
            	
            	// 서버에서 온 데이터를 출력한다.
            	data = dis.readUTF();
            	System.out.println(data);
            	
            	// 서버에서 스케줄 리스트를 넘겨받은 경우
            	if(data.contains("스케줄 리스트")){
            		System.out.print(Nm + "클래스 이름을 입력하세요 : ");
		    		String clsNm = input.readLine();
            		
		    		dos.writeUTF("del " + clsNm);
		    		
		    		data = dis.readUTF();
	            	System.out.println(data);
            	}

            }
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            try{
            	// 스트림과 소켓을 닫는다.
    			dis.close();
    			dos.close();
    			socket.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }   
	}
}
