import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class InputFile {
	private List<String> list; // templist
	private String[] clues;
	private String group;
	/**
	 * First line must be group name and each of the following line is a full-word string 
	 * @param filePath
	 */
	public InputFile(String filePath) {
		list = new ArrayList<String>();
		BufferedReader br;
		String line;
		try {
			br = new BufferedReader(new FileReader(filePath));
			group = br.readLine(); // first line is group name

			while ((line=br.readLine())!=null) {
				list.add(line); // get group
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getGroup() {
		return group;
	}
	public String[] getClues() {
		Collections.shuffle(list); // shuffle it!
		clues = new String[list.size()];
		for (int i=0; i<list.size(); i++) {
			clues[i] = list.get(i);
		}
		return clues;
	}
} 
