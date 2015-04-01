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
        if (getBean() != null && getBean().getRegistFirstName() != null && getBean().getRegistFirstName().length() == 0) {
            addFieldError("bean.registFirstName", getText("registFirstName.required"));
        }
        if (getBean() != null && getBean().getRegistLastName() != null && getBean().getRegistLastName().length() == 0) {
            addFieldError("bean.registLastName", getText("registLastName.required"));
        }
        if (getBean() != null && getBean().getRegistEmail() != null && getBean().getRegistEmail().length() == 0) {
            addFieldError("bean.registEmail", getText("registEmail.required"));
        }
        if (getBean() != null && getBean().getRegistUser() != null && getBean().getRegistUser().length() == 0) {
            addFieldError("bean.registUser", getText("registUser.required"));
        }
        if (getBean() != null && getBean().getRegistUser() != null && getBean().getLoginUser().length() > 16) {
            addFieldError("bean.registUser", getText("registUser.limits"));
        }
        if (getBean() != null && getBean().getRegistAddress() != null && getBean().getRegistAddress().length() == 0) {
            addFieldError("bean.registAddress", getText("registAddress.required"));
        }
        if (getBean() != null && getBean().getRegistPhone() != null && getBean().getRegistPhone().length() == 0) {
            addFieldError("bean.registPhone", getText("registPhone.required"));
        }
        if (getBean() != null && getBean().getRegistPass() != null && getBean().getRegistPass().length() == 0) {
            addFieldError("bean.registPass", getText("registPass.required"));
        }
        if (getBean() != null && getBean().getRegistPass2() != null && getBean().getRegistPass2().length() == 0) {
            addFieldError("bean.registPass2", getText("registPass2.required"));
        }
        if (getBean() != null && getBean().getRegistPass() != null && getBean().getRegistPass().compareTo(getBean().getRegistPass2()) != 0) {
            addFieldError("bean.registPass", getText("registPass.incoincident"));
        }
    }

    @Override
    public String execute() throws Exception {
        if (getBean() == null) {
            addActionError(getText("login.expire"));
            System.out.println("RegistAction = login");
            return "login";
        }
        if (getBean().doRegist() == CMD.OK) {
            addActionMessage(getText("regist.ok"));
            getBean().setRegistAddress("");
            getBean().setRegistEmail("");
            getBean().setRegistFirstName("");
            getBean().setRegistLastName("");
            getBean().setRegistPass("");
            getBean().setRegistPass2("");
            getBean().setRegistPhone("");
            getBean().setRegistUser("");
            System.out.println("RegistAction = SUCCESS");
            return SUCCESS;
        }

        addActionError(getText("regist.error"));
        System.out.println("RegistAction = ERROR");
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
