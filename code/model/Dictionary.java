package code.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
	private String dictFilename;
	private ArrayList<String> words;
	
	/**
	 * Constructs the Dictionary by reading the given file.
	 */
	public Dictionary(String dictFilename) {
		this.dictFilename = dictFilename;
		words = new ArrayList<String>();
		try {
			Scanner input = new Scanner(new File(dictFilename));
			while(input.hasNextLine()) {
				words.add(input.nextLine().toUpperCase());
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the file path of this Dictionary
	 */
	public String getFilepath() {
		return dictFilename;
	}
	
	/**
	 * Determines whether the dictionary contains the given word
	 * @param word
	 * @return
	 */
	public boolean contains(Word word) {
		String wordString = word.toString();
		for(String w : words) {
			if(w.equals(wordString)) {
				return true;
			}
		}
		return false;
	}
}
