package com.example.asus.songrecognition.fingerprint;

import android.util.Log;

/**
 *
 * @author nur4nnis4@gmail.com
 */

public final class STFT {
   
    public static Complex[][] stft(short[] audioData, int frameSize, int overlap){
        int M = frameSize-overlap;
        int noOfFrames = (int) Math.ceil(Math.abs(audioData.length-frameSize)/M)+1;
        int dataIndex;        
        double[] window = hammingWindow(frameSize);

        Complex[][] results= new Complex[noOfFrames][];
        String hamingw = "";
        for(int i=0;i<noOfFrames;i++){
            Complex[] buffer = new Complex[frameSize];
            for(int j=0;j<frameSize;j++){
                dataIndex=j+i*M;
                if (dataIndex<audioData.length)
                {           
                   buffer[j] = new Complex(audioData[dataIndex]*window[j],0);
                   if (i==0){
                       hamingw += j+"|"+buffer[j].re()+" ";
                   }
                }   
                else
                {
                    buffer[j] = new Complex(0,0);//zero padding
                }                
            }

            results[i] = fft(buffer);
        }
        Log.d("STFT-HW(n|W(n))", hamingw);
        return results;
    }
    
    
    public static double[] hammingWindow(int N){
        double [] w = new double [N];
        for (int i=0; i<N; i++){
            w[i]= (0.54-0.46*Math.cos(2*Math.PI*i/(N-1)));
        }
        return w;
    }
        
    public static Complex[] fft(Complex[] x){
        int i,j,k,n1,n2,a,n,m;
        Complex t;
        double[] cos,sin;
        double t1,t2;
        n = x.length;
        
        // Make sure n is a power of 2
        m = (int)(Math.log(n) / Math.log(2));
        if(n != (1<<m))
            throw new RuntimeException("FFT length must be power of 2");
 
        // Lookup tables.  Only need to recompute when size of FFT changes
        cos = new double[n/2];
        sin = new double[n/2];    
     
        for(i=0; i<n/2; i++) {
            cos[i] = Math.cos(-2*Math.PI*i/n);
            sin[i] = Math.sin(-2*Math.PI*i/n);
        }
     
        // Bit-reverse
        j = 0;
        n2 = n/2;
        for (i=1; i < n - 1; i++) {
            n1 = n2;
            while ( j >= n1 ) {
                j = j - n1;
                n1 = n1/2;
            }
            j = j + n1;
     
            if (i < j) {
                t = x[i];
                x[i] = x[j];
                x[j] = t;
            }
        }
 
        // FFT
        n1 = 0;
        n2 = 1;
   
        for (i=0; i < m; i++) {
            n1 = n2;
            n2 = n2 + n2;
            a = 0;     
            for (j=0; j < n1; j++) {            
                for (k=j; k < n; k=k+n2) {
                    t1 = cos[a]*x[k+n1].re() - sin[a]*x[k+n1].im();
                    t2 = sin[a]*x[k+n1].re() + cos[a]*x[k+n1].im();
                    x[k+n1] = new Complex((x[k].re() - t1),(x[k].im() - t2));
                    x[k] = new Complex((x[k].re() + t1),(x[k].im() + t2));
                }
                a +=  1 << (m-i-1);
            }
        }
        return x;
    }
}
