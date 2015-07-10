import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import javax.swing.BorderFactory;
import java.util.TimerTask;

import data.packages.UNICODE.*;

public class UNICODE_GuiPanel extends JPanel
{

    //attributes
        //establish parent frame
        public JFrame applicationFrame = null;
        
        //establish uni-code (tm) managers
            //establish file manager
            public UNICODE_ConfigurationManager configurationManager = new UNICODE_ConfigurationManager ( "data/config/CONFIG.ini" );
            
    //establish uni-code (tm) controllers
            //establish opacity manager
            private UNICODE_OpacityController opacityController = new UNICODE_OpacityController ( ); 
            //establish rendering hint to install anti-aliasing
            private UNICODE_AntiAliasingController antiAliasController = new UNICODE_AntiAliasingController ( configurationManager.getAntiAliasingStateFromFile ( ) );
            //establish Buffer Dimension Controller
            private UNICODE_BufferDimensionController bufferDimensionController = new UNICODE_BufferDimensionController ( );
           
            
    //establish menus and related requirments  
    
        //docks
            //dock container
            private JPanel dockContainer = new JPanel ( );
            //button docks and associated components
            private BUTTON_DOCK_0 buttonDock0 = null;
            
    //establish audio player
    private UNICODE_AudioPlayer audioPlayer = new UNICODE_AudioPlayer ( );
   

    //app bg
    private Image logoImage = null;
    private ImageIcon logoImageIcon = null;
  
    //PANEL REPOSITIONING VARS
        //establish current coordinates genrated by pressed down mouse
        private Point pressedMouseCoords = null;
        
        //establish current coordinates geenrated by mouse dragging
        private Point draggedMouseCoords = null;
        
    //app performance indicator
    public UNICODE_AnimatedBubble performanceIndicationBubble = null;
    private Color performanceIndicationBubbleColor = null;
    
    
    //os simulator specific data
        //establish simulated os environment
        public SimulatedProcessEnvironment SIMULATED_PROCESS_ENVIRONMENT = null;
       
    //constructor
    public UNICODE_GuiPanel ( JFrame _applicationFrame, int directoryDisplayerVisualDisplayLimit )
    {
        //generate logo
        logoImageIcon = new ImageIcon ( "data/images/background.png" );
        logoImage = logoImageIcon.getImage ( );
        logoImage = createImage ( logoImage.getSource ( ) );
        
        //establish parent frame
        applicationFrame = _applicationFrame;
        
        
        //establish background colour of this panel
        setBackground ( configurationManager.getColourFromFile ( ) );
        
        
        //ADJUST BUFFER DIMENSION CONFIG WRT USER SCREEN
        //get user screen size
        int applicationWidth = 1200;
        int applicationHeight = 740; 
        
        //update controller value wrt user specifications
        bufferDimensionController.setBufferDimensionString ( "" + applicationWidth + "," + applicationHeight );    
        
        //updateconfigurationManager's buffer controller value
        configurationManager.updateBufferDimensionController ( bufferDimensionController );
        
        //update config file wrt buffer controller value
        configurationManager.updateConfigFile ( );
        
        
        //establish performance indicator( ( int ) applicationFrame.getWidth ( ) )
        performanceIndicationBubble = new UNICODE_AnimatedBubble ( ( applicationWidth / 2 ) - ( 74 / 2 ), ( applicationHeight / 2 ) - ( 74 / 4 ) + 10, 74, 74, this );
        performanceIndicationBubbleColor = Color.black;//new Color ( 245, 245, 245 ); //INTERNAL
        //performanceIndicationBubble.pause ( );
        
        ////////////////////////////////////////////////////
        //ESTABLISH SIMULATED OS ENVIRONMENT
        ////////////////////////////////////////////////////
        SIMULATED_PROCESS_ENVIRONMENT = new SimulatedProcessEnvironment ( this, configurationManager );
        
        
        //item 0 - establish - main menu
        buttonDock0 = new BUTTON_DOCK_0 ( generatedCustomComponentListbuttonDock0 ( ), true, 440, 40, 7, 60, 2220, "clockwise", "horizontal", "data/images/main menu/", 84, 84, configurationManager.getColourFromFile ( ), configurationManager.getColourFromFile ( ), "rr", 10, 10, 6 );    
        
        
        //add stuff to this panel
        add ( SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.STATUS_PANEL );
        add ( buttonDock0 );
        
        //add mouse listening
        addMouseListener ( new mouseListening ( ) );
        addMouseMotionListener ( new mouseListening ( ) );
        
        
        //set focus to this panel
        setFocusable ( true );
    }
    
    //return current opacity level
    public float getStartupOpacityLevel ( )
    {
        opacityController.setOpacLevel ( configurationManager.getOpacityFromFile ( ) );
        return opacityController.getOpacLevel ( );
    }
    
    //paint component
    public void paintComponent ( Graphics graphics )
    {
        super.paintComponent ( graphics );
        Graphics2D graphics2d = ( Graphics2D ) graphics;
        
        //draw logo
        graphics.drawImage ( logoImage, 0, 0, this );
        
        //draw performance indicator
        performanceIndicationBubble.draw ( graphics2d, performanceIndicationBubbleColor );
        
        //establish anti aliasing
        antiAliasController.setupAntiAliasing ( graphics2d );     
        
        //establish docks' dimensions
        buttonDock0.setBackground ( configurationManager.getColourFromFile ( ) );
        buttonDock0.setBounds ( 0, 650, 1200, 110 );
        
        //simulated environment
        SIMULATED_PROCESS_ENVIRONMENT.draw ( graphics2d );
        
        addMouseMotionListener ( new mouseListening ( ) );
        addMouseListener ( new mouseListening ( ) );
    }
    
    //mouse listening
    private class mouseListening implements MouseMotionListener, MouseListener
    {
        public void mouseDragged ( MouseEvent mouseEvent )
        {
            //establish dragged mouse coordinates
            draggedMouseCoords = mouseEvent.getLocationOnScreen ( );    
            applicationFrame.setLocation ( ( int ) ( draggedMouseCoords.getX ( ) - pressedMouseCoords.getX ( ) ), ( int ) ( draggedMouseCoords.getY ( ) - pressedMouseCoords.getY ( ) ) );
        }
        public void mouseMoved ( MouseEvent mouseEvent )
        {
        }     
        public void mouseEntered ( MouseEvent mouseEvent )
        {
        }
        public void mouseExited ( MouseEvent mouseEvent )
        {
        }   
        public void mouseClicked ( MouseEvent mouseEvent )
        {
        }     
        public void mousePressed ( MouseEvent mouseEvent )
        {
            //establish mouse pressed coords
            pressedMouseCoords = mouseEvent.getPoint ( );
        }
        public void mouseReleased ( MouseEvent mouseEvent )
        {
        }   
    }
    
    
    //add custom components which dont exist in UNICODE_MenuPanel by default,
    //where button click response is conceerned.
    public ArrayList <Object> generatedCustomComponentListbuttonDock0 ( )
    {
        ArrayList <Object> value = new ArrayList <Object> ( );
        
        value.add ( configurationManager ); //0
        value.add ( this ); //1
        
        return value;
    }
    
    

    
    //CORE TABLE
 
}