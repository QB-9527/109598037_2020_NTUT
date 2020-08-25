public class GateAND extends Device {
    @Override
    public boolean getOutput()
    {
        // complete this method by yourself
        boolean outputvalue = this.iPins.get(0).getOutput();
        for(int i=0; i< iPins.size();i++){
            outputvalue=outputvalue & this.iPins.get(i).getOutput();
        }
        return outputvalue;
    }
}
