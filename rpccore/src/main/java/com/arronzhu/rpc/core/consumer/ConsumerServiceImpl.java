package com.arronzhu.rpc.core.consumer;

import com.alibaba.fastjson.JSON;
import com.arronzhu.rpc.core.Constant;
import com.arronzhu.rpc.core.entity.RequestDO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author arronzhu
 * @date 2018/10/22
 * @description
 */
@Component
public class ConsumerServiceImpl implements IConsumerService {
    @Override
    public Set<String> getServiceIPsByID(String serviceID) {
        //调用服务注册查找中心的服务,获取ip列表
        Set<String> ips = new HashSet<>();
        String url = "http://" + Constant.SERVICEACCESSCENTER_IP + ":" + Constant.SERVICEACCESSCENTER_PORT
        + Constant.SERVICECONTEXT + Constant.QUERYSERVICEIPSBYID + "?serviceID=" + serviceID;

        HttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            Set<String> res = JSON.parseObject(httpEntity.getContent(), Set.class);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    @Override
    public String getIP(String serviceID, String methodName, List<Object> params, Set<String> ips) {
        String[] temparr = new String[ips.size()];
        ips.toArray(temparr);
        return temparr[0];
    }

    @Override
    public RequestDO getRequestDO(String interfaceName, String version, String methodName, List<Object> params) {
        RequestDO requestDO = new RequestDO();
        requestDO.setInterfaceName(interfaceName);
        requestDO.setMethodName(methodName);
        requestDO.setParams(params);
        requestDO.setVersion(version);
        return requestDO;
    }

    @Override
    public Object sendData(String ip, RequestDO requestDO) throws IOException, ClassNotFoundException {
        ObjectOutputStream objectOutputStream = null;
        Socket socket = null;
        ObjectInputStream objectInputStream = null;
        try {
            socket = new Socket(ip, Constant.PORT);//向远程服务端建立连接
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());//获得输出流
            objectOutputStream.writeObject(requestDO);//发送序列化结果
            objectOutputStream.flush();
            socket.shutdownOutput();
            //等待响应
            objectInputStream = new ObjectInputStream(socket.getInputStream());//获得输入流
            Object result = objectInputStream.readObject();//序列化为Object对象
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
            return result;
        }catch (Exception e){
            throw e;
        }finally {
            try {
                if(objectInputStream != null)objectInputStream.close();
                if (objectOutputStream != null)objectOutputStream.close();
                if (socket !=null )socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }

        }
    }

}
