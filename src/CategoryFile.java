import java.io.File;


public class CategoryFile {
	/**
	 * Obtains category name from each clue set; in other words, it returns
	 * the first line of all the files in resources folder 
	 * @param currPackage 1,2,3 
	 */
	public static String[] getNames(int currPackage) {
		File folder = new File("src/resources/File/"+getSubfolder(currPackage)+"/");
		File[] listOfFiles = folder.listFiles();
		String[] out = new String[listOfFiles.length];
		InputFile fi;
		for (int i=0; i<listOfFiles.length; i++) {
			fi = new InputFile(listOfFiles[i].getAbsolutePath());
			out[i] = fi.getGroup();
		}
		return out;
	}
	private static String getSubfolder(int x) {
		return x==1? "Entertainment": x==2? "Academic": "Vocabulary";
	}
	/**
	 * Return filename of specific package and set
	 * @param currPackage 1,2,3
	 * @param currSet 1,2,3,4,5
	 */
	public static String getFileName(int currPackage, int currSet) {
		File folder = new File("src/resources/File/"+getSubfolder(currPackage)+"/");
		File[] listOfFiles = folder.listFiles();
		return listOfFiles[currSet-1].getAbsolutePath();
	}
}
