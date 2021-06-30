package com.zeta.embeddedatropos.sample.testUtils;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import in.zeta.commons.zms.service.ZMSDelegatingWrapper;
import in.zeta.commons.zms.service.ZetaHostMessagingService;
import in.zeta.oms.atropos.model.Subscription;
import in.zeta.oms.atropos.model.SubscriptionFilter;
import in.zeta.oms.atropos.model.SubscriptionFilterValue;
import in.zeta.oms.atropos.model.SubscriptionsFilterType;
import olympus.common.JID;
import olympus.pubsub.model.OperationType;
import olympus.pubsub.model.PubSubEvent;
import olympus.pubsub.model.TopicScope;
import olympus.spartan.ImmutableMessage;
import olympus.spartan.NodeDrainHandler;
import olympus.spartan.RegistrationHandler;
import olympus.spartan.lookup.BucketDrainHandler;
import org.apache.http.NameValuePair;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static com.zeta.embeddedatropos.sample.testUtils.Constants.ATROPOS_PUBUSB_SANITY_OBJECT_TYPE;
import static com.zeta.embeddedatropos.sample.testUtils.Constants.ATROPOS_PUBUSB_SANITY_STATE;
import static com.zeta.embeddedatropos.sample.testUtils.Constants.DEFAULT_OPS_JID_RANGE;
import static com.zeta.embeddedatropos.sample.testUtils.Constants.TENANT_ID;


public class ObjectProvider {

    public static PubSubEvent.Builder getOlympusPubSubEventBuilder() throws Throwable {
        PubSubEvent.Builder eventBuilder;
        try {
            ZMSDelegatingWrapper zhms = getZMS(getRandomJID());
            eventBuilder = new PubSubEvent.Builder()
                    .tenant(TENANT_ID.toString())
                    .topicScope(TopicScope.TENANT)
                    .objectType(ATROPOS_PUBUSB_SANITY_OBJECT_TYPE)
                    .objectID("objectJID")
                    .operationType(OperationType.CREATED)
                    .sourceAttributes(new NameValuePair[0])
                    .tags(ImmutableList.of())
                    .stateMachineState(ATROPOS_PUBUSB_SANITY_STATE)
                    .origin(zhms)
                    .publisher(zhms)
                    .data(new JsonObject());
            return eventBuilder;
        } catch (Throwable e) {
            throw e.getCause();
        }
    }

    private static ZMSDelegatingWrapper getZMS(JID publisherJID) {
        return new ZMSDelegatingWrapper() {
            @Override
            public CompletionStage<Void> registerOMSSubscription(String s, String s1, String s2, String s3, Map<String, String> map, List<Map<SubscriptionsFilterType, List<SubscriptionFilterValue>>> list) {
                return null;
            }

            @Override
            public CompletionStage<Void> registerWebhookSubscription(String s, String s1, String s2, String s3, String s4, Map<String, String> map, List<Map<SubscriptionsFilterType, List<SubscriptionFilterValue>>> list) {
                return null;
            }

            @Override
            public CompletionStage<Void> unregisterSubscription(Optional<String> optional, String s, String s1) {
                return null;
            }

            @Override
            public CompletionStage<Void> pauseSubscription(Optional<String> optional, String s, String s1, String s2) {
                return null;
            }

            @Override
            public CompletionStage<Void> resumeSubscription(Optional<String> optional, String s, String s1, String s2) {
                return null;
            }

            @Override
            public CompletionStage<Map<String, Subscription>> getSubscriptionByID(String s) {
                return null;
            }

            @Override
            public CompletionStage<Map<String, List<Subscription>>> getSubscriptions(SubscriptionFilter subscriptionFilter, String s, Integer integer, Integer integer1) {
                return null;
            }

            @Override
            public ZetaHostMessagingService getDelegated() {
                return null;
            }

            @Override
            public void register(ImmutableMessage.Listener messageListener, NodeDrainHandler nodeDrainHandler, BucketDrainHandler bucketDrainHandler, RegistrationHandler registrationHandler) {
            }

            @Override
            public String getServiceName() {
                return publisherJID.getServiceName();
            }

            @Override
            public JID getInstanceJID() {
                return publisherJID;
            }
        };
    }

    public static JID getRandomJID() {
        int nodeId = (int) ((Math.random() * DEFAULT_OPS_JID_RANGE) + 1);
        return new JID("services.olympus", "atropos", String.valueOf(nodeId));
    }
}
