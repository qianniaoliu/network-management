package com.network.management.common.socket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 网络套接字客户端
 *
 * @author yyc
 * @date 2021/12/13 12:26
 */
@Slf4j
public class SocketClient {
    public static void main(String args[]) {
        //String cmdInfor = "5A 5A 7E E7 FB 46 06 01 94 D8";
        //String returnString  = send(cmdInfor);
        //System.out.println("PowerClient result:" + returnString);
    }

    /**
     * 发送网络套接字命令
     *
     * @param host ip
     * @param port 端口
     * @param cmd  命令
     * @return 返回值
     */
    public static String send(String host, Integer port, String cmd, Boolean isControl) {
        String strReturn = null;
        InputStream is = null;
        PrintWriter pw = null;
        OutputStream os = null;
        Socket clientSocket = null;
        try {
            //将十六进制的字符串转换成字节数组
            byte[] cmdBytes = hexStringToByteArray(cmd);
            clientSocket = new Socket();
            // 设置socket默认超时时间10秒
            clientSocket.connect(new InetSocketAddress(host, port), 10000);
            os = clientSocket.getOutputStream();
            pw = new PrintWriter(os);
            is = clientSocket.getInputStream();
            os.write(cmdBytes);
            Thread.sleep(1000);
            os.flush();
            clientSocket.shutdownOutput();
            if (isControl) {
                return null;
            }
            strReturn = readBytes(is);
        } catch (Exception e) {
            log.error("网络套接字发送信息失败");
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(pw);
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(clientSocket);
        }
        return strReturn;
    }

    /**
     * read数据流
     *
     * @param is {@link InputStream}
     * @return 16进制的字符串
     */
    private static String readBytes(InputStream is) {
        String strReturn = null;
        try {
            int size = 0;
            int readLength = 0;
            int fileLength = is.available();
            byte[] buf = new byte[is.available()];
            while ((size = is.read(buf)) != -1) {
                readLength += size;
                strReturn = binaryToHexString(buf);
                if (fileLength == readLength) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("读取数据异常", e);
        }
        return strReturn;
    }

    /**
     * byte[]数组转换为16进制的字符串
     *
     * @param bytes 要转换的字节数组
     * @return 转换后的结果
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 将字节数组转换成十六进制的字符串
     *
     * @return
     */
    private static String binaryToHexString(byte[] bytes) {
        String hexStr = "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for (byte b : bytes) {
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
            hex += String.valueOf(hexStr.charAt(b & 0x0F));
            result += hex + " ";
        }
        return result;
    }

    /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param hexString 16进制表示的字符串
     * @return byte[] 字节数组
     */
    private static byte[] hexStringToByteArray(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                    .digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }
}
