import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class LogicSimulator {
    private Vector<Device> circuits;
    private Vector<Device> iPins;
    private Vector<Device> oPins;
    private Vector<Vector<Double>> circuits_pin;
    int inputNums=0;
    int gateNums=0;
    int gateKind=0;
    String str="";

    public LogicSimulator() {
        circuits = new Vector<Device>();
        iPins = new Vector<Device>();
        oPins = new Vector<Device>();
        circuits_pin = new Vector<Vector<Double>>();
    }

    public void load(String file1Path) throws IOException {
        FileReader fr = new FileReader(file1Path);
        BufferedReader br = new BufferedReader(fr);

        if (br.ready()) {
            inputNums=Integer.parseInt(br.readLine());
            for(int i=0;i<inputNums;i++){
                this.iPins.add(new IPin());
            }
            gateNums=Integer.parseInt(br.readLine());
            for(int i=0;i<gateNums;i++) {
                this.oPins.add(new OPin());
                str=br.readLine();
                String[] str_arr = str.split("\\s+");
                gateKind=Integer.parseInt(str_arr[0]);

                if (gateKind==1){
                    this.circuits.add(new GateAND());
                }else if (gateKind==2){
                    this.circuits.add(new GateOR());
                }else if (gateKind==3) {
                    this.circuits.add(new GateNOT());
                }

                this.circuits_pin.add(new Vector());
                for(int j=1;j< str_arr.length;j++){
                    this.circuits_pin.get(i).add(Double.parseDouble(str_arr[j]));
                }
            }
            //System.out.println(inputNums);
            //System.out.println(gateNums);
            //System.out.println(circuits_pin.get(0).get(3));
        }
        fr.close();
    }
    public void LineUP(){
        int compare_num=0;
        double mpin=0;
        int mpin_index=0;
        for(int i=0;i<gateNums;i++){
            for(int j=0;j<circuits_pin.get(i).size();j++){
                mpin=circuits_pin.get(i).get(j);
                compare_num=Double.compare(mpin,0);
                if (compare_num==0){
                    break;
                }else if(compare_num<0){    //ipin
                    mpin_index = ((int)mpin*-1)-1;
                    //System.out.println(i+" "+mpin_index);
                    //iPin.setInput(inputValues.get(mpin_index));
                    circuits.get(i).addInputPin(iPins.get(mpin_index));
                }else if(compare_num>0){    //gate
                    mpin_index = ((int)mpin)-1;
                    //System.out.println(i+" "+mpin_index);
                    circuits.get(i).addInputPin(oPins.get(mpin_index));
                }
            }
        }
        for(int i=0;i<oPins.size();i++){
            oPins.get(i).addInputPin(circuits.get(i));
        }
    }


    public boolean getSimulationResult(Vector<Boolean> inputValues) {
        LineUP();
        for(int i=0;i<inputValues.size();i++){
            this.iPins.get(i).setInput(inputValues.get(i));
        }
        return oPins.get(0).getOutput();
    }
}
