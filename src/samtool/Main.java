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
            byte[] clbytes = Utils.extractResource ("SamClass");
            ByteArrayClassLoader bac = new ByteArrayClassLoader (clbytes);
            Class<?> cl = bac.loadClass ("samtool.SamClass");
            meth = cl.getMethod("xmain", PrintStream.class, String[].class);
        }
        catch (Exception e)
        {
            System.out.println ("Init failed: "+e);
        }
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
