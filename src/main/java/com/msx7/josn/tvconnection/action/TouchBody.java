package com.msx7.josn.tvconnection.action;


import com.msx7.josn.tvconnection.mima.common.util.ByteUtil;
import com.msx7.josn.tvconnection.pack.IDecoder;
import com.msx7.josn.tvconnection.pack.IEncoder;
import com.msx7.josn.tvconnection.pack.message.MessageBody;

/**
 * Created by Josn on 2015/12/21.
 */
public class TouchBody implements MessageBody, IDecoder {

    public TouchInfo[] infos;

    public TouchBody(TouchInfo... infos) {
        this.infos = infos;
    }

    @Override
    public void decoder(byte[] bytes) {
        int length = (bytes.length - 1) / 9;
        infos = new TouchInfo[length];
        for (int i = 0; i < length; i++) {
            byte[] _byte = new byte[9];
            System.arraycopy(bytes, 9 * i, _byte, 0, 9);
            infos[i] = new TouchInfo();
            infos[i].decoder(_byte);
        }
    }

    @Override
    public int getBodyLength() {
        return infos.length * 9 + 1;
    }

    @Override
    public byte[] encode() {
        byte[] _byte = new byte[getBodyLength()];
        for (int i = 0; i < infos.length; i++) {
            System.arraycopy(infos[i].encode(), 0, _byte, 9 * i, 9);
        }
        _byte[_byte.length - 1] = messageEndFlag;
        return _byte;
    }

    public static class TouchInfo implements IDecoder, IEncoder {
        public  byte action;
        public  int x;
        public  int y;

        public TouchInfo() {
        }

        public TouchInfo(byte action, int x, int y) {
            this.action = action;
            this.x = x;
            this.y = y;
        }

        @Override
        public void decoder(byte[] bytes) {
            action = bytes[0];
            byte[] _byte = new byte[4];
            System.arraycopy(bytes, 1, _byte, 0, 4);
            x = ByteUtil.bytesToInt(_byte);
            System.arraycopy(bytes, 5, _byte, 0, 4);
            y = ByteUtil.bytesToInt(_byte);
        }

        @Override
        public byte[] encode() {
            byte[] _byte = new byte[9];
            _byte[0] = action;
            System.arraycopy(ByteUtil.intToBytes(x), 0, _byte, 1, 4);
            System.arraycopy(ByteUtil.intToBytes(y), 0, _byte, 5, 4);
            return _byte;
        }
    }
}
