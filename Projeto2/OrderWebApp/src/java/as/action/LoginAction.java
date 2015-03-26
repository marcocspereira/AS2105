package as.action;

import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import as.model.Bean;

public class LoginAction extends ActionSupport implements SessionAware {
    
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;

    @Override
    public void validate() {
        if (getBean().getLoginUser().length() == 0){
        addFieldError("bean.loginUser", getText("loginUser.required"));
        }
        if (getBean().getLoginPass().length() == 0){
            addFieldError("bean.loginPass", getText("loginPass.required"));
        }
    }

	@Override
	public String execute() throws Exception {

            if (getBean() == null)
            {
                addActionError(getText("login.expire"));
                return "login";
            }
            if (getBean().getLoginUser() != null && getBean().getLoginPass() != null && getBean().doLogin() > 0)
            {
                session.put("login", "true");
                addActionError(getText("login.ok"));
                System.out.println(SUCCESS);
                return SUCCESS;
            }
            addActionError(getText("login.error"));
            return ERROR;
	}

	public String logout() throws Exception {
            if (getBean().doLogout()> 0)
            {
                session.remove("login");
                session.clear();
                addActionError(getText("logout.ok"));
                return SUCCESS;
            }

                addActionError(getText("logout.error"));
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
