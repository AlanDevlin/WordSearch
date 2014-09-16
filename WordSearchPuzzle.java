import java.util.*;
import java.io.*;
import java.util.Collections;

public class WordSearchPuzzle{
    
private char[][] puzzle ;
private List<String> puzzleWords ;
private int randomRow;
private int randomCol;
private int squareVisited;
private String[] direction;
private int chooseDirection;
private int dimensions;

public WordSearchPuzzle(List<String> userSpecifiedWords) {
    
    //sort the list by order of length (longest words first)
    Collections.sort(userSpecifiedWords, new WordsOrderedByLength()); 
 
    //Find the length of the longest word in the List
     int longest =  findLongestWord(userSpecifiedWords);
     
     //Find the sum of all the words in the List
     int sum = findSumOfWords(userSpecifiedWords);
    
    //Create enough space to obsure the words
    double overallSum = (double) sum * longest;
    double row = Math.sqrt(overallSum);
    double col = Math.sqrt(overallSum);
  
  
    //Create the 2-D array
    this.puzzle =  new char[(int)row][(int)col];
   
    this.puzzleWords = userSpecifiedWords;

    generateWordSearchPuzzle();
    showWordSearchPuzzle();

}
public WordSearchPuzzle(String wordFile,int wordCount,int shortest, int longest) {
   { 
     
       //Ensure that the length entered for longest is larger than shortest..if not swap them
    int temp;
    
    if(shortest > longest){
        temp = longest;
        longest = shortest;
        shortest = temp;
    }
    //Call the loadFile method to get the words within the length range specified
    this.puzzleWords = loadFile(wordFile, shortest, longest);
    
    //Create a new ArrayList to store the words
    ArrayList<String> randomWords = new ArrayList<String>();
    
    //Fill the ArrayList with randomWords
    for(int i = 0; i < wordCount; i++)
    {
        int posOfWord = (int)(Math.random() * puzzleWords.size());
        randomWords.add(puzzleWords.get(posOfWord));
    }
    
    //Sort the new List by length to make the placement on the grid easier
    Collections.sort(randomWords, new WordsOrderedByLength()); 
    
    
  
    int sum = findSumOfWords(randomWords);
    
  int longestWord = findLongestWord(randomWords);
 
     //Create enough space to obsure the words
    double overallSum = (double)sum * longest;
    
    double row = Math.sqrt(overallSum);
    double col = Math.sqrt(overallSum);
  
    //Create the 2-D array
    this.puzzle =  new char[(int)row][(int)col];
  
    
    this.puzzleWords = randomWords;
    
    generateWordSearchPuzzle();
    showWordSearchPuzzle();

}
}

//Method to find the sum of the words
private int findSumOfWords(List<String> listOfWords){
   
   //Create a counter
         int sum = 0;
        //Go through the List of words
        for(String s: listOfWords){
            //Find the length of each word
        
            int wordLength = s.length();
            sum = sum+ wordLength;
        }
        
        return sum;
        
    }
    
    //Method which returns the longest word in the List
    
    private int findLongestWord(List<String> listOfWords){
        
        //Has to be the first word in the list as the List has been sorted by length
        return listOfWords.get(0).length();
        
    }
    
    public char[][] getPuzzleAsGrid()
    {
        //Method which returns the puzzle as a 2D array
        return this.puzzle;

    }
    public List<String> getWordSearchList()
    {
        //returns the list of words to be found in the puzzle
      return this.puzzleWords;  
    }
    
    //Method which returns the puzzle as a string
    public String getPuzzleAsString()
        {
          String puzzle = Arrays.deepToString(getPuzzleAsGrid());
          puzzle = puzzle.replace("], [", "]\n[");
          puzzle = puzzle.replace(",", " ");
          puzzle = puzzle.substring(1, puzzle.length()-1);
          return puzzle;
            }
        
       
            //Method which is used to read a file as entered by the user
    private ArrayList<String> loadFile(String fileName, int shortest, int longest){
        try{
            FileReader aFileReader = new FileReader(fileName);
            BufferedReader aBufferReader = new BufferedReader(aFileReader);
            
            ArrayList<String> wordList = new ArrayList<String>();
          
            int lengthOfWord;
            String lineFromFile = aBufferReader.readLine();
            while(lineFromFile != null){
                lengthOfWord = lineFromFile.length();
                //if the length of the word is within the range specified by the user
                // --- then add it to the array list to be returned
                if(lengthOfWord >= shortest && lengthOfWord <= longest){
                    wordList.add(lineFromFile.toUpperCase());
                }
                lineFromFile = aBufferReader.readLine();
                
            }
            aBufferReader.close();
            aFileReader.close();
            return wordList;
        }
        catch(IOException x){
            return null;
        }
    }
    
    //Method to display the words to be found in the puzzle and the puzzle
public void showWordSearchPuzzle(){
     String listOfWords = getWordSearchList().toString();
     listOfWords = listOfWords.replace(",", " ");
     listOfWords = listOfWords.toUpperCase().substring(1, listOfWords.length()-1);
     System.out.print("The words to be found in this puzzle are: " + "\n" + listOfWords + "\n\n\n");
     System.out.print(getPuzzleAsString());
     
    }

    //Method which places the words in the puzzle
private void generateWordSearchPuzzle(){
 
for (int i = 0; i  < puzzleWords.size(); i++)
{
    //Gets each word in the List
    String word = puzzleWords.get(i);
    //We assume the word will not fit to begin
    boolean wordFits = false;
    //Array which contains the 4 possible directions the word could be placed
    this.direction = new String[]{"Left", "Right", "Up", "Down"}; 
    //Randomly choose a direction for the word
    this.chooseDirection = (int)(Math.random() * direction.length);
    
    //While the word doesnt fit in the selected position
    while(!wordFits)
    {
        //Randomnly select a row and column position in the grid
        this.randomRow = (int)(Math.random() * puzzle.length);
        this.randomCol = (int)(Math.random() * puzzle[0].length);
        

        
       //if the randomly selected coordinates do not contain a charachter...check if the word will fit
        if(Character.isLetter(puzzle[randomRow][randomCol])==false)
                wordFits = doesWordFit(word);
    }
            //Check which direction has been selected and place each charachter of the word
            if(direction[chooseDirection].equals("Left"))
            {
                for(int x = word.length(); x > 0; x--)
                {
                    int col = randomCol;
                    for(char ch: word.toUpperCase().toCharArray())
                    {
                        puzzle[randomRow][col] = ch;
                        col--;
                    }
                }
            }
            else if (direction[chooseDirection].equals("Right"))
             {
                for(int x = word.length(); x > 0; x--)
                {
                    int col = randomCol;
                    for(char ch: word.toUpperCase().toCharArray())
                    {
                        puzzle[randomRow][col] = ch;
                        col++;
                    }
                }
            }
            
                else if (direction[chooseDirection].equals("Up"))
             {
                for(int x = word.length(); x > 0; x--)
                {
                    int row = randomRow;
                    for(char ch: word.toUpperCase().toCharArray())
                    {
                        puzzle[row][randomCol] = ch;
                        row--;
                    }
                }
            }
            
                else if (direction[chooseDirection].equals("Down"))
             {
                for(int x = word.length(); x > 0; x--)
                {
                    int row = randomRow;
                    for(char ch: word.toUpperCase().toCharArray())
                    {
                        puzzle[row][randomCol] = ch;
                        row++;
                    }
                }
            }
       
        }
     //fills the remaining spaces with random characters from the alphabet   
    char [] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    for(int i =0;i < puzzleWords.size(); i++){
    //goes through the puzzle and if no character is in place it randomly adds one from the alphabet
    for(int r =0; r <puzzle.length; r++)
    {
    for(int c=0; c<puzzle[0].length; c++)
    {
    if(Character.isLetter(puzzle[r][c])==false){
    int z = (int)((Math.random()* alphabet.length));
    puzzle[r][c] = alphabet[z] ;
                                                }
                                              }
    }
    }
              
}
 //Method which checks if a word has enough space to fit in the direction chosen for it
private boolean doesWordFit(String word)
{
 
   int row = this.randomRow;
   int col = this.randomCol;
   boolean fit = false;
   for(int i = 0; i < direction.length && !fit; i++)
   {
       if(direction[chooseDirection].equals("Left"))
       {
           //if there are more letters to place, the coordinates exist and there isnt a letter already in the position
           //then go back one, when equal to the length of the word, return true
           //this states that there is enough space for the word to fit 
          int p;
          for (p = 0; (p < word.length()) && (exists(row,col)) && (Character.isLetter(puzzle[row][col])==false); p++)
          {
              col--;
            }
            if(p == word.length())
            {
                fit = true;
                return fit;
            }
    
}

 else if(direction[chooseDirection].equals("Right"))
       {
          int p;
          for (p = 0; (p < word.length()) && (exists(row,col)) && (Character.isLetter(puzzle[row][col])==false); p++)
          {
              col++;
            }
            if(p == word.length())
            {
                fit = true;
                return fit;
            }
    
}

      else if(direction[chooseDirection].equals("Up"))
       {
          int p;
          for (p = 0; (p < word.length()) && (exists(row,col)) && (Character.isLetter(puzzle[row][col])==false); p++)
          {
              row--;
            }
            if(p == word.length())
            {
                fit = true;
                return fit;
            }
    
}

      else if(direction[chooseDirection].equals("Down"))
       {
          int p;
          for (p = 0; (p < word.length()) && (exists(row,col)) && (Character.isLetter(puzzle[row][col])==false); p++)
          {
              row++;
            }
            if(p == word.length())
            {
                fit = true;
                return fit;
            }
    
}

}
return false;
} 

//Method which checks if the row and col exist on the grid
private boolean exists(int row, int col)
{
     if((row >= 0 && row < puzzle.length) && (col >= 0 && col < puzzle[0].length))
                return true;
        return false;
    }
}
    
    
    
    
    
        





  