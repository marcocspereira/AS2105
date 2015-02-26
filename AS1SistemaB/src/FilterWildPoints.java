package AS1SistemaB.src;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/******************************************************************************************************************
 * File:MiddleFilter.java
 * Course: 17655
 * Project: Assignment 1
 * Copyright: Copyright (c) 2003 Carnegie Mellon University
 * Versions:
 *	1.0 November 2008 - Sample Pipe and Filter code (ajl).
 *
 * Description:
 *
 * This class serves as an example for how to use the FilterRemplate to create a standard filter. This particular
 * example is a simple "pass-through" filter that reads data from the filter's input port and writes data out the
 * filter's output port.
 *
 * Parameters: 		None
 *
 * Internal Methods: None
 *
 ******************************************************************************************************************/

public class FilterWildPoints extends FilterFramework
{


    public void run()
    {
        Calendar TimeStamp = Calendar.getInstance();
        SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy:MM:dd:hh:mm:ss");

        byte output;


        int MeasurementLength = 8;		// This is the length of all measurements (including time) in bytes
        int IdLength = 4;				// This is the length of IDs in the byte stream

        byte databyte = 0;				// This is the data byte read from the stream
        int bytesread = 0;				// This is the number of bytes read from the stream

        long measurement;				// This is the word used to store all measurements - conversions are illustrated.
        int id;							// This is the measurement id
        int i;							// This is a loop counter

        int byteswritten = 0;				// Number of bytes written to the stream.
//		int bytesread = 0;					// Number of bytes read from the input file.
//		byte databyte = 0;					// The byte of data read from the file

        // Next we write a message to the terminal to let the world know we are alive...

        System.out.print( "\n" + this.getName() + "::FilterWildPoint Reading ");

        while (true)
        {


            try
            {
                /***************************************************************************
                 // We know that the first data coming to this filter is going to be an ID and
                 // that it is IdLength long. So we first decommutate the ID bytes.
                 ****************************************************************************/

                id = 0;

                for (i=0; i<IdLength; i++ )
                {
                    databyte = ReadFilterInputPort();	// This is where we read the byte from the stream...

                    id = id | (databyte & 0xFF);		// We append the byte on to ID...

                    if (i != IdLength-1)				// If this is not the last byte, then slide the
                    {									// previously appended byte to the left by one byte
                        id = id << 8;					// to make room for the next byte we append to the ID

                    } // if



                    WriteFilterOutputPort(databyte);
                    byteswritten++;
                } // for

                /****************************************************************************
                 // Here we read measurements. All measurement data is read as a stream of bytes
                 // and stored as a long value. This permits us to do bitwise manipulation that
                 // is neccesary to convert the byte stream into data words. Note that bitwise
                 // manipulation is not permitted on any kind of floating point types in Java.
                 // If the id = 0 then this is a time value and is therefore a long value - no
                 // problem. However, if the id is something other than 0, then the bits in the
                 // long value is really of type double and we need to convert the value using
                 // Double.longBitsToDouble(long val) to do the conversion which is illustrated.
                 // below.
                 *****************************************************************************/

                measurement = 0;

                for (i=0; i<MeasurementLength; i++ )
                {
                    databyte = ReadFilterInputPort();
                    measurement = measurement | (databyte & 0xFF);	// We append the byte on to measurement...

                    if (i != MeasurementLength-1)					// If this is not the last byte, then slide the
                    {												// previously appended byte to the left by one byte
                        measurement = measurement << 8;				// to make room for the next byte we append to the
                        // measurement
                    } // if

                    bytesread++;									// Increment the byte count

                } // for

//				if ( id == 0 ) Tempo
//				if ( id == 1 ) Velocidade
//				if ( id == 2 ) Altitude
//				if ( id == 3 ) Pressão
//				if ( id == 4 ) Temperatura
//				if ( id == 5 ) Pitch

                /****************************************************************************
                 // Here we look for an ID of 0 which indicates this is a time measurement.
                 // Every frame begins with an ID of 0, followed by a time stamp which correlates
                 // to the time that each proceeding measurement was recorded. Time is stored
                 // in milliseconds since Epoch. This allows us to use Java's calendar class to
                 // retrieve time and also use text format classes to format the output into
                 // a form humans can read. So this provides great flexibility in terms of
                 // dealing with time arithmetically or for string display purposes. This is
                 // illustrated below.
                 ****************************************************************************/

//                if ( id == 0 )
//                {
//                    TimeStamp.setTimeInMillis(measurement);
//                } // if

                /****************************************************************************
                 // Here we pick up a measurement (ID = 4 in this case), but you can pick up
                 // any measurement you want to. All measurements in the stream are
                 // decommutated by this class. Note that all data measurements are double types
                 // This illustrates how to convert the bits read from the stream into a double
                 // type. Its pretty simple using Double.longBitsToDouble(long value). So here
                 // we print the time stamp and the data associated with the ID we are interested
                 // in.
                 ****************************************************************************/
//                if is pressure
                if ( id == 3 )
                {

                    //check if is wildpoint (<50 || >80), se for mandar para file


                    if(Double.longBitsToDouble(measurement) < 50 || Double.longBitsToDouble(measurement) > 80){

                        /*****************************
                         *
                         * Escrever Splitter aqui
                         *
                         * Enviar Tudo para a class StoreFileMem e os wildpoints para um file a parte (falta escrita para ficheiro)
                         *
                         *
                         */

                        //Handle Wildpoint
                        double wildpoint = 1;
                        measurement = doubleToLong(wildpoint);
                        for(i = 0; i < 8; i++)
                        {
                            output = (byte)((measurement >> ((7 - i) * 8)) & 0xff);
                            WriteFilterOutputPort(output);
                            byteswritten++;
                        } // for
                    }  // if


                    else{
                        for(i = 0; i < 8; i++)
                        {
                            output = (byte)((measurement >> ((7 - i) * 8)) & 0xff);
                            WriteFilterOutputPort(output);
                            byteswritten++;
                        } // for
                    } // else



                } // if

//                else isn't Pressure
                else
                {
                    System.out.println("ID: " + id);
                    for(i = 0; i < 8; i++)
                    {
                        output = (byte)((measurement >> ((7 - i) * 8)) & 0xff);
                        WriteFilterOutputPort(output);
                        byteswritten++;
                    }
                } // else
            } // try

            catch (FilterFramework.EndOfStreamException e)
            {
                ClosePorts();
                //System.out.print( "\n" + this.getName() + "::Middle Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten );
                break;
            } // catch

        } // while

    } // run

    Double longToDouble(long measurement)
    {
        return Double.longBitsToDouble(measurement);
    }

    long doubleToLong(double measurement)
    {
        return Double.doubleToLongBits(measurement);
    }

} // MiddleFilter