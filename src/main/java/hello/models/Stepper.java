package hello.models;
import jssc.SerialPort;

import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;

import java.lang.String;
import java.util.Arrays;

public class Stepper {
    private static SerialPort serialPort;
    public Stepper() {
        //Передаём в конструктор имя порта
        serialPort = new SerialPort("/dev/ttyUSB0");
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
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }catch (Exception e){

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