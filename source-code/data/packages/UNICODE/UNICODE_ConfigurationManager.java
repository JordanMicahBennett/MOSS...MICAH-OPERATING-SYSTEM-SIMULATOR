package data.packages.UNICODE; //Author(s): Jordan Micah Bennett
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Dimension;

public class UNICODE_ConfigurationManager
{
    // attributes
        //establish opacity level variables
        private ArrayList config_lines = new ArrayList ( );
        private String [ ] config_labels = 
                                            {
												"colour::",
												"app-opacity::",
												"anti-aliasing::",
												"buffer-dimension::",
												"process-execution-speed::",
												"process-execution-speeds::",
												"speed-boost-collection::"
                                            };
                                        
        //string to colour conterter
        private UNICODE_StringToColourConverter string_to_colour_converter = new UNICODE_StringToColourConverter ( );
        
        //establish convenience pack
        private UNICODE_ConveniencePack conveniencePack = new UNICODE_ConveniencePack ( );
		
		//establish configuration stream
		private String config_file_stream = "";
        
    //constructor
    public UNICODE_ConfigurationManager ( String config_file_stream )
    {
		this.config_file_stream = config_file_stream;
        loadConfigData ( );
    }
    
    //load config data
    public void loadConfigData ( )
    {
        //generate config lines
        try
        {
            int count = 0;
            Scanner scanner = new Scanner ( new File ( config_file_stream ) );
            while ( scanner.hasNext ( ) ) 
            {
               config_lines.add ( scanner.next ( ) );
            }
            scanner.close ( );
        }
        catch ( Exception error )
        {
        }   

    }
	
    //get colour from file, so program can know what colour to start with 
    public Color getColourFromFile ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 0 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
    //get opacity from file, so program can know what opacity to start with
    public float getOpacityFromFile ( )
    {
        float returnValue = 0.0f;
        returnValue = Float.parseFloat ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 1 ), "", "::", 2 ) [ 1 ] );
        return returnValue;
    }

    //get anti alias value
    public String getAntiAliasingStateFromFile ( )
    {
        String returnValue = null;
        
        returnValue = conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 2 ), "", "::", 2 ) [ 1 ];
        
        return returnValue;
    }
    //get buffer dimension from file
    public Dimension getBufferDimensionFromFile ( )
    {
		Dimension returnValue = null;
		
        String bufferDimensionString = bufferDimensionString = conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 3 ), "", "::", 2 ) [ 1 ];
		
		int width = Integer.parseInt ( conveniencePack.getDelimitedArray ( bufferDimensionString, "", ",", 2 ) [ 0 ] );
		int height = Integer.parseInt ( conveniencePack.getDelimitedArray ( bufferDimensionString, "", ",", 2 ) [ 1 ] );
		
		returnValue = new Dimension ( width, height );
		
		return returnValue;
	}

    public double getProcessExecutionSpeedFromFile ( )
    {
        double returnValue = 0.00;
        returnValue = Double.parseDouble ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 4 ), "", "::", 2 ) [ 1 ] );
        return returnValue;
    }
	
    //getProcessExecutionSpeedsFromFile
    public double [ ] getProcessExecutionSpeedsFromFile ( )
    {
        double [ ] returnValue = new double [ 4 ];
        
        //transfer data to array blocks
		for ( int i = 0; i < conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 5 ), "", "::", 2 ) [ 1 ], "", ",", 4 ).length; i ++ )
			returnValue [ i ] = Double.parseDouble ( conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 5 ), "", "::", 2 ) [ 1 ], "", ",", 4 ) [ i ] );
			
        //return the fucking colour
        return returnValue;
    }
	
    //getSpeedBoostCollectionFromFile
    public int [ ] getSpeedBoostCollectionFromFile ( )
    {
        int [ ] returnValue = new int [ 4 ];
        
        //transfer data to array blocks
		for ( int i = 0; i < conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 6 ), "", "::", 2 ) [ 1 ], "", ",", 4 ).length; i ++ )
			returnValue [ i ] = Integer.parseInt ( conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 6 ), "", "::", 2 ) [ 1 ], "", ",", 4 ) [ i ] );
			
        //return the fucking colour
        return returnValue;
    }
	
    //update colour config:: takes a colour, and extracts rgb integer components, 
    //and prints them to the colour config file.
    public void updateColour ( Color colour )
    {
        //establish output colour config ( what bushman gui kit config reconizes as colour data at parsing )
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( string_to_colour_converter.getRGBString ( colour ), "", " ", 3 );
        String output_colour = rgb_array [ 0 ] + "," + rgb_array [ 1 ] + "," + rgb_array [ 2 ];
        //set colour config index to new colour
        config_lines.set ( 0, config_labels [ 0 ] + output_colour );
        //print the config array list contents
        updateConfigFile ( );
    }
	
    //update opacity
    public void updateOpacity ( UNICODE_OpacityController opacity_manager )
    {
        //set opacity config index to new opacity level
        config_lines.set ( 1, config_labels [ 1 ] + opacity_manager.getOpacLevel ( ) );
        //print the config array list contents
        updateConfigFile ( );
    }
	
    //update anti - alias file
    public void updateAntiAliasing ( UNICODE_AntiAliasingController anti_alias_manager )
    {
        //set anti - aliasing config index to new anti - aliasing state answer
        config_lines.set ( 2, config_labels [ 2 ] + anti_alias_manager.getRenderingAnswer ( ) );
        //print the config array list contents
        updateConfigFile ( );
    }
	
    //update buffer dimension 
    public void updateBufferDimensionController ( UNICODE_BufferDimensionController bufferDimensionController )
    {
        //set Method Package Regex Usage Answer
        config_lines.set ( 3, config_labels [ 3 ] + bufferDimensionController.getBufferDimensionString ( ) );
        //print the config array list contents
        updateConfigFile ( );
	}
	
	
    //update opacity
    public void updateProcessExecutionSpeed ( UNICODE_ProcessExecutionSpeedController processExecutionSpeedManager )
    {
        //set opacity config index to new opacity level
        config_lines.set ( 4, config_labels [ 4 ] + processExecutionSpeedManager.getValue ( ) );
        //print the config array list contents
        updateConfigFile ( );
    }
	
    public void updateConfigFile ( )
    {
        try
        {
            PrintWriter pw = new PrintWriter ( new FileWriter ( config_file_stream ) );
            for ( int configs = 0; configs < config_lines.size ( ); configs ++ )
                pw.println ( config_lines.get ( configs ) );
            pw.close ( );
        }
        catch ( Exception error )
        {
        }  
    }
	
	public void defineLabels ( String label_string, String delimiter )
	{
		config_labels = conveniencePack.makeArray ( label_string, delimiter );
	}
	
	public String [ ] getConfigLabels ( )
	{
		return config_labels;
	}
}
