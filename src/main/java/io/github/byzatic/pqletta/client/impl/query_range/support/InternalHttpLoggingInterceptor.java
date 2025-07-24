package io.github.byzatic.pqletta.client.impl.query_range.support;

/*
 *    Copyright 2021 Konstantin Fedorov
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */


import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Generic description
 *
 * @author Fedorov Konstantin (k.fedorov@axitech.ru)
 * @version 0.1.0 [MAJOR.MINOR.PATCH]
 * Created on 27.01.2021.
 */
public class InternalHttpLoggingInterceptor implements Interceptor {
    private final static Logger logger = LoggerFactory.getLogger(InternalHttpLoggingInterceptor.class);

    private boolean logResponseHeaders;
    private boolean logResponseBody;

    public InternalHttpLoggingInterceptor() {
        logResponseHeaders = true;
        logResponseBody = true;
    }

    public InternalHttpLoggingInterceptor(boolean logResponseHeaders, boolean logResponseBody) {
        this.logResponseHeaders = logResponseHeaders;
        this.logResponseBody = logResponseBody;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(chain.request());

        logger.debug("[HTTP] [REQUEST] {}", request);
        if (logResponseBody) {
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                logger.debug("[HTTP] [REQUEST] [BODY] [SIZE= {}] {}", buffer.size(), buffer.readUtf8());
            } else {
                logger.debug("[HTTP] [REQUEST] [BODY] [SIZE= NULL]");
            }
        }
        logger.debug("[HTTP] [RESPONSE] {}", response);
        if (logResponseHeaders)
            logger.debug("[HTTP] [RESPONSE] [HEADERS] {}", response.headers());
        if (logResponseBody) {
            // because original query_range body is read-once buffer
            // we need to fill it with data first, then log cloned copy
            // leaving original buffer untouched to allow chain use
            ResponseBody responseBody = response.body();
            if (responseBody != null) {

                // TODO support for gzipped body content

                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.getBuffer();
                logger.debug("[HTTP] [RESPONSE] [BODY] [SIZE= {}] {}",
                        buffer.size(),
                        buffer.clone().readUtf8()
                );
            } else {
                logger.debug("[HTTP] [RESPONSE] [BODY] [SIZE= NULL]");
            }
        }

        return response;
    }
}

