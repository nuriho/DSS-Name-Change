package program;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.Test;

public class DssNameChangeTest {
	
	@Test
	public void testGetDssTitle() {
		// testing main seems wrong... just test the other methods. Main calls the other methods if the input args are
		// correct.
		DssNameChange dnc = new DssNameChange();
		String kiEmpress = "기황후.E01.131028.HDTV.H264.720p-HANrel.avi";
		String actual = null;
		
		actual = dnc.getDssTitle(kiEmpress);
		String expected = "기황후.E01.131028.HDTV.H264.720p-HANrel.srt";
		assertTrue("Actual = " + actual + " | expected = " + expected, expected.equals(actual));
	}
	
	@Test
	public void testTrollFilesInFolder() {
		DssNameChange dnc = new DssNameChange();
		String path = "E:/workspace/dssNameChange/test/program/testFiles";
		ArrayList<String> expected = new ArrayList<String>();

		expected.add("I.Hear.Your.Voice.E12.130711.HDTV.x264.1080p-AyoSuzy.mkv");
		expected.add("QS.Empress.Gi.2013-E01_720p-HANrel-darksmurfsub_100-"
				+ "FANS-Translated__100-FANS-Edited__100-Elite-QC-Edited.srt");
		expected.add("QS.Empress.Gi.2013-E03_720p-HANrel-darksmurfsub_100-"
				+ "FANS-Translated__100-FANS-Edited__100-Elite-QC-Edited.srt");
		expected.add("QS.I.Hear.Your.Voice.2013-E12_720p-KOR-darksmurfsub_100-FANS-Translated__100-FANS-Edited__100-Elite-QC-Edited.srt");
		expected.add("기황후.E01.131028.HDTV.H264.720p-HANrel.avi");
		expected.add("기황후.E02.131029.HDTV.H264.720p-HANrel.avi");
		expected.add("기황후.E02.131029.HDTV.H264.720p-HANrel.srt");
		expected.add("기황후.E03.131104.HDTV.H264.720p-HANrel.mp4");
		
		final File folder = new File(path);
		ArrayList<String> actual = dnc.trollFilesInFolder(folder);
		
		assertTrue("Actual = " + actual + " | expected = " + expected, expected.equals(actual));
	}
	
	@Test
	public void testIsSrtAndAviDifferentFileName() {
		String avi = "", srt = "";
		DssNameChange dnc = new DssNameChange();
		
		avi = "기황후.E02.131029.HDTV.H264.720p-HANrel.avi";
		srt = "기황후.E02.131029.HDTV.H264.720p-HANrel.srt";
		assertFalse("Srt filename = " + srt + " | Avi filename = " + avi, dnc.isSrtAndAviDifferentFileName(srt, avi));
		
		avi = "기황후.E01.131028.HDTV.H264.720p-HANrel.avi";
		srt =
				"QS.Empress.Gi.2013-E01_720p-HANrel-darksmurfsub_100-"
						+ "FANS-Translated__100-FANS-Edited__100-Elite-QC-Edited.srt";
		assertTrue("Srt filename = " + srt + " | Avi filename = " + avi, dnc.isSrtAndAviDifferentFileName(srt, avi));
		
	}
	
	@Test
	public void testTypeOfFile() {
		String avi = "", srt = "";
		DssNameChange dnc = new DssNameChange();
		DssNameChange.FileType aviType = DssNameChange.FileType.UNK;
		DssNameChange.FileType srtType = DssNameChange.FileType.UNK;
		
		avi = "기황후.E02.131029.HDTV.H264.720p-HANrel.avi";
		srt = "기황후.E02.131029.HDTV.H264.720p-HANrel.srt";
		aviType = dnc.typeOfFile(avi);
		assertTrue("Expected AVI, Actual = " + aviType.getName(), aviType == DssNameChange.FileType.AVI);
		srtType = dnc.typeOfFile(srt);
		assertTrue("Expected AVI, Actual = " + srtType.getName(), srtType == DssNameChange.FileType.SRT);
		
		aviType = DssNameChange.FileType.UNK;
		srtType = DssNameChange.FileType.UNK;
		avi = "기황후.E01.131028.HDTV.H264.720p-HANrel.avi";
		srt =
				"QS.Empress.Gi.2013-E01_720p-HANrel-darksmurfsub_100-"
						+ "FANS-Translated__100-FANS-Edited__100-Elite-QC-Edited.srt";
		aviType = dnc.typeOfFile(avi);
		assertTrue("Expected AVI, Actual = " + aviType.getName(), aviType == DssNameChange.FileType.AVI);
		srtType = dnc.typeOfFile(srt);
		assertTrue("Expected AVI, Actual = " + srtType.getName(), srtType == DssNameChange.FileType.SRT);
	}
	
	@Test
	public void testGetMatchingEpisodes() {
		DssNameChange dnc = new DssNameChange();
		
		ArrayList<String> files = new ArrayList<String>();
		String ep1srt =
				"QS.Empress.Gi.2013-E01_720p-HANrel-darksmurfsub_100-"
						+ "FANS-Translated__100-FANS-Edited__100-Elite-QC-Edited.srt";
		String ep1avi = "기황후.E01.131028.HDTV.H264.720p-HANrel.avi";
		String ep2srt = "기황후.E02.131029.HDTV.H264.720p-HANrel.srt";
		String ep2avi = "기황후.E02.131029.HDTV.H264.720p-HANrel.avi";
		String ep3srt =
				"QS.Empress.Gi.2013-E03_720p-HANrel-darksmurfsub_100-"
						+ "FANS-Translated__100-FANS-Edited__100-Elite-QC-Edited.srt";
		String ep3avi = "기황후.E03.131104.HDTV.H264.720p-HANrel.mp4";
		
		files.add(ep1srt);
		files.add(ep3srt);
		files.add(ep1avi);
		files.add(ep2avi);
		files.add(ep2srt);
		files.add(ep3avi);
		
		ArrayList<Matches> expected = new ArrayList<Matches>();
		Matches match = new Matches(ep1srt, ep1avi);
		expected.add(match);
		match = new Matches(ep3srt, ep3avi);
		expected.add(match);
		match = new Matches(ep2srt, ep2avi);
		expected.add(match);
		
		ArrayList<Matches> actual = dnc.getMatchingEpisodes(files);
		
		for(int i = 0; i < actual.size(); i++){
			String comment = "index " + i + " :Actual: " + actual.get(i).getSrt() + " || Expected: "+ expected.get(i).getSrt();
			assertTrue(comment, actual.get(i).getSrt().equals(expected.get(i).getSrt()));
			comment = "index " + i + " :Actual: " + actual.get(i).getAvi() + " || Expected: "+ expected.get(i).getAvi();
			assertTrue(comment, actual.get(i).getAvi().equals(expected.get(i).getAvi()));
		}
		
	}
	
	@Test
	public void testSetDssTitle(){
		DssNameChange dnc = new DssNameChange();
		String path = "E:/workspace/dssNameChange/test/program/testFiles";
		
		String srt = "QS.Empress.Gi.2013-E01_720p-HANrel-darksmurfsub_100-"
				+ "FANS-Translated__100-FANS-Edited__100-Elite-QC-Edited.srt";
		String newSrt = "기황후.E01.131028.HDTV.H264.720p-HANrel.srt";
		final File folder = new File(path);
		
		boolean actual = dnc.setDssTitle(folder, srt, newSrt);
		assertTrue(actual);
		
		boolean ret = false;
		try{
		File f = new File(folder.getPath() + "/" + newSrt);
		ret = f.renameTo(new File(folder.getPath() + "/" + srt));
		}catch(Exception e){
			e.printStackTrace();
		}
		assertTrue(ret);
	}
	
	@Test
	public void testMain(){
		DssNameChange dnc = new DssNameChange();
		String expected = "E:\\workspace\\dssNameChange\\test\\program\\Ki Empress";
//		dnc.main(path);
		File file = new File("E:/workspace/dssNameChange/path.txt");
		BufferedReader br;
		try {
			String actual = "";
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line = br.readLine();
			
			if (line != null) {
				actual = line.substring(1, line.length());
			}
			boolean ret = actual.equals(expected);
			assertTrue("Actual: " + actual + " || Expected: " + expected, ret );
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
