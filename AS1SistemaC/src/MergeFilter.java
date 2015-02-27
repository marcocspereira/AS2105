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

public class MergeFilter extends FilterFramework
{

    MergeFilter()
    {
//        super(inputPorts, outputPorts);
        super(2, 1);
    }

	public void run()
    {
        byte output;

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

		// Next we write a message to the terminal to let the world know we are alive...

		System.out.println( "\n" + this.getName() + "::Merge Filter Reading ");

		while (true)
		{
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
                } // for

                for (i = 0; i < IdLength; i++){
                    output = (byte) ((id0 >> ((7 - i) * 8)) & 0xff);
                    WriteFilterOutputPort(output, 0);
                    byteswritten++;
                }

                measurement0 = 0;

                for (i=0; i<MeasurementLength; i++ )
                {
                    databyte = ReadFilterInputPort(0);
                    measurement0 = measurement0 | (databyte & 0xFF);	// We append the byte on to measurement...

                    if (i != MeasurementLength-1)					// If this is not the last byte, then slide the
                    {												// previously appended byte to the left by one byte
                        measurement0 = measurement0 << 8;				// to make room for the next byte we append to the
                    } // if

                    bytesread++;									// Increment the byte count
                } // for

                for (i=0; i<MeasurementLength; i++ ){
                    output = (byte)((measurement0 >> ((7 - i) * 8)) & 0xff);
                    WriteFilterOutputPort(output, 0);
                    byteswritten++;
                }
            } // try

            catch (EndOfStreamException e)
            {
                ClosePorts();
                System.out.println( "\n" + this.getName() + "::Merge Filter Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten );
                break;
            } // catch

            try {

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
                } // for

                for (i = 0; i < IdLength; i++){
                    output = (byte) ((id1 >> ((7 - i) * 8)) & 0xff);
                    WriteFilterOutputPort(output, 0);
                    byteswritten++;
                }

                measurement1 = 0;
                for (i=0; i<MeasurementLength; i++ )
                {
                    databyte = ReadFilterInputPort(1);
                    measurement1 = measurement1 | (databyte & 0xFF);	// We append the byte on to measurement...

                    if (i != MeasurementLength-1)					// If this is not the last byte, then slide the
                    {												// previously appended byte to the left by one byte
                        measurement1 = measurement1 << 8;				// to make room for the next byte we append to the
                    } // if

                    bytesread++;									// Increment the byte count
                } // for

                for (i=0; i<MeasurementLength; i++ ){
                    output = (byte)((measurement1 >> ((7 - i) * 8)) & 0xff);
                    WriteFilterOutputPort(output, 0);
                    byteswritten++;
                }
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