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

		SourceFilter source = new SourceFilter("FlightData.dat");

        StoreFileMem convert = new StoreFileMem();
       SplitterWildPoints splitter = new SplitterWildPoints(1,2);
		TemperatureFilter temp = new TemperatureFilter();
		HeightFilter height = new HeightFilter();
		SinkFilter sink = new SinkFilter();
       SinkFilter2 sink2 = new SinkFilter2();

		/****************************************************************************
		* Here we connect the filters starting with the sink filter (Filter 1) which
		* we connect to Filter2 the middle filter. Then we connect Filter2 to the
		* source filter (Filter3).
		****************************************************************************/
        height.Connect(source);
        temp.Connect(height);
        splitter.Connect(temp);
        sink2.Connect(splitter,0,0);
        convert.Connect(splitter,0,1);
        sink.Connect(convert);

		/****************************************************************************
		* Here we start the filters up. All-in-all,... its really kind of boring.
		****************************************************************************/


        source.start();
       height.start();
       temp.start();
       splitter.start();
        //wildPoints.start();
        convert.start();
        sink.start();
        sink2.start();

   } // main

} // Plumber