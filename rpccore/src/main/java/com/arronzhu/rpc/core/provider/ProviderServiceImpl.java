package com.arronzhu.rpc.core.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arronzhu.rpc.core.Constant;
import com.arronzhu.rpc.servicecenter.entity.ServiceInfoDO;
import com.arronzhu.rpc.servicecenter.service.ServicesSingle;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

/**
 * @author arronzhu
 * @date 2018/10/23
 * @description
 */
@Slf4j
@Component
public class ProviderServiceImpl implements IProviderService {
    @Override
    public boolean startListen() {
        new Thread(new ServerThread()).start();
        return true;
    }

    @Override
    public boolean serviceregistry(ServiceInfoDO serviceInfo) {
        String url = "http://" + Constant.SERVICEACCESSCENTER_IP + ":" + Constant.SERVICEACCESSCENTER_PORT
                + Constant.SERVICECONTEXT + Constant.SERVICEREGISTRY;

        JSONObject json = new JSONObject();
        json.put("interfaceName", serviceInfo.getInterfaceName());
        json.put("version", serviceInfo.getVersion());
        json.put("implClassName", serviceInfo.getImplClassName());
        json.put("ip", serviceInfo.getIp());

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse = null;
        try {
            StringEntity entity = new StringEntity(json.toString());
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            System.out.println("executing request " + httpPost.getURI());
            httpResponse = httpClient.execute(httpPost);
            Boolean res = JSON.parseObject(httpResponse.getEntity().getContent(), Boolean.class);
            if (res == true) {
                System.out.println("成功注册服务:[" + serviceInfo.getInterfaceName() + "]");
            }


            //step2. 存入到缓存
            if (ServicesSingle.getInstance().getServices().containsKey(serviceInfo.getInterfaceName() + "_" + serviceInfo.getVersion())){
                ServicesSingle.getInstance().getServices().get(serviceInfo.getInterfaceName() + "_" + serviceInfo.getVersion()).getIps().add(serviceInfo.getIp());
            }
            else {
                ServicesSingle.getInstance().getServices().put(serviceInfo.getInterfaceName() + "_" + serviceInfo.getVersion(), serviceInfo);
            }


            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
