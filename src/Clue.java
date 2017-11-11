import java.util.Random;
/**
 * Generates clue by taking out vowels and re-space string
 */
public class Clue {
	private static Random rnd = new Random();
	public static String getClue(String s) {
		String out =  delVowels(s);
		int spaces = countSpaces(out);
		out = out.replace(" ",""); // clump them together
		if (spaces>3) {spaces = 3;}

		for (int i=0; i<spaces; i++) { // re-space
			int pos = rnd.nextInt(out.length());
			if (pos>=1 && out.charAt(pos-1)==' ') continue; // no two adjacent spaces
			if (pos<out.length()-1 && out.charAt(pos+1)==' ') continue;
			out = out.substring(0,pos) + " " + out.substring(pos, out.length());
		}
		return out;
	}
	private static String delVowels(String s) { // and dot
		return s.toLowerCase().replaceAll("[aeiou.]", "").toUpperCase();
	}
	private static int countSpaces(String s) {
		return s.length() - s.replace(" ", "").length();
	}
}
