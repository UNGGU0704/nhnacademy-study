# Socket(23. 10. 23 ~ 24)

## Socket이란?

- Network에서 정보가 전송되는 고유 식별자
- 송신 또는 수신 프로세서가 위치한 host에 의해 식별
- 운영 체제에서 스풀링되는 물리적인 장치로의 I/O
- 기본 특성이 변경되지 않았지만, 액세스가 제한되고 mapping 알고리즘(예: 디바이스 주소 매핑 또는 가상 미니 디스크에서의 실린더 이동)에 의해 변환된 물리적인 장치
- 운영 체제에서 유지 관리되는 디렉터리와 액세스 방법을 통해 파일 시스템에 액세스
- 프로세스 간 통신을 위한 절차
- Network protocol에서 정의된 기계 간 통신을 위한 절차

## Socket Type

### Datagram Sockets

- UDP(User Datagram Protocol)를 사용하는 무연결 socket
- Datagram socket에서 보내거나 받은 packet은 개별적으로 주소가 지정되고 라우팅됨
- Datagram socket에서는 순서와 신뢰성이 보장되지 않으므로 한 기계 또는 프로세스에서 다른 기계로 전송되는 여러 packet이 임의의 순서로 도착하거나 전혀 도착하지 않을 수 있음

### Stream Sockets

- Connection-oriented Sockets
- TCP(Transmission Control Protocol)
- SCTP(Stream Control Transmission Protocol)
- DCCP(Datagram Congestion Control Protocol)
- 오류 없는 데이터 전송, packet 순서, 흐름 제어 보장
- 인터넷에서 일반적으로 TCP를 사용하여 구현되므로 응용 프로그램이 TCP/IP 프로토콜을 사용하여 네트워크를 통해 실행될 수 있음
- 송신된 순서에 따라 중복되지 않게 데이터를 수신함으로 이에 따른 overhead 발생

### Raw Sockets

- 프로토콜별 전송 계층 형식 없이 IP packet을 직접 보내고 받을 수 있음
- 전송 계층 프로토콜(예: TCP, UDP)에 상관없이 IP packet으로 주고 받음
- Berkeley sockets[[1](https://github.com/nhnacademy-bootcamp/java-network-programming/blob/main/doc/2.Socket_type.adoc#_footnotedef_1)]을 기반으로 하는 API는 raw sockets을 지원하며, 윈도우 XP는 2001년 윈삭 인터페이스에 구현된 raw sockets 지원을 통해 출시되었으나, 3년 후 마이크로소프트는 보안상의 문제로 row sockets 지원을 제한
- Nmap[[2](https://github.com/nhnacademy-bootcamp/java-network-programming/blob/main/doc/2.Socket_type.adoc#_footnotedef_2)]과 같은 보안 관련 응용 프로그램에서 사용
- 일반적으로 네트워크 장비에서 사용할 수 있으며 IGMP[[3](https://github.com/nhnacademy-bootcamp/java-network-programming/blob/main/doc/2.Socket_type.adoc#_footnotedef_3)]와 OSPF[[4](https://github.com/nhnacademy-bootcamp/java-network-programming/blob/main/doc/2.Socket_type.adoc#_footnotedef_4)]와 같은 라우팅 프로토콜에 사용
- ping[[5](https://github.com/nhnacademy-bootcamp/java-network-programming/blob/main/doc/2.Socket_type.adoc#_footnotedef_5)] 유틸리티에 의해 사용되는 인터넷 제어 메시지 프로토콜(ICMP)에도 사용

## Socket 통신

### Server socket

Server socket은 client socket의 연결 요청을 대기하고, 연결 요청이 오면 client socket을 생성하여 통신이 가능하도록 제공한다.

Server socket의 동작 과정은 다음과 같다.

- socket() 함수를 이용하여 소켓 생성
- bind() 함수를 이용해 대기 소켓의 IP 주소와 port를 설정
- listen() 함수로 클라이언트 소켓 연결 요청 대기
- Client socket 연결 요청이 오면 accept() 함수를 이용해 연결을 승인하고, 요청은 client socket과 통신을 위한 소켓을 생성
- Server socket은 listen() 함수를 통해 추가적인 연결 요청에 대비하고, 생성된 socket은 연결된 client socket과 데이터를 주고받음
- Client socket이나 생성된 socket을 닫으면 연결되어 있던 상대 socket도 닫힘

### Client Socket

Client socket은 client 프로그램이나 server에서 생성할 수 있다. 위 server socket 설명에서 accept 후 새로운 socket이 생성되는데 이 또한 client socket으로 실질적인 socket 간 통신은 client socket 간에 이루어진다.

Client socket의 동작 과정은 다음과 같다.

- socket() 함수를 이용하여 socket 생성
- connect() 함수를 이용해 지정된 sever에 연결 요청 전송
- Server에서 연결을 받아들이면 데이터 송수신 시작
- 데이터 송수신이 완료되거나 상대 socket의 닫힘이 감지되면 socket을 닫음

## Java Socket 통신

- Java에서는 socket 통신을 위해 Socket class와 서버 구성을 위한 ServerSocket class를 지원한다.

### Class Socket

Socket 생성과 함께 server 연결에 연결 요청을 한다. 이를 위해 Socket constructor에는 연결을 위한 server 정보가 제공 되어야 한다.

`Socket socket = new Socket(hostIp, port)`

Server에 성공적으로 연결되면, server와 통신을 위한 socket이 생성되어 반환되며 연습 문제를 통해 확인

`> nc -l 12345` 로 nc 서버 오픈 

```sql
try {
    Socket socket = new Socket("localhost", 12345);
    System.out.println("서버에 연결되었습니다.");

    socket.close();
} catch (IOException e) {
    System.err.println(e);
}

$ 서버에 연결되었습니다.
```

→ 서버와 연결됨을 확인할 수 있다.

### Try-with-resources

- `try` 는 하나 이상의 자원을 관리하고 있다가 해당 Statement가 끝날 때, 각각의 자원을 반환 하는 것을 목표로 한다.
- 아래의 예제는 파일을 읽는 코드이다. 이러한 `FileReader` 나 `BufferdReader` 는 해당 자원을 반환할 의무가 있다.
    
    ```java
    static String readFirstLineFromFile(String path) throws IOException {
    try (FileReader fr = new FileReader(path);
    	         BufferedReader br = new BufferedReader(fr)) {
    	        return br.readLine();
    	    }
    	}
    ```
    
    이러한 자원의 선언문은 `try` 문 즉시 나타난다.
    
    `try` 문이 끝난다면 해당 자원들은 자동으로 닫히게 된다. *(정상적이던 정상적이지 않던)*
    
- 기존에 존재하던 `try-catch-finally` ****의 경우 error stacktrace의 누락등으로 정상적인 추적에 있어 실수를 할 위험이 있다.
- 모든 메모리를 반환할 경우 `try-with-resources` 를 사용하자.
- 기존의 `Closeable` 에 부모 인터페이스인 `AutoCloseable` 을 추가하여 기존의 모든 자원 Class에 사용가능하다.
- `try - with - resourses` 를 사용한 다른 예제
    
    ```java
    public static void viewTable(Connection con) throws SQLException {
    
        String query = "select COF_NAME, SUP_ID, PRICE, SALES, TOTAL from COFFEES";
    
        // try-with-resources 구문 사용
        try (Statement stmt = con.createStatement(); 
             ResultSet rs = stmt.executeQuery(query)) {
    
            while (rs.next()) {
                String coffeeName = rs.getString("COF_NAME");
                int supplierID = rs.getInt("SUP_ID");
                float price = rs.getFloat("PRICE");
                int sales = rs.getInt("SALES");
                int total = rs.getInt("TOTAL");
    
                System.out.println(coffeeName + ", " + supplierID + ", " + 
                                   price + ", " + sales + ", " + total);
            }
        } catch (SQLException e) {
            JDBCTutorialUtilities.printSQLException(e);
        }
    }
    ```
    

- 위의 서버 연결 코드를 `try - with - resourses` 를 사용하여 변경
    
    ```java
    try(Socket socket = new Socket("localhost", 12345)) {
        System.out.println("서버에 연결되었습니다.");
    
    } catch (IOException e) {
        System.err.println(e);
    }
    ```
    

### Ex 01 : **Socket class의 함수를 이용해 client와 server 접속 정보(host, port)를 확인**

```java
import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {
        try(Socket socket = new Socket("localhost", 12345)) {
            System.out.println("서버에 연결되었습니다.");
            
            System.out.println("Local address : " + socket.getInetAddress());
            System.out.println("Local port : " + socket.getLocalPort());
            System.out.println("Remote address : " + socket.getRemoteSocketAddress());
            System.out.println("Remote port : " + socket.getPort());
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
```

### Ex 02 : Clinet Socket을 server에 연결하여 data 보낸다.

1.  지정된 server에 연결하여 socket을 생성한다.
`Socket socket = new Socket(host, port);`

2.  Server에 연결되면, socket을 반환하고 계속 진행한다.
`System.out.println("서버에 연결되었습니다.");`

3. 그렇지 않으면, IOException을 발생시킨다.
`} catch (ConnectException e) {
    System.err.println(host + ":" + port + "에 연결할 수 없습니다.");
}`

4. 연결된 socket에서 output stream을 얻어서 데이터를 전송한다.
`socket.getOutputStream().write("Hello World!".getBytes());`
    → *OutputStream class*의 write() 함수는 byte [] 를 받아 전송하므로, 문자열은 byte []로 변환한다.

5. 데이터 송/수신이 끝나면 연결을 끊고, socket을 닫는다.
`socket.close();`

**Source Code**

```java
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class Exam02 {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;

        if (args.length > 0) {
            host = args[0];
        }

        try {
            if (args.length > 1) {
                port = Integer.parseInt(args[1]);
            }
        } catch (NumberFormatException ignore) {
            System.err.println("Port가 올바르지 않습니다.");
            System.exit(1);
        }

        try {
            // tag::newSocket[]
            Socket socket = new Socket(host, port);
            // end::newSocket[]
            // tag::connected[]
            System.out.println("서버에 연결되었습니다.");
            // end::connected[]

            // tag::outputWrite[]
            socket.getOutputStream().write("Hello World!".getBytes());
            // end::outputWrite[]

            // tag::socketClose[]
            socket.close();
            // end::socketClose[]
            // tag::connectException[]
        } catch (ConnectException e) {
            System.err.println(host + ":" + port + "에 연결할 수 없습니다.");
        }
        // end::connectException[]
        // tag::IOException[]
        catch (IOException e) {
            System.err.println(e);
        }
        // end::IOException[]
    }
}
```

데이터를 받고 싶다면

`socket.getOutputStream().write("Hello World!".getBytes());` 를

```java
while ((ch = socket.getInputStream().read()) >= 0) {
                System.out.write(ch);
 }
```

로 변경한다.

### Q4. Server에 exit가 입력 될 때까지 data 입력하기

```java
while (true) {
                BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
                String input = bf.readLine();

                if (input.equals("exit")) 
                    break;
                
                socket.getOutputStream().write(input.getBytes());
            }
```

### Q5. server에서 보내는 문자열을 출력 하기

```java
BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ( (line = reader.readLine()) != null) {
                if (line.equals("exit")) {
                    System.out.println("종료합니다...");
                    break;
                }    
               System.out.println(line);
            
            }

            socket.close();
```

→ `BufferedReader` 를 활용해서 `String` 으로 처리하기!

### Q6. Echo 서버 연결해 문자열 보내고 받기

- **[Server.java](http://Server.java)** 와 **[Client.java](http://Client.java)** 로 echo 통신 구현
- **Server.java**
    
    ```java
    import java.io.*;
    import java.net.*;
    import java.util.Scanner;
    
    public class Server {
    	public static void main(String args[]) throws IOException {
    		Socket socket = null;
    		ServerSocket server_socket = null;	
    		BufferedReader in = null;            //Client로부터 데이터를 읽어들이기 위한 입력스트림
            PrintWriter out = null;   			//Client로 에코 해줄 출력 스트
            String input = null;
    
            try{
    	            server_socket = new ServerSocket(7787); // 포트번호로 연결 
    	            System.out.println("서버 실행 ");
    	         	socket = server_socket.accept(); // 자식 소켓에게 연결 
                    System.out.println("클라이언트 연결 완료 ");
    	        }catch(IOException e)
    	        {
    	            System.out.println("해당 포트가 열려있습니다.");
    	        }
            
            
            while(true) {
                System.out.println("서버 대기중...");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //입력스트림 생성
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))); //출력스트림 생성
            
    	        try {
    	            input = in.readLine();                //Client로부터 데이터를 읽어옴
    
    	            System.out.println("Client로 부터 보낸 메세지 : " + input);
    	            
    	            out.println(input); // 에코 해줄 데이터 Client로 보냄
    	            out.flush(); // 버퍼링 제거 
    	            
    	        }
    	        catch(IOException e)
    	        {
    	        	System.out.println("서버 통신 오류 발생. ");
    	        }
    	        
    	        
            }
    
    	}
    }
    ```
    
- **Client.java**
    
    ```java
    import java.io.*;
    import java.net.*;
    import java.util.*;
    
    import javax.swing.*;
    
    public class Client {
    	 public static void main(String[] arg)
    	    {
    	        Socket socket = null;            //Server와 통신하기 위한 Socket 선언 
    	        PrintWriter out = null;            //서버로 내보내기 위한 출력 스트림
    	        InetAddress ia = null;			// 클라이언트의 ip 주소 
    	        String echo = null;				// 에코를 출력할 스트
                BufferedReader reader = null;
    	        Scanner s = new Scanner(System.in);
    	        try {
    	        	ia = InetAddress.getLocalHost();  //ip 할당 
    	            System.out.println(ia);
    	            socket = new Socket(ia,7787); // 서버와 연결 
                    
    	            System.out.println(socket.toString()); // 연결 정보 출력 
                    System.out.println("서버 연결 완료 ");
    	        } catch(IOException e) {
                    throw new IllegalAccessError("echo");
    	        }
    	        
    	        while(true) {
    	        try {
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))); //출력스트림 생성
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    	            System.out.print("서버로 보낼 메세제 : ");
    	            String data = s.nextLine();            //키보드로부터 입력
    	            if(data.equals("exit")) {  // 종료 
    	            	System.out.println("Client 종료");
    	            	socket.close();
                        s.close();
    	            	System.exit(0);
    	            }
    
                    out.println(data); // 문자열을 소켓을 통해 전송
                    out.flush(); // 버퍼를 비워서 데이터를 전송
    
                    System.out.println("서버에서 보낸 echo 메세지: " + reader.readLine());
    	        } catch(IOException e) {
    	        	System.out.println("통신 오류 발생! ");
    	        }
    	        }
                
    	    }
    }
    ```
    

### Socket Vs Server Socket

- Socket
    - 호스트간 통신을 위한 양쪽의 끝
- ServerSocket
    - 서버 프로그램에서 사용하는 소켓
    - `Socket` → `ServerSocket` → `Socket`
    - 클라이언트 → `ServerSocket` → 서버
    - 살짝 문…같은거...
    

### Multi - Connection Server

- 동시에 다수의 client가 연결할 수 있도록 thread를 통해 분리해 보도록 만든다.
- Server socket은 대기 상태에서 client 연결이 이루어지면 이를 처리하기 위한 thread를 생성하여 생성된 socket을 넘겨주고 server socket은 다시 새로운 연결을 기다리도록 한다.

- `[Server.java](http://Server.java)`
    
    ```java
    public class Server {
    	public static void main(String args[]) throws IOException {
    		ServerSocket server_socket = null;	
    
            while (true) {
                server_socket = new ServerSocket(7787); // 포트번호로 연결 
                System.out.println("서버 실행 ");
                
                ClientServer childserver = new ClientServer(server_socket.accept());
                childserver.start();
                server_socket.close();
    
            }
    	}
    }
    ```
    
- `ClientServer.java`
    
    ```java
    public class ClientServer implements Runnable{
        
        Thread thread;
        BufferedReader in = null;            //Client로부터 데이터를 읽어들이기 위한 입력스트림
        PrintWriter out = null;   			//Client로 에코 해줄 출력 스트
        String input = null;
        Socket socket;
    
        public ClientServer(Socket s) {
            System.out.println("자식 서버로 이동");
            this.socket = s;
            thread = new Thread(this);
        }
    
        public void start(){
            thread.start();
        }
    
        @Override
        public void run() {
    
            while (true) {
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //입력스트림 생성
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))); //출력스트림 생성
                    input = in.readLine();                //Client로부터 데이터를 읽어옴
    
                    System.out.println("Client로 부터 보낸 메세지 : " + input);
                    
                    if (input.equals("exit")) {
                        socket.close();
                    }
                    out.println(input); // 에코 해줄 데이터 Client로 보냄
                    out.flush(); // 버퍼링 제거 
                    
                }
                catch(IOException e)
                {
                    System.out.println("서버 통신 오류 발생. ");
                }
            }
        }
    }
    ```
    

### Multi -  client chat

- Client 끼리 @를 사용해 귓속말을 사용할 수 있도록 만든다.
- `[Server.java](http://Server.java)`
    
    ```java
    import java.io.*;
    import java.net.*;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Scanner;
    
    public class Server {
        static List<ClientServer> serverlist = new ArrayList<>();	
    	public static void main(String args[]) throws IOException {
    		ServerSocket server_socket = null;
            Scanner scanner = new Scanner(System.in);
            while (true) {
                server_socket = new ServerSocket(7787); // 포트번호로 연결 
                
                ClientServer childserver = new ClientServer(server_socket.accept(), scanner);
                childserver.start();
                serverlist.add(childserver);
                server_socket.close();
    
            }
    
    	}
    
        public static void findUser(String id, String message) throws IOException {
            for (ClientServer clientServer : serverlist) {
                if (clientServer.getID().equals(id)) {
                        System.out.println(clientServer.getID() + "를 찾음!");
    
                        PrintWriter out = clientServer.getPrintWriter();
                        out.println(message);
                        out.flush();
                    
                }
            }
        }
    }
    ```
    
- `ClientServe.java`
    
    ```java
    import java.io.*;
    import java.net.*;
    import java.util.Scanner;
    import java.util.StringTokenizer;
    
    public class ClientServer implements Runnable{
        
        private Thread thread;
        private BufferedReader in = null;            //Client로부터 데이터를 읽어들이기 위한 입력스트림
        private PrintWriter out = null;   			//Client로 에코 해줄 출력 스트
        private String input = null;
        private Socket socket;
        private String id;
        
    
        public ClientServer(Socket s, Scanner scanner) {
            System.out.println("자식 서버로 이동");
             System.out.print("유저 이름 입력: ");
            
            this.id = scanner.nextLine();
            this.socket = s;
            thread = new Thread(this);
        }
    
        public void start(){
            thread.start();
        }
    
        @Override
        public void run() {
    
            while (true) {
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //입력스트림 생성
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))); //출력스트림 생성
                    input = in.readLine();                //Client로부터 데이터를 읽어옴
    
                    if(input.charAt(0) == '@') {
                        StringTokenizer st = new StringTokenizer(input.substring(1));
                        String userid = st.nextToken();
            
                        Server.findUser(userid, input.substring(userid.length() + 1));
                        continue;
                    }
    
                    System.out.println( id + "로 부터 보낸 메세지 : " + input);
                    
                    if (input.equals("exit")) {
                        socket.close();
                    }
    
                    
                }
                catch(IOException e) {
                    System.out.println("서버 통신 오류 발생. ");
                }
            }
        }
    
        public String getID() {
            return id;
        }
    
        public PrintWriter getPrintWriter() throws IOException{
            return new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))); //출력스트림 생성;
        }
    
        
    
    }
    ```
    
- `[Client.java](http://Client.java)`
    
    ```java
    import java.io.*;
    import java.net.*;
    import java.util.*;
    
    import javax.imageio.IIOException;
    import javax.swing.*;
    
    public class Client {
    	 public static void main(String[] arg) throws IOException
    	    {
    	        Socket socket = null;            //Server와 통신하기 위한 Socket 선언 
    	        PrintWriter out = null;            //서버로 내보내기 위한 출력 스트림
    	        InetAddress ia = null;			// 클라이언트의 ip 주소 
                BufferedReader in = null;
    	        Scanner s = new Scanner(System.in);
    	        try {
    	        	ia = InetAddress.getLocalHost();  //ip 할당 
    	            System.out.println(ia);
    	            socket = new Socket(ia,7787); // 서버와 연결 
    				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))); //출력스트림 생성
    				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    Reciver reciver = new Reciver(socket, in);
    				reciver.start();
    	            System.out.println(socket.toString()); // 연결 정보 출력 
                    System.out.println("서버 연결 완료 ");
    	        } catch(IOException e) {
                    throw new IOException("서버 연결 오류");
    	        }
    	        
    	        while(true) {
    				try {
    					String data = s.nextLine();            //키보드로부터 입력
    					if(data.equals("exit")) {  // 종료 
    						System.out.println("Client 종료");
    						socket.close();
    						s.close();
    						System.exit(0);
    					}
    
    					out.println(data); // 문자열을 소켓을 통해 전송
    					out.flush(); // 버퍼를 비워서 데이터를 전송
    
    					
    				} catch(IOException e) {
    					System.out.println("통신 오류 발생! ");
    				}
    	        }
                
    	    } 
    
    }
    
    class Reciver extends Thread {
    	Socket socket = null;
    	BufferedReader in = null;
    	String message = "";
    
    	
    
    	Reciver(Socket s, BufferedReader pr) {
    		this.socket = s;
    		this.in = pr;
    	}
    
    	@Override
    	public void run()  {
    
    		while (true) {
    			try {
    				message = in.readLine();
    			} catch (IOException e) {}
    			
    			System.out.println("수신된 메세지 : " + message);
    
    			try {
    				Thread.sleep(2000);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}	
    
    	}
    }
    ```
    
    - `Client` 에 `Reciver` Thread를 추가하여 송신과 수신을 나눠서 해결