package com.arronzhu.rpc.core.provider;

import com.arronzhu.rpc.core.entity.RequestDO;
import com.arronzhu.rpc.servicecenter.entity.ServiceInfoDO;
import com.arronzhu.rpc.servicecenter.service.ServicesSingle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author arronzhu
 * @date 2018/10/23
 * @description
 */
public class ServerProcessThread implements Runnable {
    private Socket socket;
    public ServerProcessThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {

        //获取客户端的数据,并写回
        //等待响应
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {

            //step1. 将请求数据反序列化为request对象
            objectInputStream = new ObjectInputStream(socket.getInputStream());//获得输入流
            RequestDO requestDO = (RequestDO) objectInputStream.readObject();//序列化为Object对象

            //step2. 获取服务接口实现类的信息
            ServiceInfoDO serviceInfoDO = ServicesSingle.getInstance().getServices().get(requestDO.getInterfaceName() + "_" + requestDO.getVersion());

            //step3.利用反射创建对象,调用方法,得到结果
            Class clz = Class.forName(serviceInfoDO.getImplClassName());
            Method methodethod = null;
            Object result = null;
            if (requestDO.getParams() != null && requestDO.getParams().size() > 0){
                Class []classes = new Class[requestDO.getParams().size()];
                Object []obj = requestDO.getParams().toArray();
                int i = 0;
                for (Object object:requestDO.getParams()){
                    if(object instanceof Integer){
                        classes[i] = Integer.TYPE;
                    }else if(object instanceof Byte){
                        classes[i] = Byte.TYPE;
                    }else if(object instanceof Short){
                        classes[i] = Short.TYPE;
                    }else if(object instanceof Float){
                        classes[i] = Float.TYPE;
                    }else if(object instanceof Double){
                        classes[i] = Double.TYPE;
                    }else if(object instanceof Character){
                        classes[i] = Character.TYPE;
                    }else if(object instanceof Long){
                        classes[i] = Long.TYPE;
                    }else if(object instanceof Boolean){
                        classes[i] = Boolean.TYPE;
                    }else {
                        classes[i] = object.getClass();
                    }

                    i++;
                }
                methodethod = clz.getDeclaredMethod(requestDO.getMethodName(),classes);
                result = methodethod.invoke(clz.newInstance(),obj);
            }else {
                methodethod = clz.getDeclaredMethod(requestDO.getMethodName());
                result = methodethod.invoke(clz.newInstance());
            }

            //step4.将结果序列化,写回调用端

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());//获得输出流
            objectOutputStream.writeObject(result);//发送序列化结果
            objectOutputStream.flush();
            socket.shutdownOutput();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(objectInputStream != null)objectInputStream.close();
                if (objectOutputStream != null)objectOutputStream.close();
                if (socket !=null )socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
