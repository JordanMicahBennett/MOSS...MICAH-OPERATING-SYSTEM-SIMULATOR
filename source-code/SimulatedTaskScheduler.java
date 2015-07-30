import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Point2D;

public class SimulatedTaskScheduler
{
    //establish attributes
    public Timer SCHEDULE_TIMER = null; //make this initially accessible
    private int taskExecutionSpeedDelayRate = 0;
    public ArrayList <SimulatedTask> SIMULATED_TASKS = null;
    private ArrayList <String> taskImageStreamList = null, taskNameStreamList = null;
    private ArrayList <SimulatedProcessEnvironmentRegion> environmentRegions = null;
    private ArrayList <SimulatedProcessEnvironmentRegionConnector> environmentRegionConnectors = null;
    public int MAXIMUM_TASKS = 0;
    public JPanel STATUS_PANEL = null;
    public int [ ] speedBoostCollection = null;
    public UNICODE_GuiPanel guiPanel = null;
    public JLabel statusReportCoreLabel = null;
    
    public SimulatedTaskScheduler ( UNICODE_GuiPanel guiPanel, int taskExecutionSpeedDelayRate, int MAXIMUM_TASKS, ArrayList <String> taskNameStreamList, ArrayList <String> taskImageStreamList, int taskSpan, ArrayList <SimulatedProcessEnvironmentRegion> environmentRegions, ArrayList <SimulatedProcessEnvironmentRegionConnector> environmentRegionConnectors )
    {
        //establish gui panel
        this.guiPanel = guiPanel; 
       
        //establish statusReportCoreLabel
        this.statusReportCoreLabel = new JLabel ( "..." );          

        //establish speed boost collection
        this.speedBoostCollection = guiPanel.configurationManager.getSpeedBoostCollectionFromFile ( );

        //establish region data
        this.environmentRegions = environmentRegions;
        this.environmentRegionConnectors = environmentRegionConnectors;
        
        //establish maximum tasks
        this.MAXIMUM_TASKS = MAXIMUM_TASKS;
        
        //establish and generate tasks
        this.taskImageStreamList = taskImageStreamList; //might make this into an accessor later
        this.taskNameStreamList = taskNameStreamList; //might make this into an accessor later
        this.SIMULATED_TASKS = new ArrayList <SimulatedTask> ( );
        
        for ( int i = 0; i < MAXIMUM_TASKS; i ++ )
            SIMULATED_TASKS.add ( new SimulatedTask ( guiPanel, taskNameStreamList.get ( i ), taskImageStreamList.get ( i ), taskSpan, taskSpan, false ) );
            
        
        //establish and generate timer designated for task execution speed
        this.taskExecutionSpeedDelayRate = taskExecutionSpeedDelayRate;
        SCHEDULE_TIMER = new Timer ( taskExecutionSpeedDelayRate, new schedulerTimerListening ( ) );
        SCHEDULE_TIMER.start ( );
        
        //establish status panel
        STATUS_PANEL = new JPanel ( );
        STATUS_PANEL.setBackground ( guiPanel.configurationManager.getColourFromFile ( ) );

        STATUS_PANEL.add ( statusReportCoreLabel );

        for ( int i = 0; i < MAXIMUM_TASKS; i ++ )
        {
            STATUS_PANEL.add ( SIMULATED_TASKS.get ( i ).STATUS_LABEL );
            SIMULATED_TASKS.get ( i ).STATUS_LABEL.setVisible ( false );
        }
        STATUS_PANEL.setLayout ( new BoxLayout ( STATUS_PANEL, BoxLayout.Y_AXIS ) );

        //establish initial task body locations
        establishInitialTaskOrientation ( );
    }
    public SimulatedTaskScheduler ( )
    {
    }
    
    
    //methods
        //accessors
        public ArrayList <String> getGeneratedStreamList ( String fileNameList, int _MAXIMUM_TASKS ) 
        {
            ArrayList <String> returnValues = new ArrayList <String> ( );
            
            Scanner scanner = new Scanner ( fileNameList );
            for ( int i = 0; i < _MAXIMUM_TASKS; i ++ )
                returnValues.add ( scanner.next ( ) );
            
            return returnValues;
        }
        boolean hasEnabledTasks ( )
        {
            boolean returnValue = false;
            
            for ( int i = 0; i < MAXIMUM_TASKS; i ++ )
                if ( SIMULATED_TASKS.get ( i ).isEnabled ( ) )
                    returnValue = true;
            
            return returnValue;
        }
        //mutators
        //extras
            //extras
            public void draw ( Graphics2D graphics2d )
            {
                for ( SimulatedTask simulatedTask : SIMULATED_TASKS )
                    simulatedTask.draw ( graphics2d );
            }
            //schedulerTimerListening
            private class schedulerTimerListening implements ActionListener
            {
                public void actionPerformed ( ActionEvent actionEvent )
                {
                    for ( int i = 0; i < MAXIMUM_TASKS; i ++ )
                        enableTaskPerformanceCapabilityAtIndex ( i );
                    guiPanel.repaint ( );
                }
            }

            public void establishInitialTaskOrientation ( )
            {
                //establish initial task body locations
                int initLocationVerticalJump = 0;
                for ( int i = 0; i < MAXIMUM_TASKS; i ++ )
                {
                    SIMULATED_TASKS.get ( i ).BODY.setLocation ( 0, initLocationVerticalJump );
                    initLocationVerticalJump += SIMULATED_TASKS.get ( i ).BODY.getWidth ( ) * 3; 
                }
            }

            public boolean getTaskCollisionEnquiry ( )
            {
                boolean returnValue = false;
                
                for ( int i = 0; i < SIMULATED_TASKS.size ( ); i ++ )
                {
                    if ( ( i + 1 ) < SIMULATED_TASKS.size ( ) )
                        if ( SIMULATED_TASKS.get ( i ).BODY.contains ( SIMULATED_TASKS.get ( i + 1 ).BODY ) ) 
                            returnValue = true;
                }

                return returnValue;
            }

            public String getTaskCollisionStatusParticipantsString ( )
            {
                String returnValue = "";
           
                for ( int i = 0; i < SIMULATED_TASKS.size ( ); i ++ )
                {
                    if ( ( i + 1 ) < SIMULATED_TASKS.size ( ) )
                        if ( SIMULATED_TASKS.get ( i ).BODY.contains ( SIMULATED_TASKS.get ( i + 1 ).BODY ) ) 
                            returnValue = SIMULATED_TASKS.get ( i ).getName ( ) + " & " + SIMULATED_TASKS.get ( i + 1 ).getName ( );

                }

                return returnValue;
            }

            public String getTaskCollisionStatus ( )
            {
                String returnValue = "...";
                
                if  ( guiPanel.SIMULATED_PROCESS_ENVIRONMENT.getNumberOfActiveSimulatedCores ( ) > 1 )  //if using single core, then parrallelism does not occur, which may cause deadlocks/collisions OTHERWISE dea\dlocks a\re recuded
                    returnValue = "no deadlock collision detected...";
                else
                    returnValue = " COLLISION between : " + getTaskCollisionStatusParticipantsString ( ); 

                return returnValue;
            }
        //public enum SimulatedTaskManipulationType { ADMITTING, INTERRUPTING, IOEVENTWAITING, IOEVENTCOMPLETING, DISPATCHING, EXITING };
        //public enum SimulatedTaskType { NEW, WAITING, RUNNING, READY, TERMINATED };
        
      
            //run task
            public void enableTaskPerformanceCapabilityAtIndex ( int selectedTaskIndex )
            {
                if ( SIMULATED_TASKS.get ( selectedTaskIndex ).isEnabled ( ) )
                {
                    //0.cycle script 0 - NO waiting
                    if ( SIMULATED_TASKS.get ( selectedTaskIndex ).PATH_INDEX == 0 )
                    {
                        //update time
                        SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME += 10;
                        SIMULATED_TASKS.get ( selectedTaskIndex ).BODY.setLocation ( SIMULATED_TASKS.get ( selectedTaskIndex ).getX ( ), SIMULATED_TASKS.get ( selectedTaskIndex ).getY ( ) );
                        statusReportCoreLabel.setText ( "ACTIVE cores >>" + guiPanel.SIMULATED_PROCESS_ENVIRONMENT.getNumberOfActiveSimulatedCores ( ) + " ...ACTIVE step size >>" + guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX + " ...LAST collision status >> " + getTaskCollisionStatus ( ) );
    
                        //trigger set in NEW region
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 200 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                            enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.NEW, null, TaskRouteDirection.FORWARD, 0 );
                        
                        //trigger update along ADMITTING route
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 250 ) 
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 370 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                            {
                                enableRouteMovement ( selectedTaskIndex, SimulatedTaskManipulationType.ADMITTING, TaskRouteDirection.FORWARD, SIMULATED_TASKS.get ( selectedTaskIndex ).admittingPointExplorationIndex );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).admittingPointExplorationIndex ++;
                            }
                        }
                        
                        //follow up with set at beggining of READY region ( a follow up is not embraced by elapsed time, but does rely on elapsed time to reach a threshold beeforehand )
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).admittingPointExplorationIndex >= getRoutePoints ( SimulatedTaskManipulationType.ADMITTING ).size ( ) )
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 370 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                                enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.READY, SimulatedTaskManipulationType.NEW, TaskRouteDirection.FORWARD, 0 );
                        }
                        
                        //trigger update along READY region
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 380 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                        {
                            enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.READY, SimulatedTaskManipulationType.ADMITTING, TaskRouteDirection.FORWARD, SIMULATED_TASKS.get ( selectedTaskIndex ).readyEnvironmentRegionBlockUnitExplorationIndex );
                            SIMULATED_TASKS.get ( selectedTaskIndex ).readyEnvironmentRegionBlockUnitExplorationIndex ++;
                        }
                        
                        //follow up with update along INTERRUPTING route ( a follow up is not embraced by elapsed time, but does rely on elapsed time to reach a threshold beeforehand )
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).readyEnvironmentRegionBlockUnitExplorationIndex >= getRegionBlockSize ( SimulatedTaskType.READY ) )
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 600 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                            {
                                enableRouteMovement ( selectedTaskIndex, SimulatedTaskManipulationType.INTERRUPTING, TaskRouteDirection.FORWARD, SIMULATED_TASKS.get ( selectedTaskIndex ).interruptingPointExplorationIndex );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).interruptingPointExplorationIndex ++;
                            }
                        }
                        
                        //trigger set in RUNNING region
                        if ( ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 600 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] ) && ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 650 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] ) )
                            enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.RUNNING, SimulatedTaskManipulationType.INTERRUPTING, TaskRouteDirection.FORWARD, 0 );
                        
                            
                        //trigger update along DISPATCHING route
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 650 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] ) 
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).dispatchingPointExplorationIndex < getReverseRoutePoints ( SimulatedTaskManipulationType.DISPATCHING ).size ( ) )
                            {
                                enableRouteMovement ( selectedTaskIndex, SimulatedTaskManipulationType.DISPATCHING, TaskRouteDirection.REVERSE, SIMULATED_TASKS.get ( selectedTaskIndex ).dispatchingPointExplorationIndex );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).dispatchingPointExplorationIndex ++;
                            }
                        }
                        
                        //follow up with set at third index of READY region ( a follow up is not embraced by elapsed time, but does rely on elapsed time to reach a threshold beeforehand )
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).dispatchingPointExplorationIndex >= getReverseRoutePoints ( SimulatedTaskManipulationType.DISPATCHING ).size ( ) )
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 1300 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                                enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.READY, SimulatedTaskManipulationType.DISPATCHING, TaskRouteDirection.FORWARD, 2 );
                        }
                        
                        //trigger reupdate along INTERRUPTING route
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 1300 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] ) 
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).interruptingPointExplorationIndex1 < getRoutePoints ( SimulatedTaskManipulationType.INTERRUPTING ).size ( ) )
                            {
                                enableRouteMovement ( selectedTaskIndex, SimulatedTaskManipulationType.INTERRUPTING, TaskRouteDirection.FORWARD, SIMULATED_TASKS.get ( selectedTaskIndex ).interruptingPointExplorationIndex1 );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).interruptingPointExplorationIndex1 ++;
                            }
                        }
                        
                        //follow up with update along RUNNING region
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).interruptingPointExplorationIndex1 >= getRoutePoints ( SimulatedTaskManipulationType.INTERRUPTING ).size ( ) )
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 2300 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                            {
                                enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.RUNNING, SimulatedTaskManipulationType.INTERRUPTING, TaskRouteDirection.FORWARD, SIMULATED_TASKS.get ( selectedTaskIndex ).runningEnvironmentRegionBlockUnitExplorationIndex );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).runningEnvironmentRegionBlockUnitExplorationIndex ++;
                            }
                        }
                        
                        //trigger update along EXITING route
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 2300 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] ) 
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).exitingPointExplorationIndex < getReverseRoutePoints ( SimulatedTaskManipulationType.EXITING ).size ( ) )
                            {
                                enableRouteMovement ( selectedTaskIndex, SimulatedTaskManipulationType.EXITING, TaskRouteDirection.REVERSE, SIMULATED_TASKS.get ( selectedTaskIndex ).exitingPointExplorationIndex );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).exitingPointExplorationIndex ++;
                            }
                        }
                        
                        //follow up with set at last index of TERMINATION region
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).exitingPointExplorationIndex >= getReverseRoutePoints ( SimulatedTaskManipulationType.EXITING ).size ( ) )
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 2900 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                                enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.TERMINATED, SimulatedTaskManipulationType.EXITING, TaskRouteDirection.FORWARD, 3 );
                        }
                        
                        //trigger task removal ( task disabling, simulated task has ended )
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 3100 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] ) 
                        {
                            //reset. Reset kills task
                            SIMULATED_TASKS.get ( selectedTaskIndex ).reset ( );
                        }
                    }

                    //1.cycle script 1 - WAITING
                    if ( SIMULATED_TASKS.get ( selectedTaskIndex ).PATH_INDEX == 1 )
                    {
                        //update time
                        SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME += 10;
                        SIMULATED_TASKS.get ( selectedTaskIndex ).BODY.setLocation ( SIMULATED_TASKS.get ( selectedTaskIndex ).getX ( ), SIMULATED_TASKS.get ( selectedTaskIndex ).getY ( ) );
                        statusReportCoreLabel.setText ( "ACTIVE cores >>" + guiPanel.SIMULATED_PROCESS_ENVIRONMENT.getNumberOfActiveSimulatedCores ( ) + " ...ACTIVE step size >>" + guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX + " ...LAST collision status >> " + getTaskCollisionStatus ( ) );
    
                        //trigger set in NEW region
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 200 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                            enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.NEW, null, TaskRouteDirection.FORWARD, 0 );
                        
                        //trigger update along ADMITTING route
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 250 ) 
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 370 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                            {
                                enableRouteMovement ( selectedTaskIndex, SimulatedTaskManipulationType.ADMITTING, TaskRouteDirection.FORWARD, SIMULATED_TASKS.get ( selectedTaskIndex ).admittingPointExplorationIndex );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).admittingPointExplorationIndex ++;
                            }
                        }
                        
                        //follow up with set at beggining of READY region ( a follow up is not embraced by elapsed time, but does rely on elapsed time to reach a threshold beeforehand )
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).admittingPointExplorationIndex >= getRoutePoints ( SimulatedTaskManipulationType.ADMITTING ).size ( ) )
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 370 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                                enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.READY, SimulatedTaskManipulationType.NEW, TaskRouteDirection.FORWARD, 0 );
                        }
                        
                        //trigger update along READY region
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 380 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                        {
                            enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.READY, SimulatedTaskManipulationType.ADMITTING, TaskRouteDirection.FORWARD, SIMULATED_TASKS.get ( selectedTaskIndex ).readyEnvironmentRegionBlockUnitExplorationIndex );
                            SIMULATED_TASKS.get ( selectedTaskIndex ).readyEnvironmentRegionBlockUnitExplorationIndex ++;
                        }
                        
                        //follow up with update along INTERRUPTING route ( a follow up is not embraced by elapsed time, but does rely on elapsed time to reach a threshold beeforehand )
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).readyEnvironmentRegionBlockUnitExplorationIndex >= getRegionBlockSize ( SimulatedTaskType.READY ) )
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 600 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                            {
                                enableRouteMovement ( selectedTaskIndex, SimulatedTaskManipulationType.INTERRUPTING, TaskRouteDirection.FORWARD, SIMULATED_TASKS.get ( selectedTaskIndex ).interruptingPointExplorationIndex );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).interruptingPointExplorationIndex ++;
                            }
                        }
                        
                        //trigger set in RUNNING region
                        if ( ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 600 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] ) && ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 650 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] ) )
                            enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.RUNNING, SimulatedTaskManipulationType.INTERRUPTING, TaskRouteDirection.FORWARD, 0 );
                        
                            
                        //trigger update along DISPATCHING route
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 650 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] ) 
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).dispatchingPointExplorationIndex < getReverseRoutePoints ( SimulatedTaskManipulationType.DISPATCHING ).size ( ) )
                            {
                                enableRouteMovement ( selectedTaskIndex, SimulatedTaskManipulationType.DISPATCHING, TaskRouteDirection.REVERSE, SIMULATED_TASKS.get ( selectedTaskIndex ).dispatchingPointExplorationIndex );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).dispatchingPointExplorationIndex ++;
                            }
                        }
                        
                        //follow up with set at third index of READY region ( a follow up is not embraced by elapsed time, but does rely on elapsed time to reach a threshold beeforehand )
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).dispatchingPointExplorationIndex >= getReverseRoutePoints ( SimulatedTaskManipulationType.DISPATCHING ).size ( ) )
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 1300 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                                enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.READY, SimulatedTaskManipulationType.DISPATCHING, TaskRouteDirection.FORWARD, 2 );
                        }
                        
                        //trigger reupdate along INTERRUPTING route
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 1300 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] ) 
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).interruptingPointExplorationIndex1 < getRoutePoints ( SimulatedTaskManipulationType.INTERRUPTING ).size ( ) )
                            {
                                enableRouteMovement ( selectedTaskIndex, SimulatedTaskManipulationType.INTERRUPTING, TaskRouteDirection.FORWARD, SIMULATED_TASKS.get ( selectedTaskIndex ).interruptingPointExplorationIndex1 );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).interruptingPointExplorationIndex1 ++;
                            }
                        }
                        
                        //follow up with update along RUNNING region
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).interruptingPointExplorationIndex1 >= getRoutePoints ( SimulatedTaskManipulationType.INTERRUPTING ).size ( ) )
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 2300 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                            {
                                enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.RUNNING, SimulatedTaskManipulationType.INTERRUPTING, TaskRouteDirection.FORWARD, SIMULATED_TASKS.get ( selectedTaskIndex ).runningEnvironmentRegionBlockUnitExplorationIndex );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).runningEnvironmentRegionBlockUnitExplorationIndex ++;
                            }
                        }

                        //trigger update along IOEVENTWAITING route
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 2300 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] ) 
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ioeventwaitingPointExplorationIndex < getRoutePoints ( SimulatedTaskManipulationType.IOEVENTWAITING ).size ( ) )
                            {
                                enableRouteMovement ( selectedTaskIndex, SimulatedTaskManipulationType.IOEVENTWAITING, TaskRouteDirection.FORWARD, SIMULATED_TASKS.get ( selectedTaskIndex ).ioeventwaitingPointExplorationIndex );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).ioeventwaitingPointExplorationIndex ++;
                            }
                        }

                        //follow up with update along WAITING region
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ioeventwaitingPointExplorationIndex >= getRoutePoints ( SimulatedTaskManipulationType.IOEVENTWAITING ).size ( ) )
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 3000 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                            {
                                enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.WAITING, SimulatedTaskManipulationType.IOEVENTWAITING, TaskRouteDirection.REVERSE, SIMULATED_TASKS.get ( selectedTaskIndex ).reverseWaitingEnvironmentRegionBlockUnitExplorationIndex );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).reverseWaitingEnvironmentRegionBlockUnitExplorationIndex --;
                            }
                        }

                        //trigger update along IOEVENTCOMPLETING route
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 3000 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] ) 
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ioeventcompletingPointExplorationIndex < getReverseRoutePoints ( SimulatedTaskManipulationType.IOEVENTCOMPLETING ).size ( ) )
                            {
                                enableRouteMovement ( selectedTaskIndex, SimulatedTaskManipulationType.IOEVENTCOMPLETING, TaskRouteDirection.REVERSE, SIMULATED_TASKS.get ( selectedTaskIndex ).ioeventcompletingPointExplorationIndex );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).ioeventcompletingPointExplorationIndex ++;
                            }
                        }

                        //follow up with update along READY region
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ioeventcompletingPointExplorationIndex >= getReverseRoutePoints ( SimulatedTaskManipulationType.IOEVENTCOMPLETING ).size ( ) )
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 3700 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                            {
                                enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.READY, SimulatedTaskManipulationType.IOEVENTCOMPLETING, TaskRouteDirection.FORWARD, SIMULATED_TASKS.get ( selectedTaskIndex ).readyEnvironmentRegionBlockUnitExplorationIndex1 );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).readyEnvironmentRegionBlockUnitExplorationIndex1 ++;
                            }
                        }

                        //trigger reupdate along INTERRUPTING route
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 3700 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] ) 
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).interruptingPointExplorationIndex2 < getRoutePoints ( SimulatedTaskManipulationType.INTERRUPTING ).size ( ) )
                            {
                                enableRouteMovement ( selectedTaskIndex, SimulatedTaskManipulationType.INTERRUPTING, TaskRouteDirection.FORWARD, SIMULATED_TASKS.get ( selectedTaskIndex ).interruptingPointExplorationIndex2 );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).interruptingPointExplorationIndex2 ++;
                            }
                        }

                        //follow up with update along RUNNING region
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).interruptingPointExplorationIndex2 >= getRoutePoints ( SimulatedTaskManipulationType.INTERRUPTING ).size ( ) )
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 4300 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                            {
                                enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.RUNNING, SimulatedTaskManipulationType.INTERRUPTING, TaskRouteDirection.FORWARD, SIMULATED_TASKS.get ( selectedTaskIndex ).runningEnvironmentRegionBlockUnitExplorationIndex1 );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).runningEnvironmentRegionBlockUnitExplorationIndex1 ++;
                            }
                        }

                        //trigger update along EXITING route
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 4300 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] ) 
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).exitingPointExplorationIndex < getReverseRoutePoints ( SimulatedTaskManipulationType.EXITING ).size ( ) )
                            {
                                enableRouteMovement ( selectedTaskIndex, SimulatedTaskManipulationType.EXITING, TaskRouteDirection.REVERSE, SIMULATED_TASKS.get ( selectedTaskIndex ).exitingPointExplorationIndex );
                                SIMULATED_TASKS.get ( selectedTaskIndex ).exitingPointExplorationIndex ++;
                            }
                        }
                        
                        //follow up with set at last index of TERMINATION region
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).exitingPointExplorationIndex >= getReverseRoutePoints ( SimulatedTaskManipulationType.EXITING ).size ( ) )
                        {
                            if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME < 4900 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] )
                                enableRegionMovement ( selectedTaskIndex, SimulatedTaskType.TERMINATED, SimulatedTaskManipulationType.EXITING, TaskRouteDirection.FORWARD, 3 );
                        }
                        
                        //trigger task removal ( task disabling, simulated task has ended )
                        if ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME > 5100 + speedBoostCollection [ guiPanel.SIMULATED_PROCESS_ENVIRONMENT.NUMBER_OF_ACTIVE_SIMULATED_CORES_EXLPORATION_INDEX ] ) 
                        {
                            //reset. Reset kills task
                            SIMULATED_TASKS.get ( selectedTaskIndex ).reset ( );
                        }
                    }
                }
            }
            
            
            //System.out.println ( SIMULATED_TASKS.get ( selectedTaskIndex ).ELAPSED_TIME );
            public void enableRouteMovement ( int selectedTaskIndex, SimulatedTaskManipulationType taskRoute, TaskRouteDirection direction, int explorationIndex )
            {
                SIMULATED_TASKS.get ( selectedTaskIndex ).STATUS_LABEL.setText ( environmentRegionConnectors.get ( getGuiRouteIndex ( taskRoute ) ).getStatus ( SIMULATED_TASKS.get ( selectedTaskIndex ).getName ( ) ) );
                
               
                switch ( direction )
                {
                    case REVERSE:
                    {
                        if ( explorationIndex < getReverseRoutePoints ( taskRoute ).size ( ) )
                        {
                            SIMULATED_TASKS.get ( selectedTaskIndex ).setX ( ( int ) getReverseRoutePoints ( taskRoute ).get ( explorationIndex ).getX ( ) );
                            SIMULATED_TASKS.get ( selectedTaskIndex ).setY ( ( int ) getReverseRoutePoints ( taskRoute ).get ( explorationIndex ).getY ( ) );
                        }
                    }
                    break;
                    case FORWARD:
                    { 
                        if ( explorationIndex < getRoutePoints ( taskRoute ).size ( ) )
                        {
                            SIMULATED_TASKS.get ( selectedTaskIndex ).setX ( ( int ) getRoutePoints ( taskRoute ).get ( explorationIndex ).getX ( ) );
                            SIMULATED_TASKS.get ( selectedTaskIndex ).setY ( ( int ) getRoutePoints ( taskRoute ).get ( explorationIndex ).getY ( ) );
                        }
                    }
                    break;
                }
            }
            
            public void enableRegionMovement ( int selectedTaskIndex, SimulatedTaskType taskExecutionRegion, SimulatedTaskManipulationType routeFrom, TaskRouteDirection direction, int explorationIndex )
            {
                switch ( direction )
                {
                    case REVERSE:
                    {
                        if ( explorationIndex >= 0 )
                        {
                            SIMULATED_TASKS.get ( selectedTaskIndex ).STATUS_LABEL.setText ( environmentRegions.get ( getGuiRegionIndex ( taskExecutionRegion ) ).getStatus ( SIMULATED_TASKS.get ( selectedTaskIndex ).getName ( ), routeFrom ) );
                            SIMULATED_TASKS.get ( selectedTaskIndex ).setX ( ( int ) environmentRegions.get ( getGuiRegionIndex ( taskExecutionRegion ) ).PROCESS_UNIT.PROCESS_SUB_UNITS.get ( explorationIndex ).getRegionProcessSubUnitBody ( ).getX ( ) );
                            SIMULATED_TASKS.get ( selectedTaskIndex ).setY ( ( int ) environmentRegions.get ( getGuiRegionIndex ( taskExecutionRegion ) ).PROCESS_UNIT.PROCESS_SUB_UNITS.get ( explorationIndex ).getRegionProcessSubUnitBody ( ).getY ( ) );
                        }
                    }
                    break;
                    case FORWARD:
                    { 
                        if ( explorationIndex < getRegionBlockSize ( taskExecutionRegion ) )
                        {
                            SIMULATED_TASKS.get ( selectedTaskIndex ).STATUS_LABEL.setText ( environmentRegions.get ( getGuiRegionIndex ( taskExecutionRegion ) ).getStatus ( SIMULATED_TASKS.get ( selectedTaskIndex ).getName ( ), routeFrom ) );
                            SIMULATED_TASKS.get ( selectedTaskIndex ).setX ( ( int ) environmentRegions.get ( getGuiRegionIndex ( taskExecutionRegion ) ).PROCESS_UNIT.PROCESS_SUB_UNITS.get ( explorationIndex ).getRegionProcessSubUnitBody ( ).getX ( ) );
                            SIMULATED_TASKS.get ( selectedTaskIndex ).setY ( ( int ) environmentRegions.get ( getGuiRegionIndex ( taskExecutionRegion ) ).PROCESS_UNIT.PROCESS_SUB_UNITS.get ( explorationIndex ).getRegionProcessSubUnitBody ( ).getY ( ) );
                        }
                    }
                    break;
                }
            }
            
            public int getGuiRouteIndex ( SimulatedTaskManipulationType taskExecutionRoute )
            {
                int returnValue = -1;
                
                switch ( taskExecutionRoute )
                {
                    case ADMITTING : returnValue = 0; break;
                    case INTERRUPTING : returnValue = 4; break; 
                    case IOEVENTWAITING : returnValue = 3; break; 
                    case IOEVENTCOMPLETING : returnValue = 1; break; 
                    case DISPATCHING : returnValue = 5; break;  
                    case EXITING : returnValue = 2; break; 
                    
                    default : returnValue = 1; break; 
                }
                
                return returnValue;
            }
            
            public int getGuiRegionIndex ( SimulatedTaskType taskExecutionRegion )
            {
                int returnValue = -1;
                
                switch ( taskExecutionRegion )
                {
                    case NEW : returnValue = 0; break;
                    case WAITING : returnValue = 4; break; 
                    case RUNNING : returnValue = 3; break; 
                    case READY : returnValue = 2; break; 
                    case TERMINATED : returnValue = 1; break;
                    
                    default : returnValue = 1; break; 
                }
                
                return returnValue;
            }
            
            public int getRegionBlockSize ( SimulatedTaskType taskExecutionRegion )
            {
                return environmentRegions.get ( getGuiRegionIndex ( taskExecutionRegion ) ).PROCESS_UNIT.PROCESS_SUB_UNITS.size ( );
            }
            
            public ArrayList <Point2D> getRoutePoints ( SimulatedTaskManipulationType taskExecutionRoute )
            {
                ArrayList <Point2D> returnValue = null;
                
                switch ( taskExecutionRoute )
                {
                    case ADMITTING : returnValue = environmentRegionConnectors.get ( 0 ).getPoints ( ); break;
                    case INTERRUPTING : returnValue = environmentRegionConnectors.get ( 4 ).getPoints ( ); break; 
                    case IOEVENTWAITING : returnValue = environmentRegionConnectors.get ( 3 ).getPoints ( ); break; 
                    case IOEVENTCOMPLETING : returnValue = environmentRegionConnectors.get ( 1 ).getPoints ( ); break; 
                    case DISPATCHING : returnValue = environmentRegionConnectors.get ( 5 ).getPoints ( ); break;  
                    case EXITING : returnValue = environmentRegionConnectors.get ( 2 ).getPoints ( ); break; 
                }
                
                return returnValue;
            }
            
            public ArrayList <Point2D> getReverseRoutePoints ( SimulatedTaskManipulationType taskExecutionRoute )
            {
                ArrayList <Point2D> returnValue = null;
                
                switch ( taskExecutionRoute )
                {
                    case ADMITTING : returnValue = environmentRegionConnectors.get ( 0 ).getReversePoints ( ); break;
                    case INTERRUPTING : returnValue = environmentRegionConnectors.get ( 4 ).getReversePoints ( ); break; 
                    case IOEVENTWAITING : returnValue = environmentRegionConnectors.get ( 3 ).getReversePoints ( ); break; 
                    case IOEVENTCOMPLETING : returnValue = environmentRegionConnectors.get ( 1 ).getReversePoints ( ); break; 
                    case DISPATCHING : returnValue = environmentRegionConnectors.get ( 5 ).getReversePoints ( ); break;  
                    case EXITING : returnValue = environmentRegionConnectors.get ( 2 ).getReversePoints ( ); break; 
                }
                
                return returnValue;
            }
}