import java.util.Random;
class Deck {

    /**
     * set of cards present in a deck
     */
    Card[] cards;

    /**
     * This constructor creates a new Deck object and populates the deck with the cards appropriate for the UNO game
     */
    public Deck(){

        int i = 0;
        cards = new Card[108];

        for(int colour = 0;colour < 4; colour ++){

            for(int rank = 0;rank < 13;rank ++){

                if(rank > 12){

                    cards[i]= new Card(4, rank);                

                }else{

                    cards[i] = new Card(colour, rank);

                }

                i++;

            }

        }

        for(int colour2 = 0; colour2 < 4; colour2 ++){

            for(int rank2 = 1; rank2 < 13; rank2++){

                cards[i]= new Card(colour2, rank2);
                i++;

            }

        }

        for(int r = 13;r<15;r++){
            for(int n = 0;n<4;n++)cards[i++] = new Card(5,r);
        }
    }

    /**
     *  this constructor initializes the instance variable to the value of the input parameter, an array of Card objects.
     */
    public Deck(Card[] cards){

        this.cards = cards;

    }

    
     /**
     * this method shuffles a Deck object's array of cards
     */
    private void shuffleDeck(){

        for(int i = 0; i < cards.length; i++){

            this.swapCards(i,randomInt(0,cards.length));

        }
        this.refineShuffle();

    }

    private void refineShuffle(){
        int count = 0;
        Card old = cards[0];
        Card cur;
        for(int i = 1;i < cards.length;i++){
            cur = cards[1];
            if(old.rank == cur.rank ||old.colour == cur.colour)count++;
            else count = 0;
            if(count >=3)this.swapCards(i,randomInt(i,cards.length));
        }
    }
    
    public void shuffler(){
        Deck deck = mergeShuffle(cards);
        this.cards = deck.cards;
    }

    private static Deck mergeShuffle(Card[] cards){
        if(cards.length <=1)return new Deck(cards);
        Card[] half1 = new Card[cards.length/2];
        for(int i = 0; i < half1.length;i++)half1[i] = cards[i];
        Deck one = new Deck(half1);
        one.shuffleDeck();
        Card[] half2 = new Card[(cards.length)-(half1.length)];
        int j = 0;
        for(int k = half1.length; k < cards.length;k++)half2[j++] = cards[k];
        Deck two = new Deck(half2);
        two.shuffleDeck();
        one = mergeShuffle(half1);
        two = mergeShuffle(half2);
        return merge(one,two);
    }
    
    private static Deck merge(Deck one,Deck two){
        Card[] full= new Card[(one.cards.length + two.cards.length)];
        int i= 0;
        int j =0;
        for(int k = 0; k < full.length;k++){
            if(i >= one.cards.length){
                if(j<=two.cards.length){
                    full[k] = two.cards[j++];
                }
            }else if(j >=two.cards.length){
                full[k] = one.cards[i++];
            }else if(i <=one.cards.length&& j <=two.cards.length){
                if(k%2 == 0 ){
                    full[k] = one.cards[i++];
                }else{
                    full[k]= two.cards[j++];
                }
            }else break;
        }
        return new Deck(full);
    }

    public static int randomInt(int low,int high){
        Random r = new Random();
        int num = r.nextInt(high);
        return num;
    }

    /**
     * this method swaps 2 cards at their index
     */
    public void swapCards( int i1, int i2){

        Card card = new Card(cards[i1].colour , cards[i1].rank);
        cards[i1] = cards[i2];
        cards[i2] = card;

    }
    
    
    /**
     * this method deals the hands 
     */
    public static Deck deal(Deck deck, int num){

        Deck hand = subDeck(deck,0,num);
        deck = subDeck(deck,num + 1);
        return hand;

    }

    /**
     * this method takes a large deck and a range of index and forms a smaller subdeck in the range
     */
    public static Deck subDeck(Deck deck, int low, int high){

        Card[] newCards = new Card[high+1-low];
        int index = 0;

        for(int i = low; i <= high;i++){

            newCards[index] = deck.cards[i];
            index++;

        }

        Deck newDeck = new Deck(newCards);
        return newDeck;

    }

    
    /**
     * this method takes a deck and a starting index and forms a new deck without the cards at an index previous to the starting index
     */
    public static Deck subDeck(Deck deck, int start){

        Card[] newCards = new Card[deck.cards.length-start];
        int index = 0;

        for(int i = start; i < deck.cards.length;i++){

            newCards[index] = deck.cards[i];
            index++;

        }
        Deck newDeck = new Deck(newCards);
        return newDeck;

    }

    /**
     * checks if a card can be played 
     */
    public static boolean canPlay(Card topCard, Card play){

        if( topCard.colour == play.colour | topCard.rank == play.rank | play.rank >= 13){

            return true;

        }

        return false;

    }
}

