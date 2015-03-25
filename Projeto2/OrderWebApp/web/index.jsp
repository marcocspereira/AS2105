<%@ taglib prefix="s" uri="/struts-tags"%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<script src="http://code.jquery.com/jquery-2.0.3.js"></script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

        <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0">
        <meta name="description" content="Projeto de AS!">
        <meta name="author" content="Gonçalo Silva Pereira">
        <!--<meta name="keywords" content="sd,distributed systems,university of coimbra">-->

        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
        <link href="bootstrap/dist/css/bootstrap.css"	rel="stylesheet" media="screen">
        <link href="bootstrap/dist/css/font-awesome.min.css" rel="stylesheet" media="screen">
        <link href="bootstrap/dist/css/style.css" rel="stylesheet" media="screen">
        <!--<link href="img/favicon.ico" rel="shortcut icon">-->
        <meta name="robots" content="index, follow">
        <meta name="googlebot" content="index, follow">

        <title><s:text name="title.message"/></title>
    </head>
    <sb:head/>
    <body>
        
          <div id="outer">
        <div id="inner" class="row">
             <div class="span4">
                 
                 <h4 id="oneAccount"> Login: </h4>
                 
                <s:form id="loginForm" name="login" action="login">
                    <s:textfield id="textfield" name="direstrutsBean.loginUser" label="Username" size="20" placeholder="Introduza o username"/>
                    <s:password id="textfield" name="direstrutsBean.loginPass" label="Password" size="20" placeholder="Introduza o username"/>
                    <s:submit cssClass="btn btn-medium btn-primary" key="btn.login" method="execute" align="center"/>
                </s:form>
            </div>
            <div class="span5">
                <h4 > Registo: </h4>

                <s:form action="regist">
                    <s:textfield id="textfield" name="direstrutsBean.registName" label="Name" size="20" placeholder="Introduza o nome"/>
                    <s:textfield id="textfield" name="direstrutsBean.registEmail" label="Email" size="20" placeholder="Introduza o email"/>
                    <s:textfield id="textfield" name="direstrutsBean.registUser" label="Username" size="20" placeholder="Introduza o username"/>
                    <s:password id="textfield" name="direstrutsBean.registPass" label="Password" size="20" placeholder="Introduza a password"/>
                    <s:password id="textfield" name="direstrutsBean.registPass2" label="Password" size="20" placeholder="Confirme a password"/>
                    <s:submit cssClass="btn btn-medium btn-primary" key="btn.regist" method="execute" align="center"/>

                </s:form>
            </div>
        </div>
    </div>
    </body>
</html>

