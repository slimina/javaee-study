package cn.slimsmart.activemq.demo.spring.msgconverter;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

/**
 * spring 消息转换器
 * @author slimina
 *
 */
public class UserMessageConverter implements MessageConverter {

	public Message toMessage(Object object, Session session)  
            throws JMSException, MessageConversionException {  
        return session.createObjectMessage((Serializable) object);  
    }  
   
    public Object fromMessage(Message message) throws JMSException,  
            MessageConversionException {  
        ObjectMessage objMessage = (ObjectMessage) message;  
        return objMessage.getObject();  
    }
}
