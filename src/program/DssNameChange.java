package program;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DssNameChange {
	/**
	 * Type of File: SRT, AVI, or UNKNOWN
	 * 
	 */
	protected enum FileType {
		SRT("srt"), AVI("avi"), UNK("unkown");
		
		private String	name;
		
		FileType(String p_name) {
			name = p_name;
		}
		
		public String getName() {
			return name;
		}
	}
	
	/**
	 * Change all dss srt name in folder to match video name.
	 * 
	 * @param args
	 *            Path of folder.
	 */
	public void main(String path) {
		// args[0] = path of folder
			final File folder = new File(path);
			
			// troll through path
			ArrayList<String> files = trollFilesInFolder(folder);
			ArrayList<Matches> matching = getMatchingEpisodes(files);

			String srt = "", avi = "";
			// for each matching episode,
			for(Matches match : matching){
				srt = match.getSrt();
				avi = match.getAvi();
				// compare srt to avi
				if (isSrtAndAviDifferentFileName(srt, avi)) {
					// if different, make srt = avi
					String newSrt = getDssTitle(avi);
					// change srt name in path to newSrt
					setDssTitle(folder, srt, newSrt);
				} else {
					// else same, continue
					continue;
				}
			}
			
		
	}
	
	protected boolean setDssTitle(File folder, String srt, String newSrt) {
		boolean ret = false;
		try{
		File f = new File(folder.getPath() + "/" + srt);
		ret = f.renameTo(new File(folder.getPath() + "/" + newSrt));
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * Match all pairs of srt and avi by Episode Number.
	 * 
	 * @param files
	 *            The ArrayList of files in the folder.
	 * @return ArrayList of Matching filename pairs.
	 */
	protected ArrayList<Matches> getMatchingEpisodes(ArrayList<String> files) {
		ArrayList<Matches> ret = new ArrayList<Matches>();
		String episodeNumber = "";
		
		// look at first file, check ep #
		for (int i = 0; i < files.size(); i++) {
			if (typeOfFile(files.get(i)) == FileType.SRT) {
				// search for "(e|E)[0-9][0-9]"
				String patternStr = "(e|E)[0-9][0-9]";
			    Pattern pattern = Pattern.compile(patternStr);
			    Matcher matcher = pattern.matcher(files.get(i));
			    if(matcher.find()){
			    	episodeNumber = files.get(i).substring(matcher.start(), matcher.end());
			    } else {
			    	continue;
			    }
				
				for (int j = 0; j < files.size(); j++) {
					if (typeOfFile(files.get(j)) == FileType.AVI) {
						// search for episode the srt is
						if(files.get(j).contains(episodeNumber)){
							// if episode numbers match, add to matches
							Matches epMatch = new Matches(files.get(i), files.get(j));
							ret.add(epMatch);
							break;
						} else{
							continue;
						}
						
					}
				}
			}
		}
		
		return ret;
	}
	
	protected FileType typeOfFile(String file) {
		FileType ret = FileType.UNK;
		if (file.endsWith(".avi") || file.endsWith(".mp4") || file.endsWith(".mkv") ) {
			ret = FileType.AVI;
		} else if (file.endsWith(".srt")) {
			ret = FileType.SRT;
		}
		return ret;
	}
	
	/**
	 * Compare srt file name to avi file name.
	 * 
	 * @param srt
	 *            The srt file name.
	 * @param avi
	 *            The avi file name.
	 * @return True if different, False otherwise.
	 */
	protected boolean isSrtAndAviDifferentFileName(String srt, String avi) {
		boolean ret = false;
		String[] subName = srt.split(".srt");
		String[] videoName = avi.split("(\\.avi)|(\\.mp4)|(\\.mkv)");
		if (!subName[0].isEmpty() && !videoName[0].isEmpty()) {
			ret = !subName[0].equals(videoName[0]);
		}
		return ret;
	}
	
	/**
	 * Get the DSS Title from input video file name.
	 * 
	 * @param videoName
	 *            Name of the .avi file name.
	 * @return The .srt file name matched to .avi file name.
	 */
	protected String getDssTitle(String videoName) {
		String ret = "";
		
		String[] name = videoName.split("(\\.avi)|(\\.mp4)|(\\.mkv)");
		if (!name[0].isEmpty()) {
			ret = name[0] + ".srt";
		}
		
		return ret;
	}
	
	/**
	 * Troll through files in input folder and add each file to output ArrayList.
	 * 
	 * @param folder
	 *            The path to troll through.
	 * @return ArrayList of each file in folder.
	 */
	protected ArrayList<String> trollFilesInFolder(final File folder) {
		ArrayList<String> ret = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			if (!fileEntry.isDirectory()) {
				ret.add(fileEntry.getName());
			} else {
				// Do not want subdirectories.
				continue;
			}
		}
		return ret;
	}
	
}
