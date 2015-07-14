import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.util.Random;

public class SimulatedTask
{
    //establish parent panel
    private UNICODE_GuiPanel guiPanel = null;
    
    //establish task status attributes >> (waiting,ready,terminated,running) 
    private SimulatedTaskType status = null;
    
    //establish visuals attributes
    private Image taskAppearanceImage = null;
    private ImageIcon taskAppearanceImageIcon = null;
    
    //establish orientation attributes
    private int xCoordinate = 0, yCoordinate = 0;
    
    private boolean visibilityEnquiry = false;
    public int admittingPointExplorationIndex = 0, interruptingPointExplorationIndex = 0, dispatchingPointExplorationIndex = 0, ioeventwaitingPointExplorationIndex = 0, ioeventcompletingPointExplorationIndex = 0, exitingPointExplorationIndex = 0;
    public int readyEnvironmentRegionBlockUnitExplorationIndex = 0, runningEnvironmentRegionBlockUnitExplorationIndex = 0, waitingEnvironmentRegionBlockUnitExplorationIndex = 0, reverseWaitingEnvironmentRegionBlockUnitExplorationIndex = 0;
    public int admittingPointExplorationIndex1 = 0, interruptingPointExplorationIndex1 = 0, dispatchingPointExplorationIndex1 = 0, ioeventwaitingPointExplorationIndex1 = 0, ioeventcompletingPointExplorationIndex1 = 0, exitingPointExplorationIndex1 = 0;
    public int readyEnvironmentRegionBlockUnitExplorationIndex1 = 0, runningEnvironmentRegionBlockUnitExplorationIndex1 = 0, waitingEnvironmentRegionBlockUnitExplorationIndex1 = 0, reverseWaitingEnvironmentRegionBlockUnitExplorationIndex1 = 0;
    public int admittingPointExplorationIndex2 = 0, interruptingPointExplorationIndex2 = 0, dispatchingPointExplorationIndex2 = 0, ioeventwaitingPointExplorationIndex2 = 0, ioeventcompletingPointExplorationIndex2 = 0, exitingPointExplorationIndex2 = 0;
    public int readyEnvironmentRegionBlockUnitExplorationIndex2 = 0, runningEnvironmentRegionBlockUnitExplorationIndex2 = 0, waitingEnvironmentRegionBlockUnitExplorationIndex2 = 0, reverseWaitingEnvironmentRegionBlockUnitExplorationIndex2 = 0;
   
    //establish parent panel
    public Rectangle BODY = null;

    public int ELAPSED_TIME = 0;
    public boolean enableEnquiry = false;
    private String taskName = "";
    public JLabel STATUS_LABEL = null;
    public int MAXMIUM_PATHS = 0, PATH_INDEX = 0;
   
    
    
    public SimulatedTask ( UNICODE_GuiPanel guiPanel, String taskName, String taskImageLocation, int taskImageWidth, int taskImageHeight, boolean visibilityEnquiry )
    {
        //establish attributes
        this.guiPanel = guiPanel;
        this.visibilityEnquiry = visibilityEnquiry;
        this.taskName = taskName;
        status = SimulatedTaskType.NEW;
        taskAppearanceImageIcon = new ImageIcon ( taskImageLocation );
        taskAppearanceImage = taskAppearanceImageIcon.getImage ( ).getScaledInstance ( taskImageWidth, taskImageHeight, 0 );
        taskAppearanceImage = guiPanel.createImage ( this.taskAppearanceImage.getSource ( ) );

        //establish MAXMIUM_PATHS
        MAXMIUM_PATHS = 2; //internal
        PATH_INDEX = new Random ( ).nextInt ( MAXMIUM_PATHS ); //let JOS randomly select an execution path

        //establish status label
        STATUS_LABEL = new JLabel ( "no activity detected... " );

        //establish body
        BODY = new Rectangle ( 0, 0, taskImageWidth, taskImageHeight );

        //estabish reverse waiting region index. no other region requires reverse traversal
        reverseWaitingEnvironmentRegionBlockUnitExplorationIndex = new SimulatedProcessEnvironmentRegionProcessUnit ( ).MAXIMUM_SUB_UNITS - 1;
        reverseWaitingEnvironmentRegionBlockUnitExplorationIndex1 = new SimulatedProcessEnvironmentRegionProcessUnit ( ).MAXIMUM_SUB_UNITS - 1;
        reverseWaitingEnvironmentRegionBlockUnitExplorationIndex2 = new SimulatedProcessEnvironmentRegionProcessUnit ( ).MAXIMUM_SUB_UNITS - 1;
    }
    
    //methods
        //accessors
        public SimulatedTaskType getStatus ( )
        {
            return status;
        }
        public int getX ( )
        {
            return xCoordinate;
        }
        public int getY ( )
        {
            return yCoordinate;
        }
        public boolean getVisibility ( )
        {
            return visibilityEnquiry;
        }
        public boolean isEnabled ( )
        {
            return enableEnquiry;
        }
        public String getName ( )
        {
            return taskName;
        }
        //mutators
        public void setEnabled ( boolean value )
        {
            enableEnquiry = value;
            visibilityEnquiry = value;
            STATUS_LABEL.setVisible ( value );
        }

        public void reset ( )
        {
            //disable
            setEnabled ( false );
            //reset elpased time
            ELAPSED_TIME = 0;
            //reset all exploration indices
            admittingPointExplorationIndex = 0; interruptingPointExplorationIndex = 0; dispatchingPointExplorationIndex = 0; ioeventwaitingPointExplorationIndex = 0; ioeventcompletingPointExplorationIndex = 0; exitingPointExplorationIndex = 0;
            readyEnvironmentRegionBlockUnitExplorationIndex = 0; runningEnvironmentRegionBlockUnitExplorationIndex = 0; waitingEnvironmentRegionBlockUnitExplorationIndex = 0;
            admittingPointExplorationIndex1 = 0; interruptingPointExplorationIndex1 = 0; dispatchingPointExplorationIndex1 = 0; ioeventwaitingPointExplorationIndex1 = 0; ioeventcompletingPointExplorationIndex1 = 0; exitingPointExplorationIndex1 = 0;
            readyEnvironmentRegionBlockUnitExplorationIndex1 = 0; runningEnvironmentRegionBlockUnitExplorationIndex1 = 0; waitingEnvironmentRegionBlockUnitExplorationIndex1 = 0;
            admittingPointExplorationIndex2 = 0; interruptingPointExplorationIndex2 = 0; dispatchingPointExplorationIndex2 = 0; ioeventwaitingPointExplorationIndex2 = 0; ioeventcompletingPointExplorationIndex2 = 0; exitingPointExplorationIndex2 = 0;
            readyEnvironmentRegionBlockUnitExplorationIndex2 = 0; runningEnvironmentRegionBlockUnitExplorationIndex2 = 0; waitingEnvironmentRegionBlockUnitExplorationIndex2 = 0;
        }
        
        public void restart ( )
        {
            reset ( );
            setEnabled ( true );
        }
        
        public void setStatus ( SimulatedTaskType value )
        {
            status = value;
        }
        public void setX ( int value )
        {
            xCoordinate = value;
        }
        public void setY ( int value )
        {
            yCoordinate = value;
        }
        public void setVisibile ( boolean value )
        {
            visibilityEnquiry = value;
        }
        public void setName ( String value )
        {
            taskName = value;
        }
        //extras
        public void draw ( Graphics2D graphics2d )
        {
            //graphics2d.draw ( BODY  );
            if ( visibilityEnquiry )
                graphics2d.drawImage ( taskAppearanceImage, xCoordinate, yCoordinate, guiPanel );
        }
}