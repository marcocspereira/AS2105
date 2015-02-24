import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

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

public class SortFilter extends FilterFrameworkGeneric
{

	public void run()
    {
        byte output;

        Calendar TimeStamp = Calendar.getInstance();
        SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");

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

		System.out.println( "\n" + this.getName() + "::Sort Filter Reading ");
        ArrayList<Calendar> calendars = new ArrayList<Calendar>();
        ArrayList<Integer> ids = new ArrayList<Integer>();

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

                id = 0;

                for (i=0; i<IdLength; i++ )
                {
                    databyte = ReadFilterInputPort(0);	// This is where we read the byte from the stream...

                    id = id | (databyte & 0xFF);		// We append the byte on to ID...

                    if (i != IdLength-1)				// If this is not the last byte, then slide the
                    {									// previously appended byte to the left by one byte
                        id = id << 8;					// to make room for the next byte we append to the ID

                    } // if

                    bytesread++;						// Increment the byte count
//                    WriteFilterOutputPort(databyte, 0);
//                    byteswritten++;
                } // for

                ids.add(id);
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
                    databyte = ReadFilterInputPort(0);
                    measurement = measurement | (databyte & 0xFF);	// We append the byte on to measurement...

                    if (i != MeasurementLength-1)					// If this is not the last byte, then slide the
                    {												// previously appended byte to the left by one byte
                        measurement = measurement << 8;				// to make room for the next byte we append to the
                        // measurement
                    } // if

                    bytesread++;									// Increment the byte count
//                    output = (byte)((measurement >> ((7 - i) * 8)) & 0xff);
//                    WriteFilterOutputPort(output, 0);
//                    byteswritten++;

                } // for

                TimeStamp = Calendar.getInstance();
                TimeStamp.setTimeInMillis(measurement);
                calendars.add(TimeStamp);
//                System.out.println(TimeStampFormat.format(TimeStamp.getTime()));


            } // try

			catch (EndOfStreamException e)
			{
                Collections.sort(calendars, new Comparator<Calendar>() {
                    public int compare(Calendar x, Calendar y) {
                        if (x.before(y)) return -1;
                        if (x.after(y)) return 1;
                        return 0;
                    }
                });
//                System.out.println("sorted");
                for (int j = 0; j < calendars.size(); j++) {
//                    System.out.println(TimeStampFormat.format(calendars.get(j).getTime()));
                    for (i = 0; i < IdLength; i++){
                        output = (byte) ((ids.get(j) >> ((7 - i) * 8)) & 0xff);
                        WriteFilterOutputPort(output, 0);
                        byteswritten++;
                    }
                    for (i=0; i<MeasurementLength; i++ ){
                        output = (byte)((calendars.get(j).getTimeInMillis() >> ((7 - i) * 8)) & 0xff);
                        WriteFilterOutputPort(output, 0);
                        byteswritten++;
                    }
                }

                ClosePorts();
                System.out.println( "\n" + this.getName() + "::Sort Filter Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten );
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