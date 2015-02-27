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

public class SortFilter extends FilterFramework
{

    public void run()
    {
        int count = 0;
        ArrayList<ListNode> lista = new ArrayList<ListNode>();

//        byte output;

        Calendar TimeStamp = Calendar.getInstance();
//        SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");

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

        System.out.println(this.getName() + "::Sort Filter Reading ");
//        ArrayList<Calendar> calendars = new ArrayList<Calendar>();
//        ArrayList<Integer> ids = new ArrayList<Integer>();

        //Vars to save to list
        long temperature = 0;
        long height = 0;
        long pitch = 0;
        double pressure = 0;
        long speed = 0;
        long timestamp = 0;
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
                    databyte = ReadFilterInputPort();	// This is where we read the byte from the stream...

                    id = id | (databyte & 0xFF);		// We append the byte on to ID...

                    if (i != IdLength-1)				// If this is not the last byte, then slide the
                    {									// previously appended byte to the left by one byte
                        id = id << 8;					// to make room for the next byte we append to the ID

                    } // if

                    bytesread++;						// Increment the byte count
                } // for

//                ids.add(id);
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
                if ( id == 0 ) {


                    if (count > 0) {
                        ListNode novo = new ListNode();
                        Calendar tempo = Calendar.getInstance();
                        tempo.setTimeInMillis(timestamp);
                        novo.setTime(tempo);
                        novo.setTemperature(temperature);
                        novo.setHeight(height);
                        novo.setSpeed(speed);
                        novo.setPressure(pressure);
                        novo.setPitch(pitch);
                        lista.add(novo);

                        temperature = 0;
                        height = 0;
                        pitch = 0;
                        pressure = 0;
                        speed = 0;
                        timestamp = 0;
                    }
                    if (count >= 0) {
                        count++;
                    }
                    timestamp = measurement;
                    TimeStamp.setTimeInMillis(measurement);
                }

                if ( id == 1 ) {
                    speed = measurement;
                } // if
                if( id == 2){
                    height = measurement;
                }
                if( id == 3){
                    pressure = Double.longBitsToDouble(measurement);
                }
                if(id == 4){
                    temperature =  measurement;
                }
                if(id == 5){
                    pitch = measurement;
                }
            } // try

            catch (EndOfStreamException e)
            {
                ListNode novo = new ListNode();
                TimeStamp.setTimeInMillis(timestamp);
                novo.setTime(TimeStamp);
                novo.setTemperature(temperature);
                novo.setHeight(height);
                novo.setSpeed(speed);
                novo.setPressure(pressure);
                // novo.setPitch(pitch);
                lista.add(novo);

                Collections.sort(lista, new Comparator<ListNode>() {
                    public int compare(ListNode x, ListNode y) {
                        if (x.getTime().before(y.getTime())) return -1;
                        if (x.getTime().after(y.getTime())) return 1;
                        return 0;
                    }
                });

                for (int j = 0; j < lista.size(); j++) {
                    byteswritten += sendId(0);
                    byteswritten += sendMeasurement(lista.get(j).getTime().getTimeInMillis());
                    byteswritten += sendId(1);
                    byteswritten += sendMeasurement(lista.get(j).getSpeed());
                    byteswritten += sendId(2);
                    byteswritten += sendMeasurement(lista.get(j).getHeight());
                    byteswritten += sendId(3);
                    byteswritten += sendMeasurement(Double.doubleToLongBits(lista.get(j).getPressure()));
                    byteswritten += sendId(4);
                    byteswritten += sendMeasurement(lista.get(j).getTemperature());
                    // byteswritten += sendId(5);
                    // byteswritten += sendMeasurement(lista.get(j).getPitch());

                }

                ClosePorts();
                System.out.println(this.getName() + "::Sort Filter Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten );
                break;
            } // catch

        } // while
    } // run

    int sendId(int id)
    {
        int byteswritten = 0;
        int IdLength = 4;
        byte output;
        for (int i = 0; i < IdLength; i++){
            output = (byte) ((id >> ((7 - i) * 8)) & 0xff);
            WriteFilterOutputPort(output);
            byteswritten++;
        }
        return byteswritten;
    }

    int sendMeasurement(long measure)
    {
        int byteswritten = 0;
        int MeasurementLength = 8;
        byte output;

        for (int i =0; i<MeasurementLength; i++ ){
            output = (byte)((measure >> ((7 - i) * 8)) & 0xff);
            WriteFilterOutputPort(output);
            byteswritten++;
        }
        return byteswritten;
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