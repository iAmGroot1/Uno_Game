class AI {
    
    
    
    
    /**
     * This method takes the topCard of the played deck and the computers's hand and returns the index of a desirable card 
     */
    public static int best(Card topCard, Deck hand){
        
        int count1 = 0;//the count of the number of cards in the hand matching the colour of the top card
        int count2 = 0;//the count of the number of cards in the hand matching the rank of the top card
        int count3 = 0;//the count of the number of wild cards in the hand
        
        for(int i = 0; i < hand.cards.length;i++){
            
            if(topCard.colour == hand.cards[i].colour){//if the selected card matches the top card's colour
                
                count1 ++;
                
            }else if(topCard.rank == hand.cards[i].rank){//if the selected card matches the top card's rank
                
                count2 ++;
                
            }else if(hand.cards[i].rank > 12){// if the selected card is a wild
                
                count3 ++;
                
            }
            
        }
        
        Card[] colourCards = new Card[count1];// array of the cards matching the top card's colour
        Card[] rankCards = new Card[count2];// array of the cards matching the top card's rank
        Card[] wildCards = new Card[count3];// array of wild cards in the hand
        
        int colourIndex = 0;//current index of colourCards
        int rankIndex = 0;//current index of rankCards
        int wildIndex = 0;//current index of wildCards
        
        for(int index = 0;index < hand.cards.length;index++){// this loop fills in the empty arrays
            
            
            if(topCard.colour == hand.cards[index].colour){//if the selected card matches the top card's colour
                
                colourCards[colourIndex] = hand.cards[index];
                colourIndex++;
                
            }else if(topCard.rank == hand.cards[index].rank){//if the selected card matches the top card's rank
                
                rankCards[rankIndex] = hand.cards[index];
                rankIndex++;
                
            }else if(hand.cards[index].rank > 12){// if the selected card is a wild
                
                wildCards[wildIndex] = hand.cards[index];
                wildIndex++;
                
            }
        }
        
        
        if(colourCards.length == 0 && rankCards.length == 0){// if no cards match the rank or colour of the top card
            
            if(wildCards.length == 0){// if there are no wild cards
                
                return -1;// this indicates there is nothing to play
                
            }else{// if there are wild cards
                
                return findIndex(hand,wildBest(wildCards));// the best of the wild cards is found and its index is returned
                
            }
            
        }
        
        
        if(topCard.rank > 12){// if the top card is a wild
            
            return forWild(topCard.colour,hand);// returns the index of the most favourable card
            
        }
        
        
        if(colourCards.length == 0){//if there are no cards that match the top card's colour
            
             Card rankBest = playRank(hand,rankCards);
             return findIndex(hand, rankBest);// the best card among the cards which correspond to the top card's rank is return
             
        }else if(rankCards.length == 0 ){// if there are no cards that match the top card's rank
            
             Card colourBest = playColour(colourCards);
             return findIndex(hand,colourBest);// the best card among the cards which correspond to the top card's colour is return
             
        }else{//if there are both cards that match the top card's rank and colour
            
            /*the best card among the cards which correspond to the top card's rank and the best card among the cards which correspond to the top card's colour
              are compare and the desirable card's index is returned
             */
            Card colourBest = playColour(colourCards);
            Card rankBest = playRank(hand,rankCards);
            
            if(colourBest.rank > 10){
                
                return findIndex(hand,colourBest);
                
            }else{     
                
                return findIndex(hand,rankBest);    
                
            }
            
        }   
        
    }
    
    
    
   
    /**
     * This method finds the best Card to play among the cards which match the colour of the top card
     */
    public static Card playColour(Card[] cards){
        
        
            
            int bestIndex = 0;
            
            for(int i = 1; i < cards.length;i++){
                
                if(cards[bestIndex].rank < 10 && cards[i].rank == 0){// if the best card is not a power card and the current card's rank is equal to 0
                    
                    bestIndex = i;// the current index is the new bestIndex
                    
                }else if(cards[bestIndex].rank == 0 && cards[i].rank > 10){// if the best card's rank is 0 and the current card is a power card
                    
                    bestIndex = i;//the current index is the new bestIndex
                    
                }else if(cards[i].rank > cards[bestIndex].rank){// if the current card's rank is greater than the best card's rank
                    
                    bestIndex = i;//the current index is the new bestIndex
                    
                }
                
                
            }
            
            return cards[bestIndex];
            
        
        
        
    }
    
    
    
    
    /**
     * This method finds the best Card to play among the cards which match the rank of the top card
     */
    public static Card playRank(Deck hand, Card[] cards){
        
        //the card which shares its colour with most cards in the deck is returned
       int[] hist = Sort.sortArray(colourHist(hand.cards));
       Card best = new Card();
       
       for(int i = 0; i < hist.length; i ++){
           
           for(int index = 0; index< cards.length; index ++){
               
               if(cards[index].colour == hist[i]){
                   
                   best = cards[index];
                   break;
                   
                }
                
            }
            
       }  
       
       return best;
       
    }
    
    /**
     * This method makes a Histogram of the colours in the computer's hand
     */
    public static int[] colourHist(Card[] hand){
        
        int[] hist = new int[4];
        
        for(int i = 0; i < hist.length; i++){
            
            int count = 0;
            
            for(int index = 0; index < hand.length; index++){
                
                if(hand[index].colour == i){
                    
                    count ++;
                    
                }
                
            }
            
            hist[i] = count;
            
        }
        
        return hist;
        
    }
    
    /**
     *  This method returns the best card among the wild cards in the computer's hand
     */
    public static Card wildBest(Card[] wildCards){
        
        // if there is a wild draw four, it is returned
        int rank = 0;
        
        for(int i = 0; i < wildCards.length; i++){
            
            if(wildCards[i].rank == 14)return wildCards[i];
            
        }
        
        return wildCards[0];
        
    }
    
     /**
      * This method finds the index of the Card to be played in relation to the computer's hand
      */
    public static int findIndex(Deck cards, Card target){
        
        for(int i = 0; i < cards.cards.length;i++){
            
            if(cards.cards[i] == target)return i;
            
        }
        
        return -1;
        
    }
    
    /**
     * This method chooses which colour to change to after playing a wild card
     */
    public static int changeColour(Deck hand){
        
        //the colour which is most in number is returned
        int[] hist = colourHist(hand.cards);
        int maxIndex = 0;
        
        for(int i = 1; i < hist.length; i ++){
            
            if(hist[i] > hist[maxIndex]){
                
                maxIndex = i;
                
            }
            
        }
        
        return maxIndex;
        
    }
    
    /**
     * This method returns the best card to be played if the top card is a wild.
     */
    public static int forWild(int colour, Deck hand){
        
        int count = 0;// the count of the cards that have the chosen colour
        
        for(int i = 0 ; i < hand.cards.length ; i++){
            
            if(hand.cards[i].colour == colour)count ++;
            
        }
        
        Card[] possibilities = new Card[count];// the cards that are of the chosen colour
        int posIndex = 0;
        if(count == 0){
            return -1;
        }else if(count==1){
            return 0;
        }
           
        // this loop fills in the array of possible cards
        for(int index = 0; index < hand.cards.length; index ++){
            
            if(hand.cards[index].colour == colour){
                
                possibilities[posIndex] = hand.cards[index];
                posIndex++;
                
            }
            
        }
        
        return findIndex(hand,playColour(possibilities));
    }

}
