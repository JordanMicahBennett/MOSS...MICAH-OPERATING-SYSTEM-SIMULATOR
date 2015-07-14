import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.geom.Area;

public class SimulatedProcessEnvironmentRegionProcessSubUnit
{
    //establish attributes
    private Rectangle regionProcessSubUnitBody = null;
    
    private Point regionProcessSubUnitLocation = null;
    private Dimension regionProcessSubUnitDimension = null;
    private Color regionProcessSubUnitColour = null;
    
    //constructor 0 - no image
    public SimulatedProcessEnvironmentRegionProcessSubUnit ( Point regionProcessSubUnitLocation, Dimension regionProcessSubUnitDimension, Color regionProcessSubUnitColour )
    {
        //establish features    
        this.regionProcessSubUnitBody = new Rectangle ( ( int ) regionProcessSubUnitLocation.getX ( ), ( int ) regionProcessSubUnitLocation.getY ( ), ( int ) regionProcessSubUnitDimension.getWidth ( ), ( int ) regionProcessSubUnitDimension.getHeight ( ) );

        this.regionProcessSubUnitColour = regionProcessSubUnitColour;
    }
    
    //methods
        //accessors
        public Color getColour ( )
        {
            return regionProcessSubUnitColour;
        }
        public Rectangle getRegionProcessSubUnitBody ( )
        {
            return regionProcessSubUnitBody;
        }
        //muators
        //extras
        public void draw ( Graphics2D graphics2d )
        {
            graphics2d.setColor ( regionProcessSubUnitColour );
            graphics2d.draw ( regionProcessSubUnitBody );
        }
}