import java.awt.geom.RoundRectangle2D;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Graphics2D;
import data.packages.UNICODE.*;

public class SimulatedProcessEnvironmentRegion
{
    //establish attributes
    private RoundRectangle2D regionBody = null;
    private Point regionPoint = null;
    private SimulatedTaskType regionType = null; 
    private Color regionColour = null;
    public SimulatedProcessEnvironmentRegionProcessUnit PROCESS_UNIT = null; //make this initially accessible
    public String regionStatusText = "";
    private String regionLabelString = "";
    private UNICODE_ConveniencePack unicodeConveniencePack;
    
    //constructor 
    public SimulatedProcessEnvironmentRegion ( UNICODE_GuiPanel guiPanel, Point regionPoint, Dimension regionDimension, Dimension subUnitDimension, Color regionColour, Color regionUnitColour, SimulatedTaskType regionType, boolean unitVisibilityEnquiry, String regionLabelString )
    {
        //establish features    
        this.regionPoint = regionPoint;
        this.regionBody = new RoundRectangle2D.Double ( ( int ) regionPoint.getX ( ), ( int ) regionPoint.getY ( ), ( int ) regionDimension.getWidth ( ), ( int ) regionDimension.getHeight ( ), 60, 60 );
        this.regionColour = regionColour;
        this.regionType = regionType;
        this.PROCESS_UNIT = new SimulatedProcessEnvironmentRegionProcessUnit ( regionPoint, subUnitDimension, regionUnitColour, unitVisibilityEnquiry );
        this.regionLabelString = regionLabelString;
        unicodeConveniencePack = new UNICODE_ConveniencePack ( );
    }


    
    //methods
        //accessors
        public Color getRegionColour ( )
        {
            return regionColour;
        }

        public RoundRectangle2D getRegionBody ( )
        {
            return regionBody;
        }
        //public enum SimulatedTaskManipulationType { ADMITTING, INTERRUPTING, IOEVENTWAITING, IOEVENTCOMPLETING, DISPATCHING, EXITING };
        //public enum SimulatedTaskType { NEW, WAITING, RUNNING, READY, TERMINATED };
        public String getStatus ( String taskName, SimulatedTaskManipulationType taskManipulationRoute )
        {
            String returnValue = "";
            
            switch ( regionType )
            {
                /////////////////////////////////////////////////////////////////////////////////
                //for below regions, there is only 1 way for a task to end up in them
                /////////////////////////////////////////////////////////////////////////////////
                case NEW: returnValue = "LOADED [" + taskName + "] as new task in NEW simulated process region"; break;
                case WAITING: returnValue = "IO/EVENT WAITED [" + taskName + "] as new task in WAIT simulated process region"; break;
                case TERMINATED: returnValue = "TERMINATED [" + taskName + "] into TERMINATE simulated process region"; break;
                case RUNNING: returnValue = "running! INTERUPTED [" + taskName + "] into RUN simulated process region"; break;
                
                /////////////////////////////////////////////////////////////////////////////////
                //for below ready region, there are multiple ways for a task to end up in them
                /////////////////////////////////////////////////////////////////////////////////
                case READY: 
                    if ( taskManipulationRoute != null )
                    {
                        switch ( taskManipulationRoute )
                        {
                            case ADMITTING: returnValue = "ADMITTED [" + taskName + "] into READY simulated process region"; break; //the task is being addmitted
                            case INTERRUPTING: returnValue = "INTERRUPTED [" + taskName + "] into READY simulated process region"; break; //the task is being addmitted
                            case DISPATCHING: returnValue = "DISPATCHED [" + taskName + "] into READY simulated process region"; break; //the task is being addmitted
                            case IOEVENTCOMPLETING: returnValue = "IO/EVENT COMPLETED [" + taskName + "] into READY simulated process region"; break; //the task is being addmitted
                        }
                    }
                break;
                default: returnValue = "no activity detected.. "; break;
            }
            
            return returnValue;
        }
        //muators
        //extras
        public void draw ( Graphics2D graphics2d )
        {
            //draw core body
            graphics2d.setColor ( regionColour );
            graphics2d.draw ( regionBody );
            
            //draw unit
            PROCESS_UNIT.draw ( graphics2d );
            
            //draw label
            graphics2d.drawString ( regionLabelString, ( int ) ( regionBody.getX ( ) + regionBody.getWidth ( ) / 2 - unicodeConveniencePack.getDisplayWidthFromString ( regionLabelString, 12 ) / 2 ), ( int ) ( regionBody.getY ( ) + 12 * 6 ) );
        }
}