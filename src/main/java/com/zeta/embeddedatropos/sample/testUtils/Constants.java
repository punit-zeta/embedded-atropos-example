package com.zeta.embeddedatropos.sample.testUtils;

public class Constants {
    public static final Long TENANT_ID = 123L;
    public static final String SUBSCRIPTION_ID = "subscriptionId";
    public static final String TOPIC = "_"+TENANT_ID+"_xyz";
    public static final String ATROPOS_PUBUSB_SANITY_STATE = "INITIATED";
    public static final Long SYSTEM_TENANT = 0L;
    public static final String ATROPOS_PUBUSB_SANITY_OBJECT_TYPE = "PubsubSanity";
    public static final int DEFAULT_OPS_JID_RANGE = 1000;

    public static String SAMPLE_WEBHOOK_TRANSFORMER = "function transform(payload, sender) { return JSON.stringify(payload);}";
}
