package code.model;


/**
 * Driver to instantiate Scrabble
 * @author t020
 *
 */
public class Driver {
	/**
	 * Application's entry point 
	 * @param args
	 */
	public static void main(String[] args){
		if(args.length < 3 || args.length > 5) {
			System.out.println("Invalid arguements");
			System.exit(-1);
		}
		String dictionayFilename = args[0];
		
		Scrabble scrabble = new Scrabble();//Creates Scrabble object
		scrabble.setDictionary(dictionayFilename);
		for(int i = 1; i < args.length; i++)
			scrabble.addPlayer(args[i]);
		scrabble.startGame();
	}
}
