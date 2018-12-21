package com.it.jwt.util;

import java.applet.Applet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;


import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;
import netscape.javascript.JSObject;
import com.sun.corba.se.impl.copyobject.JavaStreamObjectCopierImpl;


public class scann extends Applet {


public String PortName = "COM12";
public CommPortIdentifier portId;
public SerialPort serialPort;
public InputStream in;
public static String error = "请扫码";
public static String proName = "Serial_Communication";


private URL codeBase;
private static String libPath = "";


private static String commfile1 = "win32com.dll";
private static String commfile3 = "javax.comm.properties";
static {
System.setSecurityManager(null);
}// 不使用安全管理器


@Override
public void init() {
    // TODO Auto-generated method stub
    codeBase = super.getCodeBase();
    String property = System.getProperty("java.library.path");
    System.out.println("所有路径==>" + property);
    String[] split = property.split(";");


    for (String str : split) {
        if (str.indexOf("jre") != -1) {
        System.out.println("截取前==>" + str);
        str = str.substring(0, str.indexOf("jre"))
        + str.substring(str.indexOf("jre"))
        .substring(
        0,
        str.substring(str.indexOf("jre"))
        .indexOf("/"));
        libPath = str;
        break;
        }
    }
    if ("".equals(libPath)) {
         return;


    }
    System.out.println("libPath===>" + libPath);
    downFile();
    System.out.println("开始加载。。。。");
    Initialize(12);


}


public void Initialize(int PortID) {
    PortName = "COM" + PortID;
    try {


        portId = CommPortIdentifier.getPortIdentifier(PortName);


        try {
        serialPort = (SerialPort) portId.open(proName, 2000);


    } catch (PortInUseException e) {


        if (!portId.getCurrentOwner().equals(proName)) {
            error = "该串口被其它程序占用";
            return;
        }
    }

    try {
        in = serialPort.getInputStream();
    } catch (IOException e) {
        error = "与扫描枪通讯连接失败,请重试!";
        return;
    }


    try {
    serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8,
    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
    } catch (UnsupportedCommOperationException e) {
    error = "与扫描枪通讯参数设置失败,请联系管理员!";
    return;
    }
    } catch (NoSuchPortException e) {


    error = "与扫描枪通讯参数设置失败,请联系管理员!";
    e.printStackTrace();
    return;
    }


int c;
try {
if (in != null) {
while (true) {
if (in.available() > 0) {
error = "";
int size = in.available();
System.out.println(size);
byte[] test = new byte[size];
in.read(test);
error = new String(test, "GBK");
error = error.substring(7, error.length());
System.out.println("====> " + error);
if (error.indexOf("AAAAA") != -1) {
error = "识别错误,请重新扫描.";


} else if (error.indexOf("printer") == -1) {
error = "非复印系统编码,暂不提供解析.";


} else {
error = error.substring(7, error.length());
}
System.out.println(error);
//调用js方法
runjs(error);
}
}
}


} catch (IOException e) {
error = "读取返回结果失败,请重试!";
return;
}


}



public void runjs(String message){
JSObject window = JSObject.getWindow(this);
window.call("myMessage", new Object[]{message});


}
public void downFile() {
AccessController.doPrivileged(new PrivilegedAction<String>() {


@Override
public String run() {
try {
// 获取加载库时搜索的路径列表
File dll = new File(libPath + "/bin", commfile1);
if (!dll.exists()) {
        URL url = new URL(codeBase + commfile1);
        InputStream is = url.openConnection().getInputStream();
        FileOutputStream fos = new FileOutputStream(dll);
        byte[] buf = new byte[2048]; // 读取缓存
        int len = 0;
        while ((len = is.read(buf)) != -1) {
        fos.write(buf, 0, len);
        }
        fos.flush();
        fos.close();
        is.close();
        System.out.println("创建文件完成[" + dll + "].");
}


File dll3 = new File(libPath + "/lib", commfile3);
if (!dll3.exists()) {
        URL url = new URL(codeBase + commfile3);
        InputStream is = url.openConnection().getInputStream();
        FileOutputStream fos = new FileOutputStream(dll3);
        byte[] buf = new byte[2048]; // 读取缓存
        int len = 0;
        while ((len = is.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }
        fos.flush();
        fos.close();
        is.close();
        System.out.println("创建文件完成[" + dll3 + "].");
}


System.out.println("初始化成功!");


} catch (Exception e) {
e.printStackTrace();
}
// System.load(libPath + "/bin/" + commfile1);


return "0";


}


});
}


}


