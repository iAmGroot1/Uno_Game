import java.lang.Object;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.awt.Color;
//need the rules//

class PlayGame implements MouseListener {

    static String reply = "";

    /**
     * this is where execution of the program starts. 
     */
    public static void main(String[] args) throws Exception{
        JFrame backF= new JFrame();
        backF.setSize(3000,3000);
        JPanel backP = new JPanel(new GridLayout(1,1));
        backF.add(new JLabel(new ImageIcon("/home/rohan/UnoGame/unp.jpg")));
        backP.setBackground(Color.black);
        Container c = backF.getContentPane();
        c.setBackground(Color.black);
        backF.setVisible(true);
        Scanner typed = new Scanner(System.in);
        JOptionPane.showMessageDialog(null,"Hello, and welcome to Rohan's UNO game!");
        String name =JOptionPane.showInputDialog("What's your name?");
        JFrame ask = new JFrame("NEED THE RULES?");
        ask.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ask.setResizable(false);
        ask.setLocation(450,300);
        ask.setSize(600,90);
        JPanel p = new JPanel(new GridLayout(1,2));
        ask.add(p);
        JButton yes = new JButton("yes");
        JButton no = new JButton ("no");
        yes.setName("yes");
        no.setName("no");
        yes.setSize(30,20);
        no.setSize(30,20);
        yes.addMouseListener(new PlayGame());
        no.addMouseListener(new PlayGame());
        p.add(yes);
        p.add(no);
        ask.add(p);
        ask.setVisible(true);
        while(reply == "")Thread.sleep(1);
        if(reply == "yes"){
            reply ="";
            giveRules();

        }
        reply = "";
        ask.removeNotify();
        playGame();// this is where the main part of the game occurs

    }

    /**
     * this method provides the rules in case the player is unaware of them
     */
    private static void giveRules() throws Exception{

        JFrame rulesF = new JFrame("RULES:");
        rulesF.setSize(3000,3000);
        JPanel rulesP = new JPanel(new GridLayout(1,1));
        ImageIcon rulesPic= new ImageIcon("/home/students/rohan3267/Desktop/rules.png");
        JButton rulesB = new JButton(rulesPic);
        rulesB.setBackground(Color.black);
        rulesB.setName("done");
        rulesB.addMouseListener(new PlayGame());
        rulesP.add(rulesB);
        rulesF.add(rulesP);
        rulesF.setVisible(true);
        //JOptionPane.showMessageDialog(null,"HERE ARE THE RULES OF THE GAME:If you don't need them or you're done reading, click this screen");
        while(reply == "")Thread.sleep(1);
        rulesF.removeNotify();
        reply = "";
    }

    /**
     * this method runs through the play of the game.
     */
    private static void playGame() throws Exception{

        Deck deck = new Deck();//this is the main deck.
        deck.shuffler();//the deck is shuffled.
        deck.shuffler();//the deck is shuffled.
        deck.shuffler();//the deck is shuffled.
        deck.shuffler();//the deck is shuffled.
        Deck playerHand = Deck.deal(deck,6);//the first 7 cards of the shuffled deck are dealt out to the player
        deck = Deck.subDeck(deck,7);//the cards are taken out of the deck
        Deck compHand = Deck.deal(deck,6);// the next 7 cards are dealt to the computer
        deck = Deck.subDeck(deck, 7);
        Card topCard = deck.cards[0];//the top card of the main deck is flipped
        Card played;//the player's card is declared
        Card[] playedCards = new Card[0];
        Deck playedDeck = new Deck(playedCards);
        //this loop plays a topCard that is not a power card

        for(int i = 0; i < deck.cards.length; i++){

            if(deck.cards[i].rank <= 9){

                topCard = deck.cards[i];
                deck = addDeck(Deck.subDeck(deck,0,i),Deck.subDeck(deck, i+1));
                break;

            }

        }

        //this is the loop involved in flow of the game
        while(true){

            if(playerHand.cards.length == 0 || compHand.cards.length == 0){//if one of the players has finished their cards,the loop breaks( the game is over).

                JOptionPane.showMessageDialog(null,"GAME OVER");
                break;
            }

            if(deck.cards.length == 0){// if the main deck is depleted, a new deck is created
                playedDeck.shuffler();//the played deck is shuffled and made into the main deck
                deck = new Deck(playedCards);
                playedCards = new Card[0];
                playedDeck = new Deck(playedCards);// the played deck is empty

            }
            JFrame tc ;
            if(topCard.rank < 13){
                tc = display("Top Card:",topCard);
            }else{
                tc=  displayColour(topCard.colour);
            }

            JFrame pl= new JFrame();
            while(true){//this loop deals with the player's move

                JOptionPane.showMessageDialog(null,"Play Your Turn");
                int index = 0;
                while(true){
                    String reply = getIndex(playerHand);
                    if(reply.equals("idh")){
                        JOptionPane.showMessageDialog(null,"Draw a card");
                        tc.removeNotify();
                        JFrame dc = display("You Drew:",deck.cards[0]);
                        JOptionPane.showMessageDialog(null,"Click OK when you're done");
                        dc.removeNotify();
                        playerHand = addCard(playerHand, deck.cards[0]);//adds the card at the top of the deck to the player's hand
                        deck = Deck.subDeck(deck,1);//the top card is removed from the main deck
                        index = -1;//this indicates that there is nothing to play
                        break;
                    }else{
                        index = Integer.parseInt(reply);// the fed in index is stored in a variable
                        if(Deck.canPlay(topCard,playerHand.cards[index])){
                            break;
                        }
                        JOptionPane.showMessageDialog(null,"You Can't Play That!");
                        continue;
                    }
                }

                if(index < 0){

                    played = topCard;// this tells the computer that the card to be matched is the top card
                    break;

                }else{//this deals with the implications of the player's move

                    played = playerHand.cards[index];//finds the card using the provided index
                    pl = display("You Played:",played );
                    playerHand = addDeck(Deck.subDeck(playerHand,0,index-1),Deck.subDeck(playerHand, index +1));// the played card is removed from the player's hand
                    playedDeck = addCard(playedDeck, played);//adds the played card onto the played deck
                    topCard = played;
                    if(played.rank == 10 || played.rank == 11){//if the card is reverse or skip, the loop doesn't break and the player has another turn

                        topCard = played;
                        if(playerHand.cards.length == 0){

                            break;

                        }
                        JOptionPane.showMessageDialog(null,"Oh No!It's Your turn again!!");
                        pl.removeNotify();
                        tc.removeNotify();
                        tc = display("Top Card:",topCard);
                        continue;
                    }else{// the player does not have another turn

                        if(played.rank == 12){//if the player plays draw two

                            JOptionPane.showMessageDialog(null,"You could have spared me! Now I have to pick two cards.");
                            compHand = addDeck(compHand, Deck.subDeck(deck,0,1));//the two cards on top of the main deck are added to the computer's hand
                            deck = Deck.subDeck(deck,2);//the top two cards are removed from the deck
                        }else if(played.rank >= 13){//if the card is a wild
                            if(playerHand.cards.length ==0){
                                pl.removeNotify();
                                JOptionPane.showMessageDialog(null,"GAME OVER");
                                break;
                            }
                            if(played.rank == 14){//if the card is a wild draw four

                                JOptionPane.showMessageDialog(null,"Oh man! Now I have to draw four cards");
                                compHand = addDeck(compHand, Deck.subDeck(deck,0,3));// the top four cards of the main deck are added to the computer's hand
                                deck = Deck.subDeck(deck,4);// the top four cards are removed from the main deck
                            }
                            played = new Card(getColour(playerHand),played.rank);// a new card is initialised having the player's decided colour and the wild rank

                        }

                        break;

                    }

                }
            }
            tc.removeNotify();

            if(playerHand.cards.length == 0 || compHand.cards.length == 0){//if one of the players has finished their cards,the loop breaks( the game is over).
                pl.removeNotify();
                JOptionPane.showMessageDialog(null,"GAME OVER");
                break;
            }

            if(deck.cards.length == 0){// if the main deck is depleted, a new deck is created
                playedDeck.shuffler();//the played deck is shuffled and made into the main deck
                deck = new Deck(playedCards);
                playedCards = new Card[0];
                playedDeck = new Deck(playedCards);// the played deck is empty

            }
            Card choice;// the computer's choice of card is declared
            Thread.sleep(300);
            JFrame co = new JFrame();
            while(true){

                JOptionPane.showMessageDialog(null,"Okay.Let me think ...");
                Thread.sleep(1000);
                int myPlay = AI.best(played,compHand);// the index of the computer's hand containing the desired card

                if(myPlay == -1){// if the computer doesn't have a suitable card
                    pl.removeNotify();
                    tc.removeNotify();
                    JOptionPane.showMessageDialog(null,"... nothing. I guess this means that I have to draw a card");
                    compHand = addCard(compHand, deck.cards[0]);// a card is added to the computer's hand
                    deck = Deck.subDeck(deck,1);//the card is takenn out of the dec
                    choice = played;// this tells the player that the computer did not play anything; the top card remains the same
                    break;

                }else{
                    pl.removeNotify();
                    tc.removeNotify();
                    choice = compHand.cards[myPlay];//the desired card is declared
                    co = display("I Play:",choice);
                    compHand = addDeck(Deck.subDeck(compHand,0,myPlay-1), Deck.subDeck(compHand,myPlay + 1));// the desired card is taken out of the computer's hand 
                    playedDeck = addCard(playedDeck, choice);//adds the played card onto the played deck

                    if(choice.rank >= 13){// if the card is a wild
                        String[] cols = {"yellow","red","blue","green"};
                        JOptionPane.showMessageDialog(null,"I change the colour to ..."+ cols[AI.changeColour(compHand)]);

                        if(choice.rank == 14){// if the card is a wild draw four

                            JOptionPane.showMessageDialog(null,"Yes! Now you have to pick four cards");
                            playerHand = addDeck(playerHand, Deck.subDeck(deck,0,3));// the top four cards of the main deck are added to the player's hand
                            deck = Deck.subDeck(deck, 4);// the top four cards are removed from the main deck

                        }

                        int colour = AI.changeColour(compHand);//the AI's desired change of colour is taken
                        choice = new Card(colour,choice.rank);//a new card is initialised havind the AI's decided colour and the wild rank
                        break;

                    }else if(choice.rank == 12){//if the card is a draw two

                        JOptionPane.showMessageDialog(null,"Yeah! Pick two more cards");
                        playerHand = addDeck(playerHand,Deck.subDeck(deck,0,1));//the two cards on top of the main deck are added to the player's hand
                        deck = Deck.subDeck(deck, 2);//the top two cards are removed from the deck
                        break;

                    }else if(choice.rank > 9){// if the card is a reverse or skip, the computer plays its turn again

                        played = choice;
                        JOptionPane.showMessageDialog(null,"My turn again");
                        co.removeNotify();
                    }else{// if it is a normal card

                        break;

                    }

                }

            }

            JOptionPane.showMessageDialog(null,"By the way,I have " + compHand.cards.length + " cards left");
            co.removeNotify();
            topCard = choice;//the top card of the played deck is the computer's choice

        }

        JFrame overF = new JFrame();
        overF.setSize(3000,3000);
        JPanel overP = new JPanel(new GridLayout(1,1));
        ImageIcon winner = new ImageIcon("/home/rohan/UnoGame/win.jpeg");
        JButton overB = new JButton(winner);
        overB.setBackground(Color.black);
        overP.add(overB);
        overF.add(overP);
        overF.setVisible(true);
        if(playerHand.cards.length == 0){//if the player's hand is over, he/she wins
            JOptionPane.showMessageDialog(null,"YOU WIN");
        }else{//if the computer's hand is over, the player wins
            JOptionPane.showMessageDialog(null,"I WIN");
        }
        System.exit(0);
    }

    /**
     * This method adds a card to the array of cards in a Deck object
     * input : the hand of the player, the card to be added
     */
    private static Deck addCard(Deck hand, Card card){

        Card[] newCards = new Card[hand.cards.length + 1];
        for(int i = 0; i < newCards.length; i++){
            if(i == hand.cards.length){
                newCards[i] = card;
            }else{
                newCards[i] = hand.cards[i];
            }
        }
        Deck newHand = new Deck(newCards);
        return newHand;

    }

    /**
     * This method adds an array of cards to the deck
     * input : the hand of a player, the set of cards to be added
     */
    private static Deck addDeck(Deck hand,Deck add ){

        Card[] newCards = new Card[hand.cards.length + add.cards.length];
        int addIndex = 0;

        for(int i = 0; i < newCards.length; i++){

            if(i >= hand.cards.length){

                newCards[i] = add.cards[addIndex];
                addIndex ++;

            }else{

                newCards[i] = hand.cards[i];

            }

        }

        Deck newHand = new Deck(newCards);
        return newHand;

    }

    /**
     * This method takes the index from the player and returns it
     * input : the hand of the player, the top card of the played pile.
     */
    private static String getIndex( Deck deck)throws Exception{

        JFrame j = new JFrame("YOUR CARDS:");
        j.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        j.setResizable(false);
        j.setLocation(210,480);
        j.setSize(1000,200);
        JPanel p = new JPanel(new GridLayout(1,deck.cards.length +3));
        j.add(p);
        JButton[] buts = new JButton[deck.cards.length+1];
        for(int i = 0; i < deck.cards.length;i++){
            ImageIcon ic = new ImageIcon("/home/rohan/UnoGame/"+deck.cards[i].colour+""+deck.cards[i].rank+".png");
            Image image= ic.getImage().getScaledInstance(100,150, Image.SCALE_SMOOTH);
            ic=new ImageIcon(image);
            JButton but = new JButton(ic);
            buts[i] = new JButton(ic);
            buts[i].setBackground(Color.black);
            buts[i].setSize(100,150);
            buts[i].setName(i+"");
        }
        buts[deck.cards.length] = new JButton("PASS");
        buts[deck.cards.length].setName("idh");
        for(int k = 0; k < deck.cards.length+1;k++){
            p.add(buts[k]);

        }
        j.setVisible(true);
        for(int x = 0; x < deck.cards.length+1;x++){
            buts[x].addMouseListener(new PlayGame());

        }
        while(reply == ""){
            Thread.sleep(2);
        }

        String s = reply;
        j.removeNotify();
        reply = "";
        return s;

    }

    private static JFrame displayHand( Deck deck)throws Exception{
        JFrame j = new JFrame("YOUR CARDS:");
        j.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        j.setResizable(false);
        if(deck.cards.length >8){
            j.setLocation(210,480);
            j.setSize(1200,200);
        }else{
            j.setLocation(210,480);
            j.setSize(1000,200);
        }

        JPanel p = new JPanel(new GridLayout(1,deck.cards.length));
        j.add(p);
        JButton[] buts = new JButton[deck.cards.length];
        for(int i = 0; i < deck.cards.length;i++){
            ImageIcon ic = new ImageIcon("/home/rohan/UnoGame/"+deck.cards[i].colour+""+deck.cards[i].rank+".png");
            Image image= ic.getImage().getScaledInstance(100,150, Image.SCALE_SMOOTH);
            ic=new ImageIcon(image);
            buts[i] = new JButton(ic);
            buts[i].setBackground(Color.black);
            buts[i].setSize(100,150);
        }

        for(int k = 0; k < deck.cards.length;k++){
            p.add(buts[k]);

        }
        j.setVisible(true);
        return j;
    }

    /**
     * This index takes the change of colour decided by the player after playing a wild and returns it
     */
    private static int getColour(Deck hand)throws Exception{
        JFrame cj = new JFrame();
        cj.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        cj.setResizable(false);
        JOptionPane.showMessageDialog(null,"Click on the colour you want");
        JFrame pack = displayHand(hand);
        cj.setSize(300,300);
        cj.setLocation(750,400);
        JPanel cp = new JPanel(new GridLayout(2,2));
        JButton[] buts = new JButton[4];
        String[] col = {"yellow","red","blue","green"};
        for(int i = 0; i< 4; i++){
            ImageIcon im = new ImageIcon("/home/rohan/UnoGame/"+col[i]+".jpeg");
            Image image= im.getImage().getScaledInstance(100,150, Image.SCALE_SMOOTH);
            im=new ImageIcon(image);
            buts[i] = new JButton(im);
            buts[i].setName(i+"");
            buts[i].setSize(150,150);
            buts[i].addMouseListener(new PlayGame());
            cp.add(buts[i]);
        }
        cj.add(cp);
        cj.setVisible(true);
        while(reply == "")Thread.sleep(1);
        int colour = Integer.parseInt(reply);
        pack.removeNotify();
        cj.removeNotify();
        reply = "";
        return colour;
    }

    private static JFrame display(String name, Card c){
        JFrame j = new JFrame(name);
        j.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        j.setResizable(false);
        j.setLocation(680,80);
        j.setSize(300,200);
        JPanel p = new JPanel(new GridLayout(1,1));
        ImageIcon i = new ImageIcon("/home/rohan/UnoGame/"+c.colour+""+c.rank+""+".png");
        System.out.println("the rank"+c.rank+" the colour"+c.colour);
        Image image= i.getImage().getScaledInstance(100,100, Image.SCALE_SMOOTH);
        i=new ImageIcon(image);
        JButton but = new JButton(i);
        but.setBackground(Color.black);
        p.add(but);
        j.add(p);
        j.setVisible(true);
        return j;
    }

    private static JFrame displayColour(int colour){
        String[] col = {"red","yellow","green","blue"};
        JFrame j = new JFrame("Chosen Colour:");
        j.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        j.setResizable(false);
        j.setLocation(680,80);
        j.setSize(300,200);
        JPanel p = new JPanel(new GridLayout(1,1));
        ImageIcon i = new ImageIcon("/home/rohan/UnoGame/"+col[colour]+".jpeg");
        JButton but = new JButton(i);
        but.setBackground(Color.black);
        p.add(but);
        j.add(p);
        j.setVisible(true);
        return j;
    }

    public void mouseClicked(MouseEvent e){
        JButton but = (JButton)(e.getSource());

        reply = but.getName();
        //JOptionPane.showMessageDialog(null, "HI ");
    }

    public void mousePressed(MouseEvent e){}

    public void mouseExited(MouseEvent e){}

    public void mouseEntered(MouseEvent e){}

    public void mouseReleased(MouseEvent e){}
}


