/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2012, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.resourceresolver;

import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.util.Validate;

/**
 * <p>
 *   Implementation of {@link IResourceResolver} that resolves
 *   resources as URLs:
 * </p>
 * <p>
 *   <tt><pre>
 *      try {
 *          final URL url = new URL(resourceName);
 *          return url.openStream();
 *      } catch (final Exception e1) {
 *          return null;
 *      }
 *   </pre></tt>
 * </p>
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 1.0
 *
 */
public final class UrlResourceResolver 
        implements IResourceResolver {

    
    private static final Logger logger = LoggerFactory.getLogger(UrlResourceResolver.class);
    
    public static final String NAME = "URL";

    

    public UrlResourceResolver() {
        super();
    }
    
    
    public String getName() {
        return NAME; 
    }
    
    
    public InputStream getResourceAsStream(final TemplateProcessingParameters templateProcessingParameters, final String resourceName) {
        Validate.notNull(resourceName, "Resource name cannot be null");
        
        try {
            final URL url = new URL(resourceName);
            return url.openStream();
        } catch (final Exception e) {
            if (logger.isDebugEnabled()) {
                if (logger.isTraceEnabled()) {
                    logger.trace(
                            String.format(
                                    "[THYMELEAF][%s][%s] Resource \"%s\" could not be resolved. This can be normal as " +
                                    "maybe this resource is not intended to be resolved by this resolver. " +
                                    "Exception is provided for tracing purposes: ", 
                                    TemplateEngine.threadIndex(), templateProcessingParameters.getTemplateName(),
                                    resourceName),
                            e);
                } else {
                    logger.debug(
                            String.format(
                                    "[THYMELEAF][%s][%s] Resource \"%s\" could not be resolved. This can be normal as " +
                                    "maybe this resource is not intended to be resolved by this resolver. " +
                                    "Exception message is provided: %s: %s", 
                                    TemplateEngine.threadIndex(), templateProcessingParameters.getTemplateName(),
                                    resourceName, e.getClass().getName(), e.getMessage()));
                }
            }
            return null;
        }
        
    }
    
}
