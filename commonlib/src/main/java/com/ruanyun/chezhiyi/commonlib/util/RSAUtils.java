package com.ruanyun.chezhiyi.commonlib.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 *
 */
public final class RSAUtils {

    /**
     * 商品分享加密用
     */
    public static String PUCLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKUFzJXCeifEFtTTx/r4pANr8+\n" +
            "rALEtEnx2QfQDXx+Cem2+9QLRTrB4M3AFkEFfKr3qnCCKPOoKBrgv+08oCsACRV5\n" +
            "cvtb9D+p6NPygn1Mgk3Sv3oiKkvCZHBdvYqIeUWkGy7VZlOWQkuWnz2EsEUIid4i\n" +
            "zd5Wlrab9M7jNCzo9wIDAQAB";

    /**
     *  商品分享解密用
     */
    public static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMpQXMlcJ6J8QW1N\n" +
            "PH+vikA2vz6sAsS0SfHZB9ANfH4J6bb71AtFOsHgzcAWQQV8qveqcIIo86goGuC/\n" +
            "7TygKwAJFXly+1v0P6no0/KCfUyCTdK/eiIqS8JkcF29ioh5RaQbLtVmU5ZCS5af\n" +
            "PYSwRQiJ3iLN3laWtpv0zuM0LOj3AgMBAAECgYEAubV21qcvAqnIbQqe5B5Aa51V\n" +
            "g7PlfqZ30faEg2g9xHxY+szC4e2Ud+9hLle/K8g6L8lWKURqFQtrUK8cl6/2hxnl\n" +
            "9rNtncohqLPHO5CiN9GO/qleJorgMf3fvcYNYPLgnmkj426M5ed/qAvshNV8zS5h\n" +
            "8NwofFrOfw68AQxIiwECQQDovBxVixLTs/3pLUjyytqNXFmOaDlL/+vtXQVcqc69\n" +
            "CohhvzSQGJmFO362QYX1ICp7EgYES1WgQvef/7ufoTK3AkEA3onBGGpzU0pA3ZUD\n" +
            "dEjHtrvEwAZCMdG8g9oCNvlCHPnzo5H51Wuka0QqR0hv2YM1R/xl69vn+BlmtPyR\n" +
            "3za7wQJAT01BrlhMGvzayOhYUfqTC9Xq4h5bX60de+zVVeS6gCmlnQDk7TCkpwRF\n" +
            "wd6DwamrL/JNQItW6tvGuqsOCG+J8wJBAI7x64/0aOnaa3opytM6INcXG9XA72oy\n" +
            "8CW9tuh7CeW1BLRQAyv8/dtNKN8q/3W3m1UHIqzzT7kFD/03s7eu38ECQGjHNqRy\n" +
            "JsynBZTa4LlFVoEFQeJ3jaS7v2Y1O5VUY3C06d2mp8Y4w6hyaZynGS8Ca8ySU7t8\n" +
            "YrhYgNjNaaHu7LE=";

    public static final String test="{\"access_token\":\"\",\"msg\":\"\",\"obj\":[{\"flag1\":\"1\",\"itemCode\":\"1\",\"itemName\":\"男\",\"orderby\":1,\"parentCode\":\"USERSEX\",\"parentId\":1,\"parentName\":\"性别\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"1\",\"itemName\":\"正常\",\"orderby\":1,\"parentCode\":\"USERSTATUS\",\"parentId\":3,\"parentName\":\"用户状态\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"1\",\"itemName\":\"菜单\",\"orderby\":1,\"parentCode\":\"AUTH_TYPE\",\"parentId\":6,\"parentName\":\"权限类型\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"3\",\"itemName\":\"全部\",\"orderby\":1,\"parentCode\":\"AUTH_REQUEST_TYPE\",\"parentId\":10,\"parentName\":\"权限访问类型\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"1\",\"itemName\":\"Android\",\"orderby\":1,\"parentCode\":\"APK_TYPE\",\"parentId\":14,\"parentName\":\"APK类型\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"1\",\"itemName\":\"系统管理\",\"orderby\":1,\"parentCode\":\"OPERA_TYPE\",\"parentId\":31,\"parentName\":\"操作日志类型\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"0\",\"itemName\":\"否\",\"orderby\":1,\"parentCode\":\"APP_AUTH\",\"parentId\":53,\"parentName\":\"APP权限\",\"status\":1},{\"flag1\":\"\",\"itemCode\":\"1\",\"itemName\":\"白色\",\"orderby\":1,\"parentCode\":\"CAR_COLOR\",\"parentId\":407,\"parentName\":\"车辆颜色\",\"status\":1},{\"flag1\":\"字典表\",\"itemCode\":\"1\",\"itemName\":\"DICTIONARY\",\"orderby\":1,\"parentCode\":\"STATICDATA_VERSION\",\"parentId\":411,\"parentName\":\"静态数据版本控制\",\"status\":1},{\"flag1\":\"车系\",\"itemCode\":\"1\",\"itemName\":\"CARMODEL\",\"orderby\":1,\"parentCode\":\"STATICDATA_VERSION\",\"parentId\":412,\"parentName\":\"静态数据版本控制\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"2\",\"itemName\":\"女\",\"orderby\":2,\"parentCode\":\"USERSEX\",\"parentId\":2,\"parentName\":\"性别\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"0\",\"itemName\":\"禁用\",\"orderby\":2,\"parentCode\":\"USERSTATUS\",\"parentId\":4,\"parentName\":\"用户状态\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"2\",\"itemName\":\"权限\",\"orderby\":2,\"parentCode\":\"AUTH_TYPE\",\"parentId\":7,\"parentName\":\"权限类型\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"1\",\"itemName\":\"电脑权限\",\"orderby\":2,\"parentCode\":\"AUTH_REQUEST_TYPE\",\"parentId\":8,\"parentName\":\"权限访问类型\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"2\",\"itemName\":\"IOS\",\"orderby\":2,\"parentCode\":\"APK_TYPE\",\"parentId\":15,\"parentName\":\"APK类型\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"1\",\"itemName\":\"是\",\"orderby\":2,\"parentCode\":\"APP_AUTH\",\"parentId\":54,\"parentName\":\"APP权限\",\"status\":1},{\"flag1\":\"\",\"itemCode\":\"2\",\"itemName\":\"黑色\",\"orderby\":2,\"parentCode\":\"CAR_COLOR\",\"parentId\":408,\"parentName\":\"车辆颜色\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"0\",\"itemName\":\"删除\",\"orderby\":3,\"parentCode\":\"USERSTATUS\",\"parentId\":5,\"parentName\":\"用户状态\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"2\",\"itemName\":\"手机权限\",\"orderby\":3,\"parentCode\":\"AUTH_REQUEST_TYPE\",\"parentId\":9,\"parentName\":\"权限访问类型\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"123\",\"itemName\":\"123\",\"orderby\":3,\"parentCode\":\"APP_AUTH\",\"parentId\":372,\"parentName\":\"APP权限\",\"status\":1},{\"flag1\":\"\",\"itemCode\":\"3\",\"itemName\":\"红色\",\"orderby\":3,\"parentCode\":\"CAR_COLOR\",\"parentId\":409,\"parentName\":\"车辆颜色\",\"status\":1},{\"flag1\":\"\",\"itemCode\":\"4\",\"itemName\":\"珍珠白\",\"orderby\":4,\"parentCode\":\"CAR_COLOR\",\"parentId\":410,\"parentName\":\"车辆颜色\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"5\",\"itemName\":\"用户管理\",\"orderby\":5,\"parentCode\":\"OPERA_TYPE\",\"parentId\":35,\"parentName\":\"操作日志类型\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"2\",\"itemName\":\"角色管理\",\"orderby\":6,\"parentCode\":\"OPERA_TYPE\",\"parentId\":36,\"parentName\":\"操作日志类型\",\"status\":1},{\"flag1\":\"1\",\"itemCode\":\"7\",\"itemName\":\"权限管理\",\"orderby\":7,\"parentCode\":\"OPERA_TYPE\",\"parentId\":37,\"parentName\":\"操作日志类型\",\"status\":1}],\"result\":1}";

    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];    private static String RSA = "RSA";


    /**
     * 接口用   获取数据  解密
     * @param key
     * @return
     */
    public  static String deCodeKey(String key){
        String content="";
        try {
            PrivateKey privateKey=loadPrivateKey(C.PRIVATE_KEY);
            byte[] decryptByte=decryptData(Base64.decode(key),privateKey);
            content=new String(decryptByte,"UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
  
    /** 
     * 随机生成RSA密钥对(默认密钥长度为1024) 
     *  
     * @return 
     */  
    public static KeyPair generateRSAKeyPair()  
    {  
        return generateRSAKeyPair(1024);  
    }  
  
    /** 
     * 随机生成RSA密钥对 
     *  
     * @param keyLength 
     *            密钥长度，范围：512〜2048<br> 
     *            一般1024 
     * @return 
     */  
    public static KeyPair generateRSAKeyPair(int keyLength)  
    {  
        try  
        {  
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);  
            kpg.initialize(keyLength);  
            return kpg.genKeyPair();  
        } catch (NoSuchAlgorithmException e)  
        {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * 用公钥加密 <br> 商品分享用
     * 每次加密的字节数，不能超过密钥的长度值减去11 
     *  
     * @param data
     *            需加密数据的byte数据 
     * @param publicKey 公钥
     * @return 加密后的byte型数据 
     */  
  /*  public static byte[] encryptData(byte[] data, PublicKey publicKey)
    {  
        try  
        {  
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // 编码前设定编码方式及密钥  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            // 传入编码数据并返回编码结果  
            return cipher.doFinal(data);  
        } catch (Exception e)  
        {  
            e.printStackTrace();  
            return null;  
        }  
    }  */

    public static String encodeKey(String value){
        PublicKey publicKey = null;
        try {
            publicKey = loadPublicKey(PUCLIC_KEY);
            //LogX.d("ycw", "-------publicKey-------> "  + publicKey);
            byte[] encryptByte=RSAUtils.encryptData(value.getBytes(),publicKey);
            return Base64.encode(encryptByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 用私钥解密 <br> 商品分享用
     * @param key
     * @return
     */
    public  static String decodceKey(String key) {
        String content="";
        try {
            PrivateKey privateKey=loadPrivateKey(PRIVATE_KEY);
            byte[] decryptByte=decryptData(Base64.decode(key),privateKey);
            content=new String(decryptByte,"UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

 /**
     * 用私钥解密 
     *  
     * @param encryptedData 
     *            经过encryptedData()加密返回的byte数据 
     * @param privateKey 
     *            私钥 
     * @return 
     */
    public static byte[] decryptNoBuffer(byte[] encryptedData, PrivateKey privateKey)
    {  
        try  
        {  
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            return cipher.doFinal(encryptedData);  
        } catch (Exception e)  
        {  
            return null;  
        }  
    }

    /**
     * 用私钥解密
     *
     * @param encryptedData
     *            经过encryptedData()加密返回的byte数据
     * @param privateKey
     *            私钥
     * @return
     */
    public  static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
          //  Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < encryptedData.length; i += 128) {
                byte[] doFinal = cipher.doFinal(subarray(encryptedData, i, i + 128));
                sb.append(new String(doFinal));
            }
            return sb.toString().getBytes();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static byte[] encryptData(byte[] data, PublicKey publicKey) {
        try {
            //Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
           // Cipher cipher = Cipher.getInstance("RSA");
           // Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);


            byte[] enBytes = null;
            for (int i = 0; i < data.length; i += 64) {
                // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
                byte[] doFinal = cipher.doFinal(subarray(data, i,i + 64));
                enBytes = addAll(enBytes, doFinal);
            }
            // 传入编码数据并返回编码结果
            return enBytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static byte[] subarray(final byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_BYTE_ARRAY;
        }

        final byte[] subarray = new byte[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }


    public static byte[] addAll(final byte[] array1, final byte... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        final byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static byte[] clone(final byte[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
    /** 
     * 通过公钥byte[](publicKey.getEncoded())将公钥还原，适用于RSA算法 
     *  
     * @param keyBytes 
     * @return 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeySpecException 
     */  
    public static PublicKey getPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException,  
            InvalidKeySpecException  
    {  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);  
        PublicKey publicKey = keyFactory.generatePublic(keySpec);  
        return publicKey;  
    }  
  
    /** 
     * 通过私钥byte[]将公钥还原，适用于RSA算法 
     *  
     * @param keyBytes 
     * @return 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeySpecException 
     */  
    public static PrivateKey getPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException,  
            InvalidKeySpecException  
    {  
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);  
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);  
        return privateKey;  
    }  
  
    /** 
     * 使用N、e值还原公钥 
     *  
     * @param modulus 
     * @param publicExponent 
     * @return 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeySpecException 
     */  
    public static PublicKey getPublicKey(String modulus, String publicExponent)  
            throws NoSuchAlgorithmException, InvalidKeySpecException  
    {  
        BigInteger bigIntModulus = new BigInteger(modulus);  
        BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);  
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);  
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);  
        PublicKey publicKey = keyFactory.generatePublic(keySpec);  
        return publicKey;  
    }  
  
    /** 
     * 使用N、d值还原私钥 
     *  
     * @param modulus 
     * @param privateExponent 
     * @return 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeySpecException 
     */  
    public static PrivateKey getPrivateKey(String modulus, String privateExponent)  
            throws NoSuchAlgorithmException, InvalidKeySpecException  
    {  
        BigInteger bigIntModulus = new BigInteger(modulus);  
        BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);  
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);  
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);  
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);  
        return privateKey;  
    }  
  
    /** 
     * 从字符串中加载公钥 
     *  
     * @param publicKeyStr 
     *            公钥数据字符串 
     * @throws Exception 
     *             加载公钥时产生的异常 
     */  
    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception  
    {  
        try  
        {  
            byte[] buffer = Base64.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e)  
        {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e)  
        {  
            throw new Exception("公钥非法");  
        } catch (NullPointerException e)  
        {  
            throw new Exception("公钥数据为空");  
        }  
    }  
  
    /** 
     * 从字符串中加载私钥<br> 
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。 
     *  
     * @param privateKeyStr 
     * @return 
     * @throws Exception 
     */  
    public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception  
    {  
        try  
        {  
            byte[] buffer = Base64.decode(privateKeyStr);
            // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException e)  
        {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e)  
        {  
            throw new Exception("私钥非法");  
        } catch (NullPointerException e)  
        {  
            throw new Exception("私钥数据为空");  
        }  
    }  
  
    /** 
     * 从文件中输入流中加载公钥 
     *  
     * @param in 
     *            公钥输入流 
     * @throws Exception 
     *             加载公钥时产生的异常 
     */  
    public static PublicKey loadPublicKey(InputStream in) throws Exception  
    {  
        try  
        {  
            return loadPublicKey(readKey(in));  
        } catch (IOException e)  
        {  
            throw new Exception("公钥数据流读取错误");  
        } catch (NullPointerException e)  
        {  
            throw new Exception("公钥输入流为空");  
        }  
    }  
  
    /** 
     * 从文件中加载私钥 
     *  
     *  keyFileName
     *            私钥文件名 
     * @return 是否成功 
     * @throws Exception 
     */  
    public static PrivateKey loadPrivateKey(InputStream in) throws Exception  
    {  
        try  
        {  
            return loadPrivateKey(readKey(in));  
        } catch (IOException e)  
        {  
            throw new Exception("私钥数据读取错误");  
        } catch (NullPointerException e)  
        {  
            throw new Exception("私钥输入流为空");  
        }  
    }  
  
    /** 
     * 读取密钥信息 
     *  
     * @param in 
     * @return 
     * @throws IOException 
     */  
    private static String readKey(InputStream in) throws IOException  
    {  
        BufferedReader br = new BufferedReader(new InputStreamReader(in));  
        String readLine = null;  
        StringBuilder sb = new StringBuilder();  
        while ((readLine = br.readLine()) != null)  
        {  
            if (readLine.charAt(0) == '-')  
            {  
                continue;  
            } else  
            {  
                sb.append(readLine);  
                sb.append('\r');  
            }  
        }  
  
        return sb.toString();  
    }  
  
    /** 
     * 打印公钥信息 
     *  
     * @param publicKey 
     */  
    public static void printPublicKeyInfo(PublicKey publicKey)  
    {  
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;  
        System.out.println("----------RSAPublicKey----------");  
        System.out.println("Modulus.length=" + rsaPublicKey.getModulus().bitLength());  
        System.out.println("Modulus=" + rsaPublicKey.getModulus().toString());  
        System.out.println("PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength());  
        System.out.println("PublicExponent=" + rsaPublicKey.getPublicExponent().toString());  
    }  
  
    public static void printPrivateKeyInfo(PrivateKey privateKey)  
    {  
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;  
        System.out.println("----------RSAPrivateKey ----------");  
        System.out.println("Modulus.length=" + rsaPrivateKey.getModulus().bitLength());  
        System.out.println("Modulus=" + rsaPrivateKey.getModulus().toString());  
        System.out.println("PrivateExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength());  
        System.out.println("PrivatecExponent=" + rsaPrivateKey.getPrivateExponent().toString());  
  
    }

    public static void main(String[] args) {
        try {
//    //    String s="BOquZ/vy/xU4Sxb7eBG2dJjYWAsr2F8N5IvX1EsDzhjh3j2VWBUt8AS+DQMgjG1rM79gRNs7STjlkPz0fN9PNcatQVo7A7VaiS5rZtSTOC4lr9sdMS98nHkCYbkF0YsTh2HrGFPWytV4PT/qUiBTXo2WlnbgCdqLqN9sDim/riM=";
//           String ss="FM5aVWvlBDjhVwgOymPuubrHl7S5BojTisi2AuHjsjFO0F8YdOsl57g9zu4E442FKgrw+mkcWYc65nGlSG7jLIvs7If9lF6909eYaEvCjLFqJbfDpRNT4b3jCKBR8iwj9O3iviqIG4wpX70oRIsoUIVZHMYEPcc6TnNDEziRosA=";
//            PublicKey publicKey = loadPublicKey(PUCLIC_KEY);
//           byte[] encryptByte=RSAUtils.encryptData("avc".getBytes(),publicKey);
//            String afterencrypt = Base64.encode(encryptByte);
//           // String afterencrypt = new String(encryptByte,"utf-8");
//            System.out.println("密文:"+afterencrypt);
//
//            PrivateKey privateKey = loadPrivateKey(PRIVATE_KEY);
//            byte[] decryptByte = decryptData(Base64.decode(ss), privateKey);
//            String decryptStr = new String(decryptByte);
//            System.out.println("解密:" + decryptStr);
            String responseBody = "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";
//            String enKey = encodeKey(responseBody);
//            System.out.println("加密："+enKey);
            String deKey = new String(Base64.decode(deCodeKey(responseBody)),"utf-8");
            System.out.println("解密："+deKey);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
}  