package program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Main {
	
	public static void main(String[] args) {
		DssNameChange dnc = new DssNameChange();
		// String path = "E:/eh/기황후 (Ki Empress)";
		String path = "";
		File file = new File("E:/workspace/dssNameChange/path.txt");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line = br.readLine();
			
			if (line != null) {
				path = line.substring(1, line.length());
			}
			dnc.main(path);
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
