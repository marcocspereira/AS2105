package as.action;

import as.model.Bean;
import as.model.CMD;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

public class LogoutAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;

        //get log4j
	private static final Logger logger = Logger.getLogger(LoginAction.class);
        
	@Override
	public String execute() throws Exception {
            if (getBean() == null)
            {
                addActionError(getText("login.expire"));
                return "login";
            }
            if (getBean().doLogout()== CMD.OK)
            {
                logger.info("Logout with "+getBean().getLoginUser()+".");
                addActionError(getText("logout.ok"));
                session.remove("login");
                session.clear();
                return SUCCESS;

            }
            addActionError(getText("logout.error"));
            logger.info("Logout with "+getBean().getLoginUser()+" fail.");
            return ERROR;
	}

	public Bean getBean() {
		return (Bean) session.get("bean");
	}

	public void setBean(Bean bean) {
		this.session.put("bean", bean);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
