<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%--<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0">
        <meta name="description" content="Projeto de AS!">
        <meta name="author" content="Marco Pereira">
        <title><s:text name="title.myaccount"/></title>

        <!-- Bootstrap-->
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="bootstrap/css/style.css">

        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
        <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>

    </head>
    <sb:head/>

    <body>
        
        <s:if test="#session.login != 'true'">
            <jsp:forward page="index.jsp" />
        </s:if>

        <nav class="navbar navbar-default navbar-static-top"  role="navigation">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">EPE</a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse navbar-menu" id="bs-example-navbar-collapse-1">
                  <ul class="nav navbar-nav navbar-right">
                            <li><c:out value="${bean.loginUser}"></c:out></li>
                            <li>
                            <s:form id="logoutForm" name="logout" action="LogoutAction">
                                <s:submit cssClass="btn btn-default btn-danger" key="btn.logout" method="execute" align="center"/>
                            </s:form>
                        </li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>

        <div>
            <br>
            <div role="tabpanel">
                <!-- Nav tabs -->
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active">
                        <a href="#trees" aria-controls="trees" role="tab" data-toggle="tab">Trees</a>
                    </li>
                    <li role="presentation">
                        <a href="#seeds" aria-controls="seeds" role="tab" data-toggle="tab">Seeds</a>
                    </li>
                    <li role="presentation">
                        <a href="#shrubs" aria-controls="shrubs" role="tab" data-toggle="tab">Shrubs</a>
                    </li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <s:form id="ordersForm" name="order" action="OrderAction">
                        <div role="tabpanel" class="tab-pane active" id="trees">                    
                            <%--
                            <c:forEach var="arvore" items="${bean.trees}">
                                <s:checkbox name="arvores" value="${arvore.product_code}" label="${arvore.description}" />
                            </c:forEach>
                            --%>
                        </div>
                        <div role="tabpanel" class="tab-pane" id="seeds">
                            <%--
                            <c:forEach var="semente" items="${bean.seeds}">
                                <s:checkbox name="sementes" value="${semente.product_code}" label="${semente.description}" />
                            </c:forEach>
                            --%>
                        </div>
                        <div role="tabpanel" class="tab-pane" id="shrubs">
                            <%--
                            <c:forEach var="arbusto" items="${bean.shrubs}">
                                <s:checkbox name="arbustos" value="${arbusto.product_code}"  label="${arbusto.description}" />
                            </c:forEach>
                            --%>
                        </div>
                    </s:form>
                </div>
            </div>
        </div>

    </body>
</html>
