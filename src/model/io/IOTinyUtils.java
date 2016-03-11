package model.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.ArrayList;
import java.util.List;

/**
 * io����
 */
public class IOTinyUtils {
    private static final String FOLDER_SEPARATOR = File.separator;
    private static final char EXTENSION_SEPARATOR = '.';

    public final static int DEFAULT_BUFFER_SIZE = 1024 * 4;

    static public String toString(InputStream input, String encoding) throws IOException {
        return (null == encoding) ? toString(new InputStreamReader(input)) : toString(new InputStreamReader(
                input, encoding));
    }


    static public String toString(Reader reader) throws IOException {
        CharArrayWriter sw = new CharArrayWriter();
        copy(reader, sw);
        return sw.toString();
    }


    static public long copy(Reader input, Writer output) throws IOException {
        char[] buffer = new char[1 << 12];
        long count = 0;
        for (int n = 0; (n = input.read(buffer)) >= 0; ) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }


    /**
     * ����
     * @param input
     * @param output
     * @return
     * @throws IOException
     */
    public static long copy(InputStream input, OutputStream output) throws IOException {
        final int EOF = -1;

        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];

        long count = 0;
        int n = 0;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }


    /**
     * �����ļ����ļ���
     * @param inputFile
     * @param outputFile
     * @param isOverWrite
     * @throws IOException
     */
    public static void copy(File inputFile,File outputFile,boolean isOverWrite) throws IOException{
        if(!inputFile.exists()){
            throw new RuntimeException(inputFile.getPath()+"ԴĿ¼������!");
        }
        copyPri(inputFile,outputFile,isOverWrite);
    }

    /**
     * �ݹ鸴��
     * @param inputFile
     * @param outputFile
     * @param isOverWrite
     * @throws IOException
     */
    private static void copyPri(File inputFile,File outputFile,boolean isOverWrite) throws IOException{
        //�Ǹ��ļ���
        if(inputFile.isFile()){
            copySimpleFile(inputFile,outputFile,isOverWrite);
        }else{
            //�ļ���
            if(!outputFile.exists()){
                outputFile.mkdir();
            }
            //ѭ�����ļ���
            for(File child : inputFile.listFiles()){
                copy(child,new File(outputFile.getPath()+"/"+child.getName()),isOverWrite);
            }
        }
    }


    /**
     * ���Ƶ����ļ�
     * @param inputFile
     * @param outputFile
     * @param isOverWrite
     * @throws IOException
     */
    private static void copySimpleFile(File inputFile,File outputFile,boolean isOverWrite) throws IOException{
        //Ŀ���ļ��Ѿ�����
        if(outputFile.exists()){
            if(isOverWrite){
                if(!outputFile.delete()){
                    throw new RuntimeException(outputFile.getPath()+"�޷����ǣ�");
                }
            }else{
                //��������
                return ;
            }
        }

        InputStream in = null;
        OutputStream out=null;
        try{
            in = new FileInputStream(inputFile);
            out = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int read = 0;
            while((read = in.read(buffer))!= -1){
                out.write(buffer,0,read);
            }
        }catch(IOException e){
            throw e;
        }finally{
            if(in!=null){
                in.close();
            }
            if(out!=null){
                out.close();
            }
        }
    }


    /**
     * �������������б���֤������NULL��
     */
    static public List<String> readLines(Reader input) throws IOException {
        BufferedReader reader = toBufferedReader(input);
        List<String> list = new ArrayList<String>();
        String line = null;
        for (; ; ) {
            line = reader.readLine();
            if (null != line) {
                list.add(line);
            } else {
                break;
            }
        }
        return list;
    }


    static private BufferedReader toBufferedReader(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }


    /**
     * �����ļ�
     * @param source
     * @param target
     * @throws IOException
     */
    @SuppressWarnings("resource")
    static public void copyFile(String source, String target) throws IOException {
        File sf = new File(source);
        if (!sf.exists()) {
            throw new IllegalArgumentException("source file does not exist.");
        }
        File tf = new File(target);
        tf.getParentFile().mkdirs();
        if (!tf.exists() && !tf.createNewFile()) {
            throw new RuntimeException("failed to create target file.");
        }

        FileChannel sc = null;
        FileChannel tc = null;
        try {
            tc = new FileOutputStream(tf).getChannel();
            sc = new FileInputStream(sf).getChannel();
            sc.transferTo(0, sc.size(), tc);
        } finally {
            if (null != sc) {
                sc.close();
            }
            if (null != tc) {
                tc.close();
            }
        }
    }


    /**
     * ɾ��ָ���ļ����ļ���
     * @param fileOrDir
     * @throws IOException
     */
    public static void delete(File fileOrDir) throws IOException {
        if (fileOrDir == null) {
            return;
        }

        if (fileOrDir.isDirectory()) {
            cleanDirectory(fileOrDir);
        }

        fileOrDir.delete();
    }


    /**
     * ����Ŀ¼�µ�����
     */
    public static void cleanDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        File[] files = directory.listFiles();
        if (files == null) { // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }

        IOException exception = null;
        for (File file : files) {
            try {
                delete(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    /**
     * ��ȡ�ļ���չ��
     * @param path �ļ�·��
     * @return
     */
    public static String getFilenameExtension(String path) {
        if (path == null) {
            return null;
        }
        int extIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
        if (extIndex == -1) {
            return null;
        }
        int folderIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        if (folderIndex > extIndex) {
            return null;
        }
        return path.substring(extIndex + 1);
    }


    /**
     * ��ȡ�ļ���
     * @param path  �ļ�·��
     * @return
     */
    public static String getFilename(String path) {
        if (path == null) {
            return null;
        }
        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
    }


    /**
     * ��String д���ļ�
     * @param file   �ļ�
     * @param data   ��д�������
     * @param encoding ����
     * @throws IOException
     */
    public static void save(File file, String data, String encoding) throws IOException {
        if (file==null) {
            throw new RuntimeException("�����ļ�����Ϊ��");
        }
        if (data == null) {
            throw new RuntimeException("�ļ�������Ϊ��");
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(data.getBytes(encoding));
        } finally {
            if (null != os) {
                os.close();
            }
        }
    }

    /**
     * д���ļ�
     * @param content
     * @param file
     * @throws IOException
     */
    public static void save(byte[] content, File file)throws IOException {
        if (file == null) {
            throw new RuntimeException("�����ļ�����Ϊ��");
        }
        if (content == null) {
            throw new RuntimeException("�ļ�������Ϊ��");
        }
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(content);
            save(is, file);
        } catch (IOException e) {
            throw e;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }


    /**
     * �����ļ�
     * @param streamIn
     * @param file
     * @throws IOException
     */
    public static void save(InputStream streamIn, File file)throws IOException {
        if (file==null) {
            throw new RuntimeException("�����ļ�����Ϊ��");
        }
        if (streamIn == null) {
            throw new RuntimeException("�ļ�������Ϊ��");
        }
        //�����
        OutputStream streamOut =null;
        //�ļ��в����ھʹ�����
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        streamOut = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = streamIn.read(buffer, 0, 8192)) != -1) {
            streamOut.write(buffer, 0, bytesRead);
        }
        streamOut.close();
        streamIn.close();
    }

    public static boolean createFile(String filePath) throws IOException {
        File file = new File(filePath);
        if(file.exists())
            return false;
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
       file.createNewFile();
        return true;
    }




    /**
     * ��ȡ�ļ�
     * @param in
     * @return
     */
    public static String read(InputStream in) {
        InputStreamReader reader;
        try {
            reader = new InputStreamReader(in, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return read(reader);
    }



    public static String read(Reader reader) {
        try {

            StringWriter writer = new StringWriter();

            char[] buffer = new char[DEFAULT_BUFFER_SIZE];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }

            return writer.toString();
        } catch (IOException ex) {
            throw new IllegalStateException("read error", ex);
        }
    }

    public static String read(Reader reader, int length) {
        try {
            char[] buffer = new char[length];

            int offset = 0;
            int rest = length;
            int len;
            while ((len = reader.read(buffer, offset, rest)) != -1) {
                rest -= len;
                offset += len;

                if (rest == 0) {
                    break;
                }
            }

            return new String(buffer, 0, length - rest);
        } catch (IOException ex) {
            throw new IllegalStateException("read error", ex);
        }
    }


    public static byte[] readByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }
}
