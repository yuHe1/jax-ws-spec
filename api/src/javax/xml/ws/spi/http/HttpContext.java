/*
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.xml.ws.spi.http;

import java.util.Set;

/**
 * HttpContext represents a mapping between the root URI path of a web
 * service to a {@link HttpHandler} which is invoked to handle requests
 * destined for that path on the associated container.
 * <p>
 * Container provides the implementation for this and it matches
 * request URIs to HttpContext objects.
 * <p>
 *
 * @author Jitendra Kotamraju
 * @since JAX-WS 2.2
 */
public abstract class HttpContext {

    protected HttpHandler handler;

    /**
     * JAX-WS runtime sets its handler during
     * {@link javax.xml.ws.Endpoint#publish(HttpContext)} to handle
     * HTTP requests for this context. Container or its extensions
     * use this handler to process the requests.
     *
     * @param handler the handler to set for this context
     */
    public void setHandler(HttpHandler handler) {
        this.handler = handler;
    }

    /**
     * Returns the path for this context. Container should give this
     * path based on how it matches request URIs to this HttpContext object.
     *
     * <p>
     * Endpoint's address for this context can be computed as follows:
     * <pre>
     *  HttpExchange exch = ...;
     *  String endpointAddress =
     *      exch.getScheme() + "://"
     *      + exch.getLocalAddress().getHostName() + ":" + exch.getLocalAddress().getPort()
     *      + exch.getContextPath()+ getPath();
     * </pre>
     * 
     * @return this context's path
     */
    public abstract String getPath();

    /**
     * Returns an attribute value for container's configuration
     * and other data that can be used by jax-ws runtime.
     *
     * @param name attribute name
     * @return attribute value
     */
    public abstract Object getAttribute(String name);

    /**
     * Returns all attribute names for container's configuration
     * and other data that can be used by jax-ws runtime.
     *
     * @return set of all attribute names
     */
    public abstract Set<String> getAttributeNames();

}