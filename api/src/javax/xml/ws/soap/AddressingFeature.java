/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2005-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package javax.xml.ws.soap;

import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

/**
 * AddressingFeature represents the use of WS-Addressing with either
 * the SOAP 1.1/HTTP or SOAP 1.2/HTTP binding. Using this feature
 * with any other binding is undefined.
 * <p>
 * This feature can be used during the creation of SEI proxy, and
 * {@link Dispatch} instances on the client side and {@link Endpoint}
 * instances on the server side. This feature cannot be used for {@link Service}
 * instance creation on the client side.
 * <p>
 * The following describes the effects of this feature with respect
 * to be enabled or disabled:
 * <ul>
 *  <li> ENABLED: In this Mode, WS-Addressing will be enabled. It means
 *       the endpoint supports WS-Addressing but does not require its use.
 *       A sender could send messages with WS-Addressing headers or without
 *       WS-Addressing headers. But a receiver MUST consume both types of
 *       messages.
 *  <li> DISABLED: In this Mode, WS-Addressing will be disabled.
 *       At runtime, WS-Addressing headers MUST NOT be used by a sender or
 *       receiver.
 * </ul>
 * <p>
 * If the feature is enabled, the <code>required</code> property determines
 * whether the endpoint requires WS-Addressing. If it is set true,
 * WS-Addressing headers MUST be present on incoming and outgoing messages.
 * By default the <code>required</code> property is <code>false</code>.
 *
 * <p>
 * If the web service developer has not explicitly enabled this feature,
 * WSDL's wsam:Addressing policy assertion is used to find
 * the use of WS-Addressing. By using the feature explicitly, an application
 * overrides WSDL's indication of the use of WS-Addressing. In some cases,
 * this is really required. For example, if an application has implemented
 * WS-Addressing itself, it can use this feature to disable addressing. That
 * means a JAX-WS implementation doesn't consume or produce WS-Addressing
 * headers.
 *
 * <p>
 * If addressing is enabled, a corresponding wsam:Addressing policy assertion
 * must be generated in the WSDL as per
 * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicyassertions">
 * 3.1 WS-Policy Assertions</a>
 *
 * <p>
 * <b>Example 1: </b>Possible Policy Assertion in the generated WSDL for
 * <code>&#64;Addressing</code>
 * <pre>
 *   &lt;wsam:Addressing wsp:Optional="true">
 *     &lt;wsp:Policy/>
 *   &lt;/wsam:Addressing>
 * </pre>
 *
 * <p>
 * <b>Example 2: </b>Possible Policy Assertion in the generated WSDL for
 * <code>&#64;Addressing(required=true)</code>
 * <pre>
 *   &lt;wsam:Addressing>
 *     &lt;wsp:Policy/>
 *   &lt;/wsam:Addressing>
 * </pre>
 *
 * <p>
 * <b>Example 3: </b>Possible Policy Assertion in the generated WSDL for
 * <code>&#64;Addressing(required=true, responses=Responses.ANONYMOUS)</code>
 * <pre>
 *   &lt;wsam:Addressing>
 *      &lt;wsp:Policy>
 *        &lt;wsam:AnonymousResponses/>
 *      &lt;/wsp:Policy>
 *   &lt;/wsam:Addressing>
 * </pre>
 *
 * <p>
 * See <a href="http://www.w3.org/TR/2006/REC-ws-addr-core-20060509/">
 * Web Services Addressing - Core</a>,
 * <a href="http://www.w3.org/TR/2006/REC-ws-addr-soap-20060509/">
 * Web Services Addressing 1.0 - SOAP Binding</a>,
 * and <a href="http://www.w3.org/TR/ws-addr-metadata/">
 * Web Services Addressing 1.0 - Metadata</a>
 * for more information on WS-Addressing.
 * 
 * @see Addressing
 * @since JAX-WS 2.1
 */

public final class AddressingFeature extends WebServiceFeature {
    /** 
     * Constant value identifying the AddressingFeature
     */
    public static final String ID = "http://www.w3.org/2005/08/addressing/module";
  
    /**
     * If addressing is enabled, this property determines whether the endpoint
     * requires WS-Addressing. If required is true, WS-Addressing headers MUST
     * be present on incoming and outgoing messages.
     */
    // didn't make it as private final for compatibility
    protected /* final */ boolean required;

    /**
     * If addressing is enabled, this property determines if endpoint requires
     * the use of only anonymous responses, or only non-anonymous responses, or all.
     *
     * <p>
     * {@link Responses#ALL} supports all response types and this is the default
     * value.
     *
     * <p>
     * {@link Responses#ANONYMOUS} requires the use of only anonymous
     * responses. It will result into wsam:AnonymousResponses nested assertion
     * as specified in
     * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicyanonresponses">
     * 3.1.2 AnonymousResponses Assertion</a> in the generated WSDL.
     *
     * <p>
     * {@link Responses#NON_ANONYMOUS} requires the use of only non-anonymous
     * responses. It will result into
     * wsam:NonAnonymousResponses nested assertion as specified in
     * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicynonanonresponses">
     * 3.1.3 NonAnonymousResponses Assertion</a> in the generated WSDL.
     *
     * @since JAX-WS 2.2
     */
    public enum Responses {
        /**
         * Specifies the use of only anonymous
         * responses. It will result into wsam:AnonymousResponses nested assertion
         * as specified in
         * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicyanonresponses">
         * 3.1.2 AnonymousResponses Assertion</a> in the generated WSDL.
         */
        ANONYMOUS,

        /**
         * Specifies the use of only non-anonymous
         * responses. It will result into
         * wsam:NonAnonymousResponses nested assertion as specified in
         * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicynonanonresponses">
         * 3.1.3 NonAnonymousResponses Assertion</a> in the generated WSDL.
         */
        NON_ANONYMOUS, 

        /**
         * Supports all response types and this is the default
         */
        ALL
    }

    private final Responses responses;

    /**
     * Creates and configures an <code>AddressingFeature</code> with the
     * use of addressing requirements. The created feature enables
     * ws-addressing i.e. supports ws-addressing but doesn't require
     * its use. It is also configured to accept all the response types.
     */
    public AddressingFeature() {
        this(true, false, Responses.ALL);
    }
    
    /**
     * Creates and configures an <code>AddressingFeature</code> with the
     * use of addressing requirements. If <code>enabled</code> is true,
     * it enables ws-addressing i.e. supports ws-addressing but doesn't
     * require its use. It also configures to accept all the response types.
     * 
     * @param enabled true enables ws-addressing i.e.ws-addressing
     * is supported but doesn't require its use
     */
    public AddressingFeature(boolean enabled) {
        this(enabled, false, Responses.ALL);
    }

    /** 
     * Creates and configures an <code>AddressingFeature</code> with the
     * use of addressing requirements. If <code>enabled</code> and
     * <code>required</code> are true, it enables ws-addressing and
     * requires its use. It also configures to accept all the response types.
     *
     * @param enabled true enables ws-addressing i.e.ws-addressing
     * is supported but doesn't require its use
     * @param required true means requires the use of ws-addressing .
     */
    public AddressingFeature(boolean enabled, boolean required) {
        this(enabled, required, Responses.ALL);
    }

    /**
     * Creates and configures an <code>AddressingFeature</code> with the
     * use of addressing requirements. If <code>enabled</code> and
     * <code>required</code> are true, it enables ws-addressing and
     * requires its use. Also, the response types can be configured using
     * <code>responses</code> parameter.
     *
     * @param enabled true enables ws-addressing i.e.ws-addressing
     * is supported but doesn't require its use
     * @param required true means requires the use of ws-addressing .
     * @param responses specifies what type of responses are required
     *
     * @since JAX-WS 2.2
     */
    public AddressingFeature(boolean enabled, boolean required, Responses responses) {
        this.enabled = enabled;
        this.required = required;
        this.responses = responses;
    }
    
    /**
     * {@inheritDoc}
     */
    public String getID() {
        return ID;
    }

    /**
     * If addressing is enabled, this property determines whether the endpoint
     * requires WS-Addressing. If required is true, WS-Addressing headers MUST
     * be present on incoming and outgoing messages.
     *
     * @return the current required value
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * If addressing is enabled, this property determines whether endpoint
     * requires the use of anonymous responses, or non-anonymous responses,
     * or all responses.
     *
     * <p>
     * @return {@link Responses#ALL} when endpoint supports all types of
     * responses,
     *         {@link Responses#ANONYMOUS} when endpoint requires the use of
     * only anonymous responses,
     *         {@link Responses#NON_ANONYMOUS} when endpoint requires the use
     * of only non-anonymous responses
     *
     * @since JAX-WS 2.2
     */
    public Responses getResponses() {
        return responses;
    }

}
