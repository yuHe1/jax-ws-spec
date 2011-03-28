/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2005-2011 Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.ws;

import javax.xml.ws.soap.Addressing;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The <code>WebServiceRef</code> annotation is used to
 * define a reference to a web service and
 * (optionally) an injection target for it.
 * It can be used to inject both service and proxy
 * instances. These injected references are not thread safe.
 * If the references are accessed by multiple threads,
 * usual synchronization techinques can be used to
 * support multiple threads.
 *
 * <p>
 * Web service references are resources in the Java EE 5 sense.
 * The annotations (for example, {@link Addressing}) annotated with
 * meta-annotation {@link WebServiceFeatureAnnotation}
 * can be used in conjunction with <code>WebServiceRef</code>.
 * The created reference MUST be configured with annotation's web service
 * feature.
 *
 * <p>
 * For example, in the code below, the injected 
 * <code>StockQuoteProvider</code> proxy MUST
 * have WS-Addressing enabled as specifed by the
 * {@link Addressing}
 * annotation.
 *
 * <pre><code>
 *    public class MyClient {
 *       &#64;Addressing
 *       &#64;WebServiceRef(StockQuoteService.class)
 *       private StockQuoteProvider stockQuoteProvider;
 *       ...
 *    }
 * </code></pre>
 *
 * <p>
 * If a JAX-WS implementation encounters an unsupported or unrecognized
 * annotation annotated with the <code>WebServiceFeatureAnnotation</code>
 * that is specified with <code>WebServiceRef</code>, an ERROR MUST be given.
 * 
 * @see javax.annotation.Resource
 * @see WebServiceFeatureAnnotation
 *
 * @since JAX-WS 2.0
 *
**/

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebServiceRef {
    /**
     * The JNDI name of the resource.  For field annotations,
     * the default is the field name.  For method annotations,
     * the default is the JavaBeans property name corresponding
     * to the method.  For class annotations, there is no default
     * and this MUST be specified.
     *
     * The JNDI name can be absolute(with any logical namespace) or relative
     * to JNDI <code>java:comp/env</code> namespace.
     */
    String name() default "";

    /**
     * The Java type of the resource.  For field annotations,
     * the default is the type of the field.  For method annotations,
     * the default is the type of the JavaBeans property.
     * For class annotations, there is no default and this MUST be
     * specified.
     */
    Class<?> type() default Object.class;

    /**
     * A product specific name that this resource should be mapped to.
     * The name of this resource, as defined by the <code>name</code>
     * element or defaulted, is a name that is local to the application
     * component using the resource.  (When a relative JNDI name
     * is specified, then it's a name in the JNDI
     * <code>java:comp/env</code> namespace.)  Many application servers
     * provide a way to map these local names to names of resources
     * known to the application server.  This mapped name is often a
     * <i>global</i> JNDI name, but may be a name of any form.
     * <p>
     * Application servers are not required to support any particular
     * form or type of mapped name, nor the ability to use mapped names.
     * The mapped name is product-dependent and often installation-dependent.
     * No use of a mapped name is portable.
     */
    String mappedName() default "";

    /**
     * The service class, alwiays a type extending
     * <code>javax.xml.ws.Service</code>. This element MUST be specified
     * whenever the type of the reference is a service endpoint interface.
     */
    // 2.1 has Class value() default Object.class;
    // Fixing this raw Class type correctly in 2.2 API. This shouldn't cause
    // any compatibility issues for applications.
    Class<? extends Service> value() default Service.class;

    /**
     * A URL pointing to the WSDL document for the web service.
     * If not specified, the WSDL location specified by annotations
     * on the resource type is used instead.
     */
    String wsdlLocation() default "";

    /**
     * A portable JNDI lookup name that resolves to the target
     * web service reference.
     *
     * @since JAX-WS 2.2
     */
    String lookup() default "";

}
