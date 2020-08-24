import org.junit.*;
import static org.junit.Assert.*;

public class DeviceTest
{
    @Test
    public void testDevicePolymorphism()
    {
        Device device = new IPin();
        assertEquals(IPin.class.getName(), device.getClass().getName());

        /* implement OPin, GateNOT, GateAND, GateOR test */
    }

    @Test
    public void testIPinAndOPin()
    {
        // 0 = 0
        IPin iPin = new IPin();
        iPin.setInput(true);

        OPin oPin = new OPin();
        oPin.addInputPin(iPin);

        assertEquals(true, oPin.getOutput());

        /* implement 1 = 1 test */
    }

    @Test
    public void testGateNOT()
    {
        // NOT 0 = 1
        IPin iPin = new IPin();
        iPin.setInput(false);

        GateNOT gateNOT = new GateNOT();
        gateNOT.addInputPin(iPin);

        assertEquals(true, gateNOT.getOutput());

        /* implement NOT 1 = 0 test */
    }
}
