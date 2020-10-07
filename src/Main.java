import java.awt.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
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

    public static void main (String[] args) throws Exception
    {
        String[] arg = {"-stdout","dummy","hello"};
        ByteArrayOutputStream ba = new ByteArrayOutputStream(10000);
        PrintStream p = new PrintStream (ba);
        samclass.xmain (p, arg);
        byte[] result = ba.toByteArray ();
        System.out.println (result);

//        byte[] arr = extractResource ("javasam.class");
//        ByteArrayClassLoader bc = new ByteArrayClassLoader (arr);
//        System.out.println (arr);
//        Class c = bc.loadClass ("javasam");
//        System.out.println (c);
//
//        Method meth = c.getMethod("main", String[].class);
//        String[] params = {"hello","doof"}; // init params accordingly
//        meth.invoke(null, (Object) params);
    }
}
