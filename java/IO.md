## 스트림

  스트림은 기본적으로 __프로그램__과 __데이터 소스(source)__ 또는 __싱크__(sink) 사이의 연결이다. 기본적으로 클래스로 표현되며 소스 또는 싱크의 유형에 따라 다르다.

  예를 들어 소스 또는 싱크가 파일이면 그에 걸맞는 특정 유형의 스트림을 사용한다. 마찬가지로 네트워크를 다룰 때 다른 유형의 스트림을 사용하여 스트림을 처리할 수 있다.

  스트림에는 세가지 작업이 있다.

1. Open stream : 스트림을 연다
2. Read / Write : 열려진 스트림이 입력/출력인지 여부에 따라 데이터를 읽거나 쓴다
   - 즉, 입력 스트림을 처리하는 경우 input stream에서 데이터를 읽고
   - 출력 스트림을 다루고 있다면 output stream을 연다.
3. Close Stream : 스트림을 닫는다. 스트림을 닫으면 시스템 리소스를 확보할 수 있다.
   - 스트림 사용을 마치면 연관 시스템 리소스가 released 된다. (이 작업이 실행되지 않으면 새해당 리소스가 열린 상태로 유지되고 새 스트림을 만들 수 없음)

```ㅓㅁㅍㅁ
FileInputStream in = null;
try {
	// InputStream은 파일 입력 스트림이다.
	// 뒤에서 데이터를 읽을 수 있는 도구가 필요하다.
	in = new FIleInputStream(filename); // open stream

	// read data
} catch (FileNotFOundException e) {
	// 입력 스트림 객체 파일 입력 스트림 클래스의 생성자는
	// FileNotFoundExcepiotn 을 발생시킬 수 있다.
	...
} finally {
	// 스트림의 close 문은 입력 스트림에서
	// 마지막 메소드를 호출하여 finally 블록에서 수행된다.
	try {
		// try catch 구문 : close 메소드도 lowa exception을 throw 할 수 있음
		// 또한 참조 변수가 null인지 아닌지 확인함
		if (in != null)
			// 문자열을 닫는다!
			in.close();
	}catch (IOException e){ ... }
}
```

=> Java 7에서 finally 블록을 생략할 수 있다! (Automatic resource management : 하나 또는 그 이상의 리소스를 가지는 try 구문안에서 리소스를 자동으로 close 해줌)

```java
// close 메서드가 암시적으로 호출되고 
// 실제로 컴파일러가 바이트 코드로 차단됨
// FileInputStream -> java.lang.AutoCLoseable
// in -> final
try (FileInputStream in = new FileInputStream(filename)) {
    // Read data
} catch (FileNotFOundException e) {
    ...
} catch (IOException e) {
    ...
}
```

```java
// 괄호 안에 여러 스트림이 생성되는 경우
// 스트림 -> 세미콜론으로 구분
try (FileInputStream in = new FileInputStream(filename);
    FileOutputStream out = new FileOutputStream(filename)) {
        // Read data
    }
} catch (FileNotFOundException e) {
    ...
} catch (IOException e) {
    ...
}

```

## Byte Streams

  Byte Streams는 row bytes로 빌드할 때 사용된다.

  InputStream과 OutputStream은 Byte 지향 클래스를 기반으로 클래스를 처리한다.

- InputStream : Byte input streams에 대한 추상 클래스. 8개의 byte 그룹으로 데이터를 읽는 데 사용한다. 정확히 1바이트에 도달하고 해당하는 데이터를 반환한다. 실제 작업을 수행하는 동안 문제가 있으면 메서드에서 예외가 발생한다.

  ```java
  // byte 그룹을 읽는 데 사용되는 메서드
  // byte[] b : byte 배열이 이 메서드를 통해 전달되고
  // int offset : 인덱스 위치 offset 에서 시작하여
  // 읽을수 있는 바이트 수 int length 까지 읽는당
  // 다 읽었으면 -1을 반환한다
  int read(byte[] b, int offset, int length) throws IOException
  ```

- OutputStrema : Byte output streams에 대한 추상 클래스. 입력 스트림과 같은 클추상클래스이며 8개의 byte 그룹으로 데이터를 쓰는데 사용된다.

  ```java
  // offset에서 시작하여 
  //입력 배열 byte[] b에서
  // int length 길이 바이트 수를 쓴다.
  void write(byte[] b, int offset, int length) throws IOException    
  
  ```

  

## Character Streams

  Character Streams 는 텍스트 파일과 같은 문자를 읽거나 쓰는 데 사용되지만, 본질적으로는 모든 것이 binary 이므로 byte streams 위에 위치하고 있다.

Input stream과 output stream이 일부 기본 클래스를 교차하는 방식과 마찬가지로 모든 byte 지향 스트림은 서비스 기반 클래스를 읽는다.



## File Class

기본적으로 파일을 다룰 때 디스크의 모든 파일이나 디렉토리는 고유한 성질을 갖고 있다. 디렉토리에서 파일을 찾으면 파일 클래스는 이를 인스턴스화 할 수 있다.

`File f = new File("go.txt")`

```java
public class IODemo {
	static String inFileStr = "walden.jpg";
	static String outFileStr = "walden-out.jpg";
	
	public static void fileCopyNoBuffer() {
		System.out.println("\nInside fileCopyNoBuffer ...");
		
		long startTime, elapsedTime; // for speed benchmarking

		// Print file length
		File fileIn = new File(inFileStr);
		System.out.println("File size is " + fileIn.length() + " bytes");

		try (FileInputStream in = new FileInputStream(inFileStr);
				FileOutputStream out = new FileOutputStream(outFileStr)) {
			startTime = System.nanoTime();
			int byteRead;
			// Read a raw byte, returns an int of 0 to 255.
			while ((byteRead = in.read()) != -1) {
				// Write the least-significant byte of int, drop the upper 3
				// bytes
				out.write(byteRead);
			}
			elapsedTime = System.nanoTime() - startTime;
			System.out.println("Elapsed Time is " + (elapsedTime / 1000000.0) + " msec");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Most common way to read byte streams from a file
	public static void fileCopyWithBufferAndArray() {
		System.out.println("\nInside fileCopyWithBufferAndArray ...");
		
		long startTime, elapsedTime; // for speed benchmarking
		startTime = System.nanoTime();
		try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(inFileStr));
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFileStr))) {

			byte[] byteBuf = new byte[4000];
			int numBytesRead;
			while ((numBytesRead = in.read(byteBuf)) != -1) {
				out.write(byteBuf, 0, numBytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		elapsedTime = System.nanoTime() - startTime;
		System.out.println("fileCopyWithBufferAndArray: " + (elapsedTime / 1000000.0) + " msec");
	}
	
	private static void readFromStandardInput() {
		System.out.println("\nInside readFromStandardInput ...");
		String data;
		/*
		System.out.print("Enter \"start\" to continue (Using BufferedReader): ");		
				
		try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"))){
			while ((data = in.readLine()) != null && !data.equals("start")) {
				System.out.print("\nDid not enter \"start\". Try again: ");				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Correct!!");
		*/
		
		
		System.out.print("\nEnter \"start\" to continue (Using java.util.Scanner): ");		
		Scanner scanner = new Scanner(System.in);
		
		while(!(data = scanner.nextLine()).equals("start")) {
			System.out.print("\nDid not enter \"start\". Try again: ");	
	    }	
		System.out.println("Correct!!");
		
		
		System.out.println("Now, enter the start code: ");
		int code = scanner.nextInt(); // other methods: nextLong, nextDouble, etc
		System.out.println("Thanks. You entered code: " + code);
		
		
		/**
		 * Scanner ~ a text scanner for parsing primitives & string
		 *         ~ breaks its input into tokens using a delimited pattern (default: whitespace)
		 *         ~ when System.in is used, internally constructor uses 
		 *            an InputStreamReader to read from it
		 *         ~ hasXXX & nextXXX can be used together
		 *         ~ InputMismatchException is thrown
		 *         ~ From Java 5 onwards
		 */
		
		Scanner s1 = new Scanner("Hello, How are you?");
		while(s1.hasNext()) {
			System.out.println(s1.next());
		}	
	}
	
	public static void fileMethodsDemo() {
		System.out.println("\nInside fileMethodsDemo ...");
		
		File f = new File("C:\\jid\\demo\\src\\..\\walden.jpg"); // "movies\\movies.txt" also works
		//File f = new File("walden.jpg");
		
		System.out.println("getAbsolutePath(): " + f.getAbsolutePath());
		try {
			System.out.println("getCanonicalPath(): " + f.getCanonicalPath());
			System.out.println("createNewFile(): " + f.createNewFile());
		} catch (IOException e) {}		
		System.out.println("separator: " + f.separator);
		System.out.println("separatorChar: " + f.separatorChar);
		System.out.println("getParent(): " + f.getParent());
		System.out.println("lastModified(): " + f.lastModified());
		System.out.println("exists(): " + f.exists());
		System.out.println("isFile(): " + f.isFile());
		System.out.println("isDirectory(): " + f.isDirectory());
		System.out.println("length(): " + f.length());
		
		System.out.println("My working or user directory: " + System.getProperty("user.dir"));
		System.out.println("new File(\"testdir\").mkdir(): " + new File("testdir").mkdir());
		System.out.println("new File(\"testdir\\test\").mkdir(): " + new File("testdir\\test").mkdir());
		System.out.println("new File(\"testdir\").delete(): " + new File("testdir").delete());
		System.out.println("new File(\"testdir\\test1\\test2\").mkdir(): " + new File("testdir\\test1\\test2").mkdir());
		System.out.println("new File(\"testdir\\test1\\test2\").mkdirs(): " + new File("testdir\\test1\\test2").mkdirs());
		
		try {
			File f2 = new File("temp.txt");
			System.out.println("f2.createNewFile(): " + f2.createNewFile());
			System.out.println("f2.renameTo(...): " + f2.renameTo(new File("testdir\\temp1.txt"))); // move!!
		} catch (IOException e) {}
		
	}
	
	public static void dirFilter(boolean applyFilter) {
	    System.out.println("\nInside dirFilter ...");
	    
	    File path = new File(".");
		String[] list;
		
		if(!applyFilter)
		    list = path.list();
		else
		    list = path.list(new DirFilter());
		
		//Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		for(String dirItem : list)
		    System.out.println(dirItem);
	}

	public static void main(String[] args) {
		//fileCopyNoBuffer();
		//fileCopyWithBufferAndArray();
		//readFromStandardInput();
		fileMethodsDemo();
		dirFilter(true);
	}
}

class DirFilter implements FilenameFilter {
	// Holds filtering criteria
	public boolean accept(File file, String name) {
		return name.endsWith(".jpg") || name.endsWith(".JPG");
	}
}
```



## 직렬화(Serializing)

