/******************************************************************************************************************
* File:Plumber.java
* Course: 17655
* Project: Assignment 1
* Copyright: Copyright (c) 2003 Carnegie Mellon University
* Versions:
*	1.0 November 2008 - Sample Pipe and Filter code (ajl).
*
* Description:
*
* This class serves as an example to illstrate how to use the PlumberTemplate to create a main thread that
* instantiates and connects a set of filters. This example consists of three filters: a source, a middle filter
* that acts as a pass-through filter (it does nothing to the data), and a sink filter which illustrates all kinds
* of useful things that you can do with the input stream of data.
*
* Parameters: 		None
*
* Internal Methods:	None
*
******************************************************************************************************************/
public class Plumber
{
   public static void main( String argv[])
   {
		/****************************************************************************
		* Here we instantiate three filters.
		****************************************************************************/

		SourceFilter sourceF = new SourceFilter("/Users/Marco/IdeaProjects/AS2105/AS1SistemaA/src/FlightData.dat");
        SplitterFilter splitterF = new SplitterFilter(1,2);

		TemperatureFilter temperatureF = new TemperatureFilter();
		HeightFilter heightF = new HeightFilter();

        MergeFilter mergeF = new MergeFilter(2,1);
		SinkFilter sinkF = new SinkFilter();

		/****************************************************************************
		* Here we connect the filters starting with the sink filter (Filter 1) which
		* we connect to Filter2 the middle filter. Then we connect Filter2 to the
		* source filter (Filter3).
		****************************************************************************/

       splitterF.Connect(sourceF);              // connect input of SplitterFilter port 0 to SourceFilter output port 0

       temperatureF.Connect(splitterF, 0, 0);   // connect input of TemperatureFilter port 0 to SplitterFilter output port 0
       heightF.Connect(splitterF, 0, 1);        // connect input of HeightFilter port 0 to SplitterFilter output port 1

       mergeF.Connect(temperatureF, 0, 0);      // connect input of MergerFilter port 0 to TemperatureFilter output port 0
       mergeF.Connect(heightF, 1, 0);           // connect input of MergerFilter port 1 to HeightFilter output port 0

       sinkF.Connect(mergeF);             // connect input of SinkFilter port 0 to MergerFilter output port 0


		/****************************************************************************
		* Here we start the filters up. All-in-all,... its really kind of boring.
		****************************************************************************/

		sourceF.start();
		splitterF.start();
		temperatureF.start();
		heightF.start();
        mergeF.start();
        sinkF.start();

   } // main

} // Plumber