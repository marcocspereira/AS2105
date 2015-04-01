<%-- 
    Document   : error
    Created on : Apr 1, 2015, 12:33:47 AM
    Author     : GonçaloSilva
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>EPE System</title>
    </head>
    <body>
            <s:if test="#session.login != 'true'">
      <jsp:forward page="index.jsp" />
      </s:if>
        
        <h4>You got an exception. Please throw it to someone who can handle it.</h4>
        <p><s:property value="exceptionStack" /></p>
    </body>
</html>