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

public class MergeFilter extends FilterFrameworkGeneric
{

    MergeFilter()
    {
//        super(inputPorts, outputPorts);
        super(2, 1);
    }

	public void run()
    {
        byte output;

        double temperature = 0;
        double meters = 0;

        Calendar TimeStamp = Calendar.getInstance();
        SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");

        int MeasurementLength = 8;		// This is the length of all measurements (including time) in bytes
        int IdLength = 4;				// This is the length of IDs in the byte stream

        byte databyte = 0;				// This is the data byte read from the stream
        int bytesread = 0;				// This is the number of bytes read from the stream

        long measurement0;				// This is the word used to store all measurements - conversions are illustrated.
        long measurement1;				// This is the word used to store all measurements - conversions are illustrated.
        int id0;							// This is the measurement id
        int id1;							// This is the measurement id
        int i;							// This is a loop counter

        int byteswritten = 0;				// Number of bytes written to the stream.
//		int bytesread = 0;					// Number of bytes read from the input file.
//		byte databyte = 0;					// The byte of data read from the file

		// Next we write a message to the terminal to let the world know we are alive...

		System.out.println( "\n" + this.getName() + "::Merge Filter Reading ");

		while (true)
		{
//			/*************************************************************
//			*	Here we read a byte and write a byte
//			*************************************************************/
//
//			try
//			{
//				databyte = ReadFilterInputPort();
//				bytesread++;
//				WriteFilterOutputPort(databyte);
//				byteswritten++;
//
//			} // try

            try
            {
                /***************************************************************************
                 // We know that the first data coming to this filter is going to be an ID and
                 // that it is IdLength long. So we first decommutate the ID bytes.
                 ****************************************************************************/

                id0 = 0;

                for (i=0; i<IdLength; i++ )
                {
                    databyte = ReadFilterInputPort(0);	// This is where we read the byte from the stream...

                    id0 = id0 | (databyte & 0xFF);		// We append the byte on to ID...

                    if (i != IdLength-1)				// If this is not the last byte, then slide the
                    {									// previously appended byte to the left by one byte
                        id0 = id0 << 8;					// to make room for the next byte we append to the ID

                    } // if

                    bytesread++;						// Increment the byte count
                    WriteFilterOutputPort(databyte, 0);
                    byteswritten++;
                } // for

                id1 = 0;
                for (i=0; i<IdLength; i++ )
                {
                    databyte = ReadFilterInputPort(1);	// This is where we read the byte from the stream...

                    id1 = id1 | (databyte & 0xFF);		// We append the byte on to ID...

                    if (i != IdLength-1)				// If this is not the last byte, then slide the
                    {									// previously appended byte to the left by one byte
                        id1 = id1 << 8;					// to make room for the next byte we append to the ID

                    } // if

                    bytesread++;						// Increment the byte count
                    WriteFilterOutputPort(databyte, 0);
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

                measurement0 = 0;

                for (i=0; i<MeasurementLength; i++ )
                {
                    databyte = ReadFilterInputPort(0);
                    measurement0 = measurement0 | (databyte & 0xFF);	// We append the byte on to measurement...

                    if (i != MeasurementLength-1)					// If this is not the last byte, then slide the
                    {												// previously appended byte to the left by one byte
                        measurement0 = measurement0 << 8;				// to make room for the next byte we append to the
                        // measurement
                    } // if

                    bytesread++;									// Increment the byte count
                    output = (byte)((measurement0 >> ((7 - i) * 8)) & 0xff);
                    WriteFilterOutputPort(output, 0);
                    byteswritten++;

                } // for
                measurement1 = 0;
                for (i=0; i<MeasurementLength; i++ )
                {
                    databyte = ReadFilterInputPort(1);
                    measurement1 = measurement1 | (databyte & 0xFF);	// We append the byte on to measurement...

                    if (i != MeasurementLength-1)					// If this is not the last byte, then slide the
                    {												// previously appended byte to the left by one byte
                        measurement1 = measurement1 << 8;				// to make room for the next byte we append to the
                        // measurement
                    } // if

                    bytesread++;									// Increment the byte count
                    output = (byte)((measurement1 >> ((7 - i) * 8)) & 0xff);
                    WriteFilterOutputPort(output, 0);
                    byteswritten++;
                } // for

//                System.out.println(" ID0 = " + id0);
//                System.out.println(" ID1 = " + id1);
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
////                System.out.print(" ID = " + id+" ");
//                if ( id == 0 )
//                {
//                    TimeStamp.setTimeInMillis(measurement);
//
//                } // if
//
//                if ( id == 2 )
//                {
////					TimeStamp.setTimeInMillis(measurement);
//                    meters = Double.longBitsToDouble(measurement);
//                } // if
//
//                /****************************************************************************
//                 // Here we pick up a measurement (ID = 4 in this case), but you can pick up
//                 // any measurement you want to. All measurements in the stream are
//                 // decommutated by this class. Note that all data measurements are double types
//                 // This illustrates how to convert the bits read from the stream into a double
//                 // type. Its pretty simple using Double.longBitsToDouble(long value). So here
//                 // we print the time stamp and the data associated with the ID we are interested
//                 // in.
//                 ****************************************************************************/
//
//                if ( id == 4 )
//                {
//                    temperature = Double.longBitsToDouble(measurement);
//                    System.out.format(TimeStampFormat.format(TimeStamp.getTime()) + " %3.5f %6.5f", temperature, meters);
//                    System.out.print("\n" );
//                } // if
//
////                else isn't height
//                else
//                {
//                    for(i = 0; i < 8; i++)
//                    {
//                        output = (byte)((measurement >> ((7 - i) * 8)) & 0xff);
//                        WriteFilterOutputPort(output, 0);
//                        byteswritten++;
//                    }
//                } // else
            } // try

			catch (EndOfStreamException e)
			{
                ClosePorts();
                System.out.println( "\n" + this.getName() + "::Merge Filter Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten );
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