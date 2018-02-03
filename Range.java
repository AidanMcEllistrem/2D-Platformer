public class Range {
	//You know, there probably was a range class somewhere, but I like this one.

    public int low, high;

    public Range(int low, int high){
        this.low = low;
        this.high = high;
    }
    
    public Range(double low, double high){
        this.low = (int)low;
        this.high = (int)high;
    }
    
    /**
     * Returns true if the number is in bounds [ ].
     * @param number
     * @return
     */
    public boolean contains(int number){
        return (number >= low && number <= high);
    }
    
    /**
     * Returns true if the number is in bounds ( ).
     * @param number
     * @return
     */
    public boolean containsClosed(int number) {
    	return (number >low && number < high);
    }
    
    public boolean outLeft(int number) {
    	return (number < low);
    }
    
    public boolean outRight(int number) {
    	return (number > high);
    }
}