package samtool.gui;

import samtool.Main;
import samtool.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiMain
{
    private JPanel MainPanel;
    private JTextField textField1;

    public GuiMain ()
    {
        textField1.addActionListener (new ActionListener ()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                String s = textField1.getText ();
                try
                {
                    Utils.playWave (Main.doSam (s));
                    textField1.setText ("");
                }
                catch (Exception exception)
                {
                    exception.printStackTrace ();
                }
            }
        });
    }

    public static void main (String[] args)
    {
        JFrame frame = new JFrame ("SAM for Java");
        GuiMain gui = new GuiMain();
        frame.setContentPane (gui.MainPanel);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        gui.MainPanel.add (new ImagePanel (), BorderLayout.CENTER);
        frame.pack ();
        frame.setVisible (true);
    }

    private void createUIComponents ()
    {
    }
}
