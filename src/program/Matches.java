package program;

/**
 * Holds matching filename pairs of srt and avi files by Episode Number.
 * 
 */
public class Matches {
	private String	srt;
	private String	avi;

	/**
	 * Constructor.
	 * 
	 * @param p_srt
	 *            The srt filename.
	 * @param p_avi
	 *            The avi filename.
	 */
	public Matches(String p_srt, String p_avi) {
		srt = p_srt;
		avi = p_avi;
	}
	
	public String getSrt() {
		return srt;
	}
	
	public String getAvi() {
		return avi;
	}
}
