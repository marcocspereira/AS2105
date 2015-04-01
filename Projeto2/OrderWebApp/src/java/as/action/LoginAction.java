package as.action;

import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import as.model.Bean;
import org.apache.log4j.Logger;

public class LoginAction extends ActionSupport implements SessionAware {
    
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
        
        //get log4j
	private static final Logger logger = Logger.getLogger(LoginAction.class);

    @Override
    public void validate() {
        if (getBean() != null && getBean().getLoginUser().length() == 0){
            addFieldError("bean.loginUser", getText("loginUser.required"));
        }
        if (getBean() != null && getBean().getLoginUser().length() > 16){
            addFieldError("bean.loginUser", getText("loginUser.limits"));
        }
        if (getBean() != null && getBean().getLoginPass().length() == 0){
            addFieldError("bean.loginPass", getText("loginPass.required"));
        }
        if (getBean() != null && getBean().getLoginPass().length() > 32 || getBean().getLoginPass().length() < 4){
            addFieldError("bean.loginPass", getText("loginPass.limits"));
        }
    }

	@Override
	public String execute() throws Exception {
            if (getBean() == null)
            {
                addActionError(getText("login.expire"));
                logger.warn("Login of "+getBean().getLoginUser()+" was expired.");
                return "login";
            }
            if (getBean() != null && getBean().getLoginUser() != null && getBean().getLoginPass() != null && getBean().doLogin() > 0)
            {
                logger.info("Login with "+getBean().getLoginUser()+".");
                getBean().doLoadProducts();
                session.put("login", "true");
                session.put("user", getBean().getLoginUser());
//                System.out.println(session.get("user"));
                addActionMessage(getText("login.ok"));
                if (getBean() != null && getBean().getLoginUser().compareTo("root") == 0) return "root";
                return SUCCESS;
            }
            logger.error("Login with "+getBean().getLoginUser()+" fail.");
            addActionError(getText("login.error"));
            return ERROR;
	}

	public String logout() throws Exception {
            if (getBean().doLogout()> 0)
            {
                logger.info("Logout with "+getBean().getLoginUser()+".");
                session.remove("login");
                session.remove("user");
                session.clear();
                addActionError(getText("logout.ok"));
                return SUCCESS;
            }
            addActionError(getText("logout.error"));
            logger.error("Logout with "+getBean().getLoginUser()+" fail.");
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
