public class WordFreq
{
    private int times_printed; 
    private String word;

    public WordFreq(String w)
    {
        this.times_printed=1;
        this.word=w;
    }

    public String key()
    {
        return this.word;
    }

    public void updateFrequency()
    {
        this.times_printed++;
    }

    public int getWordFrequency()
    {
        return times_printed;
    }

    public void sub()
    {
       times_printed--;
    }

    public int setWordFrequency(int i)
    {
        return times_printed=i;
    }

    public String toString()
    {
        return "The Word " + this.word + " appears " + this.times_printed + " times ";
    }
  
    public boolean less(WordFreq wf) 
    { 
        return times_printed < ((WordFreq) wf).times_printed; 
    }
}