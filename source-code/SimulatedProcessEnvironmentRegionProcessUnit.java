import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Graphics2D;

public class SimulatedProcessEnvironmentRegionProcessUnit
{
    //establish attributes
    private Point regionProcessUnitLocation = null;
    private Dimension regionProcessUnitDimension = null;
    public int MAXIMUM_SUB_UNITS = 4; //internal
    private ArrayList <Point> locationPoints = null;
    public ArrayList <SimulatedProcessEnvironmentRegionProcessSubUnit> PROCESS_SUB_UNITS = null; //make this initially accessible
    private boolean unitVisibilityEnquiry = false;
    
    //constructor 0 - no image
    public SimulatedProcessEnvironmentRegionProcessUnit ( Point regionProcessUnitLocation, Dimension regionProcessSubUnitDimension, Color regionProcessSubUnitColour, boolean unitVisibilityEnquiry )
    {
        //establish features    
        this.unitVisibilityEnquiry = unitVisibilityEnquiry;
        this.locationPoints = new ArrayList <Point> ( );
            int subUnitWidth = ( int ) regionProcessSubUnitDimension.getWidth ( ), subUnitHeight =  ( int ) regionProcessSubUnitDimension.getHeight ( );
            int unitX = ( int ) regionProcessUnitLocation.getX ( ) + ( int ) regionProcessSubUnitDimension.getWidth ( ) * 2 - subUnitWidth/3, unitY = ( int ) regionProcessUnitLocation.getY ( ) + ( int ) subUnitHeight - subUnitHeight / 4; 
        this.generateLocationPoints ( new Point ( unitX, unitY ), new Point ( unitX + subUnitWidth, unitY ), new Point ( unitX + subUnitWidth * 2, unitY ), new Point ( unitX + subUnitWidth * 3, unitY ) );

        //establish SimulatedProcessEnvironmentRegionProcessSubUnit space 
        PROCESS_SUB_UNITS = new ArrayList <SimulatedProcessEnvironmentRegionProcessSubUnit> ( );
        
        //generate simulatedProcessEnvironmentRegionProcessSubUnits
        for ( int i = 0; i < MAXIMUM_SUB_UNITS; i ++ ) 
            PROCESS_SUB_UNITS.add ( new SimulatedProcessEnvironmentRegionProcessSubUnit ( locationPoints.get ( i ), regionProcessSubUnitDimension, regionProcessSubUnitColour ) );
    }
    public SimulatedProcessEnvironmentRegionProcessUnit ( )
    {
    }    
    //methods
        //accessors
        public void generateLocationPoints ( Point value0, Point value1, Point value2, Point value3 )
        {
            locationPoints.add ( value0 );
            locationPoints.add ( value1 );
            locationPoints.add ( value2 );
            locationPoints.add ( value3 );
        }
        //muators
        
        //extras
        public void draw ( Graphics2D graphics2d )
        {
            if ( unitVisibilityEnquiry )
            {
                for ( SimulatedProcessEnvironmentRegionProcessSubUnit subUnit : PROCESS_SUB_UNITS )
                    subUnit.draw ( graphics2d );
            }
        }
}