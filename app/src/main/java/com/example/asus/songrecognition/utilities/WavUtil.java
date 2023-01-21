package com.example.asus.songrecognition.utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class WavUtil {
    public static FileInputStream getTempFileInputStream(File file){
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    public static byte[] getBytesFromInputStream(InputStream is)throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while(true){
            byte[] buffer = new byte[100];
            int noOfBytes = is.read(buffer);
            if(noOfBytes == -1 ){
                break;
            }else{
                bos.write(buffer,0,noOfBytes);
            }
        }
        bos.flush();
        bos.close();
        return bos.toByteArray();
    }
    public static short[] getSampleAmplitudes(File file) throws IOException {
        byte[] wavByte = getBytesFromInputStream(getTempFileInputStream(file));
        short[] amp= new short[wavByte.length/2];
        ByteBuffer.wrap(wavByte).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(amp);

        return amp;
    }

}
