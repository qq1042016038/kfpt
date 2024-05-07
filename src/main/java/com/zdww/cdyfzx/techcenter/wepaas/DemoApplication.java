package com.zdww.cdyfzx.techcenter.wepaas;

import com.zdww.cdyfzx.techcenter.wepaas.dto.ApplicationCreateCmd;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        com.zdww.cdyfzx.techcenter.wepaas.dto.ApplicationCreateCmd s = new ApplicationCreateCmd();
        System.out.println(s);

//        WepaasConfig wepaasConfig = context.getBean(WepaasConfig.class);
//        System.out.println(wepaasConfig.getWepaasUrl());
//        ResClient bean = context.getBean(ResClient.class);
//        MonitoringGetQry monitoringGetQry = new MonitoringGetQry();
//        monitoringGetQry.setEnvAppLyId(80L);
//        monitoringGetQry.setMonitoringType(MonitoringType.CPU);
//        SingleResponse<MonitoringDTO> monitoringDTOSingleResponse = bean.resMonitoring(monitoringGetQry);
//        System.out.println(monitoringDTOSingleResponse);

//        ApplyCreateCmd applyCreateCmd = new ApplyCreateCmd();
//        applyCreateCmd.setEnvAppName("welink");
//        applyCreateCmd.setNamespace("demo");
//        applyCreateCmd.setType(ApplyType.APPLICATION);
//        applyCreateCmd.setServiceName("welink-1-10");
//        applyCreateCmd.setEnvId(2L);
//
//        //申请资源
//        SingleResponse<ApplyResultDTO> add = bean.resApply(applyCreateCmd);
//        System.out.println(add);
//
//
//
//
//        //发布应用
//        ApplicationCreateCmd applicationCreateCmd = new ApplicationCreateCmd();
//        applicationCreateCmd.setEnvAppId(58L);
//        applicationCreateCmd.setImageUrl("harbor.zdwelink.com/dev-repository/welink:460");
//        Map<String,String> env =new HashMap<>();
//        env.put("TIMESTAMP","1672799882253");
//        env.put("ACTIVE_PROFILE","dev");
//        env.put("SW_AGENT_NAME","welink-1-10");
//        env.put("TEST","TEST");
//        applicationCreateCmd.setEnvMap(env);
//        applicationCreateCmd.setVolumeMounts(Arrays.asList("/logs/"));
////        applicationCreateCmd.setMiddlewareType(Arrays.asList(MiddlewareType.SKYWALKING));
//        System.out.println(bean.resApplyDeploy(applicationCreateCmd));
//
////        System.out.println(bean.resDelete(58L));
    }

}
