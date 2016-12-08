package com.tt;

import com.tt.aspect.RoleAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by tt on 2016/11/24.
 */
@Configuration
@EnableAspectJAutoProxy
//@ComponentScan
public class AspectjConfig {
    @Bean
    public RoleAspect roleAspect(){
        return new RoleAspect();
    }
}
