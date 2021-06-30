package com.zeta.embeddedatropos.sample.controller;

import in.zeta.oms.embeddedatropos.EmbeddedAtropos;
import olympus.pubsub.model.PubSubEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;

import static com.zeta.embeddedatropos.sample.testUtils.Constants.SAMPLE_WEBHOOK_TRANSFORMER;
import static com.zeta.embeddedatropos.sample.testUtils.Constants.SUBSCRIPTION_ID;
import static com.zeta.embeddedatropos.sample.testUtils.Constants.TENANT_ID;
import static com.zeta.embeddedatropos.sample.testUtils.ObjectProvider.getOlympusPubSubEventBuilder;

@RestController
@RequestMapping(value = "/demo/1.0")
public class Controller {


    private EmbeddedAtropos embeddedAtropos;
    private PubSubEvent.Builder builder;

    public Controller() throws Throwable {
        embeddedAtropos = new EmbeddedAtropos();
        builder = getOlympusPubSubEventBuilder();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerSubscription() {
        try {
            String topic = builder.build().getTopic();
            embeddedAtropos.registerWebhookSubscriptionWithWebhookUrl(
                    TENANT_ID,
                    SUBSCRIPTION_ID,
                    topic,
                    "*",
                    SAMPLE_WEBHOOK_TRANSFORMER,
                    "subscriber",
                    Collections.emptyMap(),
                    Collections.emptyList(),
                    "http://localhost:8080/demo/1.0/callback").toCompletableFuture().join();
            return ResponseEntity.ok("SuccessFully Registered");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/publish")
    public ResponseEntity<?> publishEvent() {
        try {
            embeddedAtropos.publishEvent(builder).toCompletableFuture().join();
            return ResponseEntity.ok("Event Published");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/events")
    public ResponseEntity<?> getEventsList() {
        try {
            Set<String> topics = embeddedAtropos.getTopics();
            return ResponseEntity.ok(topics);
        } catch (Exception e) {
            return ResponseEntity.accepted().body(e.getMessage());
        }
    }

    @PostMapping("/callback")
    public ResponseEntity<?> eventDispatchCallback(@RequestBody String payload) {
        return ResponseEntity.ok("URL hit");
    }

    @PostMapping("/unregister")
    public ResponseEntity<?> unregister() {
        try {
            embeddedAtropos.unregisterSubscriptionIfRequired(TENANT_ID, SUBSCRIPTION_ID)
                    .toCompletableFuture().join();
            return ResponseEntity.ok("SuccessFully Unregistered");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 1. Use different topic for each scenario
     * 2. Use same event data for all scenario
     *  1. id - can be uuid
     *  2. type - scenarioOne, scenarioTwo etc
     */
}