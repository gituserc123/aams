/**
 * 
 */
package com.aier.cloud.ui.template.mail;

import com.aier.cloud.basic.api.response.domain.sys.Staff;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author rain_deng
 *
 */
public class Message {
    
    private String hospname;
    
    private List<Staff> list = Lists.newArrayList();

    public String getHospname() {
        return hospname;
    }

    public void setHospname(String hospname) {
        this.hospname = hospname;
    }

    public List<Staff> getList() {
        return list;
    }

    public void setList(List<Staff> list) {
        this.list = list;
    }
    
    
}
