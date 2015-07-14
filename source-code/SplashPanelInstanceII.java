import java.awt.Color;
import java.awt.Graphics2D;

import data.packages.UNICODE.*;

public class SplashPanelInstanceII extends UNICODE_SplashPanel
{
    //attributes
    
    
    //constructor
    public SplashPanelInstanceII ( int _splashScreenDuration, String splashScreenImageDirectory, Thread _nextThread, UNICODE_SplashPanel _nextSplashPanel )
    {
        super ( _splashScreenDuration, splashScreenImageDirectory, _nextThread, _nextSplashPanel );
    }
    
    
    //extra draw method
    public void drawMore ( Graphics2D graphics2d )
    {
        
    }
}