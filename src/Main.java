import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
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
            byte[] clbytes = extractResource ("samclass.class");
            ByteArrayClassLoader bac = new ByteArrayClassLoader (clbytes);
            Class<?> cl = bac.loadClass ("samclass");
            meth = cl.getMethod("xmain", PrintStream.class, String[].class);
        }
        catch (Exception e)
        {
            System.out.println ("Init failed: "+e);
        }
    }

    /**
     * Get byte array from resource bundle
     * @param name what resource
     * @return the resource as byte array
     * @throws Exception if smth. went wrong
     */
    static public byte[] extractResource (String name) throws Exception
    {
        InputStream is = ClassLoader.getSystemResourceAsStream (name);

        ByteArrayOutputStream out = new ByteArrayOutputStream ();
        byte[] buffer = new byte[1024];
        while (true)
        {
            int r = is.read (buffer);
            if (r == -1)
            {
                break;
            }
            out.write (buffer, 0, r);
        }

        return out.toByteArray ();
    }

    /**
     * Play WAV
     * @param data the WAV file as byte array
     * @throws Exception If smth. went wrong
     */
    public static void playWave (byte[] data) throws Exception
    {
        final Clip clip = (Clip) AudioSystem.getLine (new Line.Info (Clip.class));
        InputStream inp  = new BufferedInputStream(new ByteArrayInputStream (data));
        clip.open (AudioSystem.getAudioInputStream (inp));
        clip.start ();
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
     * Main loop
     * @throws Exception if smth went wrong
     */
    public static void main (String[] irgnored) throws Exception
    {
        byte[] result = doSam ("Hello, I am Sam, the software mouth.");
        playWave (result);
        Scanner keyboard = new Scanner(System.in);
        while (true)
        {
            System.out.println("Enter some words: ");
            playWave (doSam (keyboard.nextLine ()));
        }
    }
}
