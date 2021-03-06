package com.joshlong.spring.messaging.hornetq.config;

import com.joshlong.spring.messaging.hornetq.HornetQConnectionFactoryFactory;
import com.joshlong.spring.util.config.JoshLongNamespaceUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * This configures a no-frills {@link javax.jms.ConnectionFactory} for JBoss's HornetQ message broker.
 *
 * This, in tandem with the META-INF/spring.(handlers|schemas) files, teaches Spring how to handle a given namespace.
 *
 * @author <a href="mailto:josh@joshlong.com">Josh Long</a>
 */
public class HornetQNamespaceHandler extends NamespaceHandlerSupport {

    private static final String PACKAGE_NAME = "com.joshlong.spring.messaging.hornetq";

    public void init() {
        registerBeanDefinitionParser("connection-factory", new HornetQConnectionFactoryFactoryBeanDefinitionParser());
    }

    /**
     * This handles the task of configuring the recipient.
     */
    private static class HornetQConnectionFactoryFactoryBeanDefinitionParser extends AbstractBeanDefinitionParser {

        @Override
        protected AbstractBeanDefinition parseInternal(final Element element, final ParserContext parserContext) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition( HornetQConnectionFactoryFactory.class.getName());

            JoshLongNamespaceUtils.setValueIfAttributeDefined(builder, element, "host", "host");
            JoshLongNamespaceUtils.setValueIfAttributeDefined(builder, element, "backup-host", "backupHost");
            JoshLongNamespaceUtils.setValueIfAttributeDefined(builder, element, "port", "port");
            JoshLongNamespaceUtils.setValueIfAttributeDefined(builder, element, "backup-port", "backupPort");

            JoshLongNamespaceUtils.setValueIfAttributeDefined(builder, element, "reconnect-attempts", "reconnectAttempts");
            JoshLongNamespaceUtils.setValueIfAttributeDefined(builder, element, "failover-on-server-shutdown", "failoverOnServerShutdown");

            return builder.getBeanDefinition();
        }
    }

}
