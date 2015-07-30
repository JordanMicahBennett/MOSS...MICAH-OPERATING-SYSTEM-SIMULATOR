import javax.swing.JFrame;
//import java.awt.GraphicsDevice.WindowTranslucency.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Rectangle2D;
import java.awt.Toolkit;
import java.awt.Dimension;
import data.packages.UNICODE.*;

public class UNICODE_DisplayConsole 
{
    //establish frame
    static JFrame frame = new JFrame ( );

    //establish main program gui panel stuff
        //establish covenience pack
        static UNICODE_ConveniencePack conveniencePack = new UNICODE_ConveniencePack ( );
        //establish duration pack for all splash panels //DEFAULT ~  { 500, 500, 400 }; //TESTING = { 10, 10, 10 };
        static int [ ] splashPanelDurationCollection = { 100, 100, 40 };
        //establsih gui panel: takes frame,
        
        static UNICODE_GuiPanel mainProgramPanel = new UNICODE_GuiPanel ( frame, conveniencePack.getArraySum ( splashPanelDurationCollection ) );
        //establish screen dimensions
        static Dimension screenDimension = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        
    //establish splash screen panel stuff
        static SplashPanelInstanceI splashPanelI = null;
        static SplashPanelInstanceII splashPanelII = null;
        
    public static void main ( String [ ] arguments )
    {
        //main program stuff
        Thread mainPogramThread = new Thread
        (
            new Runnable ( )
            {
                public void run ( )
                {
                    new UNICODE_AudioPlayer ( ).playAudio ( "enter.wav" );
                    //establish Jframe stuff
                        //remove splash panel
                        frame.remove ( splashPanelII ); //splashPanel has been defined at this point of run time. Now we can remove splash panel from the frame, and add the main program panel instead.
                        //add main program panel
                        frame.add ( mainProgramPanel );
                         //corectly display all panel components by passing panel to set content pane
                        frame.setContentPane ( mainProgramPanel ); /*translucency requirement*/
                        ///set application window dimension
                        frame.setSize ( new Dimension ( 1200, 800 ) );
                        //center application on screen buffer based on user's screen size
                        frame.setLocationRelativeTo ( null );
                        //set frame shape
                        frame.setShape ( new RoundRectangle2D.Double ( 0, 0, 1200, 800, 50, 50 ) );
                        //enable full screen mode
                        //conveniencePack.enableFullScreenMode ( frame );
                        //set opacity
                        frame.setOpacity ( mainProgramPanel.getStartupOpacityLevel ( ) );
                }
            }
        );

        
        //splash screen stuff
            //ENGINE LOGO
            splashPanelII = new SplashPanelInstanceII ( splashPanelDurationCollection [ 1 ], "data/images/splash1.png", mainPogramThread, null ); 
            Thread splashPanelIIThread = new Thread
            (
                new Runnable ( )
                {
                    public void run ( )
                    {
                        new UNICODE_AudioPlayer ( ).playAudio ( "startupStage1.wav" );
                        //establish Jframe stuff      
                            //clear previous splash panel from 
                            frame.remove ( splashPanelI ); //splashPanel has been defined at this point of run time. Now we can remove splash panel from the frame, and add the main program panel instead.
                            //add gui panel
                            frame.add ( splashPanelII );
                            //corectly display all panel components by passing panel to set content pane
                            frame.setContentPane ( splashPanelII ); /*translucency requirement*/
                            //set application window dimension
                            frame.setSize ( new Dimension ( 500, 220 ) );
                            //center application on screen buffer based on user's screen size
                            frame.setLocationRelativeTo ( null );
                            //set frame shape
                            frame.setShape ( new RoundRectangle2D.Double ( 0, 0, 500, 220, 20, 20 ) );  
                            //show the frame
                            frame.setVisible ( true );
                    }
                }
            );
            
            //OS NAME LOGO
            splashPanelI = new SplashPanelInstanceI ( splashPanelDurationCollection [ 0 ], "data/images/splash0.png", splashPanelIIThread, splashPanelII ); 
            Thread splashPanelIThread = new Thread
            (
                new Runnable ( )
                {
                    public void run ( )
                    {
                        new UNICODE_AudioPlayer ( ).playAudio ( "startupStage0.wav" );
                        
                        
                        //establish Jframe stuff
                            //establish look and feel
                            frame.setDefaultLookAndFeelDecorated ( true ); /*translucency requirement*/
                            //remove frame from window
                            frame.setUndecorated ( true );
                            //add gui panel
                            frame.add ( splashPanelI );
                            //corectly display all panel components by passing panel to set content pane
                            frame.setContentPane ( splashPanelI ); /*translucency requirement*/
                            //set application window dimension
                            frame.setSize ( new Dimension ( 400, 280 ) );
                            //center application on screen buffer based on user's screen size
                            frame.setLocationRelativeTo ( null );
                            //set frame shape
                            frame.setShape ( new RoundRectangle2D.Double ( 0, 0, 400, 280, 20, 20 ) );  
                            //show the frame
                            frame.setVisible ( true );
                    }
                }
            );
            splashPanelI.commence ( );
            splashPanelIThread.start ( );
    }
}