package ru.scrumdev.sample.server;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.scrumdev.sample.client.GreetingService;
import ru.scrumdev.sample.shared.Bid;
import ru.scrumdev.sample.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.List;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
@Service
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {
  @Autowired
  protected DealManager dealManager;

  public String greetServer(String input) throws IllegalArgumentException {
    // Verify that the input is valid.
    if (!FieldVerifier.isValidName(input)) {
      // If the input is not valid, throw an IllegalArgumentException back to
      // the client.
      throw new IllegalArgumentException(
          "Name must be at least 4 characters long");
    }

    String serverInfo = getServletContext().getServerInfo();
    String userAgent = getThreadLocalRequest().getHeader("User-Agent");

    // Escape data from the client to avoid cross-site script vulnerabilities.
    input = escapeHtml(input);
    userAgent = escapeHtml(userAgent);

    return "Hello, " + input + "!<br><br>I am running " + serverInfo
        + ".<br><br>It looks like you are using:<br>" + userAgent;
  }

  @Override
  public List<Bid> getSellBids() {
    return dealManager.getSellBids();
  }

  /**
   * Escape an html string. Escaping data received from the client helps to
   * prevent cross-site script vulnerabilities.
   *
   * @param html the html string to escape
   * @return the escaped string
   */
  private String escapeHtml(String html) {
    if (html == null) {
      return null;
    }
    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
            ">", "&gt;");
  }

  @Override
  public String processCall(String payload) throws SerializationException {
    try {
      Object handler = getBean(this.getClass());
      RPCRequest rpcRequest = RPC.decodeRequest(payload, handler.getClass(), this);
      onAfterRequestDeserialized(rpcRequest);
      return RPC.invokeAndEncodeResponse(handler, rpcRequest.getMethod(), rpcRequest.getParameters(), rpcRequest.getSerializationPolicy());
    } catch (IncompatibleRemoteServiceException ex) {
      log("An IncompatibleRemoteServiceException was thrown while processing this call.", ex);
      return RPC.encodeResponseForFailure(null, ex);
    }
  }

  /**
   * Look up a spring bean with the specified name in the current web
   * application context.
   *
   * @return the bean
   */
  protected Object getBean(Class beanClass) {
    WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    if (applicationContext == null) {
      throw new IllegalStateException("No Spring web application context found");
    }
    return applicationContext.getBean(beanClass);
  }
}
