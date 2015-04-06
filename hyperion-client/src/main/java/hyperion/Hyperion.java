package hyperion;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import proto.Message.ClearRequest;
import proto.Message.ColorRequest;
import proto.Message.HyperionReply;
import proto.Message.HyperionRequest;
import proto.Message.ImageRequest;

import com.google.protobuf.ByteString;

/**
 * Proto client taken from  https://github.com/tvdzwan/hyperion/wiki/Java-proto-client-example
 *
 */
public class Hyperion {
    private final Socket mSocket;   

    public Hyperion(String address, int port) throws UnknownHostException, IOException {
        mSocket = new Socket(address, port);
    }

    @Override
    protected void finalize() throws Throwable {
        if (mSocket != null && mSocket.isConnected()) {
            mSocket.close();
        }
        super.finalize();
    }

    public void clear(int priority) throws IOException {
        ClearRequest clearRequest = ClearRequest.newBuilder()
                .setPriority(priority)
                .build();

        HyperionRequest request = HyperionRequest.newBuilder()
                .setCommand(HyperionRequest.Command.CLEAR)
                .setExtension(ClearRequest.clearRequest, clearRequest)
                .build();

        sendRequest(request);
    }

    public void clearall() throws IOException {
        HyperionRequest request = HyperionRequest.newBuilder()
                .setCommand(HyperionRequest.Command.CLEARALL)
                .build();

        sendRequest(request);       
    }

    public void setColor(Color color, int priority) throws IOException {
        setColor(color, priority, -1);
    }

    public void setColor(Color color, int priority, int duration_ms) throws IOException {
        ColorRequest colorRequest = ColorRequest.newBuilder()
                .setRgbColor(color.getRGB())
                .setPriority(priority)
                .setDuration(duration_ms)
                .build();

        HyperionRequest request = HyperionRequest.newBuilder()
                .setCommand(HyperionRequest.Command.COLOR)
                .setExtension(ColorRequest.colorRequest, colorRequest)
                .build();

        sendRequest(request);
    }

    public void setImage(byte[] data, int width, int height, int priority) throws IOException {
        setImage(data, width, height, priority, -1);
    }

    public void setImage(byte[] data, int width, int height, int priority, int duration_ms) throws IOException {
        ImageRequest imageRequest = ImageRequest.newBuilder()
                .setImagedata(ByteString.copyFrom(data))
                .setImagewidth(width)
                .setImageheight(height)
                .setPriority(priority)
                .setDuration(duration_ms)
                .build();

        HyperionRequest request = HyperionRequest.newBuilder()
                .setCommand(HyperionRequest.Command.IMAGE)
                .setExtension(ImageRequest.imageRequest, imageRequest)
                .build();

        sendRequest(request);
    }

    private void sendRequest(HyperionRequest request) throws IOException {
        int size = request.getSerializedSize();

        // create the header
        byte[] header = new byte[4];
        header[0] = (byte)((size >> 24) & 0xFF);
        header[1] = (byte)((size >> 16) & 0xFF);
        header[2] = (byte)((size >>  8) & 0xFF);
        header[3] = (byte)((size      ) & 0xFF);

        // write the data to the socket
        OutputStream output = mSocket.getOutputStream();
        output.write(header);
        request.writeTo(output);
        output.flush();

        HyperionReply reply = receiveReply();
        if (!reply.getSuccess()) {
            System.out.println(reply.toString());
        }
    }

    private HyperionReply receiveReply() throws IOException {
        InputStream input = mSocket.getInputStream();

        byte[] header = new byte[4];
        input.read(header, 0, 4);
        int size = (header[0]<<24) | (header[1]<<16) | (header[2]<<8) | (header[3]);
        byte[] data = new byte[size];
        input.read(data, 0, size);
        HyperionReply reply = HyperionReply.parseFrom(data);

        return reply;
    }

   
}