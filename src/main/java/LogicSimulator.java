import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class LogicSimulator {
    private Vector<Device> circuits;
    private Vector<Device> iPins;
    private Vector<Device> oPins;
    int inputNums=0;
    int gateNums=0;
    int gateKind=0;

    public void load(String file1Path) throws IOException {
        FileReader fr = new FileReader(file1Path);
        BufferedReader br = new BufferedReader(fr);

        if (br.ready()) {
            inputNums=Integer.parseInt(br.readLine());
            gateNums=Integer.parseInt(br.readLine());
            for(int i=0;i<gateNums;i++) {

                gateKind=Integer.parseInt(br.readLine());
                if (gateKind==1){
                    this.circuits.add(new GateAND());
                }else if (gateKind==2){
                    this.circuits.add(new GateOR());
                }else if (gateKind==3) {
                    this.circuits.add(new GateNOT());
                }
            }
            System.out.println(inputNums);
            System.out.println(gateNums);
        }
        fr.close();
    }

}
