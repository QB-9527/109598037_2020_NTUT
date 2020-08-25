public class GateNOT extends Device{
    @Override
    public boolean getOutput()
    {
        // complete this method by yourself
        boolean outputvalue = this.iPins.get(0).getOutput();
        return !outputvalue;
    }
}
