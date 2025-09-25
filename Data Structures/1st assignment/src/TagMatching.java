import java.io.*;
import java.util.StringTokenizer;

public class TagMatching {
    public static void main(String[]args){
        StringStackImpl matchingStack=new StringStackImpl();
        try{
            BufferedReader buff = new BufferedReader(new FileReader(args[0]));
            StringTokenizer Tokens;
            String token;
            String line;
            String lastTag;
            int tagStartingPosition;
            int tagEndingPosition;
            int sizeTag;
            int sizeNewToken;
            boolean flag;
            boolean tokenHasTags;
            line = buff.readLine();
            while(line!=null) {
                Tokens = new StringTokenizer(line);
                while (Tokens.hasMoreTokens()){
                    token = Tokens.nextToken();
                    if(token.contains("<") && token.contains(">")) {
                        tokenHasTags = true;
                        //while the token has more tags we keep working on it
                        while (tokenHasTags) {
                            //find the tag that has the token
                            tagStartingPosition = token.indexOf("<");
                            tagEndingPosition = token.indexOf(">");
                            char[] chToken = token.toCharArray();

                            //saving the tag of the token in chTag array and after update tag variable
                            sizeTag = tagEndingPosition - tagStartingPosition + 1;
                            char[] chTag = new char[sizeTag];
                            for (int i = 0; i < sizeTag; i++) {
                                chTag[i] = chToken[i + tagStartingPosition];
                            }
                            String tag = new String(chTag); // Save chTag array in Tag variable as a string

                            // Updating stack according to tag / chTag 
                            if (chTag[1] != '/') {
                                matchingStack.push(tag);
                            } else {
                                lastTag = matchingStack.peek();
                                char[] chLastTag = lastTag.toCharArray();
                                flag = true;
                                for (int i = 1; i < chLastTag.length - 1; i++) {
                                    if (chTag[i + 1] != chLastTag[i]) {
                                        flag = false;
                                        break;
                                    }
                                }
                                if (flag) {
                                    matchingStack.pop();
                                }
                            }

                            //checking for the rest of the token (if there is more)
                            if (chToken.length > chTag.length) {
                                //save the rest of the token 
                                sizeNewToken = chToken.length - tagEndingPosition - 1;
                                char[] chNewToken = new char[sizeNewToken];
                                for (int i = 0; i < sizeNewToken; i++) {
                                    chNewToken[i] = chToken[i + 1 + tagEndingPosition];
                                }
                                token = new String(chNewToken);

                                //check if token has more tags
                                if (!token.contains("<") || !token.contains(">")) {
                                    // if not tokenHasTags is false
                                    tokenHasTags = false;
                                }
                            } else {
                                tokenHasTags = false;
                            }
                        }
                    }
                }
                line = buff.readLine();
                }
            buff.close();
            if (matchingStack.isEmpty()) { //not open tags left in stack
                System.out.println("All tags are closed");
            } else {
                System.out.println("Error! This is not a correct HTML file");
            }
        } catch (IOException ex) {
            System.err.println("Error at reading HTML file");
        }
    }
}