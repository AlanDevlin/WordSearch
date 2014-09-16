
import java.util.Comparator;
public class WordsOrderedByLength implements Comparator<String>
{
    // Create a compare method to order the words in terms of length ( longest words first)
  public int compare (String x, String y)
  {
            return y.length() - x.length();
}
}
