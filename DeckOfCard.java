import java.util.*;
import java.io.*;
public class DeckOfCard{
	Card[] allCards;
	ArrayList<Card> deck;
	int numberOfCards;
	int currNumber;
	Random random; // generates random index
	Random randomBin; //if indexes same decides which direction to go
	public DeckOfCard(int numberSuits, int numberCards){
		numberOfCards = numberSuits * numberCards;
		currNumber = numberSuits * numberCards;
		allCards = new Card[numberSuits * numberCards];
		for (int i=0; i < numberSuits; i++){
			Suit suitI = new Suit(i);
			for (int j = 0; j < numberCards; j++){
				allCards[i*numberCards + j] = new Card(suitI, j);
			}
		}
		deck = new ArrayList<Card>();
		random = new Random(numberOfCards);
		randomBin = new Random(2);
	}
	public void shuffle(){
		clean();
		HashSet<Integer> usedPosition = new HashSet<Integer>();
		for (int i=0; i<numberOfCards; i++){
			int nextIndex = random.nextInt(numberOfCards);
			if(usedPosition.contains(nextIndex)){
				int smRan = randomBin.nextInt();
				int step;
				if(smRan == 1) {
					step = 1;
				} else{
					step = -1;
				}
				int counter = 10;
				while (usedPosition.contains(nextIndex) && counter >0){
					if(step == -1 && nextIndex == 0){
						nextIndex = numberOfCards -1;
					} else if(step == 1 && nextIndex == numberOfCards -1){
						nextIndex = 0;
					} else{
						nextIndex += step;
					}
				}
			}
			deck.add(allCards[nextIndex]);
			usedPosition.add(nextIndex);
		}
	}
	public Card getNext() throws EmtpyDeck{
		if(deck.size()>0){
			return deck.remove(deck.size()-1);
		}
		throw new EmtpyDeck();
	}
	private void clean(){
		deck = new ArrayList<Card>();
	}
	public static void main(String[] args){
		int numSuits = Integer.parseInt(args[0]);
		int numberCards = Integer.parseInt(args[1]);
		DeckOfCard newDeck = new DeckOfCard(numSuits, numberCards);
		newDeck.shuffle();
		System.out.println("Deck with " + numSuits + " suits and " + numberCards + " cards generated");
		System.out.println("Enter Exit to Exit");
		String command = System.console().readLine();
		while(!command.equals("Exit")){
			switch(command){
				case "Shuffle":
					newDeck.shuffle();
					break;
				case "GetNextCard":
					try{
						Card next = newDeck.getNext();
						System.out.println("Suit: " + next.suit.suit+ ", Card:" + next.value);
					} catch (EmtpyDeck err){
						System.out.println("Deck is Empty");
					};
					break;
				case "exit":
					return;
				case "":
					break;
				default:
					System.out.println("No such command");
			}
			command = System.console().readLine();
		}
	}


		//throws exception if deck is empty
	private class EmtpyDeck extends Exception{
      //Parameterless Constructor
    	public EmtpyDeck() {}

      //Constructor that accepts a message
    	public EmtpyDeck(String message){
	        super(message);
	    }
 	}
}