<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%--<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<script src="http://code.jquery.com/jquery-2.0.3.js"></script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

        <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0">
        <meta name="description" content="Projeto de AS!">
        <meta name="author" content="Gonçalo Silva Pereira">
        <!--<meta name="keywords" content="sd,distributed systems,university of coimbra">-->

        <!-- Bootstrap-->
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="bootstrap/css/style.css">

        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
        <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
        
        <meta name="robots" content="index, follow">
        <meta name="googlebot" content="index, follow">

        <title><s:text name="title.message"/></title>
    </head>
    <sb:head/>
    <body>
        <s:if test="#session.login == 'true'">
            <s:if test="#session.user == 'root'">
                <jsp:forward page="admin.jsp" />
            </s:if>
            <s:if test="#session.user != 'root'">
                <jsp:forward page="myaccount.jsp" />
            </s:if>
        </s:if>
        
        <s:if test="hasActionErrors()">
   <div class="errors">
      <s:actionerror/>
   </div>
</s:if>
        
          <div id="outer">
        <div id="inner" class="row">
             <div class="span4">
                 
                 <h4 id="oneAccount"> Login: </h4>
                 
                <s:form id="loginForm" name="login" action="LoginAction">
                    <s:textfield id="textfield" name="bean.loginUser" label="Username" size="20" placeholder="Introduza o username"/>
                    <s:password id="textfield" name="bean.loginPass" label="Password" size="20" placeholder="Introduza o username"/>
                    <s:submit cssClass="btn btn-medium btn-primary" key="btn.login" method="execute" align="center"/>
                </s:form>
            </div>
<!--            <div class="span5">
                <h4 > Registo: </h4>
                <s:form id="registForm" name="regist" action="RegistAction">
                    <s:textfield id="textfield" name="bean.registEmail" label="Email" size="50" placeholder="Introduza o email"/>
                    <s:textfield id="textfield" name="bean.registUser" label="Username" size="20" placeholder="Introduza o username"/>
                    <s:textfield id="textfield" name="bean.registFirstName" label="Firstname" size="25" placeholder="Introduza o primeiro nome"/>
                    <s:textfield id="textfield" name="bean.registLastName" label="Lastname" size="25" placeholder="Introduza o último nome"/>
                    <s:textfield id="textfield" name="bean.registAddress" label="Address" size="100" placeholder="Introduza a morada"/>
                    <s:textfield id="textfield" name="bean.registPhone" label="Phone" size="20" placeholder="Introduza o telefone"/>
                    <s:password id="textfield" name="bean.registPass" label="Password" size="20" placeholder="Introduza a password"/>
                    <s:password id="textfield" name="bean.registPass2" label="Password" size="20" placeholder="Confirme a password"/>
                    <s:submit cssClass="btn btn-medium btn-primary" key="btn.regist" method="execute" align="center"/>
                </s:form>
            </div>-->
        </div>
    </div>
    </body>
</html>

