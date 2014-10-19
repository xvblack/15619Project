/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package ProjectServer;

import Querier.AbstractQ2Querier;
import Querier.DefaultQ2Querier;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Deque;
import java.util.Map;

import static io.undertow.Handlers.path;


/**
 * @author Stuart Douglas
 */
public class P1Server {

    static final BigInteger X = new BigInteger("6876766832351765396496377534476050002970857483815262918450355869850085167053394672634315391224052153");
    static final String TeamName = "nimbus";
    static final String[] ids = {"nimbus", "sharkuras", "foxstep"};
    static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss\n");
    static final AbstractQ2Querier querier = new DefaultQ2Querier();

    public static void main(final String[] args) {
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(path().addPrefixPath("/q1", new HttpHandler() {
                    @Override
                    public void handleRequest(final HttpServerExchange exchange) throws Exception {
                        System.out.println("OK");
                        BigInteger key = new BigInteger(exchange.getQueryParameters().get("key").getFirst());
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                        exchange.getResponseSender().send((key.divide(X)).toString() + "\n" +
                                TeamName + "," + ids[0] + "," + ids[1] + "," + ids[2] + "\n" +
                                formatter.format(new Date())
                        );
                    }
                }).addPrefixPath("/q2", new HttpHandler() {
                    @Override
                    public void handleRequest(HttpServerExchange exchange) throws Exception {
                        System.out.println("OK");
                        Map<String,Deque<String>> parameters = exchange.getQueryParameters();
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                        StringBuilder builder = new StringBuilder();
                        builder.append(TeamName);
                        for (String id : ids) {
                            builder.append(",").append(id);
                        }
                        builder.append("\n");
                        String user_id = parameters.get("userid").getFirst();
                        String tweet_time = parameters.get("tweet_time").getFirst();
                        String[] results = querier.query(user_id, tweet_time);
                        for (String s : results){
                            builder.append(s).append("\n");
                        }
                        builder.append("\n");
                        exchange.getResponseSender().send(builder.toString());
                    }
                })
                ).build();
        System.out.println("Working at localhost:8080");
        server.start();
    }

}
