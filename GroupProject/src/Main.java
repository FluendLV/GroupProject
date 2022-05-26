import java.io.*;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {
	
	public static Scanner sc;
    
	public static void main(String[] args) throws Exception {
		sc = new Scanner(System.in);
		String choise="";
		
		loop : while(true) {
			try {
			System.out.println("input option:");
			
			choise=sc.next();
			
			switch(choise) {
			
			case "encode" : encoder();
			break;
			
			case "decode" : decoder();
			break;

			case "size":
				size();
				break;

			case "equal":				
				equal();
				break;
				
			case "about":
				about();
				break;

			case "exit":
				break loop;
			
			default: System.out.println("wrong command");
					break;
		
			}

			
		} catch (FileNotFoundException fnf){
			System.out.println("Wrong filename!");
			continue;
		} catch (Exception ex){
			System.out.println("Input error");
			continue;
		}
	}
			
		
		
		sc.close();
		
	
}

	public static void size() {
		
		System.out.print("file name: ");
		String sourceFile = sc.next();
		
		try {
			FileInputStream f = new FileInputStream(sourceFile);
			System.out.println("size: " + f.available());
			f.close();
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

	}

	public static void equal() {
		System.out.print("first file name: ");
		String firstFile = sc.next();
		System.out.print("second file name: ");
		String secondFile = sc.next();
		try {
			FileInputStream f1 = new FileInputStream(firstFile);
			FileInputStream f2 = new FileInputStream(secondFile);
			int k1, k2;
			byte[] buf1 = new byte[1000];
			byte[] buf2 = new byte[1000];
			do {
				k1 = f1.read(buf1);
				k2 = f2.read(buf2);
				if (k1 != k2) {
					f1.close();
					f2.close();
					System.out.println(false);
					return;
				}
				for (int i=0; i<k1; i++) {
					if (buf1[i] != buf2[i]) {
						f1.close();
						f2.close();
						System.out.println(false);
						return;
					}
						
				}
			} while (k1 == 0 && k2 == 0);
			f1.close();
			f2.close();
			System.out.println(true);
			return;
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
			System.out.println(false);
		}
	}
	
	public static void about() {
		System.out.println("Team Sidewinder:>");
		System.out.println("211RDB283 Oļegs Fjodorovs");
		System.out.println("211RDB206 Liene Saklaure");
		System.out.println("211RDB484 Sarmīte Vineta Laukakmene");
		System.out.println("211RDB265 Ģirts Trapāns");
		System.out.println("211RDB389 Artūrs Romaška");
	}
	
	public static void encoder() throws Exception {
		
		
		System.out.println("input file:");
		String inputF=sc.next();
		System.out.println("output file:");
		String outputF=sc.next();
		Encoder encoder = new Encoder(inputF,outputF);
		encoder.encodeFile();
		System.out.println("file encoded");
		
	}
	
	public static void decoder() throws Exception {
		
		System.out.println("input file:");
		String inputF=sc.next();
		System.out.println("output file:");
		String outputF=sc.next();
		Decoder decoder = new Decoder(inputF,outputF);
		decoder.decodeFile();
		System.out.println("file decoded");
		

	}
}


class CFileBitReader {
    
   	private String fileName;
	private File inputFile;
	private FileInputStream fin;
	private BufferedInputStream in;
	private String currentByte;
	
	
	public CFileBitReader() throws Exception{
		try{
			fileName = "";
		}catch(Exception e){
			throw e;
		}
		
	}
	
	public CFileBitReader(String txt) throws Exception{
		try{
			fileName = txt;
			loadFile(fileName);
		}catch(Exception e){
			throw e;
		}
		
	}
	
	public boolean loadFile(String txt) throws Exception{
		fileName = txt;
		
		try
		{
			inputFile = new File(fileName);
			fin = new FileInputStream(inputFile);
			in = new BufferedInputStream(fin);
			currentByte = "";
			return true;

		}catch(Exception e){
			throw e;
		}
		
	}

	 String leftPad8(String txt){
		while(txt.length() < 8 )
			txt =  "0" + txt;
		return txt;
		}
	 String rightPad8(String txt){
		while(txt.length() < 8 )
			txt +=  "0";
		return txt;
		}
		
	//Get EVERY SINGLE BITE of a bits link
	public String getBit() throws Exception{
		try{
			if(currentByte.length() == 0 && in.available() >= 1){
				int k = in.read();
				
				currentByte = Integer.toString(k,2);
				currentByte = leftPad8(currentByte);
			}
			if(currentByte.length() > 0){
			String ret = currentByte.substring(0,1);
			currentByte = currentByte.substring(1);
			return ret;
			}
			return "";
		}catch(Exception e){
			throw e;
		}
	}
	
	//Get bits of Bytes
	public String getBits(int n) throws Exception{
		try{
			String ret = "";
			for(int i=0;i<n;i++){
			 ret += getBit();
			}
			return ret;
		}catch(Exception e){
			throw e;
		}
	}
	
	//Read the bytes of String
	public String getBytes(int n) throws Exception{
		try{
			String ret = "",temp;
			for(int i=0;i<n;i++){
				temp = getBits(8);
				char k = (char)Integer.parseInt(temp,2);
				ret += k;
			}
			return ret;
		}catch(Exception e){
			throw e;
		}
	}
	
	public boolean eof() throws Exception{
		try{
			
			return (in.available()== 0);
		}catch(Exception e){
			throw e;
		}
		
		}
		
	public long available() throws Exception{
		try{
			return in.available();
		}catch(Exception e){
			throw e;
		}
		
		}


	public void closeFile() throws Exception{
		try{
			in.close();
		}catch(Exception e){
			throw e;
		}
		
		}

    
}

class CFileBitWriter {
    
   	private String fileName;
	private File outputFile;
	private FileOutputStream fout;
	private BufferedOutputStream outf;
	private String currentByte;
	

	//WriteBits
	public CFileBitWriter(String txt) throws Exception{
		try{
			fileName = txt;
			loadFile(fileName);
		}catch(Exception e){
			throw e;
		}
		
	}
	
	public boolean loadFile(String txt) throws Exception{
		fileName = txt;
		
		try
		{
			outputFile = new File(fileName);
			fout = new FileOutputStream(outputFile);
			outf = new BufferedOutputStream(fout);

			currentByte = "";
			return true;

		}catch(Exception e){
			throw e;
		}
		
	}
	
	public void putBit(int bit) throws Exception{
		
		try{
			bit = bit % 2;
			currentByte = currentByte + Integer.toString(bit);
			
			if(currentByte.length() >= 8){
				
				int byteval = Integer.parseInt(currentByte.substring(0,8),2);
				outf.write(byteval);
				currentByte = ""; //reset
			}
			
		}catch(Exception e){throw e;}
		}
	
	public void putBits(String bits) throws Exception{
		
		try{
			while(bits.length() > 0){
			int bit = Integer.parseInt(bits.substring(0,1));
			putBit(bit);
			bits = bits.substring(1);
			}
		}catch(Exception e){throw e;}
		}

	public void putString(String txt) throws Exception{
		
		try{
			while(txt.length() > 0){
			String binstring = Integer.toString(txt.charAt(0),2);
			binstring = leftPad8(binstring );
			
			putBits(binstring);
			txt = txt.substring(1);
			}
		}catch(Exception e){throw e;}
		}
		
	 String leftPad8(String txt){
		while(txt.length() < 8 )
			txt =  "0" + txt;
		return txt;
		}
	 String rightPad8(String txt){
		while(txt.length() < 8 )
			txt +=  "0";
		return txt;
		}
		
	public void closeFile() throws Exception{
		
		try{
		
		//check if incomplete byte exists
		while(currentByte.length() > 0){
			putBit(1);
			}
		outf.close();
		
		}catch(Exception e){ throw e;}
		}
		
    
}
class Encoder {
	
 public int MAXCHARS = 256;
 public String strExtension = ".lzw";
 public String lzwSignature = "LZW";
 public int MAXCODES = 4096;
 public int MAXBITS = 12;
    
private String inputFilename,outputFilename;
private long inputFilelen;



private FileInputStream fin;
private BufferedInputStream in;


public Encoder() {
	inputFilename = "";
	outputFilename = "";
	inputFilelen = 0;
}

	public Encoder(String txt){
	inputFilename = txt;
	outputFilename = txt + strExtension;
	inputFilelen = 0;
	}
	
public Encoder(String txt,String txt2){

	inputFilename = txt;
	outputFilename = txt2;
	inputFilelen = 0;
	}
	
@SuppressWarnings("removal")
public boolean encodeFile() throws Exception{
	
	if(inputFilename.length() == 0) return false;
	CFileBitWriter out;
	
	try{
		fin = new FileInputStream (inputFilename);
		in = new BufferedInputStream(fin);
		
		out = new CFileBitWriter(outputFilename);
		
	}catch(Exception e){
		throw e;
	}
	
	try{
	
	inputFilelen = in.available();
	if(inputFilelen == 0 ) throw new Exception("\nFile is Empty!");
    
    Hashtable <String,Integer> table = new Hashtable<String,Integer>();
		
    for(int k=0;k<MAXCHARS;k++){
    	String buf = "" + (char)k;
    	table.put(buf,new Integer(k));
    }
    
	out.putString(lzwSignature);
	out.putBits(leftPadder(Long.toString(inputFilelen,2),32));
	
	long i = 0;
	int codesUsed = MAXCHARS;
	
	int currentCh = 0;
	String prevStr = "";

		while(i < inputFilelen){
			currentCh = in.read();
			i++;
			String temp = prevStr + (char)currentCh;
			Integer e  = table.get(temp);
			
			if(e == null){ //not found
				if(codesUsed < MAXCODES) table.put(temp,codesUsed++);
				
				Integer encoded = table.get(prevStr);
				if(encoded!=null){
				String wri = leftPadder(Integer.toString(encoded.intValue(),2),MAXBITS);
				out.putBits(wri);
				}
				
				prevStr = "" + (char)currentCh;
			}else{
				prevStr = temp;
			}
			}
	
	
	Integer encoded = table.get(prevStr);
	if(encoded != null){
	String wri = leftPadder(Integer.toString(encoded.intValue(),2),MAXBITS);
	out.putBits(wri);
	}
	
	out.closeFile();
	}catch(Exception e){
		throw e;
	}
		return true;		
	}

	
String leftPadder(String txt,int n){
	while(txt.length() < n )
		txt =  "0" + txt;
	return txt;
	}

String rightPadder(String txt,int n){
	while(txt.length() < n )
		txt += "0";
	return txt;
	}


}

class Decoder {
	
	 public int MAXCHARS = 256;
	 public String strExtension = ".lzw";
	 public String lzwSignature = "LZW";
	 public int MAXCODES = 4096;
	 public int MAXBITS = 12;
    
    private String inputFilename,outputFilename;
	private long inputFilelen,outputFilelen;
	
	
	private FileOutputStream fout;
	private BufferedOutputStream out;
	
	
    
		
	public Decoder(String txt,String txt2){
		inputFilename = txt;
		outputFilename = txt2;
		inputFilelen = 0;
		outputFilelen = 0;
		}
		
	@SuppressWarnings("removal")
	public boolean decodeFile() throws Exception{
		
		if(inputFilename.length() == 0) return false;
		CFileBitReader in;
		
		try{
			in = new CFileBitReader(inputFilename);
			
			fout = new FileOutputStream (outputFilename);
			out = new BufferedOutputStream(fout);
		}catch(Exception e){
			throw e;
		}
		
		try{		
		inputFilelen = in.available();
		if(inputFilelen == 0 ) throw new Exception("\nFile is Empty!");
		
		
        Hashtable <Integer,String> table = new Hashtable<Integer,String>();
   		
   		
   		for(int k=0;k<MAXCHARS;k++){
        	String buf = "" + (char)k;
        	table.put(new Integer(k),buf);
        }
        
		String sig = in.getBytes(lzwSignature.length());
		if(!sig.equals(lzwSignature)) return false;
		
		long i = 0;
		int codesUsed = MAXCHARS;
		int encodedCodeword = 0;
		String prevStr,codeWord = "";
		prevStr = "";

		//first byte		
		encodedCodeword = Integer.parseInt(in.getBits(MAXBITS),2);
		String encodedString = table.get(encodedCodeword );
		out.write(encodedString.getBytes());
		i += encodedString.length();
		codeWord = encodedString;
		
		while(i < outputFilelen){
			encodedCodeword = Integer.parseInt(in.getBits(MAXBITS),2);
			encodedString = table.get(encodedCodeword );

			if(encodedString != null){
					prevStr = encodedString;
			}else{
					prevStr = codeWord;
					prevStr = prevStr + codeWord.charAt(0);
			}
			
			for(int j=0;j<prevStr.length();j++)
			out.write(prevStr.charAt(j));
			
			i += prevStr.length();
			if(codesUsed < MAXCODES) table.put(codesUsed++,codeWord + prevStr.charAt(0));
			codeWord = prevStr;
			}
		
		out.close();
		}catch(Exception e){
			
			throw e;
		}

			return true;		
		}

		
	String leftPadder(String txt,int n){
		while(txt.length() < n )
			txt =  "0" + txt;
		return txt;
		}
	
	String rightPadder(String txt,int n){
		while(txt.length() < n )
			txt += "0";
		return txt;
		}
   
}