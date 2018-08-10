class Card {
    
    /**
     * The colour of an UNO card
     */
    int colour;
    /**
     * the rank/number of an UNO card
     */
    int rank;
    
    /**
     * This is a constructor that initatialise the instance variables(int colour, int rank) to 0.
     */
    public Card(){
        
        this.colour = 0;
        this.rank= 0;
        
    }
    
    /**
     * This is a constructor that takes an input of two ints(colour, rank) and intialises the instance variables to their values.
     */
    public Card(int colour, int rank){
        
        this.colour = colour ;
        this.rank = rank;
        
    }
    
}
