public class IPin extends Device{
    private boolean inputvalue;
    @Override
    public void setInput(boolean inputvalue)
    {
        // complete this method by yourself
        this.inputvalue = inputvalue;
    }

    @Override
    public boolean getOutput()
    {
        // complete this method by yourself
        return this.inputvalue;
    }
}
