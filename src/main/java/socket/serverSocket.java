package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import schedule.ScheduleLauncher;
import util.cronCheck;

public class serverSocket {

	public static void main(String[] args) {
		
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		OutputStream out = null;
		DataOutputStream dos = null;
		InputStream in = null;
		DataInputStream dis = null;
		
		String Nm = "[SERVER] ";
		
		cronCheck croncheck = null;
		Thread thread = null;
		HashMap<String, Thread> threadArray = new HashMap();
		
		try {
			serverSocket = new ServerSocket(5000);
			
			System.out.println(Nm + "서버가 준비되었습니다.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (true) {
            try {
                System.out.println(Nm + "연결요청을 기다립니다.");
                // 서버소켓은 클라이언트의 연결요청이 올 때까지 실행을 멈추고 계속 기다린다.
                // 클라이언트의 연결요청이 오면 클라이언트 소켓과 통신할 새로운 소켓을 생성한다.
                socket = serverSocket.accept();
                System.out.println(Nm + socket.getInetAddress() + " 로부터 연결요청이 들어왔습니다.");
                
                // 소켓의 출력스트림을 얻는다.
                out = socket.getOutputStream(); 
                dos = new DataOutputStream(out); // 기본형 단위로 처리하는 보조스트림
                
                // 원격 소켓(remote socket)에 데이터를 보낸다.
                dos.writeUTF(Nm + "서버 연결.");
                System.out.println(Nm + "데이터를 전송했습니다.");
                
                in = socket.getInputStream();
                dis = new DataInputStream(in);
                
                while(true){
	                 	                
                	try {
						String data = dis.readUTF();
						System.out.println("[SERVER DATA] : " + data);
						
						// 받은 테이터를 구분한다.
						// (클래스명:크론) - 형식으로 넘어오면 스케줄을 실행시킨다.
						if(data.indexOf(":") != -1){
							String[] jobs = data.split(":");
							
							String clsNm = jobs[0];
							String time = jobs[1];

							// 스케줄이 이미 존재하지 않으면 스케줄을 실행한다.
							if(!threadArray.containsKey(clsNm)){
							
								croncheck = new cronCheck();
								
								thread = new Thread(new ScheduleLauncher(clsNm, time));
	
								// 크론식 체크
								if(croncheck.checkValid(time)){
									threadArray.put(clsNm, thread);
									thread.start();
									dos.writeUTF(Nm + clsNm + " 스케줄이 실행되었습니다.");
								}else{
									// 크론식이 부적절할 경우
									thread.interrupt();
									dos.writeUTF(Nm + "크론식을 다시 입력해주세요.");
								}
							}else{
								// 스케줄이 이미 존해할 경우
								dos.writeUTF(Nm + clsNm + " 스케줄이 이미 존해합니다.");
							}
						}else if(data.equals("2")){
							// 스케줄 목록 (2)
							// 스케줄 종료를 클릭한 경우 실행중인 스케줄 목록을 출력한다.
							if(threadArray.keySet().isEmpty()){
								dos.writeUTF(Nm + "실행중인 스케줄이 없습니다.");
							}else{
								dos.writeUTF(Nm + "스케줄 리스트 : " + threadArray.keySet());
							}
						}else if(data.startsWith("del")){	
							// 스케줄 종료 (del + 클래스명)
							// 스케줄 이름 subString
							String del = data.substring(4, data.length());
							
							// 스케줄 목록에 있는 스케줄일 경우
							if(threadArray.containsKey(del)){
								Thread th = threadArray.get(del);
								
								System.out.println(Nm + del + " 스케줄 종료");
								th.interrupt();
								threadArray.remove(del);
								dos.writeUTF(Nm + del + " 스케줄이 종료되었습니다.");
							}else{
								// 스케줄 목록에 없는 스케줄일 경우
								dos.writeUTF(Nm + del +" 스케줄이 존재하지 않습니다.");								
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
	    			
	    			
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try{
                	// 스트림과 소켓을 달아준다.
                    dos.close();
                    dis.close();
                    socket.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }   
        }
		
		
	}
	
	
}
