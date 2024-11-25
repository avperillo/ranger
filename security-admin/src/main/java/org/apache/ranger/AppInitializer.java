/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.ranger;

//import jakarta.servlet.Filter;
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRegistration;
//import org.glassfish.jersey.servlet.ServletContainer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.ContextLoaderListener;
//import org.springframework.web.context.request.RequestContextListener;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
////import org.springframework.web.context.support.XmlWebApplicationContext;
//import org.springframework.web.filter.DelegatingFilterProxy;

public class AppInitializer {// implements WebApplicationInitializer {

//    private static final Logger logger = LoggerFactory.getLogger(AppInitializer.class);
//
//
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        logger.debug("###################### mother fucker!!");
//
//        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
////        context.scan("org.apache.ranger");
//        context.setConfigLocation("org.apache.ranger.config");
//
////        XmlWebApplicationContext context = new XmlWebApplicationContext();
////        context.setConfigLocations(
////                "file:META-INF/applicationContext.xml",
////                "classpath:WEB-INF/conf/security-applicationContext.xml",
////                "file:META-INF/scheduler-applicationContext.xml"
////        );
//
//        servletContext.addListener(new ContextLoaderListener(context));
//        servletContext.addListener(new RequestContextListener());
//
//        Filter springSecurityFilterChain = new DelegatingFilterProxy("springSecurityFilterChain");
//        servletContext
//                .addFilter("springSecurityFilterChain", springSecurityFilterChain)
//                .addMappingForUrlPatterns(null, false, "/*");
//
//
//        ServletRegistration.Dynamic jerseyServlet = servletContext.addServlet("REST Service", new ServletContainer());
//        jerseyServlet.setInitParameter("jersey.config.server.provider.packages",
//                "org.apache.ranger.rest,org.apache.ranger.common,xa.rest");
//        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
//                "org.apache.ranger.service.filter.RangerRESTAPIFilter");
//        jerseyServlet.setInitParameter("jersey.config.server.provider.scanning.recursive", "true");
//
//        jerseyServlet.setLoadOnStartup(1);
//        jerseyServlet.addMapping("/service/*", "/login/*");
//
//    }
}
