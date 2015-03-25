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
    int bytesread = 0;				// This is the number of bytes read from the stream
    int byteswritten = 0;				// Number of bytes written to the stream.


    MergeFilter(int numInputPort, int numOutputPorts)
    {
        super(numInputPort, numOutputPorts);
    }


    public void run()
    {
        byte output;

//        Calendar TimeStamp = Calendar.getInstance();
//        SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");

        int MeasurementLength = 8;		// This is the length of all measurements (including time) in bytes
        int IdLength = 4;				// This is the length of IDs in the byte stream

        byte databyte = 0;				// This is the data byte read from the stream


        long measurement0;				// This is the word used to store all measurements - conversions are illustrated.
        long measurement1;				// This is the word used to store all measurements - conversions are illustrated.
        int id0;							// This is the measurement id
        int id1;							// This is the measurement id
        long measurement2;				// This is the word used to store all measurements - conversions are illustrated.
        long measurement3;				// This is the word used to store all measurements - conversions are illustrated.
        int id2;							// This is the measurement id
        int id3;							// This is the measurement id
        int i;							// This is a loop counter

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


            // from input 0
            id0 = readIdFromFilter(0);
            if(id0==-1)break;
            measurement0 = readMeasurementFromFilter(0);
            if(measurement0==-1)break;
            id1 = readIdFromFilter(0);
            if(id1==-1)break;
            measurement1 = readMeasurementFromFilter(0);
            if(measurement1==-1)break;

            // from input 1
            id2 = readIdFromFilter(1);
            if(id2==-1)break;
            measurement2 = readMeasurementFromFilter(1);
            if(measurement2==-1)break;
            id3 = readIdFromFilter(1);
            if(id3==-1)break;
            measurement3 = readMeasurementFromFilter(1);
            if(measurement3==-1)break;


            // se timestamp for o mesmo
            if(id0==id2){
                // escrever id timestamp
                sendId(id0);
                sendMeasurement(measurement0);

                // escrever altura
                sendId(id3);
                sendMeasurement(measurement3);

                // escrever id 4 e a temperatura
                sendId(id1);
                sendMeasurement(measurement1);
            }



        } // while

    } // run

    public int readIdFromFilter(int inputPort){
        int i;
        int id=0;
        int IdLength = 4;				// This is the length of IDs in the byte stream
        byte databyte = 0;

        try {
            for (i = 0; i < IdLength; i++) {
                databyte = ReadFilterInputPort(inputPort);    // This is where we read the byte from the stream...

                id = id | (databyte & 0xFF);        // We append the byte on to ID...

                if (i != IdLength - 1)                // If this is not the last byte, then slide the
                {                                    // previously appended byte to the left by one byte
                    id = id << 8;                    // to make room for the next byte we append to the ID

                } // if

                bytesread++;                        // Increment the byte count
            } // for
        }
        catch (EndOfStreamException e)
        {
            ClosePorts();
            System.out.println( "\n" + this.getName() + "::Merge Filter Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten );
            return -1;
        } // catch

        return id;
    }

    public long readMeasurementFromFilter(int inputPort){
        int i;
        long measurement=0;
        int MeasurementLength = 8;				// This is the length of IDs in the byte stream
        byte databyte = 0;

        try {
            for (i = 0; i < MeasurementLength; i++) {
                databyte = ReadFilterInputPort(inputPort);    // This is where we read the byte from the stream...

                measurement = measurement | (databyte & 0xFF);        // We append the byte on to ID...

                if (i != MeasurementLength - 1)                // If this is not the last byte, then slide the
                {                                    // previously appended byte to the left by one byte
                    measurement = measurement << 8;                    // to make room for the next byte we append to the ID

                } // if

                bytesread++;                        // Increment the byte count
            } // for
        }
        catch (EndOfStreamException e)
        {
            ClosePorts();
            System.out.println( "\n" + this.getName() + "::Merge Filter Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten );
            return -1;
        } // catch

        return measurement;
    }

    public void sendId(int id)
    {

        int IdLength = 4;
        byte output;
        for (int i = 0; i < IdLength; i++){
            output = (byte) ((id >> ((7 - i) * 8)) & 0xff);
            WriteFilterOutputPort(output);
            byteswritten++;
        }
    }

    public void sendMeasurement(long measure)
    {
        int MeasurementLength = 8;
        byte output;

        for (int i =0; i<MeasurementLength; i++ ){
            output = (byte)((measure >> ((7 - i) * 8)) & 0xff);
            WriteFilterOutputPort(output);
            byteswritten++;
        }
    }



    Double longToDouble(long measurement)
    {
        return Double.longBitsToDouble(measurement);
    }

    long doubleToLong(double measurement)
    {
        return Double.doubleToLongBits(measurement);
    }

} // MiddleFilter