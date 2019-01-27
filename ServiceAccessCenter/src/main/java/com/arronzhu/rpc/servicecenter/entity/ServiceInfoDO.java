package com.arronzhu.rpc.servicecenter.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author arronzhu
 * @date 2018/10/22
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceInfoDO {
    private String interfaceName;
    private String version;
    private String implClassName;
    private Set<String> ips = new HashSet<>();
    private String ip;

    public boolean valid() {
        if (Strings.isNullOrEmpty(interfaceName)
                || Strings.isNullOrEmpty(version)
                || Strings.isNullOrEmpty(implClassName)
                || Strings.isNullOrEmpty(ip)) {
            return false;
        }
        return true;
    }
}
