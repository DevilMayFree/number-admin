package com.freeying.framework.data.generator;

@SuppressWarnings({"squid:S2187","squid:S2925"})
public class IdentifierUtilTest {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(500,5);
            System.out.println(IdentifierUtil.genId());
        }
    }
}
