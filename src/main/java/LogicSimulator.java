import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class LogicSimulator {
    private Vector<Device> circuits;
    private Vector<Device> iPins;
    private Vector<Device> oPins;
    private Vector<Vector<Double>> circuits_pin;
    private Vector<Integer> oPinsList;
    private int inputNums=0;
    private int gateNums=0;
    private String str="";

    public LogicSimulator() {
        circuits = new Vector<>();
        iPins = new Vector<>();
        oPins = new Vector<>();
        circuits_pin = new Vector<Vector<Double>>();
        oPinsList = new Vector<>();
    }

    public void load(String file1Path) throws IOException {
        resetAll();
        FileReader fr = new FileReader(file1Path);
        BufferedReader br = new BufferedReader(fr);

        if (br.ready()) {
            inputNums=Integer.parseInt(br.readLine());
            for(int i=0;i<inputNums;i++){
                this.iPins.add(new IPin());
            }
            gateNums=Integer.parseInt(br.readLine());
            for(int i=0;i<gateNums;i++) {
                //this.oPins.add(new OPin());     //OPin
                oPinsList.add(i);
                str=br.readLine();
                String[] str_arr = str.split("\\s+");
                int gateKind = Integer.parseInt(str_arr[0]);

                if (gateKind ==1){
                    this.circuits.add(new GateAND());
                }else if (gateKind ==2){
                    this.circuits.add(new GateOR());
                }else if (gateKind ==3) {
                    this.circuits.add(new GateNOT());
                }

                this.circuits_pin.add(new Vector());
                for(int j=1;j< str_arr.length;j++){
                    this.circuits_pin.get(i).add(Double.parseDouble(str_arr[j]));
                }
            }
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
                }else if(compare_num<0){    //iPin
                    mpin_index = ((int)mpin*-1)-1;
                    circuits.get(i).addInputPin(iPins.get(mpin_index));
                }else if(compare_num>0){    //gate
                    mpin_index = ((int)mpin)-1;
                    circuits.get(i).addInputPin(circuits.get(mpin_index));
                    oPinsList.removeElement(mpin_index);
                }
            }
        }
        for(int i=0;i<oPinsList.size();i++){
            this.oPins.add(new OPin());     //OPin
            oPins.get(oPins.size()-1).addInputPin(circuits.get(oPinsList.get(i)));
        }
    }

    public String getSimulationResult(Vector<Boolean> inputValues) {
        LineUP();

        StringBuilder simulationResultStr = new StringBuilder();
        simulationResultStr.append("Simulation Result:\n");

        //"i i i | o\n"
        for(int i=0;i<inputNums;i++){
            simulationResultStr.append("i ");
        }
        simulationResultStr.append("|");
        for(int i=0;i<oPins.size();i++){
            simulationResultStr.append(" o");
        }
        simulationResultStr.append("\n");

        //"1 2 3 | 1\n"
        for(int i=0;i<inputNums;i++){
            simulationResultStr.append((i + 1) +" ");
        }
        simulationResultStr.append("|");
        for(int i=0;i<oPins.size();i++){
            simulationResultStr.append(" "+ (i + 1));
        }
        simulationResultStr.append("\n");

        //"------+--\n"
        for(int i=0;i<inputNums;i++){
            simulationResultStr.append("--");
        }
        simulationResultStr.append("+");
        for(int i=0;i<oPins.size();i++){
            simulationResultStr.append("--");
        }
        simulationResultStr.append("\n");

        //"0 1 1 | 0\n"
        for(int i=0;i<inputValues.size();i++){
            this.iPins.get(i).setInput(inputValues.get(i));

            /*if (inputValues.get(i)){
                simulationResultStr.append("1 ");
            }else{
                simulationResultStr.append("0 ");
            }*/
            simulationResultStr.append(inputValues.get(i) ? "1 " : "0 ");
        }
        simulationResultStr.append("|");
        for(int i=0;i<oPins.size();i++){
            simulationResultStr.append(oPins.get(i).getOutput() ? " 1" : " 0");
        }
        simulationResultStr.append("\n");

        return simulationResultStr.toString();
    }

    public void resetAll(){
        circuits.clear();
        iPins.clear();
        oPins.clear();
        circuits_pin.clear();
        oPinsList.clear();
    }
}
