package samtool;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Main
{
    static Method meth;

    /*
     * Load the sam!
     */
    static
    {
        try
        {
            byte[] clbytes = Utils.extractResource ("samclass.class");
            ByteArrayClassLoader bac = new ByteArrayClassLoader (clbytes);
            Class<?> cl = bac.loadClass ("samclass");
            meth = cl.getMethod("xmain", PrintStream.class, String[].class);
        }
        catch (Exception e)
        {
            System.out.println ("Init failed: "+e);
        }
    }

    public static void swap2 (byte[] in, int off)
    {
        byte tmp = in[0+off];
        in[0+off] = in[1+off];
        in[1+off] = tmp;
    }

    public static void swap4 (byte[] in, int off)
    {
        byte tmp = in[0+off];
        in[0+off] = in[3+off];
        in[3+off] = tmp;
        tmp = in[1+off];
        in[1+off] = in[2+off];
        in[2+off] = tmp;
    }

    /**
     * Genetrate WAV from text input using SAM
     * @param txt text to speak
     * @return WAV data
     * @throws Exception if smth. went wrong
     */
    public static byte[] doSam (String txt) throws Exception
    {
        String[] arg = {"-stdout","dummy",txt};
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        PrintStream p = new PrintStream (ba);
        meth.invoke(null, p, (Object) arg);
        byte[] result = ba.toByteArray ();
        swap4 (result, 4);
        swap4 (result, 16);
        swap2 (result, 20);
        swap2 (result, 22);
        swap4 (result, 24);
        swap4 (result, 28);
        swap2 (result, 32);
        swap2 (result, 34);
        swap4 (result, 40);
        result[44] = (byte)0xcd;
        result[45] = (byte)0xcd;
        result[46] = (byte)0xcd;
        return result;
    }

    /**
     * samtool.Main loop
     * @throws Exception if smth went wrong
     */
    public static void main (String[] irgnored) throws Exception
    {
        byte[] result = doSam ("Hello, I am Sam, the software mouth.");
        Utils.playWave (result);
        Scanner keyboard = new Scanner(System.in);
        while (true)
        {
            System.out.println("Enter some words: ");
            Utils.playWave (doSam (keyboard.nextLine ()));
        }
    }
}
