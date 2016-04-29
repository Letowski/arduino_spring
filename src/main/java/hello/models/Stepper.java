package hello.models;
import jssc.SerialPort;

import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

import java.lang.String;
import java.util.Arrays;

public class Stepper {
    private static SerialPort serialPort;
    private static String[] portNames = {
            "/dev/ttyUSB0",
            "/dev/ttyACM0",
            "/dev/ttyS0",
            "/dev/ttyUSB1",
            "/dev/ttyS1"
    };

    public Stepper() {
        for( String portName : portNames ) {

            //Передаём в конструктор имя порта
            serialPort = new SerialPort(portName);
            try {
                serialPort.openPort();
                System.out.println("open");
                //Выставляем параметры
                serialPort.setParams(SerialPort.BAUDRATE_9600,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
                //Включаем аппаратное управление потоком
                serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                        SerialPort.FLOWCONTROL_RTSCTS_OUT);
                break;
            }
            catch (SerialPortException e) {
                e.printStackTrace();
            }catch (Exception e){
            }

        }
    }

    public void send(String command){
        try {
            serialPort.writeString("#"+command+";");
        }catch (SerialPortException e){
            System.out.println("write exception");
        }
    }

}