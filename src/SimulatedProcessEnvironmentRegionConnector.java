import java.awt.geom.QuadCurve2D;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.Toolkit;
import java.awt.Graphics2D;
import java.awt.geom.PathIterator;

import data.packages.UNICODE.*;

public class SimulatedProcessEnvironmentRegionConnector
{
    //establish attributes
    private QuadCurve2D regionConnectorBody = null;
    private Point regionConnectorStartPoint = null;
    private Point regionConnectorEndPoint = null;
    private Dimension regionConnectorCurveThreshold = null;
    private SimulatedTaskManipulationType taskManipulationType = null;
    private Color regionConnectorColour = null;
    private UNICODE_GuiPanel guiPanel = null;
    private String regionConnectorLabelString = "";
    private UNICODE_ConveniencePack unicodeConveniencePack;
    
    //constructor 0 - no image
    public SimulatedProcessEnvironmentRegionConnector ( UNICODE_GuiPanel guiPanel, Point regionConnectorStartPoint, Point regionConnectorEndPoint, Dimension regionConnectorCurveThreshold, Color regionConnectorColour, SimulatedTaskManipulationType taskManipulationType, String regionConnectorLabelString )
    {
        //establish features    
        this.guiPanel = guiPanel;
        this.regionConnectorBody = new QuadCurve2D.Double ( regionConnectorStartPoint.getX ( ), regionConnectorStartPoint.getY ( ), regionConnectorCurveThreshold.getWidth ( ), regionConnectorCurveThreshold.getHeight ( ), regionConnectorEndPoint.getX ( ), regionConnectorEndPoint.getY ( ) );
        this.regionConnectorColour = regionConnectorColour;
        this.taskManipulationType = taskManipulationType;
        this.regionConnectorLabelString = regionConnectorLabelString;
        unicodeConveniencePack = new UNICODE_ConveniencePack ( );
    }
    
    //methods
        //accessors
        public Color getRegionConnectorColour ( )
        {
            return regionConnectorColour;
        }
        public QuadCurve2D getRegionConnectorBody ( )
        {
            return regionConnectorBody;
        }
        
        //public enum SimulatedTaskManipulationType { ADMITTING, INTERRUPTING, IOEVENTWAITING, IOEVENTCOMPLETING, DISPATCHING, EXITING };
        
        public String getStatus ( String taskName )
        {
            String returnValue = "";
            
            switch ( taskManipulationType )
            {
                /////////////////////////////////////////////////////////////////////////////////
                //for below regions, there is only 1 way for a task to end up in them
                /////////////////////////////////////////////////////////////////////////////////
                case ADMITTING: returnValue = "ADMITTING [" + taskName + "] along ADMIT route"; break;
                case INTERRUPTING: returnValue = "INTERRUPTING [" + taskName + "] along INTERRUPT route"; break;
                case IOEVENTWAITING: returnValue = "IOEVENTWAITING [" + taskName + "] along OEVENTWAIT route"; break;
                case IOEVENTCOMPLETING: returnValue = "IOEVENTCOMPLETING [" + taskName + "] along IOEVENTCOMPLETION route"; break;
                case DISPATCHING: returnValue = "DISPATCHING [" + taskName + "] along DISPATCH route"; break;
                case EXITING: returnValue = "EXITING [" + taskName + "] along EXIT route"; break;
                default: returnValue = "no activity detected.. "; break;
            }
            
            return returnValue;
        }
        
        
        //adapted and modified from: http://www.coderanch.com/t/455284/GUI/java/Moving-circle-line (Craig Wood)
        //modified to reveal points instead of explore
        public ArrayList <Point2D> getPoints ( )
        {
            ArrayList <Point2D> returnValues = new ArrayList <Point2D> ( );
            
            double flatness = guiPanel.SIMULATED_PROCESS_ENVIRONMENT.PROCESS_EXECUTION_SPEED;
            PathIterator pit = regionConnectorBody.getPathIterator ( null, flatness );  
            double [ ] coords = new double [ 2 ];  
            
            while ( !pit.isDone ( ) ) 
            {  
                int type = pit.currentSegment ( coords );  
                returnValues.add ( new Point2D.Double ( coords [ 0 ], coords [ 1 ] ) );
                pit.next ( );  
            }  
            
            return returnValues;
        }
        public ArrayList <Point2D> getReversePoints ( )
        {
            ArrayList <Point2D> returnValues = new ArrayList <Point2D> ( );
            
            for ( int i = getPoints ( ).size ( ) - 1; i > 0; i -- )
                returnValues.add ( getPoints ( ).get ( i ) );
                
            return returnValues;
        }
        //muators


        
        //extras
        public void draw ( Graphics2D graphics2d )
        {
            //draw core body
            graphics2d.setColor ( regionConnectorColour );
            graphics2d.draw ( regionConnectorBody );
            //draw label
            graphics2d.drawString ( regionConnectorLabelString, ( int ) ( getPoints ( ).get ( getPoints ( ).size ( ) / 2 ).getX ( ) - unicodeConveniencePack.getDisplayWidthFromString ( regionConnectorLabelString, 12 ) / 2 ), ( int ) ( getPoints ( ).get ( getPoints ( ).size ( ) / 2 ).getY ( ) - 12 ) );
        }
}