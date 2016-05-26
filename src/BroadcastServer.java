/**
 * Created by idalov on 25.05.16.
 */
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class BroadcastServer  {
    private DatagramPacket packet;
    private InetAddress address;
    private int port = 8796;
    byte[] buffer = getBuffer();
    private DatagramSocket socket;
    public BroadcastServer() throws IOException{
        socket = new DatagramSocket(8795);
        socket.setBroadcast(true);

        transmit();


    }

    private void transmit(){
        long Time = (long)System.currentTimeMillis();
        try {
            address = InetAddress.getByName("192.168.0.255");
            for (int i = 0;i<58000909;i++) {
                if(i%100000 == 0)System.out.println("Total send: " + i +"time " +(System.currentTimeMillis()-Time));
                packet = new DatagramPacket(buffer, buffer.length, address, port);
                socket.send(packet);
         /*       packet = new DatagramPacket(buffer, buffer.length, address, port+1);
                socket.send(packet);*/
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            socket.close();
        }
        System.out.print(System.currentTimeMillis()-Time);

    }

    public byte[] getBuffer() {
        byte [] buff = new byte [1481];
        int i = 0;
        byte ADDR = 7;
        buff[i++]=ADDR;
        byte FC = 12;
        buff[i++]=FC;
        short LEN = 1253;
        for (byte b : ByteBuffer.allocate(2).putShort(LEN).array())
            buff[i++]= b;
        byte [] event = getEvent();
        for (int j = 0; j<91;j++)
            for (byte b : event)
                buff[i++]=b;
        return buff;
    }

    public byte[] getEvent(){
        byte [] event = new byte[16];
        int i = 0;
        byte SRC = 5;
        event[i++]= SRC;
        byte flag = 56;
        event[i++]= flag;
        short rez = 0;
        for (byte b : ByteBuffer.allocate(2).putShort(rez).array())
            event[i++]= b;
        int ts = 123456;
        for (byte b : ByteBuffer.allocate(4).putInt(ts).array())
            event[i++]= b;
        short xp = 1123;
        for (byte b : ByteBuffer.allocate(2).putShort(xp).array())
            event[i++]= b;
        short xm = 1234;
        for (byte b : ByteBuffer.allocate(2).putShort(xm).array())
            event[i++]= b;
        short yp = 1234;
        for (byte b : ByteBuffer.allocate(2).putShort(yp).array())
            event[i++]= b;
        short ym = 1235;
        for (byte b : ByteBuffer.allocate(2).putShort(ym).array())
            event[i++]= b;
        return event;
    }
}
