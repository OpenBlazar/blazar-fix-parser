/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blazarquant.bfp.core.mail.connection;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

/**
 * @author Wojciech Zankowski
 */
public class MailSessionImpl implements MailSession {

    private final Session session;

    public MailSessionImpl(Session session) {
        this.session = session;
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public Transport getTransport(String protocol) throws NoSuchProviderException {
        return session.getTransport(protocol);
    }

}
