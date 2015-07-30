import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.text.NumberFormat;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.TimerTask;

import data.packages.UNICODE.*;


public class BUTTON_DOCK_0 extends UNICODE_MenuPanel
{
    //short tempered attributes

    private UNICODE_ConveniencePack convenienePack = null;
    
    public BUTTON_DOCK_0 ( ArrayList <Object> customComponentList, boolean menuVisibility, int _xCoord, int _yCoord, int _MAXIMUM_BUTTONS, int BUTTON_PROXIMITY, int AXIS_PROXIMITY, String axisDirection, String axisLayoutType, String buttonListDirectory, int buttonWidth, int buttonHeight, Color bgColour, Color buttonOutlineColour, String buttonShapeType, int arcHeight, int arcDepth, int lastButtonChopValue )
    {
        super ( customComponentList, menuVisibility, _xCoord, _yCoord, _MAXIMUM_BUTTONS, BUTTON_PROXIMITY, AXIS_PROXIMITY, axisDirection, axisLayoutType, buttonListDirectory, buttonWidth, buttonHeight, bgColour, buttonOutlineColour, buttonShapeType, arcHeight, arcDepth, lastButtonChopValue );
        
        convenienePack = new UNICODE_ConveniencePack ( );
    }
    
    //abstract method
    public void mouseClickDefinition ( MouseEvent mEvent )
    { 
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 6 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            final UNICODE_GuiPanel guiPanel = ( UNICODE_GuiPanel ) getCustomComponentList ( ).get ( 1 );
            final String taskName0 = guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.SIMULATED_TASKS.get ( 0 ).getName ( );
            final String taskName1 = guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.SIMULATED_TASKS.get ( 1 ).getName ( );
            final String taskName2 = guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.SIMULATED_TASKS.get ( 2 ).getName ( );

            //start task 0 
            Thread taskThread0 = new Thread
            (
               new Runnable ( )
               {
                   public void run ( )
                   {
                       if ( guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.SIMULATED_TASKS.get ( 0 ).isEnabled ( ) )
                       {
                           new UNICODE_MessageBoxWindow ( true, "only one instance of ["+taskName0+"] allowed", 0.9f, new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 72, 72, 20, 20, 0, 0 );    
                       }
                       else     
                       {
                           guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.SIMULATED_TASKS.get ( 0 ).setEnabled ( true );
                           new UNICODE_MessageBoxWindow ( true, "started task 0 ["+taskName0+"]", 0.9f, new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 72, 72, 20, 20, 0, 0 );    
                       }
                    }
               }
            );
            
            //start task 1
            Thread taskThread1 = new Thread
            (
               new Runnable ( )
               {
                   public void run ( )
                   {
                       if ( guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.SIMULATED_TASKS.get ( 1 ).isEnabled ( ) )
                       {
                           new UNICODE_MessageBoxWindow ( true, "only one instance of ["+taskName1+"] allowed", 0.9f, new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 72, 72, 20, 20, 0, 0 );    
                       }
                       else     
                       {
                           guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.SIMULATED_TASKS.get ( 1 ).setEnabled ( true );
                           new UNICODE_MessageBoxWindow ( true, "started task 0 ["+taskName1+"]", 0.9f, new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 72, 72, 20, 20, 0, 0 );    
                       }
                    }
               }
            );
            
            //start task 2
            Thread taskThread2 = new Thread
            (
                new Runnable ( )
                {
                   public void run ( )
                   {
                       if ( guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.SIMULATED_TASKS.get ( 2 ).isEnabled ( ) )
                       {
                           new UNICODE_MessageBoxWindow ( true, "only one instance of ["+taskName2+"] allowed", 0.9f, new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 72, 72, 20, 20, 0, 0 );    
                       }
                       else     
                       {
                           guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.SIMULATED_TASKS.get ( 2 ).setEnabled ( true );
                           new UNICODE_MessageBoxWindow ( true, "started task 0 ["+taskName2+"]", 0.9f, new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 72, 72, 20, 20, 0, 0 );    
                       }
                    }
                }
            );
            
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) ); 
            new UNICODE_MessageBoxWindow ( true, taskThread0, taskThread1, taskThread2, "Notepad :--: Calculator :--: Paint :--: Cancel", 0.9f,  new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/new - task/","rr", 64, 64, 20, 20, 0, 0 );
        }
        
        
        //play process
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 5 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            final UNICODE_GuiPanel guiPanel = ( UNICODE_GuiPanel ) getCustomComponentList ( ).get ( 1 );
            
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) ); 
            
            if ( guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.hasEnabledTasks ( ) )
            {
                guiPanel.performanceIndicationBubble.play ( );  
                guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.SCHEDULE_TIMER.start ( );
            }
            else
            {
                //start runErrorThread
                Thread runErrorThread = new Thread
                (
                    new Runnable ( )
                    {
                       public void run ( )
                       {
                           new UNICODE_MessageBoxWindow ( true, "You need to start a task, with the 'new task' button!", 0.9f, new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 72, 72, 20, 20, 0, 0 );    
                       }
                    }
                );
                
                new UNICODE_MessageBoxWindow ( true, runErrorThread, "NOTHING TO PLAY!", 0.9f, new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay - threaded/","rr", 72, 72, 20, 20, 0, 0 );            
            }
        }
        
        
        //pause process
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 4 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            final UNICODE_GuiPanel guiPanel = ( UNICODE_GuiPanel ) getCustomComponentList ( ).get ( 1 );
            
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) ); 
            
            if ( guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.hasEnabledTasks ( ) )
            {
                guiPanel.performanceIndicationBubble.pause ( );  
                guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.SCHEDULE_TIMER.stop ( );
            }
            else
            {
                //start runErrorThread
                Thread runErrorThread = new Thread
                (
                    new Runnable ( )
                    {
                       public void run ( )
                       {
                           new UNICODE_MessageBoxWindow ( true, "You need to start a task, with the 'new task' button!", 0.9f, new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 72, 72, 20, 20, 0, 0 );    
                       }
                    }
                );
                
                new UNICODE_MessageBoxWindow ( true, runErrorThread, "NOTHING TO PAUSE!", 0.9f, new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay - threaded/","rr", 72, 72, 20, 20, 0, 0 );            
            }
        }
        
        //step through process
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 3 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            final UNICODE_GuiPanel guiPanel = ( UNICODE_GuiPanel ) getCustomComponentList ( ).get ( 1 );
            
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) ); 
            
            if ( guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.hasEnabledTasks ( ) )
            {
                guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.SCHEDULE_TIMER.stop ( );
   
                for ( int i = 0; i < guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.MAXIMUM_TASKS; i ++ )
                    if ( guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.SIMULATED_TASKS.get ( i ).isEnabled ( ) )
                        guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.enableTaskPerformanceCapabilityAtIndex ( i );
            }
            else
            {
                //start runErrorThread
                Thread runErrorThread = new Thread
                (
                    new Runnable ( )
                    {
                       public void run ( )
                       {
                           new UNICODE_MessageBoxWindow ( true, "You need to start a task, with the 'new task' button!", 0.9f, new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 72, 72, 20, 20, 0, 0 );    
                       }
                    }
                );
                
                new UNICODE_MessageBoxWindow ( true, runErrorThread, "NOTHING TO STEP THROUGH!", 0.9f, new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay - threaded/","rr", 72, 72, 20, 20, 0, 0 );            
            }
        }
        
        //restart process
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 2 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            final UNICODE_GuiPanel guiPanel = ( UNICODE_GuiPanel ) getCustomComponentList ( ).get ( 1 );
            
            audioPlayer.playAudio ( "" + getAudioByAlias ( "e" ) ); 
            
            if ( guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.hasEnabledTasks ( ) )
            {
                for ( int i = 0; i < guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.SIMULATED_TASKS.size ( ); i ++ )
                {
                    if ( guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.SIMULATED_TASKS.get ( i ).isEnabled ( ) )
                        guiPanel.SIMULATED_PROCESS_ENVIRONMENT.SIMULATED_TASK_SCHEDULER.SIMULATED_TASKS.get ( i ).restart ( );
                }
            }
            else
            {
                //start runErrorThread
                Thread runErrorThread = new Thread
                (
                    new Runnable ( )
                    {
                       public void run ( )
                       {
                           new UNICODE_MessageBoxWindow ( true, "You need to start a task, with the 'new task' button!", 0.9f, new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay/","rr", 72, 72, 20, 20, 0, 0 );    
                       }
                    }
                );
                
                new UNICODE_MessageBoxWindow ( true, runErrorThread, "NOTHING TO RESTART!", 0.9f, new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay - threaded/","rr", 72, 72, 20, 20, 0, 0 );            
            }
        }
        
        //change simulated cpu speed
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON - 1 ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            UNICODE_GuiPanel guiPanel = ( UNICODE_GuiPanel ) getCustomComponentList ( ).get ( 1 );
            
            audioPlayer.playAudio ( "" + getAudioByAlias ( "t" ) ); 
            
            guiPanel.SIMULATED_PROCESS_ENVIRONMENT.updateProcessExecutionSpeed ( );
            int numberOfActiveSimulatedCores = guiPanel.SIMULATED_PROCESS_ENVIRONMENT.getNumberOfActiveSimulatedCores ( );
            
            new UNICODE_MessageBoxWindow ( true, "Simulated CPU speed changed! now using " + numberOfActiveSimulatedCores + " core(s)", 0.9f, new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 255, 255, 255 ), new Color ( 170, 170, 170 ), true, "data/images/all/message box/okay - threaded/","rr", 72, 72, 20, 20, 0, 0 );            
        }
        
        //quit software
        if ( getMenu ( ).getButtonList ( ).get ( LAST_BUTTON ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
        {
            audioPlayer.playAudio ( "" + getAudioByAlias ( "t" ) ); 
            
            System.exit ( 0 );
        }
        
        repaint ( );
    }

    public void mouseMovedExtendedDefinition ( MouseEvent mEvent )
    {
    }
    
    public void mouseWheelRolledExtendedDefinition ( MouseWheelEvent mwEvent )
    {
    }
}  