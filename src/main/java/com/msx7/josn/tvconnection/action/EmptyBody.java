package com.msx7.josn.tvconnection.action;

import com.msx7.josn.tvconnection.pack.IDecoder;
import com.msx7.josn.tvconnection.pack.message.MessageBody;

/**
 * Created by Josn on 2015/12/26.
 */
public class EmptyBody implements MessageBody, IDecoder {

    @Override
    public void decoder(byte[] bytes) {

    }

    @Override
    public int getBodyLength() {
        return 1;
    }

    @Override
    public byte[] encode() {
        return new byte[]{messageEndFlag};
    }
}
