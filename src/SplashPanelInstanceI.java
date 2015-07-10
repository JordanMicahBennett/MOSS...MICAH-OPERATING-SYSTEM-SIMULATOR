import java.awt.Color;
import java.awt.Graphics2D;

import data.packages.UNICODE.*;

public class SplashPanelInstanceI extends UNICODE_SplashPanel
{
    //attributes
    private UNICODE_AnimatedBubble animatedBubble = null;
    private Color animatedBubbleColour = null;
    
    //constructor
    public SplashPanelInstanceI ( int _splashScreenDuration, String splashScreenImageDirectory, Thread _nextThread, UNICODE_SplashPanel _nextSplashPanel )
    {
        super ( _splashScreenDuration, splashScreenImageDirectory, _nextThread, _nextSplashPanel );
        
        animatedBubble = new UNICODE_AnimatedBubble ( 400/2 - ( 74/2 * 2 + ( 74 - 22 ) ), 280/2 - ( 74/3 + 4 ), 74, 74, this );
        animatedBubbleColour = new Color ( 0, 0, 0 );
    }
    
    
    //extra draw method
    public void drawMore ( Graphics2D graphics2d )
    {
        animatedBubble.draw ( graphics2d, animatedBubbleColour );
    }
}