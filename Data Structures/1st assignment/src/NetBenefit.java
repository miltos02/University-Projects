import java.io.*;
import java.util.StringTokenizer;

public class NetBenefit {
    public static void main(String[]args){
        IntQueueImpl accountingQueue=new IntQueueImpl();
        try{
            BufferedReader buff = new BufferedReader(new FileReader(args[0]));
            StringTokenizer Tokens;
            String token;
            String line;
            int amount; //amount with which we multiply price
            int price; //price that is muliplied by quantity
            int amount2; //quantity for selling
            int price2; //price of sell
            int temp; //temporary variable
            int asset; //amount of assets
            int diff; //difference between shares we have and shares we want to share
            boolean isFirstLine=true; //checks if this is the first line
            boolean lastLine=false; //checks last line for transaction completion
            boolean wrongFile=false; //is the file wrong?
            int finalResult=0; //total clear profit or total clear damage
            line = buff.readLine();
            while(line!=null){
                Tokens = new StringTokenizer(line);
                while(Tokens.hasMoreTokens()){
                    token=Tokens.nextToken();
                    if(isFirstLine){ //checks if the file begins with a buy transaction
                        if(!token.equals("buy")){
                            token="not buy";
                            wrongFile=true;
                        }
                        isFirstLine=false;
                    }
                    asset=0;
                    if(token.equals("buy")){
                        lastLine=false; 
                        temp=Integer.parseInt(Tokens.nextToken());
                        accountingQueue.put(temp);
                        asset+=temp; //asset increased by the first sell
                        token=Tokens.nextToken();
                        accountingQueue.put(Integer.parseInt(Tokens.nextToken()));
                    }else if(token.equals("sell")){
                        lastLine=true; 
                        amount=Integer.parseInt(Tokens.nextToken());
                        token=Tokens.nextToken();
                        price=Integer.parseInt(Tokens.nextToken());
                        while(amount>0){ //until the sell is complete
                            if(!accountingQueue.isEmpty()){
                                amount2=accountingQueue.get();
                                asset-=amount2; //element that got in the queue is subtracted
                                price2=accountingQueue.get();
                                if(amount2<=amount){
                                    finalResult+=amount2*(price-price2); //finalResult updated according to the first element of the line
                                    amount-=amount2;
                                    if(accountingQueue.isEmpty() && amount2<amount){ //error if more shares are sold than the existing shares
                                        wrongFile=true;
                                    }
                                }else{ //amount2>amount
                                    diff=amount2-amount; //if there's a surplus of shares then the remainder gets in the queue again
                                    finalResult+=amount*(price-price2);
                                    accountingQueue.put(diff);
                                    accountingQueue.put(price2);
                                    amount = 0;
                                }
                            }else{ //error if there's a demand for sell with an empty queue
                                amount = 0;
                                wrongFile=true;
                            }
                        }
                    }else{ //error if a line begins neither with buy nor sell
                        wrongFile=true;
                    }
                }
                line = buff.readLine();
                if(wrongFile) line = null;
            }
            buff.close();
            if(!lastLine){ //error if last line is a sell transaction
                wrongFile=true;
            }
            if(!wrongFile){ //print profit or damage if there hasn't been an error with the .txt file
                System.out.println(finalResult);
            }else{ //prints error if there's a syntax error on .txt file
                System.out.println("Problematic .txt file");
            }
        }catch (IOException ex) {
            System.err.println("Error at reading .txt file");
        }
    }
}