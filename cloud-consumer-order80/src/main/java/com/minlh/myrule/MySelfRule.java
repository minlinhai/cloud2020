package com.minlh.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class MySelfRule {
    @Bean
    public IRule myRule() {
        //自定义规则为随机
        return new RandomRule();
    }
}
