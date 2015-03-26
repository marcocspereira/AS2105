package as.action;

import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import as.model.Bean;
import as.model.CMD;

public class RegistAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    @Override
    public void validate() {
        if (getBean().getRegistFirstName().length() == 0) {
            addFieldError("bean.registFirstName", getText("registFirstName.required"));
        }
        if (getBean().getRegistLastName().length() == 0) {
            addFieldError("bean.registLastName", getText("registLastName.required"));
        }
        if (getBean().getRegistEmail().length() == 0) {
            addFieldError("bean.registEmail", getText("registEmail.required"));
        }
        if (getBean().getRegistUser().length() == 0) {
            addFieldError("bean.registUser", getText("registUser.required"));
        }
        if (getBean().getRegistPass().length() == 0) {
            addFieldError("bean.registPass", getText("registPass.required"));
        }
        if (getBean().getRegistPass2().length() == 0) {
            addFieldError("bean.registPass2", getText("registPass2.required"));
        }
        if (getBean().getRegistPass().compareTo(getBean().getRegistPass2()) != 0) {
            addFieldError("bean.registPass", getText("registPass.incoincident"));
        }
    }

    @Override
    public String execute() throws Exception {
        if (getBean() == null) {
            addActionError(getText("login.expire"));
            return "login";
        }
        if (getBean().doRegist() == CMD.OK) {
            addActionError(getText("regist.ok"));
            return SUCCESS;
        }

        addActionError(getText("regist.error"));
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