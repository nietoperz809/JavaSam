import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Main
{
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

    public static Clip playWave (byte[] data, boolean cont) throws Exception
    {
        final Clip clip = (Clip) AudioSystem.getLine (new Line.Info (Clip.class));
        InputStream inp  = new BufferedInputStream(new ByteArrayInputStream (data));
        clip.open (AudioSystem.getAudioInputStream (inp));
        if (cont)
            clip.loop (Clip.LOOP_CONTINUOUSLY);
        clip.start ();
        return clip;
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

    public static void main (String[] args) throws Exception
    {
        byte[] clbytes = extractResource ("samclass.class");
        ByteArrayClassLoader bac = new ByteArrayClassLoader (clbytes);
        Class cl = bac.loadClass ("samclass");
        Method meth = cl.getMethod("xmain", PrintStream.class, String[].class);
        String[] arg = {"-stdout","dummy","hello world"};
        ByteArrayOutputStream ba = new ByteArrayOutputStream(10000);
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


//                                Path path = Paths.get("C:\\Users\\Administrator\\Desktop\\lljvm\\wavs\\repwav.dat");
//                                Files.write(path, result);

        playWave (result, false);
        System.out.println (result);
        Thread.sleep (10000);

//        byte[] arr = extractResource ("javasam.class");
//        ByteArrayClassLoader bc = new ByteArrayClassLoader (arr);
//        System.out.println (arr);
//        Class c = bc.loadClass ("javasam");
//        System.out.println (c);
//
    }
}
