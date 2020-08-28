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

    public LogicSimulator() {
        circuits = new Vector<>();  //gate
        iPins = new Vector<>();     //輸入腳
        oPins = new Vector<>();     //輸出腳
        circuits_pin = new Vector<>();  //gate連接的腳位
        oPinsList = new Vector<>(); //輸出腳名單，從gate中篩選出
    }

    public void load(String file1Path) throws IOException {
        resetAll();
        FileReader fr = new FileReader(file1Path);
        BufferedReader br = new BufferedReader(fr);

        if (br.ready()) {
            inputNums=Integer.parseInt(br.readLine());
            gateNums=Integer.parseInt(br.readLine());
            for(int i=0;i<gateNums;i++) {
                oPinsList.add(i);
                String str=br.readLine();
                String[] str_arr = str.split("\\s+");

                Vector<Double> mVec=new Vector<>();
                for(int j=0;j< str_arr.length;j++){
                    mVec.add(Double.parseDouble(str_arr[j]));
                }
                this.circuits_pin.addElement(mVec);
            }
        }
        fr.close();
    }

    public void LineUP(){
        clearDevice();
        for(int i=0;i<inputNums;i++){
            this.iPins.add(new IPin());
        }

        for(int i=0;i<gateNums;i++){
            int gateKind =  circuits_pin.get(i).get(0).intValue();
            if (gateKind ==1){
                this.circuits.add(new GateAND());
            }else if (gateKind ==2){
                this.circuits.add(new GateOR());
            }else if (gateKind ==3) {
                this.circuits.add(new GateNOT());
            }
        }

        int compare_num=0;
        double mpin=0;
        int mpin_index=0;
        for(int i=0;i<gateNums;i++){
            for(int j=1;j<circuits_pin.get(i).size();j++){
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

    public StringBuilder tableCommonStr(){
        StringBuilder commonStr = new StringBuilder();

        //"i i i | o\n"
        for(int i=0;i<inputNums;i++){
            commonStr.append("i ");
        }
        commonStr.append("|");
        for(int i=0;i<oPins.size();i++){
            commonStr.append(" o");
        }
        commonStr.append("\n");

        //"1 2 3 | 1\n"
        for(int i=0;i<inputNums;i++){
            commonStr.append((i + 1) +" ");
        }
        commonStr.append("|");
        for(int i=0;i<oPins.size();i++){
            commonStr.append(" "+ (i + 1));
        }
        commonStr.append("\n");

        //"------+--\n"
        for(int i=0;i<inputNums;i++){
            commonStr.append("--");
        }
        commonStr.append("+");
        for(int i=0;i<oPins.size();i++){
            commonStr.append("--");
        }
        commonStr.append("\n");

        return commonStr;
    }

    public String getSimulationResult(Vector<Boolean> inputValues) {
        LineUP();

        StringBuilder simulationResultStr = new StringBuilder();
        simulationResultStr.append("Simulation Result:\n");

        //"i i i | o\n"
        //"1 2 3 | 1\n"
        //"------+--\n"
        simulationResultStr.append(tableCommonStr());

        //"0 1 1 | 0\n"
        for(int i=0;i<inputValues.size();i++){
            this.iPins.get(i).setInput(inputValues.get(i));
            simulationResultStr.append(inputValues.get(i) ? "1 " : "0 ");
        }
        simulationResultStr.append("|");
        for(int i=0;i<oPins.size();i++){
            simulationResultStr.append(oPins.get(i).getOutput() ? " 1" : " 0");
        }
        simulationResultStr.append("\n");

        return simulationResultStr.toString();
    }

    public String getTruthTable(){
        LineUP();

        StringBuilder truthTableStr = new StringBuilder();
        truthTableStr.append("Truth table:\n");

        //"i i i | o\n"
        //"1 2 3 | 1\n"
        //"------+--\n"
        truthTableStr.append(tableCommonStr());

        String inputValueStr = "";

        //"0 0 0 | 0\n"
        for(int i=0;i<(int)Math.pow(2,inputNums);i++){
            inputValueStr=String.format("%0"+ inputNums +"d", Integer.valueOf(Integer.toBinaryString(i)));
            for(int j=0;j<inputValueStr.length();j++){
                this.iPins.get(j).setInput(inputValueStr.charAt(j) == '1');
                truthTableStr.append(inputValueStr.charAt(j)+" ");
            }
            truthTableStr.append("|");
            for(int j=0;j<oPins.size();j++){
                truthTableStr.append(oPins.get(j).getOutput() ? " 1" : " 0");
            }
            truthTableStr.append("\n");
        }


        return truthTableStr.toString();
    }

    public void clearDevice(){
        circuits.clear();
        iPins.clear();
        oPins.clear();
    }

    public void resetAll(){
        clearDevice();
        circuits_pin.clear();
        oPinsList.clear();
        inputNums=0;
        gateNums=0;
    }
}
