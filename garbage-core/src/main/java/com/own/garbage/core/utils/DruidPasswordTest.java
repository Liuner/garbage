package com.own.garbage.core.utils;


import com.alibaba.druid.filter.config.ConfigTools;

public class DruidPasswordTest {

    public static void main(String[] args) throws Exception {
        String names[] = {"PayUsr01"};
        ConfigTools.main(names);

        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQASwAwSAJBALQ54C4F5zS/DusZbRqNxQoJbOUk8TFBQBlR0DNmLSIsR0p4/w0xetMXo//sOEekVjTkva0vPfkF9uvpRqW4h8CAwEAAQ==";
        String password = "UylzfISZmpLAEfxqnGqaEyryKq8FI/NDjbNg+QDOyD/8q5L7lOm8rfOtGlRDe5oggdDpTSdNvrnq4k3ZNjkg==";
        System.out.println(ConfigTools.decrypt(publicKey, password));
    }
}