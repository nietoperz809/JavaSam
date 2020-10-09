package samtool;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Utils
{
    public static InputStream getResource (String name)
    {
        InputStream is = ClassLoader.getSystemResourceAsStream (name);
        if (is == null)
        {
            System.out.println ("could not load: "+name);
            return null;
        }
        return new BufferedInputStream (is);
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
}
